package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedAbs extends Operator {

	public CheckedAbs(UExpression value) {
		super(new UExpression[]{value});
	}
	
	@Override
	protected int calc(int... values) {
		if (values[0] == Integer.MIN_VALUE) {
			throw new OverflowCalcException();
		}
		return Math.abs(values[0]);
	}

}
