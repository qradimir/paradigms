package expression.generic;

public class Subtract<T extends Number> extends GBinaryOperator<T> {

	public Subtract(GExpression<T> left, GExpression<T> right) {
		super(left, right);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> left, Numeric<T> right) {
		left.sub(right);
		return left;
	}	
}
