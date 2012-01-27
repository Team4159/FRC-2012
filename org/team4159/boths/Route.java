package org.team4159.boths;

public class Route
{
	private final String pathPrefix;
	private final Class viewClass;

	public Route ()
	{
		this (null, null);
	}
	
	public Route (String pathPrefix, Class viewClass)
	{
		this.pathPrefix = pathPrefix;
		this.viewClass = viewClass;
	}
	
	public boolean matches (String path)
	{
		if (pathPrefix == null)
			return false;
		return path.startsWith (pathPrefix);
	}

	public Class getViewClass (String path)
	{
		if (viewClass == null)
			return null;
		return viewClass;
	}
	
	public int hashCode ()
	{
		return pathPrefix.hashCode () ^ viewClass.hashCode ();
	}
	
	public boolean equals (Object othero)
	{
		if (othero.getClass () != Route.class)
			return false;
		
		Route other = (Route) othero;
		
		return (
			this.pathPrefix.equals (other.pathPrefix) &&
			this.viewClass.equals (other.viewClass)
		);
	}
}
