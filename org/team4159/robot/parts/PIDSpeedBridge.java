package org.team4159.robot.parts;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

public class PIDSpeedBridge implements SpeedController {
	
	private final PIDController controller;
	private final double speedCoefficient;
	
	public PIDSpeedBridge (PIDController controller, double speedCoefficient)
	{
		this.controller = controller;
		this.speedCoefficient = speedCoefficient;
	}

	public double get() {
		return controller.getSetpoint () / speedCoefficient;
	}

	public void set(double speed, byte syncGroup) {
		set (speed);
	}

	public void set(double speed) {
		controller.setSetpoint (speed * speedCoefficient);
	}

	public void disable() {
		controller.disable ();
	}

	public void pidWrite(double output) {
		set (output);
	}
}
