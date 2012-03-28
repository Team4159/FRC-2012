package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AnalogUltrasonic;

public class UltrasonicModule extends Module
{
	private final AnalogUltrasonic backSensor = new AnalogUltrasonic(HWPorts.AnalogInput.ULTRASONIC_BACK);
	
	public double getBackDistance()
	{
		return backSensor.getDistanceInInches(); 
	}

	public void runOperator()
	{
		/*
		if(CameraStickModule.getInstance().isGetSensor())
		{
			DriverStationModule.getInstance().printToDriverStation(5, "back distance is : " + getBackDistance() + " inches");
		}
		*/
	}
	private static UltrasonicModule instance;
	public static synchronized UltrasonicModule getInstance ()
	{
		if (instance == null)
			instance = new UltrasonicModule ();
		return instance;
	}
}
