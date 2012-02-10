package org.team4159.robot.parts;

import edu.wpi.first.wpilibj.Encoder;

public class FixedEncoder extends Encoder
{
	public FixedEncoder (int aChannel, int bChannel)
	{
		super (aChannel, bChannel);
	}

	public FixedEncoder (int aChannel, int bChannel, boolean reverseDirection)
	{
		super (aChannel, bChannel, reverseDirection);
	}

	public FixedEncoder (int aChannel, int bChannel, boolean reverseDirection, EncodingType encodingType)
	{
		super (aChannel, bChannel, reverseDirection, encodingType);
	}
	
	public double getRate ()
	{
		double a = super.getRate ();
		double b = super.getRate ();
		return (Math.abs (a) > Math.abs (b)) ? a : b;
	}
}
