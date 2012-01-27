package org.team4159.boths.template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.team4159.boths.Response;
import org.team4159.boths.template.Lexer.Token;
import org.team4159.boths.template.Lexer.TokenTypes;

public class Template
{
	private static final Hashtable EMPTY_CONTEXT = new Hashtable ();
	
	private Node rootNode;
	
	public Template (String tmplString)
	{
		rootNode = parse (tmplString);
	}
	
	public String render (Hashtable context)
	{
		if (context == null)
			context = EMPTY_CONTEXT;
		return rootNode.render (context);
	}
	
	public String render ()
	{
		return render (null);
	}
	
	public Response renderToResponse (Hashtable context)
	{
		return new Response (render (context));
	}
	
	public Response renderToResponse ()
	{
		return renderToResponse (null);
	}
	
	private Node parse (String tmpl)
	{
		TreeNode node = new TreeNode ();
		Lexer lexer = new Lexer (tmpl);
		
		try {
			while (lexer.hasMoreTokens ())
			{
				Token token = lexer.nextToken ();
				switch (token.type)
				{
					
					case TokenTypes.TEXT:
						node.addChild (new TextNode (token.text));
						break;
						
					case TokenTypes.START_VARIABLE:
						
						Token varToken = lexer.expectToken (TokenTypes.VARIABLE);
						
						boolean safe;
						if (safe = (lexer.peekToken ().type == TokenTypes.VARIABLE_SAFE))
							lexer.nextToken (); // consume safety token
						
						lexer.expectToken (TokenTypes.END_VARIABLE);
						
						node.addChild (new VariableNode (varToken.text, safe));
						break;
						
					default:
						throw new ParseException ("unrecognized token type");
						
				}
			}
		} catch (ParseException e) {
			e.printStackTrace ();
			throw new RuntimeException (e.toString ());
		}
		
		return node;
	}
	
	public static Template load (InputStream is)
	{
		if (is == null)
			throw new NullPointerException ("input stream must not be null");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		
		try {
			int k;
			while ((k = is.read ()) != -1)
				baos.write (k);
			baos.flush ();
		} catch (IOException e) {
			throw new RuntimeException (e + ": failed to read input stream");
		}
		
		return new Template (new String (baos.toByteArray ()));
	}
	
	public static Template load (Class cls, String name)
	{
		InputStream is = cls.getResourceAsStream (name);
		if (is == null)
			throw new RuntimeException ("resource of name " + name + " for class " + cls.getName () + " not found");
		return load (is);
	}
	
	public static Template load (Object obj, String name)
	{
		return load (obj.getClass (), name);
	}
}