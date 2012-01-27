package org.team4159.boths;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Vector;
import org.team4159.boths.util.LimitingInputStream;
import org.team4159.boths.util.StringUtils;
import com.sun.squawk.io.BufferedReader;

public class Request
{
	private static final int MAX_REQUEST_SIZE = 4096;
	
	public final String method;
	public final String fullPath;
	public final String version;
	
	public final String path;
	public final String queryString;
	
	private final Hashtable headers = new Hashtable ();
	
	private final Hashtable singleParams = new Hashtable ();
	private final Hashtable multiParams = new Hashtable ();
	
	public Request (InputStream is) throws RequestException
	{
		BufferedReader rr = new BufferedReader (
			new InputStreamReader (
				new LimitingInputStream (is, MAX_REQUEST_SIZE)
			), 1
		);
		
		// parse request line and headers
		{
			String firstLine;
			try {
				firstLine = rr.readLine ();
			} catch (IOException e) {
				e.printStackTrace ();
				throw new RequestException ("failed to read request line");
			}
			
			String[] firstLineElements = StringUtils.splitByWholeSeparator (firstLine, " ");
			if (firstLineElements.length != 3)
				throw new RequestException ("wrong number of elements in first line of HTTP request");
			
			method = firstLineElements[0];
			fullPath = firstLineElements[1];
			version = firstLineElements[2];
			
			if (method.length () == 0 || fullPath.length () == 0 || version.length () == 0)
				throw new RequestException ("bad HTTP first line");
			if (fullPath.charAt (0) != '/')
				throw new RequestException ("HTTP path does not begin with /");
			
			for (;;)
			{
				String headerLine;
				
				try {
					headerLine = rr.readLine ();
				} catch (IOException e) {
					e.printStackTrace ();
					throw new RequestException ("failed to read header line");
				}
				
				if (headerLine == null) // premature death
					throw new RequestException ("headers terminated prematurely");
				if (headerLine.length () == 0) // end of headers
					break;
				
				int separatorLocation = headerLine.indexOf (": ");
				if (separatorLocation == -1)
					throw new RequestException ("separator not found in header entry");
				
				String key = headerLine.substring (0, separatorLocation);
				String value = headerLine.substring (separatorLocation + 2);
				
				headers.put (key, value);
			}
		}
		
		// parse the path
		{
			int qsep = fullPath.indexOf ('?');
			if (qsep == -1)
			{
				path = fullPath;
				queryString = "";
			}
			else
			{
				path = fullPath.substring (0, qsep);
				queryString = fullPath.substring (qsep + 1);
			}
		}
		
		// parse the query string
		{
			String[] fragments = StringUtils.splitByWholeSeparator (queryString, "&");
			for (int i = 0; i < fragments.length; i++)
			{
				String fragment = fragments[i];
				if (fragment.length () == 0)
					continue;
				
				int eq = fragment.indexOf ('=');
				String key, value;
				
				if (eq != -1)
				{
					key = fragment.substring (0, eq);
					value = fragment.substring (eq + 1);
				}
				else
				{
					key = fragment;
					value = "";
				}
				
				key = StringUtils.urlUnquote (key);
				value = StringUtils.urlUnquote (value);
				
				singleParams.put (key, value);
				
				Vector vec = (Vector) multiParams.get (key);
				if (vec == null)
					multiParams.put (key, vec = new Vector ());
				vec.addElement (value);
			}
		}
	}
}
