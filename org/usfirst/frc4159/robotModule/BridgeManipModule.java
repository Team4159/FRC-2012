package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import edu.wpi.first.wpilibj.Victor;

public class BridgeManipModule extends Module {
	
	private Victor bridgeManipulator;
	private double bridgeDownValue;
	
	public BridgeManipModule(){
		bridgeManipulator = new Victor(HWPorts.DigitalSideCar.PWM.BRIDGE_MANIP_VICTOR);
		bridgeDownValue = 0.2;
	}

	public void runOperator(){
		CamStickModule csm = CamStickModule.getInstance();
		if(csm.isBridgeUpPressed())
			bridgeManipulator.set(0.5);
		else if(csm.isBridgeDownPressed()){
			bridgeDownValue += .7* 5 / 1000;
			bridgeManipulator.set(-bridgeDownValue);
		}
		else{
			bridgeManipulator.set(0.0);
			bridgeDownValue = 0.2;
		}
	}
	
	private static BridgeManipModule instance;
	public static synchronized BridgeManipModule getInstance(){
		if(instance == null)
			instance = new BridgeManipModule();
		return instance;
	}
}
