package org.team4159.boths;

import javax.microedition.io.StreamConnection;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

class ConnectionHandler
{
	static final Hashtable HTTP_STATUS_MESSAGES = new Hashtable ();
	private static void addSM (int code, String msg) { HTTP_STATUS_MESSAGES.put (new Integer (code), msg); }
	static {
		addSM (100, "Continue");
		addSM (101, "Switching Protocols");
		addSM (200, "OK");
		addSM (201, "Created");
		addSM (202, "Accepted");
		addSM (203, "Non-Authoritative Information");
		addSM (204, "No Content");
		addSM (205, "Reset Content");
		addSM (206, "Partial Content");
		addSM (300, "Multiple Choices");
		addSM (301, "Moved Permanently");
		addSM (302, "Found");
		addSM (303, "See Other");
		addSM (304, "Not Modified");
		addSM (305, "Use Proxy");
		addSM (307, "Temporary Redirect");
		addSM (400, "Bad Request");
		addSM (401, "Unauthorized");
		addSM (402, "Payment Required");
		addSM (403, "Forbidden");
		addSM (404, "Not Found");
		addSM (405, "Method Not Allowed");
		addSM (406, "Not Acceptable");
		addSM (407, "Proxy Authentication Required");
		addSM (408, "Request Timeout");
		addSM (409, "Conflict");
		addSM (410, "Gone");
		addSM (411, "Length Required");
		addSM (412, "Precondition Failed");
		addSM (413, "Request Entity Too Large");
		addSM (414, "Request-URI Too Long");
		addSM (415, "Unsupported Media Type");
		addSM (416, "Requested Range Not Satisfiable");
		addSM (500, "Internal Server Error");
		addSM (501, "Not Implemented");
		addSM (502, "Bad Gateway");
		addSM (503, "Service Unavailable");
		addSM (504, "Gateway Timeout");
		addSM (505, "HTTP Version Not Supported");
	}
	
	Server server;

	ConnectionHandler (Server server)
	{
		this.server = server;
	}

	void handleConnection (StreamConnection sc)
	{
		InputStream is;
		OutputStream os;
		
		try {
			is = sc.openInputStream ();
			os = sc.openOutputStream ();
		} catch (IOException e) {
			e.printStackTrace ();
			return;
		}
		
		Request req = new Request (is);
		View view;
		Response res;
		
		Vector routes = server.routes;
		int nroutes = routes.size ();
		Route route = null;
		
		for (int i = 0; i < nroutes; i++)
		{
			Route r = (Route) routes.elementAt (i);
			if (r.matches (req.getPath ()))
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
			view = (View) route.getViewClass ().newInstance ();
		} catch (Exception e) {
			e.printStackTrace ();
			sendError (500, os);
			return;
		}
		
		try {
			res = view.getResponse ();
		} catch (Throwable e) {
			e.printStackTrace ();
			sendError (500, os);
			return;
		}
		
		send (res, os);
	}

	void send (Response res, OutputStream os)
	{
		// TODO Auto-generated method stub
		
	}

	void sendError (int code, OutputStream os)
	{
		// TODO Auto-generated method stub
		String html = getClass ().getResourceAsStream ("error.html");
		
		Response res = new Response ("");
	}
	
}
