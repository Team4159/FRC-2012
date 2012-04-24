package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.Gyro;
import org.team4159.robot.HWPorts;

public class GyroModule extends Module
{
	//private final Gyro sensor = new Gyro (HWPorts.AnalogInput.GYRO);
	private GyroModule ()
	{
		//sensor.reset ();
		//sensor.setSensitivity(.0125);//frc default value, once set, angle will be more stable.
	}
	/*public void enterOperator(){
		sensor.reset();
	}*/
	public void runOperator()
	{
		/*
		CameraStickModule csm = CameraStickModule.getInstance();
		if(csm.isGetGyroButtonPressed())
		{
			DriverStationModule.getInstance().printToDriverStation("" + sensor.getAngle());
		}
		*/
		
	}
	/*private int getAdjustedAngle(){
		double xAngle = (Math.toDegrees(sensor.getAngle()));
		double xFiltered = .9 * xFiltered + (1-.9)*xAngle;
		double error = xFiltered - sensor.getAngle();
		double angle = .98*((angle + (sensor.getAngle()+error))/2) + (1 - .98) * xAngle;
		return (int)angle;
	}*/
	private static GyroModule instance;
	public static synchronized GyroModule getInstance ()
	{
		if (instance == null)
			instance = new GyroModule ();
		return instance;
	}
}
