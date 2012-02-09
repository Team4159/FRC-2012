package org.team4159.robot;

import org.team4159.robot.parts.AbsoluteTimer;
import org.team4159.robot.www.RobotServer;
import org.team4159.robot.modules.*;
import edu.wpi.first.wpilibj.RobotBase;

public class Entry extends RobotBase
{
	private AbsoluteTimer disabledTimer = new AbsoluteTimer (5);
	private AbsoluteTimer autonomousTimer = new AbsoluteTimer (5);
	private AbsoluteTimer operatorTimer = new AbsoluteTimer (2);
	
	public Entry ()
	{
		getWatchdog ().setEnabled (false);
		
		// set up modules
		configureModules ();
		
		// start HTTP server
		new RobotServer ().start ();
	}
	
	private void configureModules ()
	{
		// initialize user inputs first
		ModuleController.addModule (DriveStickModule.getInstance ());
		ModuleController.addModule (CameraStickModule.getInstance ());
		
		// initialize sensors
		ModuleController.addModule (AccelerometerModule.getInstance ());
		ModuleController.addModule (CameraModule.getInstance ());
		ModuleController.addModule (EncoderModule.getInstance ());
		ModuleController.addModule (GyroModule.getInstance ());
		ModuleController.addModule (UltrasonicModule.getInstance ());
		
		// initialize drive
		ModuleController.addModule (DriveModule.getInstance ());
		
		// initialize motors
		ModuleController.addModule (MotorModule.getInstance ());
		ModuleController.addModule (CameraServoModule.getInstance ());
	}
	
	public void startCompetition ()
	{
		System.out.println ("Entering main code.");
		
		while (true)
		{
			try {
				if (isDisabled())
					runMode (ModuleController.MODE_DISABLED, disabledTimer);
				else if (isAutonomous())
					runMode (ModuleController.MODE_AUTONOMOUS, autonomousTimer);
				else
					runMode (ModuleController.MODE_OPERATOR, operatorTimer);
				getWatchdog ().feed ();
			} catch (Throwable t) {
				System.err.println ("SEVERE ERROR: " + t);
				t.printStackTrace ();
			}
		}
	}
	
	private void runMode (int mode, AbsoluteTimer timer)
	{
		timer.startDelayedCode ();
		ModuleController.runMode (mode);
		timer.endDelayedCode ();
	}
}
