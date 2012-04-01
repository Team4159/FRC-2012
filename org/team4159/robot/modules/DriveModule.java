package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveModule extends Module
{
	private final static double FROZEN_COEFF = -1.8;
	
	private final RobotDrive drive = PIDModule.getInstance ().createDrive ();
	
	private boolean lastFrozen = false;
	private double leftFrozenPos, rightFrozenPos;
	
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
	
	/*
	public void enterAutonomous ()
	{
		EncoderModule em = EncoderModule.getInstance();
		em.getLeftEncoder().reset();
		em.getRightEncoder().reset ();
	}
	
	public void runAutonomous ()
	{
		long elapsed = ModuleController.getModeElapsedTime ();
		if (elapsed > 12000)
		{
			EncoderModule em = EncoderModule.getInstance();
			double displacement = (em.getLeftEncoder().getDistance() + em.getRightEncoder().getDistance()) / 2.0;
			System.out.println(displacement);
			if (displacement < 2.5){
				drive.arcadeDrive (-0.3, .01);
				DriverStationModule.getInstance ().printToDriverStation (0, "Driving Toward Bridge!");
			}
			else
				drive.arcadeDrive (0, 0);
		}
	}*/
	
	public void runOperator ()
	{
		DriveStickModule dsm = DriveStickModule.getInstance ();
		
		boolean frozen = dsm.isFrozen ();
		boolean lastFrozen = this.lastFrozen;
		this.lastFrozen = frozen;
		
		if (frozen)
		{
			EncoderModule em = EncoderModule.getInstance ();
			double leftCurrentPos = em.getLeftEncoder ().getDistance ();
			double rightCurrentPos = em.getRightEncoder ().getDistance ();
			
			if (!lastFrozen)
			{
				leftFrozenPos = leftCurrentPos;
				rightFrozenPos = rightCurrentPos;
			}
			
			drive.setLeftRightMotorOutputs (
				(leftCurrentPos - leftFrozenPos) * FROZEN_COEFF,
				(rightCurrentPos - rightFrozenPos) * FROZEN_COEFF
			);
		}
		else
		{
			//if (dsm.isBridgeManipButtonPressed ())
			//	drive.stopMotor ();
			//else
			if(dsm.fullPower())
			{
				drive.arcadeDrive(dsm.getMoveValue(),dsm.getRotateValue());
			}
			else
			{
				drive.arcadeDrive (dsm.getMoveValue ()*.75, dsm.getRotateValue ()*.75);
			}
		}
	}
	
	private static DriveModule instance;
	public static synchronized DriveModule getInstance ()
	{
		if (instance == null)
			instance = new DriveModule ();
		return instance;
	}
}
