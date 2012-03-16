package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveModule extends Module
{
	private final RobotDrive drive = PIDModule.getInstance ().createDrive ();
	private double startLocation;
	
	private DriveModule ()
	{
		essential = true;
		drive.setExpiration (0.20);
	}
	
	public void drive (double move, double rotate)
	{
		drive.arcadeDrive (move, rotate);
	}
	
	public void stop ()
	{
		drive.stopMotor ();
	}
	
	public void runDisabled ()
	{
		drive.stopMotor ();
	}
	
	public void enterAutonomous ()
	{
		EncoderModule em = EncoderModule.getInstance();
		em.getLeftEncoder().reset();
		em.getRightEncoder().reset ();
	}
	
	public void runAutonomous ()
	{
		long elapsed = ModuleController.getModeElapsedTime ();
		if (elapsed > 12500)
		{
			EncoderModule em = EncoderModule.getInstance();
			double displacement = (em.getLeftEncoder().getDistance() + em.getRightEncoder().getDistance()) / 2.0;
			System.out.println(displacement);
			if (displacement < 2.6)
				drive.arcadeDrive (-0.4, -.01);
			else
				drive.arcadeDrive (0, 0);
		}
	}
	
	public void runOperator ()
	{
		DriveStickModule dsm = DriveStickModule.getInstance ();
		//if (dsm.isBridgeManipButtonPressed ())
		//	drive.stopMotor ();
		//else
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
