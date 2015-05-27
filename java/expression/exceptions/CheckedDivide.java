package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedDivide extends Operator {

	public CheckedDivide(UExpression arg0, UExpression arg1) {
		super(new UExpression[]{arg0, arg1});
	}
	
	protected int calc(int... values) {
		if (values[1] == 0) {
			throw new UncorrectInputCalcException("division by zero");
		}
		if (values[0] == Integer.MIN_VALUE && values[1] == -1) {
			throw new OverflowCalcException();
		}
		return  values[0] / values[1];
	}
}
