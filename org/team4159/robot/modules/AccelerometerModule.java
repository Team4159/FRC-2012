package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.SensorBase;

public class AccelerometerModule extends Module
{
	private final ADXL345_I2C accelerometer = new ADXL345_I2C (SensorBase.getDefaultDigitalModule (), ADXL345_I2C.DataFormat_Range.k16G);
	
	private static AccelerometerModule instance;
	public static synchronized AccelerometerModule getInstance ()
	{
		if (instance == null)
			instance = new AccelerometerModule ();
		return instance;
	}
}
