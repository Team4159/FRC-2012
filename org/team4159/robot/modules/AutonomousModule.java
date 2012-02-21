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
			BallLauncherModule.getInstance ().set (0.8);
		}
		else if (elapsed < 13000)
		{
			BallPickUpModule.getInstance ().setUpperPickup (true);
		}
		else if (elapsed < 16000)
		{
			BallLauncherModule.getInstance ().set (0.0);
			BallPickUpModule.getInstance ().setUpperPickup (false);
			DriveModule.getInstance ().drive (0.2, 0.0);
		}
		else
		{
			if (UltrasonicModule.getInstance ().getBackDistance () < 0.60)
				DriveModule.getInstance ().stop ();
		}
	}
	
	private static AutonomousModule instance;
	public static synchronized AutonomousModule getInstance ()
	{
		if (instance == null)
			instance = new AutonomousModule ();
		return instance;
	}
}
