package org.team4159.boths.views;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.team4159.boths.Request;
import org.team4159.boths.Response;

public class CommandWebSocketView extends BaseWebSocketView
{
	public static interface Handler
	{
		public Message processMessage (Message msg);
	}
	
	private Handler handler;
	
	public CommandWebSocketView ()
	{
		this (null);
	}
	
	public CommandWebSocketView (Handler handler)
	{
		this.handler = handler;
	}
	
	public void postResponse (Request req, Response res, InputStream is, OutputStream os) throws IOException
	{
		if (res.getStatusCode () != 101)
			return;
		
		FragmentProcessor fp = new FragmentProcessor (is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		DataOutputStream dos = new DataOutputStream (os);
		
		for (;;)
		{
			Message msg = receiveMessageFromSocket (fp, baos);
			
			switch (msg.opcode)
			{
				case OPCODE_CLOSE:
					break;
				case OPCODE_PING:
					sendMessageToSocket (new Message (OPCODE_PONG, null), dos);
				case OPCODE_PONG:
					continue;
			}
			
			Message rmsg = processMessage (msg);
			if (rmsg == null)
				continue;
			
			sendMessageToSocket (rmsg, dos);
			
			if (rmsg.opcode == OPCODE_CLOSE)
				break;
		}
	}
	
	public Message processMessage (Message msg)
	{
		if (handler != null)
			return handler.processMessage (msg);
		else
			return new Message (OPCODE_CLOSE, null);
	}
}