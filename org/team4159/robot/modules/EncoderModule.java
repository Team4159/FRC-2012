package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderModule extends Module
{
	private final Encoder leftEncoder = new Encoder (HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_A_SOURCE, HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_B_SOURCE);
	private final Encoder rightEncoder = new Encoder (HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_A_SOURCE, HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_B_SOURCE);
	
	private EncoderModule ()
	{
		Encoder[] encoders = new Encoder[]{leftEncoder, rightEncoder};
		for (int i = 0; i < encoders.length; i++)
		{
			Encoder e = encoders[i];
			e.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
			e.setDistancePerPulse((6*.0254/180)*0.2611498357628683); // 0.26... is the gear ratio from motor to wheel
			e.start ();
		}
	}
	
	public Encoder getLeftEncoder ()
	{
		return leftEncoder;
	}
	
	public Encoder getRightEncoder ()
	{
		return rightEncoder;
	}
	
	private static EncoderModule instance;
	public static synchronized EncoderModule getInstance ()
	{
		if (instance == null)
			instance = new EncoderModule ();
		return instance;
	}
}
