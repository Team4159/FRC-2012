package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.Relay;
import org.team4159.robot.HWPorts;

public class BallPickUpModule extends Module
{
	private final Relay ballPickUpFromGround = new Relay(HWPorts.Digital_Sidecar.Relay.BALL_PICKUP_GROUND);
	private final Relay ballPickUpToShoot = new Relay(HWPorts.Digital_Sidecar.Relay.BALL_PICKUP_SHOOT);
	
	public void runOperator()
	{
		CameraStickModule dsm = CameraStickModule.getInstance();
		if(dsm.isBallPickUpButtonPressed())
		{
			ballPickUpFromGround.set(Relay.Value.kOn);
		}
		else
		{
			ballPickUpFromGround.set(Relay.Value.kOff);
		}
		if(dsm.isBallPickUpToShootButtonPressed())
		{
			ballPickUpToShoot.set(Relay.Value.kOn);
		}
		else
		{
			ballPickUpToShoot.set(Relay.Value.kOff);
		}
	}
	
	private static BallPickUpModule instance;
	public static synchronized BallPickUpModule getInstance ()
	{
		if (instance == null)
			instance = new BallPickUpModule ();
		return instance;
	}
}
