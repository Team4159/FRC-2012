package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.robotPartsTemplates.PIDSetpointController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;

public class DrivePIDModule extends Module {

	private static final double PID_KP = 0.300, 
								PID_KI = 0.140, 
								PID_KD = 0.006,
								SPEED_COEFFICIENT = 6.0;
	
	private final PIDController leftPIDController, rightPIDController;
	private final PIDSetpointController leftPIDBridge, rightPIDBridge;
	
	private boolean pidEnabled = false;
	
	public DrivePIDModule(){
		essential = true;
		
		EncoderModule em= EncoderModule.getInstance();
		MotorModule mm = MotorModule.getInstance();
		
		leftPIDController = new PIDController(PID_KP, PID_KI, PID_KD,
				em.getLeftEncoder(), mm.getLeftMotor());
		rightPIDController = new PIDController(PID_KP, PID_KI, PID_KD,
				em.getRightEncoder(), mm.getRightMotor());
		
		leftPIDBridge = new PIDSetpointController(leftPIDController, mm.getLeftMotor(), SPEED_COEFFICIENT, true);
		rightPIDBridge = new PIDSetpointController(rightPIDController, mm.getRightMotor(), SPEED_COEFFICIENT, false);
	}
	
	public void enterAutonomous(){
		disablePID();
		MotorModule.getInstance().getLeftMotor().set(0.0);
		MotorModule.getInstance().getRightMotor().set(0.0);
	}
	public void enterOperator(){
		enablePID();
		resetPID();
	}
	public void runOperator(){
		enablePID();
	}
	
	private void enablePID(){
		if(pidEnabled)
			return;
		pidEnabled = true;
		leftPIDController.enable();
		rightPIDController.enable();
		resetPID();
	}
	private void disablePID(){
		if(!pidEnabled)
			return;
		pidEnabled = false;
		leftPIDController.disable();
		rightPIDController.disable();
		resetPID();
	}
	
	private void resetPID(){
		leftPIDController.reset();
		rightPIDController.reset();
	}
	
	public RobotDrive createDrive(){return new RobotDrive(leftPIDBridge, rightPIDBridge);}
	
	private static DrivePIDModule instance;
	public static synchronized DrivePIDModule getInstance(){
		if(instance == null)
			instance = new DrivePIDModule();
		return instance;
	}
}
