package org.usfirst.frc4159.robotModule;

import edu.wpi.first.wpilibj.KinectStick;

public class KinectModule extends Module {

	private KinectStick leftArm, rightArm;
	
	public KinectModule(){
		leftArm = new KinectStick(1);
		rightArm = new KinectStick(2);
	}
	
	public void runAutonomous(){
		DriveModule dm = DriveModule.getInstance();
		dm.drive(leftArm.getY()*.4, rightArm.getY()*.4);
	}
	
	
	
	private static KinectModule instance;
	public static synchronized KinectModule getInstance(){
		if(instance == null)
			instance = new KinectModule();
		return instance;
	}
}
