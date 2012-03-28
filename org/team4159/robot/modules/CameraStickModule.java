package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AdjustedJoystick;
import edu.wpi.first.wpilibj.Joystick;

public class CameraStickModule extends Module
{
	private final AdjustedJoystick stick = new AdjustedJoystick (HWPorts.USBController.CAMERA_STICK);
	
	private double launcherFineTuneBase = 0;
	private double launcherFineTuneAdj = 0;
	private boolean launcherFineTuneDecPressed, launcherFineTuneIncPressed; 
	
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
	
	public double getLauncherSpeed ()
	{
		double currentBase = getRoller ();
		
		if (Math.abs (currentBase - launcherFineTuneBase) >= 0.002)
			launcherFineTuneAdj = 0;
		launcherFineTuneBase = currentBase;
		
		return Math.max (launcherFineTuneBase + launcherFineTuneAdj, 0.0);
	}
	
	public boolean isUpperPickupPressed ()
	{
		return stick.getTrigger();
	}
	
	public boolean isUpperPickupReversePressed ()
	{
		return stick.getRawButton (UPPER_PICKUP_REVERSE_1) || stick.getRawButton (UPPER_PICKUP_REVERSE_2);
	}
	
	public boolean isBridgeUpPressed () { return stick.getRawButton (BRIDGE_UP); }
	public boolean isBridgeDownPressed () { return stick.getRawButton (BRIDGE_DOWN); }
	
	public void runOperator ()
	{
		{
			boolean curdec = stick.getRawButton (DECREASE_FINE_TUNE); 
			if (curdec && !launcherFineTuneDecPressed)
				launcherFineTuneAdj -= 0.001;
			launcherFineTuneDecPressed = curdec;
		}
		
		{
			boolean curinc = stick.getRawButton (INCREASE_FINE_TUNE); 
			if (curinc && !launcherFineTuneIncPressed)
				launcherFineTuneAdj += 0.001;
			launcherFineTuneIncPressed = curinc;
		}
	}
	
	private static CameraStickModule instance;
	public static synchronized CameraStickModule getInstance ()
	{
		if (instance == null)
			instance = new CameraStickModule ();
		return instance;
	}

	private static final int BUTTON_UNUSED1          = 1;
	private static final int BRIDGE_DOWN             = 2;
	private static final int BRIDGE_UP               = 3;
	private static final int DECREASE_FINE_TUNE      = 4;
	private static final int INCREASE_FINE_TUNE      = 5;
	private static final int UNUSED_6                = 6;
	private static final int UNUSED_7                = 7;
	private static final int UPPER_PICKUP_REVERSE_1  = 8;
	private static final int UPPER_PICKUP_REVERSE_2  = 9;
	private static final int BUTTON_UNUSED10         = 10;
}
