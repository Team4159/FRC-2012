package org.team4159.robot.www;

import org.team4159.boths.Server;
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
	
	public RobotServer ()
	{
		super (8080, 32);
		addRoute ("/", new TemplateView ("/www/root.html"));
		addRoute ("/broadcast/", broadcast);
		addRoute ("/files/", new DirectoryView ("/www/files/"), false);
	}
}