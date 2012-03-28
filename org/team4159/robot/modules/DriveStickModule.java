package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AdjustedJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class DriveStickModule extends Module
{
	private final Joystick stick = new Joystick(HWPorts.USBController.DRIVE_STICK);
	
	/*private DriveStickModule ()
	{
		stick.setMapping (Joystick.AxisType.kX, 1.0, 0.04, 0.3, .5);
		stick.setMapping (Joystick.AxisType.kY, 1.0, 0.04, 0.5, .5);
	}*/
	
	public Joystick getJoystick ()
	{
		return stick;
	}
	
	public boolean isVelocityPressed() { return stick.getRawButton(GET_CURRENT_VELOCITY);} // may need to change button number
	public boolean isDisablePIDPressed () { return stick.getRawButton(DISABLE_PID); }
	//public boolean isBridgeManipButtonPressed () { return stick.getRawButton (BRIDGE_MANIP); }
	public double getMoveValue () { return stick.getY (); }
	public double getRotateValue () { return stick.getX (); }

	public boolean isLowerPickupPressed ()
	{
		return stick.getTrigger();
	}
	public boolean fullPower()
	{
		return stick.getRawButton(FULL);
	}
	public boolean isLowerPickupReversePressed ()
	{
		return stick.getRawButton (LOWER_PICKUP_REVERSE_1) || stick.getRawButton (LOWER_PICKUP_REVERSE_2);
	}
	
	private static DriveStickModule instance;
	public static synchronized DriveStickModule getInstance ()
	{
		if (instance == null)
			instance = new DriveStickModule ();
		return instance;
	}
	private static final int BUTTON_UNUSED1          = 1;
	private static final int DISABLE_PID             = 2;
	private static final int FULL                    = 3;
	private static final int BUTTON_UNUSED_4         = 4;
	private static final int BUTTON_UNUSED5          = 5;
	private static final int GET_CURRENT_VELOCITY    = 6;
	private static final int BUTTON_UNUSED_7         = 7;
	private static final int LOWER_PICKUP_REVERSE_1  = 8;
	private static final int LOWER_PICKUP_REVERSE_2  = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
