package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import org.usfirst.frc4159.robotPartsTemplates.FixedEncoder;
import org.usfirst.frc4159.robotPartsTemplates.PIDSetpointController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module {
	
	private static final double
		PID_KP = 0.00045,
		PID_KI = 0.00010, 
		PID_KD = 0.00002;
	
	private Encoder lowerEncoder, upperEncoder;
	private Victor lowerMotor, upperMotor;
	//private PIDController lowerController, upperController;
	
	private double speed;
	
	public BallLauncherModule(){
		
		lowerMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_LOWER);
		upperMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_UPPER);

		lowerEncoder = new Encoder (HWPorts.DigitalSideCar.DigitalIO.LOWER_LAUNCHER_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.LOWER_LAUNCHER_ENCODER_B);
		upperEncoder = new Encoder (HWPorts.DigitalSideCar.DigitalIO.UPPER_LAUNCHER_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.UPPER_LAUNCHER_ENCODER_B);
		
		lowerEncoder.setPIDSourceParameter (PIDSourceParameter.kRate);
		upperEncoder.setPIDSourceParameter (PIDSourceParameter.kRate);
		
		// 1 revolution / 180 marks per revolution
		lowerEncoder.setDistancePerPulse(1 / 180.);
		upperEncoder.setDistancePerPulse(1 / 180.);

		//lowerController = new PIDController (PID_KP, PID_KI, PID_KD, lowerEncoder, lowerMotor);
		//upperController = new PIDController (PID_KP, PID_KI, PID_KD, upperEncoder, upperMotor);
		
		lowerEncoder.start();
		upperEncoder.start();
		//lowerController.enable();
		//upperController.enable();

		speed = 0.0;
	}
	
	public void runAutonomous(){
		if(ModuleController.getModeElapsedTime() < 8000){
			set(.1915);
			DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.BALL_LAUNCH_PROGRESS, "Ball Launching In Progress!");
		}
		else if (ModuleController.getModeElapsedTime() < 15000){
			set(.1855);
			DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.BALL_LAUNCH_PROGRESS, "Ball Launching In Progress!");
		}
	}
	
	public void runOperator(){
		CamStickModule csm = CamStickModule.getInstance();
		speed = csm.getLauncherPower();
		set (speed);
	}

	public void set(double power){
		DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.LAUNCHER_POWER, "Power: " + speed);
		lowerMotor.set(-(power * 1.00));
		upperMotor.set(-(power * .50));
		
		//top spin faster than bottom
		//lowerMotor.set(-(power*.50));
		//upperMotor.set(-(power*1.00));
		DriverStationModule.getInstance().printToDriverStation(4, "RPM: " + lowerEncoder.getRate() * 60);
		
		//lowerController.setSetpoint (-(speed * 1.00 * 5000));
		//upperController.setSetpoint (-(speed * 0.50 * 5000));
		//lowerEncoder.pidGet();
	}
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance(){
		if(instance == null)
			instance = new BallLauncherModule();
		return instance;
	}
}
