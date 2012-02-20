package org.team4159.robot.modules;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;

public class DriverStationModule extends Module
{
	private static final Line[] lines = {
		Line.kMain6,
		Line.kUser2,
		Line.kUser3,
		Line.kUser4,
		Line.kUser5,
		Line.kUser6
	};
	
	private DriverStationLCD lcd = DriverStationLCD.getInstance ();
	
	public void printToDriverStation (int row, String text)
	{
		StringBuffer sb = new StringBuffer (text);
		while (sb.length () < DriverStationLCD.kLineLength)
			sb.append (' ');
		
		lcd.println(lines[row], 1, sb.toString ());
		lcd.updateLCD ();
	}
	
	private static DriverStationModule instance;
	public static synchronized DriverStationModule getInstance ()
	{
		if (instance == null)
			instance = new DriverStationModule ();
		return instance;
	}
}
