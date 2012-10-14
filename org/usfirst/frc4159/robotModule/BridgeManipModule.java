package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;

public class BridgeManipModule extends Module {
	
	private Victor bridgeManipulator;
	private Servo bridgeLock;
	private double bridgeDownValue;
	
	public BridgeManipModule(){
		bridgeManipulator = new Victor(HWPorts.DigitalSideCar.PWM.BRIDGE_MANIP_VICTOR);
		bridgeLock = new Servo (HWPorts.DigitalSideCar.PWM.BRIDGE_LOCK_SERVO);
		bridgeDownValue = 0.4;
	}

	public void runOperator(){
		DriveStickModule dsm = DriveStickModule.getInstance();
		if(dsm.bridgeUp())
			bridgeManipulator.set(0.5);
		else if(dsm.bridgeDown()){
			bridgeDownValue += .7* 5 / 1000;
			bridgeManipulator.set(-bridgeDownValue);
		}
		else{
			bridgeManipulator.set(0.0);
			bridgeDownValue = 0.4;
		}
		/*CamStickModule csm = CamStickModule.getInstance();
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
		if(csm.isBridgeLocked()){
			DriverStationModule.getInstance().printToDriverStation(1,"BRIDGE LOCKED!!");
			bridgeLock.set(1);
		}
		else
			bridgeLock.set(0);
			*/
	}
	
	private static BridgeManipModule instance;
	public static synchronized BridgeManipModule getInstance(){
		if(instance == null)
			instance = new BridgeManipModule();
		return instance;
	}
}
