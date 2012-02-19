package org.team4159.robot.modules;
import edu.wpi.first.wpilibj.Victor;
import org.team4159.robot.HWPorts;

public class BridgeManipModule extends Module
{
	private Victor bridgeManipulator = new Victor(HWPorts.Digital_Sidecar.PWM.BRIDGE_MANIP_VICTOR);
	
	public void runOperator ()
	{
		DriveStickModule dsm = DriveStickModule.getInstance();
		if(dsm.isBridgeManipButtonPressed())
			bridgeManipulator.set(dsm.getJoystick ().getY ());
	}
	
	private static BridgeManipModule instance;
	public static synchronized BridgeManipModule getInstance ()
	{
		if (instance == null)
			instance = new BridgeManipModule ();
		return instance;
	}
}
