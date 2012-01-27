package org.team4159.boths.template;

public class ParsingException extends Exception
{
	public ParsingException ()
	{
		super ();
	}
	
	public ParsingException (String msg)
	{
		super (msg);
	}

	public ParsingException (String msg, int line, int column)
	{
		super (msg + " (line " + line +", column " + column + ")");
	}
}
