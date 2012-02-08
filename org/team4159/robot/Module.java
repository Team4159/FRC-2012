package org.team4159.robot;

import java.util.Hashtable;
import java.util.Vector;
import com.sun.squawk.util.Arrays;
import com.sun.squawk.util.Comparer;

public abstract class Module
{
	/*
	 * STATIC
	 */
	
	private static class ModuleVector extends Vector implements Comparer
	{
		public void sort ()
		{
			Arrays.sort (elementData, 0, elementCount, this);
		}

		public int compare (Object a, Object b)
		{
			return ((Module) a).priority - ((Module) b).priority;
		}
	}
	
	private static final Hashtable modules = new Hashtable ();
	private static final ModuleVector priorities = new ModuleVector ();
	
	public static synchronized void addModule (Module module)
	{
		String name = module.name;
		
		if (modules.containsKey (name))
			throw new IllegalArgumentException (name + " is already defined");
		else
			modules.put (name, module);
		
		priorities.addElement (module);
		priorities.sort ();
	}
	
	public static synchronized void runAllDisabled ()
	{
		int sz = priorities.size ();
		for (int i = 0; i < sz; i++)
			((Module) priorities.elementAt (i)).runDisabled ();
	}
	
	public static synchronized void runAllAutonomous ()
	{
		int sz = priorities.size ();
		for (int i = 0; i < sz; i++)
			((Module) priorities.elementAt (i)).runAutonomous ();
	}
	
	public static synchronized void runAllOperator ()
	{
		int sz = priorities.size ();
		for (int i = 0; i < sz; i++)
			((Module) priorities.elementAt (i)).runOperator ();
	}
	
	/*
	 * INSTANCE
	 */
	
	public final String name;
	public final int priority;
	
	protected Module (String name, int priority)
	{
		this.name = name;
		this.priority = priority;
	}
	
	public void runDisabled () {}
	public void runAutonomous () {}
	public void runOperator () {}
}