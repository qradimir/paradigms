package expression;

import expression.exceptions.CalcException;

public interface Expression {
	
	public int evaluate(int value) throws CalcException;
}
