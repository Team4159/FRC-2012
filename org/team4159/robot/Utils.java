package org.team4159.robot;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class Utils
{
	public static boolean byteStringEquals (String a, byte[] b)
	{
		return byteStringEquals (a, b, b.length);
	}
	
	public static boolean byteStringEquals (String as, byte[] b, int bl)
	{
		if (as.length () != bl)
			return false;
		for (int i = 0; i < bl; i++)
			if ((int) as.charAt (i) != (int)(b[i] & 0xff))
				return false;
		return true;
	}
	
	public static byte[] readStreamUntil (InputStream i, int b) throws IOException
	{
		return readStreamUntil (i, b, true);
	}
	
	public static byte[] readStreamUntil (InputStream i, int b, boolean includeLast) throws IOException
	{
		int k;
		ByteArrayOutputStream o = new ByteArrayOutputStream ();
		
		do {
			k = i.read ();
			if (k == -1)
				throw new EOFException ();
			if (k != b || includeLast)
				o.write (k);
		} while (k != b);
		
		return o.toByteArray ();
	}
}
