/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4159;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import org.usfirst.frc4159.robotModule.*;
import org.usfirst.frc4159.robotPartsTemplates.AbsoluteTimer;

public class MainEntry extends RobotBase {

	public static final boolean DEBUG = false;
	private AbsoluteTimer disabledTimer = new AbsoluteTimer (5);
	private AbsoluteTimer autonomousTimer = new AbsoluteTimer (5);
	private AbsoluteTimer operatorTimer = new AbsoluteTimer (5);

	public MainEntry(){
		getWatchdog ().setEnabled (false);
		getWatchdog ().setExpiration (1.0);

		// set up modules
		configureModules ();
	}

	private void configureModules(){
		//initialize user inputs and display
		ModuleController.addModule(DriveStickModule.getInstance());
		ModuleController.addModule(CamStickModule.getInstance());
		ModuleController.addModule(DriverStationModule.getInstance());
		//ModuleController.addModule(DashboardModule.getInstance());
		
		//initialize onboard sensors
		ModuleController.addModule(CameraModule.getInstance());
		ModuleController.addModule(EncoderModule.getInstance());
		
		//initialize driving systems
		ModuleController.addModule(DriveModule.getInstance());
		ModuleController.addModule(DrivePIDModule.getInstance());
		ModuleController.addModule(MotorModule.getInstance());
	
		//other motor systems
		ModuleController.addModule(CamServoModule.getInstance());
		ModuleController.addModule(BridgeManipModule.getInstance());
		ModuleController.addModule(BallLauncherModule.getInstance());
		ModuleController.addModule(BallPickUpModule.getInstance());
		
		//initialize kinect control
		ModuleController.addModule(KinectModule.getInstance());
	}

	public void startCompetition() {
		System.out.println ("Entering main code.");

		try {
			getWatchdog ().setEnabled (false);
			while (true)
			{
				if (isDisabled())
				{
					runMode (ModuleController.MODE_DISABLED, disabledTimer);
					DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.ROBOT_STATUS, "DISABLED");
					DashboardModule.getInstance().updateDashboard();
				}
				else if (isAutonomous())
				{
					runMode (ModuleController.MODE_AUTONOMOUS, autonomousTimer);
					DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.ROBOT_STATUS, "AUTONOMOUS");
					DashboardModule.getInstance().updateDashboard();
				}
				else
				{
					runMode (ModuleController.MODE_OPERATOR, operatorTimer);
					DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.ROBOT_STATUS, "OPERATOR");
					DashboardModule.getInstance().updateDashboard();
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

	private void runMode(int mode, AbsoluteTimer timer){
		timer.startDelayedCode ();
		ModuleController.runMode (mode);
		timer.endDelayedCode ();
	}

}
