package org.usfirst.frc4159.robotPartsTemplates;

import edu.wpi.first.wpilibj.SpeedController;

public class PrintSpeedController implements SpeedController
{
	private final SpeedController sub;
	private final int row;
	private final String name;
	
	public PrintSpeedController (SpeedController sub, int row, String name)
	{
		this.sub = sub;
		this.row = row;
		this.name = name;
	}

	public void pidWrite (double output)
	{
		set (output);
	}

	public double get ()
	{
		return sub.get ();
	}

	public void set (double speed, byte syncGroup)
	{
		set (speed);
	}

	public void set (double speed)
	{
		//DriverStationModule.getInstance ().printToDriverStation (row, name + ": " + speed);
		sub.set (speed);
	}

	public void disable ()
	{
		set (0);
	}
}