package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedSubtract extends Operator {
	
	public CheckedSubtract(UExpression arg0, UExpression arg1) {
		super(new UExpression[]{arg0, arg1});
	}
	
	protected int calc(int... values) {
		if (values[0] >= 0 ? 
				values[1] < values[0] - Integer.MAX_VALUE :
				values[1] > values[0] - Integer.MIN_VALUE) {
			throw new OverflowCalcException();
		}
		return values[0] - values[1];
	}

}
