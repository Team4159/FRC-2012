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
		DriveStickModule dsm = DriveStickModule.getInstance();
		CameraStickModule csm = CameraStickModule.getInstance();
		lowerPickup.set (dsm.isLowerPickupPressed () ? Relay.Value.kOn : Relay.Value.kOff);
		upperPickup.set (csm.isUpperPickupPressed () ? Relay.Value.kOn : Relay.Value.kOff);

	}
	
	private static BallPickUpModule instance;
	public static synchronized BallPickUpModule getInstance ()
	{
		if (instance == null)
			instance = new BallPickUpModule ();
		return instance;
	}
}
