package org.team4159.robot.modules;

import com.sun.squawk.util.SquawkVector;
import edu.wpi.first.wpilibj.Watchdog;

public abstract class ModuleController
{
	public static final int MODE_DISABLED = 0;
	public static final int MODE_AUTONOMOUS = 1;
	public static final int MODE_OPERATOR = 2;
	
	private static final SquawkVector modules = new SquawkVector ();
	private static int lastMode = -1;
	
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
		boolean rawe = false;
		
		boolean modeChanged = false;
		if (lastMode != mode)
		{
			lastMode = mode;
			modeChanged = true;
		}
		
		for (int i = 0; i < sz; i++)
		{
			Module m = (Module) modules.elementAt (i);
			try {
				switch (mode)
				{
					case MODE_DISABLED:
						if (modeChanged)
							m.enterDisabled ();
						m.runDisabled ();
						break;
					case MODE_AUTONOMOUS:
						if (modeChanged)
							m.enterAutonomous ();
						m.runAutonomous ();
						break;
					case MODE_OPERATOR:
						if (modeChanged)
							m.enterOperator ();
						m.runOperator ();
						break;
					default:
						rawe = true;
						throw new IllegalArgumentException ("invalid mode: " + mode);
				}
			} catch (Throwable t) {
				if (rawe)
					throw (RuntimeException) t;
				
				System.err.println ("MODULE FAILURE: " + m.getClass ());
				t.printStackTrace ();
				
				if (m.essential)
				{
					System.err.println ("ESSENTIAL MODULE FAILED, SHUTTING DOWN");
					Watchdog.getInstance ().kill ();
					throw new RuntimeException ("ROBOT DIES NOW");
				}
				else
				{
					System.err.println ("NON-ESSENTIAL MODULE FAILED, TRYING TO CONTINUE");
					modules.removeElementAt (i--);
				}
			}
		}
	}
}