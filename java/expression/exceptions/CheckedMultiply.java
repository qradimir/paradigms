package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedMultiply extends Operator {
	
	public CheckedMultiply(UExpression arg0, UExpression arg1) {
		super(new UExpression[]{arg0, arg1});
	}
	
	protected static boolean isOverflow(int a, int b) {
		 return (a > 0 ? 
				b > Integer.MAX_VALUE / a ||
				b < Integer.MIN_VALUE / a :
					(a < -1 ?
							b > Integer.MIN_VALUE / a ||
							b < Integer.MAX_VALUE / a :
								a == -1 && b == Integer.MIN_VALUE));
	}
	
	protected int calc(int... values) {
		if (isOverflow(values[0], values[1])) {
			throw new OverflowCalcException();
		}
		return values[0] * values[1];
	}
}
