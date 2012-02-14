package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.AnalogUltrasonic;

public class UltrasonicModule extends Module
{
	private final AnalogUltrasonic frontSensor = new AnalogUltrasonic(HWPorts.AnalogInput.ULTRASONIC_FRONT);
	private final AnalogUltrasonic backSensor = new AnalogUltrasonic(HWPorts.AnalogInput.ULTRASONIC_BACK);
	private final AnalogUltrasonic leftSensor = new AnalogUltrasonic(HWPorts.AnalogInput.ULTRASONIC_LEFT);
	private final AnalogUltrasonic rightSensor = new AnalogUltrasonic(HWPorts.AnalogInput.ULTRASONIC_RIGHT);
	
	private static UltrasonicModule instance;
	public static synchronized UltrasonicModule getInstance ()
	{
		if (instance == null)
			instance = new UltrasonicModule ();
		return instance;
	}
}
