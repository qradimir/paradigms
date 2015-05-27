package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedExponent extends Operator {
	
	public CheckedExponent(UExpression value, UExpression exp) {
		super(new UExpression[] {value, exp});
	}
	
	@Override
	protected int calc(int... values) {
		int r = 1, a = values[0], n = values[1];
		if (a == 0 && n == 0 || n < 0) {
			throw new UncorrectInputCalcException("uncorecct exponent");
		}
		while (true) {
			if (n % 2 == 1) {
				if  (CheckedMultiply.isOverflow(r, a)) {
					throw new OverflowCalcException();
				}
				r *= a;
			}
			n /= 2;
			if (n == 0) {
				break;
			}
			if  (CheckedMultiply.isOverflow(a, a)) {
				throw new OverflowCalcException();
			}
			a *= a;
		}
		return r;
	}
}
