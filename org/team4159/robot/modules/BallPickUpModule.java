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
		/*upperPickup.setDirection (Direction.kForward);
		lowerPickup.setDirection (Direction.kForward);*/
	}
	
	public void runAutonomous ()
	{
		if (ModuleController.getModeElapsedTime () > 2800)
		{
			lowerPickup.set(Relay.Value.kForward);
			upperPickup.set(Relay.Value.kForward);
		}
	}
	
	public void runOperator()
	{
		if(DriveStickModule.getInstance().isLowerPickupPressed())
		{
			lowerPickup.set(Relay.Value.kForward);
		}
		else
		{
			lowerPickup.set(Relay.Value.kOff);
		}
		if(CameraStickModule.getInstance().isUpperPickupPressed())
		{
			upperPickup.set(Relay.Value.kForward);
		}
		else
		{
			upperPickup.set(Relay.Value.kOff);
		}
		/*setLowerPickup (DriveStickModule.getInstance().isLowerPickupPressed ());
		setUpperPickup (CameraStickModule.getInstance().isUpperPickupPressed ());*/
	}
	
	/*public void setLowerPickup (boolean on)
	{
		lowerPickup.set (on ? Relay.Value.kOn : Relay.Value.kOff);
	}
	
	public void setUpperPickup (boolean on)
	{
		upperPickup.set (on ? Relay.Value.kOn : Relay.Value.kOff);
	}*/
	
	private static BallPickUpModule instance;
	public static synchronized BallPickUpModule getInstance ()
	{
		if (instance == null)
			instance = new BallPickUpModule ();
		return instance;
	}
}
