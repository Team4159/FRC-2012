package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module
{
	private final Victor upperMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_UPPER_MOTOR);
	private final Victor lowerMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_LOWER_MOTOR);
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance ()
	{
		if (instance == null)
			instance = new BallLauncherModule ();
		return instance;
	}
}