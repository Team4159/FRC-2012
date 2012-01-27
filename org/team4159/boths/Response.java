package org.team4159.boths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Hashtable;

/**
 * The {@link Response} class is returned by {@link View}s containing the headers and content to be
 * sent back to the client.
 * 
 * @author Team 4159
 */
public class Response extends ByteArrayOutputStream
{
	private final Hashtable headers = new Hashtable ();
	private final Hashtable headersRealKeys = new Hashtable ();
	
	/**
	 * A Writer that allows character-level writing to this Response.
	 */
	public final Writer writer;
	
	/**
	 * A PrintStream that writes to this Response.
	 */
	public final PrintStream printStream;
	
	/**
	 * Creates an empty response and sets the content type to text/html.
	 */
	public Response ()
	{
		this (null, "text/html");
	}

	/**
	 * Creates a response with a HTML string.
	 * 
	 * @param content
	 *           The HTML to be returned to the client.
	 */
	public Response (String content)
	{
		this (content, "text/html");
	}

	/**
	 * Creates a response with the specified content and content type.
	 * 
	 * @param content
	 *           The HTML to be returned to the client.
	 * 
	 * @param content_type
	 *           The MIME type of the returned content.
	 */
	public Response (String content, String content_type)
	{
		this.writer = new OutputStreamWriter (this);
		this.printStream = new PrintStream (this);
		
		if (content != null)
			try {
				writer.write (content);
			} catch (IOException e) {
				e.printStackTrace ();
			}
		
		if (content_type != null)
			setHeader ("Content-Type", content_type);
	}
	
	/**
	 * Sets an HTTP header on this response, overwriting it
	 * if it already exists.
	 * 
	 * @param key
	 * The key of the HTTP header.
	 * @param value
	 * The value of the HTTP header.
	 */
	public void setHeader (String key, String value)
	{
		String low = key.toLowerCase ();
		headers.put (low, value);
		headersRealKeys.put (low, key);
	}
	
	/**
	 * Gets an HTTP header from this request.
	 * 
	 * @param key
	 * The key of the HTTP header.
	 * 
	 * @return The value of the HTTP header, or null if it does not exist.
	 */
	public String getHeader (String key)
	{
		return (String) headers.get (key.toLowerCase ());
	}
	
	/**
	 * Deletes an HTTP headers from this request.
	 * 
	 * If this header does not already exist, nothing will happen.
	 * 
	 * @param key
	 * The key of the HTTP header to delete.
	 */
	public void deleteHeader (String key)
	{
		String low = key.toLowerCase ();
		headers.remove (low);
		headersRealKeys.remove (low);
	}
	
	/**
	 * Returns a string representation of this response instance.
	 */
	public String toString ()
	{
		return getClass ().getName () + "@" + Integer.toHexString (hashCode ());
	}
}