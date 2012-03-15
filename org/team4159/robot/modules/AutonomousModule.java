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
		/*long elapsed = System.currentTimeMillis () - startTime;
		DriverStationModule.getInstance().printToDriverStation(5," back dist :  " +UltrasonicModule.getInstance().getBackDistance() + " inches");
		if (elapsed < 3000)
		{
			double minVelocity = Math.sqrt((9.8*UltrasonicModule.getInstance().getBackDistance()*UltrasonicModule.getInstance().getBackDistance())/2*(Math.cos(45)*Math.cos(45))*(72-UltrasonicModule.getInstance().getBackDistance()*Math.tan(45)));
			double minPower = 3600*minVelocity / (1.85*7300*Math.PI);
			System.out.println("power is" + minPower);
			BallLauncherModule.getInstance ().set (.2);
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
		}*/
	}
	
	private static AutonomousModule instance;
	public static synchronized AutonomousModule getInstance ()
	{
		if (instance == null)
			instance = new AutonomousModule ();
		return instance;
	}
}
