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

	public boolean isBridgeManipButtonPressed()
	{
		System.out.println("birdge Manip pressed");
		return stick.getRawButton(BRIDGE_MANIP_ACTIVATOR);
	}

	public boolean isBallPickUpButtonPressed()
	{
		System.out.println("ball Pickup pressed");
		return stick.getRawButton(BALL_PICKUP_GROUND);
	}
	public boolean isBallPickUpToShootButtonPressed()
	{
		return stick.getRawButton(BALL_PICKUP_SHOOT);
	}
	public boolean isGetGyroButtonPressed()
	{
		return stick.getRawButton(GET_GYRO_VALUE);
	}
	public boolean isBallLauncherTriggerPressed()
	{
		return stick.getTrigger();
	}
	private static CameraStickModule instance;
	public static synchronized CameraStickModule getInstance ()
	{
		if (instance == null)
			instance = new CameraStickModule ();
		return instance;
	}

	private static final int BUTTON_UNUSED1          = 1;
	private static final int BUTTON_UNUSED2          = 2;
	private static final int BRIDGE_MANIP_ACTIVATOR  = 3;
	private static final int BALL_PICKUP_GROUND      = 4;
	private static final int BALL_PICKUP_SHOOT       = 5;
	private static final int GET_GYRO_VALUE          = 6;
	private static final int BUTTON_UNUSED7          = 7;
	private static final int BUTTON_UNUSED8          = 8;
	private static final int BUTTON_UNUSED9          = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
