package org.team4159.boths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class View
{
	public abstract Response getResponse (Request req, Route route);

	public void postResponse (Request req, Response res, InputStream is, OutputStream os) throws IOException
	{
		/* override this */
	}
}
