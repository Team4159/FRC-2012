package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.Servo;

public class CameraServoModule extends Module
{
	private final Servo horzServo = new Servo (HWPorts.Digital_Sidecar.PWM.CAMERA_HORIZONTAL_SERVO);
	private final Servo vertServo = new Servo (HWPorts.Digital_Sidecar.PWM.CAMERA_VERTICAL_SERVO);
	
	public void runOperator ()
	{
		CameraStickModule csm = CameraStickModule.getInstance ();
		horzServo.set ((csm.getHorizontal () + 1) / 2);
		vertServo.set ((csm.getVertical () + 1) / 2);
	}
	
	private static CameraServoModule instance;
	public static synchronized CameraServoModule getInstance ()
	{
		if (instance == null)
			instance = new CameraServoModule ();
		return instance;
	}
}
