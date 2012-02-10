package org.team4159.robot.modules;

import org.team4159.robot.www.RobotServer;

public class RobotServerModule extends Module
{
	private RobotServer server = new RobotServer ();
	
	private RobotServerModule ()
	{
		server.start ();
	}
	
	private static RobotServerModule instance;
	public static synchronized RobotServerModule getInstance ()
	{
		if (instance == null)
			instance = new RobotServerModule ();
		return instance;
	}
}
