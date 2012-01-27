package org.team4159.boths.template;

public class ParseException extends Exception
{
	public ParseException ()
	{
		super ();
	}
	
	public ParseException (String msg)
	{
		super (msg);
	}

	public ParseException (String msg, int line, int column)
	{
		super (msg + " (line " + line + ", column " + column + ")");
	}
}
