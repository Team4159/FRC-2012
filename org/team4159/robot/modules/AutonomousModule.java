package org.team4159.robot.modules;

public class AutonomousModule extends Module
{
	private long startTime;
	
	private AutonomousModule ()
	{
	}
	
	public void enterAutonomous ()
	{
		startTime = System.currentTimeMillis ();
	}
	
	public void runAutonomous ()
	{
		long elapsed = System.currentTimeMillis () - startTime;
		if (elapsed < 3000)
		{
			BallLauncherModule.getInstance ().setAngle (calculateAngle ());
		}
	}
	
	private double calculateAngle ()
	{
		return 45.0;
	}
	
	private static AutonomousModule instance;
	public static synchronized AutonomousModule getInstance ()
	{
		if (instance == null)
			instance = new AutonomousModule ();
		return instance;
	}
}
