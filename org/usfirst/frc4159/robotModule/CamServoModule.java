package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import edu.wpi.first.wpilibj.Servo;

public class CamServoModule extends Module {

	private final Servo horzServo = new Servo(HWPorts.DigitalSideCar.PWM.CAMERA_HORIZ_SERVO);
	private final Servo vertServo = new Servo(HWPorts.DigitalSideCar.PWM.CAMERA_VERTI_SERVO);
	
	public void runOperator(){
		CamStickModule csm = CamStickModule.getInstance();
		horzServo.set((csm.getHorizontal()+1)/2);
		vertServo.set(((csm.getVertical() + 1) / 2) + .2);
	}
	
	private static CamServoModule instance;
	public static synchronized CamServoModule getInstance(){
		if(instance == null)
			instance = new CamServoModule();
		return instance;
	}
}
