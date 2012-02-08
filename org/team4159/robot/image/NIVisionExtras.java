package org.team4159.robot.image;

import com.sun.cldc.jna.BlockingFunction;
import com.sun.cldc.jna.NativeLibrary;
import com.sun.cldc.jna.Pointer;
import com.sun.cldc.jna.TaskExecutor;
import com.sun.squawk.Klass;
import com.sun.squawk.VM;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class NIVisionExtras
{	
	private static final TaskExecutor taskExecutor;
	
	static {
		Klass klass = Klass.asKlass (NIVision.class);
		
		int i = 0;
		Object obj;
		do {
			obj = klass.getObject (i++);
		} while (!(obj instanceof TaskExecutor));
		
		taskExecutor = (TaskExecutor) obj;
	}
	
	//IMAQ_FUNC int IMAQ_STDCALL imaqThreshold(Image* dest, const Image* source, float rangeMin, float rangeMax, int useNewValue, float newValue);
	private static final BlockingFunction imaqThresholdFn = NativeLibrary.getDefaultInstance ().getBlockingFunction ("imaqThreshold");
	static { imaqThresholdFn.setTaskExecutor (taskExecutor); }
	public static final void threshold (Pointer dest, Pointer source, float rangeMin, float rangeMax, boolean useNewValue, float newValue) throws NIVisionException
	{
		assertCleanStatus (imaqThresholdFn.call6 (
			dest.address ().toUWord ().toPrimitive (),
			source.address ().toUWord ().toPrimitive (),
			VM.floatToIntBits (rangeMin),
			VM.floatToIntBits (rangeMax),
			useNewValue ? 1 : 0,
			VM.floatToIntBits (newValue)
		));
	}
	
	protected static void assertCleanStatus (int code) throws NIVisionException
	{
		if (code == 0)
			throw new NIVisionException (NIVision.getLastError ());
	}
}
