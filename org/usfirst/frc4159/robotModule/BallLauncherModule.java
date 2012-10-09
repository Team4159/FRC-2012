package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module {
	
	private static final double OPERATOR_COEFFICIENT = 0.8;
	
	private final Victor upperMotor, lowerMotor;
	private double speed;
	
	public BallLauncherModule(){
		upperMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_UPPER);
		lowerMotor = new Victor(HWPorts.DigitalSideCar.PWM.BALL_LAUNCH_LOWER);
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
		set(speed);
	}

	public void set(double power){
		DriverStationModule.getInstance().printToDriverStation(HWPorts.DSLineNum.LAUNCHER_POWER, "Power: " + speed);
		lowerMotor.set(-(speed * 1.00));
		upperMotor.set(-(speed * 0.50));
	}
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance(){
		if(instance == null)
			instance = new BallLauncherModule();
		return instance;
	}
}
