package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import org.usfirst.frc4159.robotPartsTemplates.FixedEncoder;
import org.usfirst.frc4159.robotPartsTemplates.PIDSetpointController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module {
	
	private static final double
		PID_KP = 0.500, 
		PID_KI = 0.100, 
		PID_KD = 0.010;
	
	private PIDController lowerController, upperController;
	
	private double speed;
	
	public BallLauncherModule(){
		
		Victor lowerMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_LOWER);
		Victor upperMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_UPPER);
		
		lowerController = new PIDController (PID_KP, PID_KI, PID_KD,
			new FixedEncoder (HWPorts.DigitalSideCar.DigitalIO.LOWER_LAUNCHER_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.LOWER_LAUNCHER_ENCODER_B),
			lowerMotor);
		upperController = new PIDController (PID_KP, PID_KI, PID_KD,
			new FixedEncoder (HWPorts.DigitalSideCar.DigitalIO.UPPER_LAUNCHER_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.UPPER_LAUNCHER_ENCODER_B),
			upperMotor);

		speed = 0.0;
	}
	
	public void runAutonomous(){
		/*if(ModuleController.getModeElapsedTime() < 8000){
			set(.189);
			DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.BALL_LAUNCH_PROGRESS, "Ball Launching In Progress!");
		}
		else if (ModuleController.getModeElapsedTime() < 15000){
			set(.195);
			DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.BALL_LAUNCH_PROGRESS, "Ball Launching In Progress!");
		}*/
	}
	
	public void runOperator(){
		CamStickModule csm = CamStickModule.getInstance();
		speed = csm.getLauncherPower();
		set (speed);
	}

	public void set(double power){
		DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.LAUNCHER_POWER, "Power: " + speed);
		lowerController.setSetpoint (-(speed * 1.00));
		upperController.setSetpoint (-(speed * 0.50));
	}
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance(){
		if(instance == null)
			instance = new BallLauncherModule();
		return instance;
	}
}
