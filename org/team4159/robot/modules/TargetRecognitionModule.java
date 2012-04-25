package org.team4159.robot.modules;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import com.sun.squawk.util.SquawkVector;

public class TargetRecognitionModule extends Module
{
	public static class Target
	{
		public final double x;
		public final double y;
		public final double z;
		
		private Target (double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public void runOperator(){
		
		//print sqrt (y^2 + z^2)
		
	}
	private class Task extends Thread
	{
		public void run ()
		{
			Datagram datagram;
			int max;
			
			try {
				datagram = connection.newDatagram (max = connection.getMaximumLength ());
			} catch (IOException e) {
				return;
			}
			
			outer:
			while (true)
			{
				datagram.reset ();
				datagram.setLength (max);
				
				try {
					connection.receive (datagram);
				} catch (IOException e1) {
					try {
						Thread.sleep (500);
					} catch (InterruptedException e2) {}
					continue;
				}
				
				tmp.removeAllElements ();
				while (datagram.getOffset () < datagram.getLength ())
				{
					long x, y, z;
					try {
						x = datagram.readLong ();
						y = datagram.readLong ();
						z = datagram.readLong ();
					} catch (IOException e) {
						continue outer;
					}
					
					tmp.addElement (new Target (x / 1000., y / 1000., z / 1000.));
				}
				
				synchronized (task)
				{
					SquawkVector old = points;
					points = tmp;
					tmp = old;
				}
			}
		}
	}
	
	private SquawkVector points = new SquawkVector (), tmp = new SquawkVector ();
	private DatagramConnection connection;
	private final Task task;
	
	private TargetRecognitionModule ()
	{
		try {
			connection = (DatagramConnection) Connector.open ("datagram://:2228");
		} catch (IOException e) {
			System.err.println ("Unable to initialize target recognition client!");
			e.printStackTrace ();
		}
		
		task = new Task ();
		task.start ();
	}
	
	public Target[] getTargets ()
	{
		synchronized (task)
		{
			int sz = points.size ();
			Target[] ret = new Target[sz];
			for (int i = 0; i < sz; i++)
				ret[i] = (Target) points.elementAt (i);
			return ret;
		}
	}
	
	private static TargetRecognitionModule instance;
	public static synchronized TargetRecognitionModule getInstance ()
	{
		if (instance == null)
			instance = new TargetRecognitionModule ();
		return instance;
	}
}
