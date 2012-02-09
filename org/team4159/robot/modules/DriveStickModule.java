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
	
	public double getMoveValue () { return stick.getY (); }
	public double getRotateValue () { return stick.getX (); }
	
	private static DriveStickModule instance;
	public static synchronized DriveStickModule getInstance ()
	{
		if (instance == null)
			instance = new DriveStickModule ();
		return instance;
	}
}
