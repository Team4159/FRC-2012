package org.team4159.robot.parts;

import edu.wpi.first.wpilibj.SpeedController;

public class PrintSpeedController implements SpeedController
{
	private final SpeedController sub;
	private final String name;
	
	public PrintSpeedController (SpeedController sub, String name)
	{
		this.sub = sub;
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
		System.out.println (name + ": " + speed);
		sub.set (speed);
	}

	public void disable ()
	{
		set (0);
	}
}
