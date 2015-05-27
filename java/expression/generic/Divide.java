package expression.generic;

public class Divide<T extends Number> extends GBinaryOperator<T> {

	public Divide(GExpression<T> left, GExpression<T> right) {
		super(left, right);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> left, Numeric<T> right) {
		left.div(right);
		return left;
	}	
}
