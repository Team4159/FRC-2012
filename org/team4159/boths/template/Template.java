package org.team4159.boths.template;

import java.util.Hashtable;
import org.team4159.boths.template.Lexer.Token;
import org.team4159.boths.template.Lexer.TokenTypes;

public class Template
{
	private Node rootNode;
	
	public Template (String tmplString)
	{
		rootNode = parse (tmplString);
	}
	
	public String render (Hashtable context)
	{
		return rootNode.render (context);
	}
	
	public String render (String[] context)
	{
		if (context.length % 2 != 0)
			throw new IllegalArgumentException ("length of context must be multiple of 2");
		
		Hashtable table = new Hashtable (context.length / 2 + 1);
		
		for (int i = 0; i < context.length; i += 2)
		{
			String key = context[i], value = context[i+1];
			if (key == null || value == null)
				throw new IllegalArgumentException ("all Strings in context must be non-null");
			table.put (key, value);
		}
		
		return render (table);
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
						throw new ParsingException ("unrecognized token type");
						
				}
			}
		} catch (ParsingException e) {
			e.printStackTrace ();
			throw new RuntimeException (e.toString ());
		}
		
		return node;
	}
}
