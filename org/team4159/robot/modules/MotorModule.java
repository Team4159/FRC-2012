package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

public class MotorModule extends Module
{
	private final Jaguar leftMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.LEFT_MOTOR_JAGUAR);
	private final Jaguar rightMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.RIGHT_MOTOR_JAGUAR);
	
	private MotorModule ()
	{
		leftMotor.enableDeadbandElimination (true);
		rightMotor.enableDeadbandElimination (true);
	}
	
	public SpeedController getLeftMotor ()
	{
		return leftMotor;
	}
	
	public SpeedController getRightMotor ()
	{
		return rightMotor;
	}
	
	private static MotorModule instance;
	public static synchronized MotorModule getInstance ()
	{
		if (instance == null)
			instance = new MotorModule ();
		return instance;
	}
}
