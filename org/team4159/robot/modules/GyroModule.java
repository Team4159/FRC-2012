package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.Gyro;

public class GyroModule extends Module
{
	private final Gyro sensor = new Gyro (2);
	
	private GyroModule ()
	{
		sensor.reset ();
	}
	
	private static GyroModule instance;
	public static synchronized GyroModule getInstance ()
	{
		if (instance == null)
			instance = new GyroModule ();
		return instance;
	}
}
