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
	private static GyroModule instance;
	public static synchronized GyroModule getInstance ()
	{
		if (instance == null)
			instance = new GyroModule ();
		return instance;
	}
}
