package org.team4159.boths.views;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.team4159.boths.Request;
import org.team4159.boths.Response;
import org.team4159.boths.util.Queue;

public class BroadcastWebSocketView extends BaseWebSocketView
{
	private final Hashtable threads = new Hashtable ();
	
	public void postResponse (Request req, Response res, InputStream is, OutputStream os) throws IOException
	{
		FragmentProcessor fp = new FragmentProcessor (is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		
		Thread thread = Thread.currentThread ();
		Queue queue = new Queue ();
		DataOutputStream dos = new DataOutputStream (os);
		
		threads.put (thread, queue);
		
		try {
			for (;;)
			{
				if (is.available () > 0)
				{
					switch (receiveMessageFromSocket (fp, baos).opcode)
					{
						case OPCODE_CLOSE:
							return;
						case OPCODE_PING:
							sendMessageToSocket (new Message (0xa, null), dos);
					}
				}
				
				Message msg = (Message) queue.poll ();
				if (msg != null)
					sendMessageToSocket (msg, dos);
			}
		} finally {
			threads.remove (thread);
		}
	}
	
	public void sendMessage (Message msg)
	{
		synchronized (threads)
		{
			Enumeration e = threads.elements ();
			while (e.hasMoreElements ())
				((Queue) e.nextElement ()).add (msg);
		}
	}
	
	public void sendMessage (byte[] data)
	{
		sendMessage (new Message (OPCODE_BINARY, data));
	}
	
	public void sendMessage (String str)
	{
		sendMessage (new Message (OPCODE_TEXT, str.getBytes ()));
	}
}