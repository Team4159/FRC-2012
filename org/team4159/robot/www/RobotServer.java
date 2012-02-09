package org.team4159.robot.www;

import java.util.Hashtable;
import org.team4159.boths.Server;
import org.team4159.boths.views.BroadcastWebSocketView;
import org.team4159.boths.views.CommandWebSocketView;
import org.team4159.boths.views.DirectoryView;
import org.team4159.boths.views.TemplateView;

public class RobotServer extends Server
{
	public static class RootView extends TemplateView
	{
		public RootView () { super ("/www/root.html"); }
	}
	
	public final BroadcastWebSocketView broadcast = new BroadcastWebSocketView ();
	
	public final CommandWebSocketView command = new CommandWebSocketView () {
		
		public Message processMessage (Message msg)
		{
			String str = new String (msg.data);
			
			int eq = str.indexOf ('=');
			if (eq >= 0) // set
			{
				String key = str.substring (0, eq);
				String value = str.substring (eq + 1);
				try {
					setVar (key, value);
					return new Message ("success");
				} catch (Throwable t) {
					return new Message ("failure");
				}
			}
			else // get
			{
				try {
					return new Message (getVar (str));
				} catch (Throwable t) {
					return new Message ("");
				}
			}
		}
		
		public String getVar (String key)
		{
			return (String) table.get (key);
		}
		
		public void setVar (String key, String value)
		{
			table.put (key, value);
		}
		
	};
	
	public final Hashtable table = new Hashtable ();
	
	public RobotServer ()
	{
		super (8081, 32);
		addRoute ("/", new TemplateView ("/www/root.html"));
		addRoute ("/broadcast/", broadcast);
		addRoute ("/command/", command);
		addRoute ("/files/", new DirectoryView ("/www/files/"), false);
	}
}