package org.team4159.robot.www;

import org.team4159.boths.Server;
import org.team4159.boths.views.BidirectionalWebSocketView;
import org.team4159.boths.views.BidirectionalWebSocketView.BidirectionalWebSocket;
import org.team4159.boths.views.BroadcastWebSocketView;
import org.team4159.boths.views.DirectoryView;
import org.team4159.boths.views.TemplateView;

public class RobotServer extends Server
{
	public static class RootView extends TemplateView
	{
		public RootView () { super ("/www/root.html"); }
	}
	
	public final BroadcastWebSocketView broadcast = new BroadcastWebSocketView ();
	
	public final BidirectionalWebSocketView command = new BidirectionalWebSocketView () {
		public void handleBidirectionalWebSocket (BidirectionalWebSocket sock) {
			while (sock.isOpen ())
			{
				Message msg = sock.nextMessage ();
				String str = new String (msg.data);
			}
		}
	};
	
	public RobotServer ()
	{
		super (8081, 32);
		addRoute ("/", new TemplateView ("/www/root.html"));
		addRoute ("/broadcast/", broadcast);
		//addRoute ("/command/", command);
		addRoute ("/files/", new DirectoryView ("/www/files/"), false);
	}
}