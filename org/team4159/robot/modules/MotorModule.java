package org.team4159.robot.modules;

import org.team4159.robot.Entry;
import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.PrintSpeedController;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

public class MotorModule extends Module
{
	private static final boolean DISABLED = false;
	
	private final Jaguar leftMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.LEFT_MOTOR_JAGUAR);
	private final Jaguar rightMotor = new Jaguar (HWPorts.Digital_Sidecar.PWM.RIGHT_MOTOR_JAGUAR);
	
	private final SpeedController wrappedLeftMotor = new PrintSpeedController (leftMotor, 1, "LEFT");
	private final SpeedController wrappedRightMotor = new PrintSpeedController (rightMotor, 2, "RIGHT");
	
	private final SpeedController nullMotor = new SpeedController () {
		public void pidWrite (double output) {}
		public double get () { return 0; }
		public void set (double speed, byte syncGroup) {}
		public void set (double speed) {}
		public void disable () {}
	};
	
	private MotorModule ()
	{
		essential = true;
		leftMotor.enableDeadbandElimination (true);
		rightMotor.enableDeadbandElimination (true);
	}
	
	public SpeedController getLeftMotor ()
	{
		if (DISABLED)
			return nullMotor;
		return wrappedLeftMotor;
	}
	
	public SpeedController getRightMotor ()
	{
		if (DISABLED)
			return nullMotor;
		return wrappedRightMotor;
	}
	
	private static MotorModule instance;
	public static synchronized MotorModule getInstance ()
	{
		if (instance == null)
			instance = new MotorModule ();
		return instance;
	}
}