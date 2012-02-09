package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT;

public class CameraModule extends Module
{
	private final AxisCamera camera = AxisCamera.getInstance ("10.41.59.11");
	
	private CameraModule ()
	{
		camera.writeCompression (30);
		camera.writeResolution (ResolutionT.k320x240);
	}
	
	private static CameraModule instance;
	public static synchronized CameraModule getInstance ()
	{
		if (instance == null)
			instance = new CameraModule ();
		return instance;
	}
}
