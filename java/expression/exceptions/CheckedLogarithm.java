package expression.exceptions;

import expression.Operator;
import expression.UExpression;

public class CheckedLogarithm extends Operator {

	public CheckedLogarithm(UExpression value, UExpression base) {
		super(new UExpression[] {value, base});
	}

	@Override
	protected int calc(int... values) {
		int r = values[0], b = values[1],  ans = 0;
		if (r <= 0 || b <= 0) {
			throw new UncorrectInputCalcException("argument sub zero");
		}
		if (b == 1) {
			throw new UncorrectInputCalcException("log base = 1");
		}
		while (r >= b) {
			 r /= b;
			 ans++;
		} 
		return ans;

		
	}

}
