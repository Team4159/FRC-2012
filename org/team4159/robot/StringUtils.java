package org.team4159.robot;

public class StringUtils
{
	/**
	 * Represents a failed index search.
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That
	 * functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty (String str)
	{
		return str == null || str.length () == 0;
	}

	/**
	 * <p>
	 * Counts how many times the substring appears in the larger String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String input returns <code>0</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", null)  = 0
	 * StringUtils.countMatches("abba", "")    = 0
	 * StringUtils.countMatches("abba", "a")   = 2
	 * StringUtils.countMatches("abba", "ab")  = 1
	 * StringUtils.countMatches("abba", "xxx") = 0
	 * </pre>
	 * 
	 * @param str
	 *           the String to check, may be null
	 * @param sub
	 *           the substring to count, may be null
	 * @return the number of occurrences, 0 if either String is <code>null</code>
	 */
	public static int countMatches (String str, String sub)
	{
		if (isEmpty (str) || isEmpty (sub))
		{
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf (sub, idx)) != INDEX_NOT_FOUND)
		{
			count++;
			idx += sub.length ();
		}
		return count;
	}

	public static String[] splitByWholeSeparator (String str, String separator)
	{
		int separatorLength = separator.length ();
		
		String[] ret = new String[countMatches (str, separator) + 1];
		int i = 0;
		
		int start = 0;
		int end;
		
		for (;;)
		{
			end = str.indexOf (separator, start);
			if (end != -1)
			{
				ret[i++] = str.substring (start, end);
				start = end + separatorLength;
			}
			else
			{
				ret[i++] = str.substring (start);
				break;
			}
		}
		
		return ret;
	}
}
