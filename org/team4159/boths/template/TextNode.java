package org.team4159.boths.template;

import java.util.Hashtable;

public class TextNode extends Node
{
	public final String text;
	
	public TextNode (String text)
	{
		this.text = text;
	}
	
	public String render (Hashtable context)
	{
		return text;
	}
}
