package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import org.usfirst.frc4159.robotPartsTemplates.FixedEncoder;
import edu.wpi.first.wpilibj.Encoder;

public class EncoderModule extends Module {
	private final Encoder leftEncoder, rightEncoder;
	
	public EncoderModule(){
		leftEncoder = new FixedEncoder(HWPorts.DigitalSideCar.DigitalIO.LEFT_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.LEFT_ENCODER_B);
		rightEncoder = new FixedEncoder(HWPorts.DigitalSideCar.DigitalIO.RIGHT_ENCODER_A,HWPorts.DigitalSideCar.DigitalIO.RIGHT_ENCODER_B);
		configureEncoder(leftEncoder);
		configureEncoder(rightEncoder);
	}

	private void configureEncoder(Encoder e){
		e.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		e.setDistancePerPulse((6*.0254/180)*0.2611498357628683);
		e.reset();
		e.start();
	}
	private String distCalc(){
		double dist = (Math.abs(leftEncoder.getDistance()) + Math.abs(rightEncoder.getDistance())) / 2;
		return "Traveled: " + (int)dist + "in";
	}
	public Encoder getLeftEncoder(){ return leftEncoder;}
	public Encoder getRightEncoder(){ return rightEncoder;}
	
	public void runOperator(){
		double rate = (Math.abs(leftEncoder.getRate()) > Math.abs(rightEncoder.getRate()))? leftEncoder.getRate() : rightEncoder.getRate();
		DriverStationModule dsm = DriverStationModule.getInstance();
		dsm.printToDriverStation(HWPorts.DSLineNum.SPEED, "Speed: " +(rate*6*Math.PI));
		dsm.printToDriverStation(HWPorts.DSLineNum.MILEAGE, distCalc());
	}
	
	private static EncoderModule instance;
	public static synchronized EncoderModule getInstance(){
		if(instance == null)
			instance = new EncoderModule();
		return instance;
	}
}
