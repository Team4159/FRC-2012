package org.team4159.robot.parts;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

public class PIDSetpointController implements SpeedController {
	
	private final PIDController pidController;
	private final SpeedController speedController;
	private final boolean reversed;
	private final double speedCoefficient;
	
	public PIDSetpointController (PIDController controller, SpeedController speedController, double speedCoefficient)
	{
		this (controller, speedController, speedCoefficient, false);
	}
	
	public PIDSetpointController (PIDController pidController, SpeedController speedController, double speedCoefficient, boolean reversed)
	{
		this.pidController = pidController;
		this.speedController = speedController;
		this.reversed = reversed;
		this.speedCoefficient = reversed ? -speedCoefficient : speedCoefficient;
	}

	public double get() {
		if (pidController.isEnable ())
			return pidController.getSetpoint () / speedCoefficient;
		else
			return speedController.get ();
	}

	public void set(double speed, byte syncGroup) {
		set (speed);
	}

	public void set(double speed) {
		if (pidController.isEnable ())
			pidController.setSetpoint (speed * speedCoefficient);
		else
			speedController.set (speed);
	}

	public void disable() {
		pidController.disable ();
	}

	public void pidWrite(double output) {
		set (output);
	}
}
