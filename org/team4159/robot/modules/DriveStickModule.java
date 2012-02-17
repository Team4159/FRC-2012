package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AdjustedJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class DriveStickModule extends Module
{
	private final AdjustedJoystick stick = new AdjustedJoystick (HWPorts.USBController.DRIVE_STICK);
	
	private DriveStickModule ()
	{
		stick.setMapping (Joystick.AxisType.kX, 1.0, 0.04, 0.6, 1.0);
		stick.setMapping (Joystick.AxisType.kY, 1.0, 0.04, 0.5, 1.0);
	}
	
	public Joystick getJoystick ()
	{
		return stick;
	}
	
	public boolean isVelocityPressed() { return stick.getRawButton(GET_CURRENT_VELOCITY);} // may need to change button number
	public boolean isDisablePIDPressed () { return stick.getRawButton (DISABLE_PID); }
	public double getMoveValue () { return stick.getY (); }
	public double getRotateValue () { return stick.getX (); }
	
	private static DriveStickModule instance;
	public static synchronized DriveStickModule getInstance ()
	{
		if (instance == null)
			instance = new DriveStickModule ();
		return instance;
	}
	private static final int BUTTON_UNUSED1          = 1;
	private static final int DISABLE_PID             = 2;
	private static final int BUTTON_UNUSED3          = 3;
	private static final int BUTTON_UNUSED4          = 4;
	private static final int BUTTON_UNUSED5          = 5;
	private static final int GET_CURRENT_VELOCITY    = 6;
	private static final int BUTTON_UNUSED7          = 7;
	private static final int BUTTON_UNUSED8          = 8;
	private static final int BUTTON_UNUSED9          = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
