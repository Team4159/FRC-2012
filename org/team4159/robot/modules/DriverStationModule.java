package org.team4159.robot.modules;
import edu.wpi.first.wpilibj.DriverStationLCD;

public class DriverStationModule extends Module
{
	private DriverStationLCD ds;
	private static DriverStationModule instance;
	public void printToDriverStation(int startingColumn, String text )
	{
		ds.println(DriverStationLCD.Line.kMain6, startingColumn, text);
		ds.updateLCD();
	}
	public static synchronized DriverStationModule getInstance ()
	{
		if (instance == null)
			instance = new DriverStationModule ();
		return instance;
	}
}
