package org.team4159.robot;

import org.team4159.robot.parts.AbsoluteTimer;
import org.team4159.robot.modules.*;
import edu.wpi.first.wpilibj.RobotBase;

public class Entry extends RobotBase
{
	public static final boolean DEBUG = false;
	
	private AbsoluteTimer disabledTimer = new AbsoluteTimer (5);
	private AbsoluteTimer autonomousTimer = new AbsoluteTimer (5);
	private AbsoluteTimer operatorTimer = new AbsoluteTimer (5);
	
	public Entry ()
	{
		getWatchdog ().setEnabled (false);
		getWatchdog ().setExpiration (1.0);
		
		// set up modules
		configureModules ();
	}
	
	private void configureModules ()
	{
		// initialize user inputs and displays first
		ModuleController.addModule (DriveStickModule.getInstance ());
		ModuleController.addModule (CameraStickModule.getInstance ());
		ModuleController.addModule (DriverStationModule.getInstance());
//		
		// initialize sensors
		ModuleController.addModule (AccelerometerModule.getInstance ());
		ModuleController.addModule (CameraModule.getInstance ());
		ModuleController.addModule (EncoderModule.getInstance ());
		ModuleController.addModule (GyroModule.getInstance ());
		ModuleController.addModule (UltrasonicModule.getInstance ());
		ModuleController.addModule (TargetRecognitionModule.getInstance());
		
		// initialize drive
		ModuleController.addModule (DriveModule.getInstance ());
		
		// initialize motor passthroughs
		ModuleController.addModule (PIDModule.getInstance ());
		
		// initialize motors
		ModuleController.addModule (MotorModule.getInstance ());
		ModuleController.addModule (CameraServoModule.getInstance ());
		ModuleController.addModule (BridgeManipModule.getInstance());
		ModuleController.addModule (BallLauncherModule.getInstance ());
		ModuleController.addModule (BallPickUpModule.getInstance ());
	}
	
	public void startCompetition ()
	{
		System.out.println ("Entering main code.");
		
		try {
			getWatchdog ().setEnabled (false);
			while (true)
			{
				if (isDisabled())
				{
					runMode (ModuleController.MODE_DISABLED, disabledTimer);
				}
				else if (isAutonomous())
				{
					runMode (ModuleController.MODE_AUTONOMOUS, autonomousTimer);
				}
				else
				{
					runMode (ModuleController.MODE_OPERATOR, operatorTimer);
				}
				getWatchdog ().feed ();
			}
		} catch (Throwable t) {
			System.err.println ("SEVERE ERROR: " + t);
			DriverStationModule.getInstance().printToDriverStation(5, t.toString ());
			t.printStackTrace ();
			getWatchdog ().kill ();
		}
	}
	
	private void runMode (int mode, AbsoluteTimer timer)
	{
		timer.startDelayedCode ();
		ModuleController.runMode (mode);
		timer.endDelayedCode ();
	}
}
