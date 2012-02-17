package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module
{
	private final Victor upperMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_UPPER_MOTOR);
	private final Victor lowerMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_LOWER_MOTOR);
	
	public void BallLauncherModule()
	{
		upperMotor.enableDeadbandElimination(true);
		lowerMotor.enableDeadbandElimination(true);
	}
	public void runOperator()
	{
		CameraStickModule csm = CameraStickModule.getInstance();
		if(csm.isBallLauncherTriggerPressed())
		{
			lowerMotor.set(.2);
			upperMotor.set(csm.getVertical());
		}
	}
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance ()
	{
		if (instance == null)
			instance = new BallLauncherModule ();
		return instance;
	}
}
