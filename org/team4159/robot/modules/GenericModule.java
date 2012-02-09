package org.team4159.robot.modules;

public class GenericModule extends Module
{
	private static GenericModule instance;
	public static synchronized GenericModule getInstance ()
	{
		if (instance == null)
			instance = new GenericModule ();
		return instance;
	}
}
