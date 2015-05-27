package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedSqrt extends Operator {

	private final static int MAX_POSIBLE = 46341; 
	
	public CheckedSqrt(UExpression value) {
		super(new UExpression[]{value});
	}
	@Override
	protected int calc(int... values) {
		if (values[0] < 0) {
			throw new UncorrectInputCalcException("negative argument");
		}
		int l = -1;
		int r = Math.min(values[0] + 1, MAX_POSIBLE);
		while (r - l > 1) {
			int m = (r - l) / 2 + l;
			if (m*m > values[0]) {
				r = m;
			} else {
				l = m;
			}
		}
		return l;
	}

}
