package org.team4159.boths.template;

public class StringUtils
{
	public static String htmlEscape (String input)
	{
		StringBuffer output = new StringBuffer ();
		
		int len = input.length ();
		for (int i = 0; i < len; i++)
		{
			char c = input.charAt (i);
			switch (c)
			{
				case '"':
					output.append ("&quot;");
					break;
				case '&':
					output.append ("&amp;");
					break;
				case '\'':
					output.append ("&apos;");
					break;
				case '<':
					output.append ("&lt;");
					break;
				case '>':
					output.append ("&gt;");
					break;
				default:
					output.append (c);
					break;
			}
		}
		
		return output.toString ();
	}
	
	public static void locate (String str, int pos, int[] out)
	{
		if (out.length != 2)
			throw new IllegalArgumentException ("out must have a length of 2");
		if (pos >= str.length ())
			pos = str.length () - 1;
		
		int line = 1;
		int col = 0;
		
		for (int i = 0; i < pos; i++)
		{
			char c = str.charAt (i + 1);
			if (c == '\n')
			{
				line++;
				col = 0;
			}
			else
			{
				col++;
			}
		}
		
		if (col == 0)
			col = 1;
		
		out[0] = line;
		out[1] = col;
	}
}
