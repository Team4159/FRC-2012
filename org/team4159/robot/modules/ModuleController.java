package org.team4159.robot.modules;

import com.sun.squawk.util.SquawkVector;

public abstract class ModuleController
{
	public static final int MODE_DISABLED = 0;
	public static final int MODE_AUTONOMOUS = 1;
	public static final int MODE_OPERATOR = 2;
	
	private static final SquawkVector modules = new SquawkVector ();
	
	public static synchronized void addModule (Module module)
	{
		if (modules.contains (module))
			throw new IllegalStateException ("module " + module + " already added");
		else
			modules.addElement (module);
	}
	
	public static synchronized void runMode (int mode)
	{
		int sz = modules.size ();
		for (int i = 0; i < sz; i++)
		{
			Module m = (Module) modules.elementAt (i);
			switch (mode)
			{
				case MODE_DISABLED:
					m.runDisabled ();
					break;
				case MODE_AUTONOMOUS:
					m.runAutonomous ();
					break;
				case MODE_OPERATOR:
					m.runOperator ();
					break;
				default:
					throw new IllegalArgumentException ("invalid mode: " + mode);
			}
		}
	}
}