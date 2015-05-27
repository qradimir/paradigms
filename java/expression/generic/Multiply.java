package expression.generic;

public class Multiply<T extends Number> extends GBinaryOperator<T> {

	public Multiply(GExpression<T> left, GExpression<T> right) {
		super(left, right);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> left, Numeric<T> right) {
		left.mul(right);
		return left;
	}	
}
