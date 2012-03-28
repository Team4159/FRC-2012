package org.team4159.robot.modules;
import edu.wpi.first.wpilibj.Victor;
import org.team4159.robot.HWPorts;

public class BridgeManipModule extends Module
{
	private Victor bridgeManipulator = new Victor(HWPorts.Digital_Sidecar.PWM.BRIDGE_MANIP_VICTOR);
	private double bridgeDownValue = 0.2;
	
	public void runOperator ()
	{
		CameraStickModule csm = CameraStickModule.getInstance();
		if (csm.isBridgeUpPressed ())
		{
			bridgeManipulator.set (0.5);
		}
		else if (csm.isBridgeDownPressed ())
		{
			bridgeDownValue += 0.7 * 5 / 1000;
			bridgeManipulator.set (-bridgeDownValue);
		}
		else
		{
			bridgeManipulator.set (0.0);
			bridgeDownValue = 0.2;
		}
	}
	
	private static BridgeManipModule instance;
	public static synchronized BridgeManipModule getInstance ()
	{
		if (instance == null)
			instance = new BridgeManipModule ();
		return instance;
	}
}
