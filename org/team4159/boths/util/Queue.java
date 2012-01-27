package org.team4159.boths.util;

import java.util.NoSuchElementException;

public class Queue
{
	private static final int OBJECT = 0;
	private static final int NEXT = 1;
	
	private Object[] head = { null, null };
	private Object[] tail = head;
	private int size = 0;
	
	public void add (Object e)
	{
		Object[] next = new Object[]{ null, null };
		tail[OBJECT] = e;
		tail[NEXT] = next;
		tail = next;
		size++;
	}
	
	public Object element ()
	{
		return head[OBJECT];
	}
	
	public Object poll ()
	{
		if (size == 0)
			return null;
		size--;
		
		Object ret = head[OBJECT];
		head = (Object[]) head[NEXT];
		return ret;
	}
	
	public Object remove ()
	{
		if (size == 0)
			throw new NoSuchElementException ();
		return poll ();
	}
	
	public int size ()
	{
		return size;
	}
}
