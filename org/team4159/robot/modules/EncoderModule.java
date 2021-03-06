package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import org.team4159.robot.parts.FixedEncoder;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderModule extends Module
{
	private final Encoder leftEncoder = new FixedEncoder (HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_A_SOURCE, HWPorts.Digital_Sidecar.DigitalIO.LEFT_DRIVE_ENCODER_B_SOURCE);
	private final Encoder rightEncoder = new FixedEncoder (HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_A_SOURCE, HWPorts.Digital_Sidecar.DigitalIO.RIGHT_DRIVE_ENCODER_B_SOURCE);
	
	private EncoderModule ()
	{
		configureEncoder (leftEncoder);
		configureEncoder (rightEncoder);
	}
	
	private void configureEncoder (Encoder e)
	{
		e.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		e.setDistancePerPulse((6*.0254/180)*0.2611498357628683); // 0.26... is the gear ratio from motor to wheel
		e.reset ();
		e.start ();
	}
	
	public Encoder getLeftEncoder ()
	{
		return leftEncoder;
	}
	
	public Encoder getRightEncoder ()
	{
		return rightEncoder;
	}
	
	public void runOperator ()
	{
		DriverStationModule dsm = DriverStationModule.getInstance ();
		dsm.printToDriverStation (6, "LENC: " + leftEncoder.getRate () + " RENC: " + rightEncoder.getRate());
	}
	
	private static EncoderModule instance;
	public static synchronized EncoderModule getInstance ()
	{
		if (instance == null)
			instance = new EncoderModule ();
		return instance;
	}
}
