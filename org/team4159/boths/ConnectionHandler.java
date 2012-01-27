package org.team4159.boths;

import javax.microedition.io.StreamConnection;
import org.team4159.boths.template.Template;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

class ConnectionHandler
{
	Vector routes;

	ConnectionHandler (Server server)
	{
		this.routes = server.routes;
	}
	
	void handleConnection (StreamConnection sc)
	{
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = sc.openInputStream ();
			os = sc.openOutputStream ();
			handleConnection (is, os);
		} catch (IOException e) {
			e.printStackTrace ();
			return;
		} finally {
			try {
				is.close ();
				os.close ();
			} catch (IOException e) {}
		}
	}
	
	void handleConnection (InputStream is, OutputStream os)
	{
		Request req;
		View view;
		Response res;
		
		try {
			req = new Request (is);
		} catch (RequestException e) {
			e.printStackTrace ();
			sendError (500, os);
			return;
		}
		
		int nroutes = routes.size ();
		Route route = null;
		
		for (int i = 0; i < nroutes; i++)
		{
			Route r = (Route) routes.elementAt (i);
			if (r.matches (req.path))
			{
				route = r;
				break;
			}
		}
		
		if (route == null)
		{
			sendError (404, os);
			return;
		}
		
		try {
			view = (View) route.getViewClass (req.path).newInstance ();
		} catch (Exception e) {
			e.printStackTrace ();
			sendError (500, os);
			return;
		}
		
		try {
			res = view.getResponse ();
		} catch (Throwable e) {
			System.err.println ("error while processing view");
			e.printStackTrace ();
			sendError (500, os);
			return;
		}
		
		send (res, os);
	}

	void send (Response res, OutputStream os)
	{
		try {
			res.writeResponseToOutputStream (os);
		} catch (Throwable e) {
			System.err.println ("failed to send response to client");
			e.printStackTrace ();
			return;
		}
	}

	void sendError (int code, OutputStream os)
	{
		String msg = (String) Response.HTTP_STATUS_MESSAGES.get (new Integer (code));
		if (msg == null)
			msg = "Unknown Error";
		
		Hashtable ht = new Hashtable ();
		ht.put ("status_code", new Integer (code));
		ht.put ("status_message", msg);
		
		Response res = Template.load (this, "error.html").renderToResponse (ht);
		res.setStatusCode (code);
		
		send (res, os);
	}
}