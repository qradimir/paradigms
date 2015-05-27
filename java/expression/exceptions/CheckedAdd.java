package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedAdd extends Operator {
	
	public CheckedAdd(UExpression arg0, UExpression arg1) {
		super(new UExpression[]{arg0, arg1});
	}
	
	protected int calc(int... values) {
		if (values[0] > 0 ? 
				values[1] > Integer.MAX_VALUE - values[0] :
				values[1] < Integer.MIN_VALUE - values[0]) {
			throw new OverflowCalcException();
		}
		return values[0] + values[1];
	}

}
