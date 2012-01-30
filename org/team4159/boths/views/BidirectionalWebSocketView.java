package org.team4159.boths.views;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.team4159.boths.Request;
import org.team4159.boths.Response;

public class BidirectionalWebSocketView extends BaseWebSocketView
{
	public static interface Handler
	{
		public void handleBidirectionalWebSocket (BidirectionalWebSocket sock);
	}
	
	public class BidirectionalWebSocket
	{
		protected final Request request;
		protected final InputStream is;
		protected final OutputStream os;
		private final ByteArrayOutputStream ibaos;
		private final DataOutputStream dos;
		private final FragmentProcessor fp;
		
		private boolean open = true;
		
		BidirectionalWebSocket (Request req, InputStream is, OutputStream os)
		{
			this.request = req;
			this.is = is;
			this.os = os;
			this.ibaos = new ByteArrayOutputStream ();
			this.dos = new DataOutputStream (os);
			this.fp = new FragmentProcessor (is);
		}
		
		public boolean isOpen () { return open; }
		
		public boolean messageAvailable ()
		{
			try {
				return is.available () > 0;
			} catch (IOException e) {
				e.printStackTrace ();
				throw new RuntimeException (e.toString ());
			}
		}
		
		public Message nextMessage ()
		{
			Message msg = null;
			
			while (msg == null)
			{
				try {
					msg = receiveMessageFromSocket (fp, ibaos);
				} catch (IOException e) {
					e.printStackTrace ();
					throw new RuntimeException (e.toString ());
				}
				
				switch (msg.opcode)
				{
					case OPCODE_CLOSE:
						open = false;
						break;
					case OPCODE_PING:
						sendMessage (new Message (0xa, null));
						msg = null;
						break;
					case OPCODE_PONG:
						msg = null;
						break;
				}
			}
			
			return msg;
		}
		
		public void sendMessage (byte[] data)
		{
			sendMessage (new Message (OPCODE_BINARY, data));
		}
		
		public void sendMessage (String str)
		{
			sendMessage (new Message (OPCODE_TEXT, str.getBytes ()));
		}
		
		public void sendMessage (Message msg)
		{
			try {
				sendMessageToSocket (msg, dos);
			} catch (IOException e) {
				e.printStackTrace ();
				throw new RuntimeException (e.toString ());
			}
		}
	}
	
	private Handler handler;
	
	public BidirectionalWebSocketView ()
	{
		this (null);
	}
	
	public BidirectionalWebSocketView (Handler handler)
	{
		this.handler = handler;
	}
	
	public void postResponse (Request req, Response res, InputStream is, OutputStream os) throws IOException
	{
		if (res.getStatusCode () != 101)
			return;
		
		BidirectionalWebSocket sock = new BidirectionalWebSocket (req, is, os);
		handleBidirectionalWebSocket (sock);
	}

	public void handleBidirectionalWebSocket (BidirectionalWebSocket sock)
	{
		if (handler != null)
			handler.handleBidirectionalWebSocket (sock);
	}
}
