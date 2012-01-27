package org.team4159.boths;

public class Route
{
	private final String pathPrefix;
	private final Class viewClass;
	private final boolean exactPathMatch;

	public Route (String pathPrefix, Class viewClass)
	{
		this (pathPrefix, viewClass, true);
	}
	
	public Route (String pathPrefix, Class viewClass, boolean exactPathMatch)
	{
		this.pathPrefix = pathPrefix;
		this.viewClass = viewClass;
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

	public Class getViewClass (String path)
	{
		if (viewClass == null)
			return null;
		return viewClass;
	}
	
	public int hashCode ()
	{
		return pathPrefix.hashCode () ^ viewClass.hashCode () + (exactPathMatch ? 1 : 0);
	}
	
	public boolean equals (Object othero)
	{
		if (othero.getClass () != Route.class)
			return false;
		
		Route other = (Route) othero;
		
		return (
			this.pathPrefix.equals (other.pathPrefix) &&
			this.viewClass.equals (other.viewClass) &&
			(this.exactPathMatch == other.exactPathMatch)
		);
	}
}
