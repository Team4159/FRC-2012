package org.team4159.robot.www;

import org.team4159.boths.Server;
import org.team4159.boths.views.TemplateView;

public class RobotServer extends Server
{
	public static class RootView extends TemplateView
	{
		public RootView () { super ("/www/root.html"); }
	}
	
	public RobotServer ()
	{
		addRoute ("/", new RootView ());
	}
}