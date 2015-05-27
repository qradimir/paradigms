package expression;

import expression.exceptions.CalcException;

public class Divide extends Operator {

	public Divide(UExpression arg0, UExpression arg1) {
		super(new UExpression[]{arg0, arg1});
	}
	
	protected int calc(int... values) throws CalcException{
		return  values[0] / values[1];
	}
}
