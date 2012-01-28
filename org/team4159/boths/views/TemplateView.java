package org.team4159.boths.views;

import java.util.Hashtable;
import org.team4159.boths.Request;
import org.team4159.boths.Response;
import org.team4159.boths.Route;
import org.team4159.boths.View;
import org.team4159.boths.template.Template;

public class TemplateView extends View
{
	private Template template;
	
	public TemplateView (String templateName)
	{
		template = Template.load (this, templateName);
	}

	public Response getResponse (Request req, Route route)
	{
		return template.renderToResponse (getContext (req));
	}
	
	public Hashtable getContext (Request req)
	{
		return null;
	}
}