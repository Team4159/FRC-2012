package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import org.team4159.robot.HWPorts;

public class BallPickUpModule extends Module
{
	private final Relay upperPickup = new Relay(HWPorts.Digital_Sidecar.Relay.UPPER_PICKUP);
	private final Relay lowerPickup = new Relay(HWPorts.Digital_Sidecar.Relay.LOWER_PICKUP);
	
	private BallPickUpModule ()
	{
		upperPickup.setDirection (Direction.kForward);
		lowerPickup.setDirection (Direction.kForward);
	}
	
	public void runOperator()
	{
		CameraStickModule csm = CameraStickModule.getInstance();
		lowerPickup.set (csm.isLowerPickupPressed () ? Relay.Value.kOn : Relay.Value.kOff);
		upperPickup.set (csm.isUpperPickupPressed () ? Relay.Value.kOn : Relay.Value.kOff);
		
		/*if(dsm.isBallPickUpButtonPressed())
		{
			ballPickUpFromGround.set(Relay.Value.kOn);
			ds.printToDriverStation(0, "BALL PICKUP IN PROGRESS ");
		}
		else
		{
			ballPickUpFromGround.set(Relay.Value.kOff);
			ds.printToDriverStation(0, "BALL PICKUP OFF");
		}
		if(dsm.isBallPickUpToShootButtonPressed())
		{
			ballPickUpToShoot.set(Relay.Value.kOn);
			ds.printToDriverStation(1, "FEEDING BALL INTO THE LAUNCHER");
		}
		else
		{
			ballPickUpToShoot.set(Relay.Value.kOff);
			ds.printToDriverStation(1, "BALL PICKUP SYSTEM OCCUPIED");
		}*/
	}
	
	private static BallPickUpModule instance;
	public static synchronized BallPickUpModule getInstance ()
	{
		if (instance == null)
			instance = new BallPickUpModule ();
		return instance;
	}
}
