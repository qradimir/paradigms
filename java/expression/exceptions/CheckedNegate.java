package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedNegate extends Operator {
	
	public CheckedNegate(UExpression arg){
		super(new UExpression[]{arg});
	}
	
	protected int calc(int... values) {
		if (values[0] == Integer.MIN_VALUE) {
			throw new OverflowCalcException();
		}
		return -values[0];
	}

}

