package org.usfirst.frc4159.robotModule;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc4159.HWPorts;

public class DriveStickModule extends Module {

	private final Joystick stick;
	public DriveStickModule(){
		stick = new Joystick(HWPorts.USBController.DRIVE_STICK);
	}
	
	public Joystick getJoystick(){
		return stick;
	}
	
	public boolean isVelocityPressed(){ return stick.getRawButton(GET_CURRENT_VELOCITY);}
	public double getMoveValue(){ return stick.getX();}
	public double getRotateValue(){ return stick.getY();}
	public boolean isLowerPickupPressed(){ return stick.getTrigger();}
	public boolean isLowerPickupReversePressed(){ return stick.getRawButton(LOWER_PICKUP_REVERSE_1)|| stick.getRawButton(LOWER_PICKUP_REVERSE_2);}
	public boolean fullPower(){ return stick.getRawButton(FULL);}
	public boolean bridgeDrive(){ return stick.getRawButton(BRIDGEDRIVE);}
	public boolean bridgeUp(){ return stick.getRawButton(BRIDGE_UP);}
	public boolean bridgeDown() { return stick.getRawButton(BRIDGE_DOWN);}
	
	private static DriveStickModule instance;
	public static synchronized DriveStickModule getInstance(){
		if(instance == null)
			instance = new DriveStickModule();
		return instance;
	}
	
	private static final int BUTTON_UNUSED1          = 1;
	private static final int BRIDGE_UP	             = 2;
	private static final int BRIDGE_DOWN             = 3;
	private static final int BRIDGEDRIVE             = 4;
	private static final int FULL                    = 5;
	private static final int GET_CURRENT_VELOCITY    = 6;
	private static final int BUTTON_UNUSED_7         = 7;
	private static final int LOWER_PICKUP_REVERSE_1  = 8;
	private static final int LOWER_PICKUP_REVERSE_2  = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
