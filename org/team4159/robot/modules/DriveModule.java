package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveModule extends Module
{
	private final RobotDrive drive = PIDModule.getInstance ().createDrive ();
	
	private DriveModule ()
	{
		drive.setExpiration (0.20);
	}
	
	public void runDisabled ()
	{
		drive.stopMotor ();
	}
	
	public void runAutonomous ()
	{
		/* TO BE IMPLEMENTED */
	}
	
	public void runOperator ()
	{
		DriveStickModule dsm = DriveStickModule.getInstance ();
		drive.arcadeDrive (dsm.getMoveValue (), dsm.getRotateValue ());
	}
	
	private static DriveModule instance;
	public static synchronized DriveModule getInstance ()
	{
		if (instance == null)
			instance = new DriveModule ();
		return instance;
	}
}
