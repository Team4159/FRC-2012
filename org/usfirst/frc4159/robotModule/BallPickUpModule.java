package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;

public class BallPickUpModule extends Module {
	
	private final Relay upperPickup, lowerPickup;
	
	public BallPickUpModule(){
		upperPickup = new Relay(HWPorts.DigitalSideCar.Relay.UPPER_PICKUP);
		lowerPickup = new Relay(HWPorts.DigitalSideCar.Relay.LOWER_PICKUP);
	}

	public void runAutonomous(){
		if(ModuleController.getModeElapsedTime() > 3500)
			upperPickup.set(Relay.Value.kForward);
		if(ModuleController.getModeElapsedTime() > 7500)
			lowerPickup.set(Relay.Value.kForward);
	}
	public void runOperator(){
		if(DriveStickModule.getInstance().isLowerPickupPressed())
			lowerPickup.set(Relay.Value.kForward);
		else if(DriveStickModule.getInstance().isLowerPickupReversePressed())
			lowerPickup.set(Relay.Value.kReverse);
		else
			lowerPickup.set(Relay.Value.kOff);
		if(CamStickModule.getInstance().isUpperPickupPressed())
			upperPickup.set(Relay.Value.kForward);
		else if (CamStickModule.getInstance().isUpperPickupReversePressed())
			upperPickup.set (Relay.Value.kReverse);
		else
			upperPickup.set(Relay.Value.kOff);
	}
	
	private static BallPickUpModule instance;
	public static synchronized BallPickUpModule getInstance(){
		if(instance == null)
			instance = new BallPickUpModule();
		return instance;
	}
}
