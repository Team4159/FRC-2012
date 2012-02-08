package org.team4159.robot.parts;

import java.util.Hashtable;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Joystick;

public class AdjustedJoystick extends Joystick
{
	private class Mapping
	{
		private int axis;
		private double exponent;
		private double minimumInput;
		private double maximumOutputTriggerOff;
		private double maximumOutputTriggerOn;
		private double coefficient;
		
		public Mapping (AxisType axisType, double exponent, double minimumInput,
			double maximumOutputTriggerOff, double maximumOutputTriggerOn)
		{
			if (axisType == null)
				this.axis = -1;
			else
				this.axis = axisType.value;
			
			this.exponent = exponent;
			this.minimumInput = minimumInput;
			this.maximumOutputTriggerOff = maximumOutputTriggerOff;
			this.maximumOutputTriggerOn = maximumOutputTriggerOn;
			this.coefficient = 1 / executeRawFunction (1.0);
		}
		
		private double executeRawFunction (double val)
		{
			return MathUtils.pow (val - minimumInput, exponent);
		}
		
		public double executeFunction (double val)
		{
			double abs = Math.abs (val);
			if (abs <= minimumInput)
				return 0.0;
			
			double exec = executeRawFunction (abs) * coefficient;
			exec *= getTrigger () ? maximumOutputTriggerOn : maximumOutputTriggerOff;
			exec *= (val > 0 ? 1 : -1);
			return exec;
		}
	}
	
	private final Hashtable mappings = new Hashtable ();
	
	public AdjustedJoystick (int port)
	{
		super (port);
		setMapping (null, 1.0, 0.0, 1.0, 1.0);
	}
	
	public void setMapping (AxisType axisType, double exponent, double minimumInput,
		double maximumOutputTriggerOff, double maximumOutputTriggerOn)
	{
		Mapping mapping = new Mapping (axisType, exponent, minimumInput,
			maximumOutputTriggerOff, maximumOutputTriggerOn);
		mappings.put (new Integer (mapping.axis), mapping);
	}
	
	public double getX (Hand hand) { return processAxis (AxisType.kX, super.getX (hand)); }
	public double getY (Hand hand) { return processAxis (AxisType.kY, super.getY (hand)); }
	public double getZ (Hand hand) { return processAxis (AxisType.kZ, super.getZ (hand)); }
	
	private double processAxis (final AxisType axisType, double val)
	{
		int axis = axisType.value;
		
		Mapping mapping = (Mapping) mappings.get (new Integer (axis));
		if (mapping == null)
			mapping = (Mapping) mappings.get (new Integer (-1));
		
		return mapping.executeFunction (val);
	}
}
