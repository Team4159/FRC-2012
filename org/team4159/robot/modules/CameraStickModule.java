package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AdjustedJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class CameraStickModule extends Module
{
	private final AdjustedJoystick stick = new AdjustedJoystick (HWPorts.USBController.CAMERA_STICK);

	private CameraStickModule ()
	{
		stick.setMapping (null, 1.0, 0.04, 1.0, 1.0);
	}

	public Joystick getJoystick ()
	{
		return stick;
	}

	public double getHorizontal ()
	{
		return stick.getX ();
	}

	public double getVertical ()
	{
		return stick.getY ();
	}
	
	public double getRoller ()
	{
		return (1.0 - stick.getZ ()) / 2;
	}

	public boolean isUpperPickupPressed ()
	{
		return stick.getRawButton (UPPER_PICKUP);
	}
	
	public boolean isLowerPickupPressed ()
	{
		return stick.getRawButton (LOWER_PICKUP);
	}

	public boolean isBallLauncherTriggerPressed()
	{
		return stick.getTrigger();
	}
	
	public boolean isBridgeUpPressed () { return stick.getRawButton (BRIDGE_UP); }
	public boolean isBridgeDownPressed () { return stick.getRawButton (BRIDGE_DOWN); }
	
	public boolean isLockLauncherPressed () { return stick.getRawButton (LOCK_LAUNCHER); }
	public boolean isUnlockLauncherPressed () { return stick.getRawButton (UNLOCK_LAUNCHER); }
	
	private static CameraStickModule instance;
	public static synchronized CameraStickModule getInstance ()
	{
		if (instance == null)
			instance = new CameraStickModule ();
		return instance;
	}

	private static final int BUTTON_UNUSED1          = 1;
	private static final int LOWER_PICKUP            = 2;
	private static final int UPPER_PICKUP            = 3;
	private static final int LOCK_LAUNCHER           = 4;
	private static final int UNLOCK_LAUNCHER         = 5;
	private static final int BRIDGE_UP               = 6;
	private static final int BRIDGE_DOWN             = 7;
	private static final int BUTTON_UNUSED8          = 8;
	private static final int BUTTON_UNUSED9          = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
