package org.usfirst.frc4159.robotModule;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveModule extends Module {

	private final RobotDrive drive;
	private double startLocation;

	public DriveModule(){
		drive = DrivePIDModule.getInstance().createDrive();
		essential = true;
		drive.setExpiration(.2);
	}
	public void drive(double move,double rotate){ drive.arcadeDrive(move, rotate);}
	public void stop(){ drive.stopMotor();}
	public void runDisabled(){ drive.stopMotor();}

	public void runOperator(){
		DriveStickModule dsm = DriveStickModule.getInstance();
		if(dsm.fullPower())
		{
			drive.arcadeDrive(dsm.getMoveValue(),dsm.getRotateValue());
		}
		else if(dsm.bridgeDrive())
		{
			drive.arcadeDrive (dsm.getMoveValue ()*.6, dsm.getRotateValue ());
		}
		else{
			drive.arcadeDrive(dsm.getMoveValue()*.8, dsm.getRotateValue());
		}
	}

	private static DriveModule instance;
	public static synchronized DriveModule getInstance(){
		if(instance == null)
			instance = new DriveModule();
		return instance;
	}
}
