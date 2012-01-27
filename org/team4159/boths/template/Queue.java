package org.team4159.boths.template;

import java.util.NoSuchElementException;

public class Queue
{
	private static final int OBJECT = 0;
	private static final int NEXT = 1;
	
	private Object[] head = { null, null };
	private Object[] tail = head;
	private int size = 0;
	
	void add (Object e)
	{
		Object[] next = new Object[]{ null, null };
		tail[OBJECT] = e;
		tail[NEXT] = next;
		tail = next;
		size++;
	}
	
	Object element ()
	{
		return head[OBJECT];
	}
	
	Object poll ()
	{
		if (size == 0)
			return null;
		size--;
		
		Object ret = head[OBJECT];
		head = (Object[]) head[NEXT];
		return ret;
	}
	
	Object remove ()
	{
		if (size == 0)
			throw new NoSuchElementException ();
		return poll ();
	}
	
	int size ()
	{
		return size;
	}
}
