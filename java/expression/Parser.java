package expression;

import expression.exceptions.ParserException;

public interface Parser {
	
	public TripleExpression parse(String expr) throws ParserException;
}
