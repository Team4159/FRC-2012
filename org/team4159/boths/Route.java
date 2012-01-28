package org.team4159.boths;

public class Route
{
	public final String pathPrefix;
	public final View view;
	public final boolean exactPathMatch;

	public Route (String pathPrefix, View view)
	{
		this (pathPrefix, view, true);
	}
	
	public Route (String pathPrefix, View view, boolean exactPathMatch)
	{
		this.pathPrefix = pathPrefix;
		this.view = view;
		this.exactPathMatch = exactPathMatch;
	}
	
	public boolean matches (String path)
	{
		if (pathPrefix == null)
			return false;
		
		if (exactPathMatch)
			return path.equals (pathPrefix);
		else
			return path.startsWith (pathPrefix);
	}

	public View getView (String path)
	{
		if (view == null)
			return null;
		return view;
	}
	
	public int hashCode ()
	{
		return pathPrefix.hashCode () ^ view.hashCode () + (exactPathMatch ? 1 : 0);
	}
	
	public boolean equals (Object othero)
	{
		if (othero.getClass () != Route.class)
			return false;
		
		Route other = (Route) othero;
		
		return (
			this.pathPrefix.equals (other.pathPrefix) &&
			this.view.equals (other.view) &&
			(this.exactPathMatch == other.exactPathMatch)
		);
	}
}
