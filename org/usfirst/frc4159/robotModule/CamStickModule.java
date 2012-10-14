package org.usfirst.frc4159.robotModule;

import org.usfirst.frc4159.HWPorts;
import edu.wpi.first.wpilibj.Joystick;

public class CamStickModule extends Module {
	
	private final Joystick stick;
	private double fineTuneAdj;
	private boolean launcherFineTuneDecPressed, launcherFineTuneIncPressed; 
	
	public CamStickModule(){
		stick = new Joystick(HWPorts.USBController.CAMERA_STICK);
		fineTuneAdj = 0;
	}
	
	public Joystick getJoystick(){ return stick;}
	public double getHorizontal(){ return stick.getX();}
	public double getVertical(){ return stick.getY();}
	public double getRoller(){ return (1.0-stick.getZ())/2;}
	public double getLauncherPower(){return (getRoller() + fineTuneAdj);}
	public boolean isUpperPickupPressed(){ return stick.getTrigger();}
	public boolean isUpperPickupReversePressed(){ return stick.getRawButton(UPPER_PICKUP_REVERSE_1) || stick.getRawButton(UPPER_PICKUP_REVERSE_2);}
	public boolean isBridgeUpPressed(){ return stick.getRawButton(BRIDGE_UP);}
	public boolean isBridgeDownPressed(){ return stick.getRawButton(BRIDGE_DOWN);}
	public boolean isIncreaseTunePressed(){ return stick.getRawButton(INCREASE_FINE_TUNE);}
	public boolean isDecreaseTunePressed(){ return stick.getRawButton(DECREASE_FINE_TUNE);}
	public boolean isBridgeLocked(){ return stick.getRawButton(BRIDGE_LOCK);}

	public void runDisabled(){
		fineTuneAdj = 0;
	}
	public void runOperator(){
		{
			boolean curdec = isDecreaseTunePressed(); 
			if (curdec && !launcherFineTuneDecPressed)
				fineTuneAdj -= 0.001;
			launcherFineTuneDecPressed = curdec;
		}
		
		{
			boolean curinc = isIncreaseTunePressed(); 
			if (curinc && !launcherFineTuneIncPressed)
				fineTuneAdj += 0.001;
			launcherFineTuneIncPressed = curinc;
		}
	}
	
	private static final int BUTTON_UNUSED1          = 1;
	private static final int BRIDGE_DOWN             = 2;
	private static final int BRIDGE_UP               = 3;
	private static final int DECREASE_FINE_TUNE      = 4;
	private static final int INCREASE_FINE_TUNE      = 5;
	private static final int BRIDGE_LOCK             = 6;
	private static final int UNUSED_7                = 7;
	private static final int UPPER_PICKUP_REVERSE_1  = 8;
	private static final int UPPER_PICKUP_REVERSE_2  = 9;
	private static final int BUTTON_UNUSED10         = 10;
	
	private static CamStickModule instance;
	public static synchronized CamStickModule getInstance(){
		if(instance == null)
			instance = new CamStickModule();
		return instance;
	}
}
