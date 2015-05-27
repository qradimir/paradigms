package expression.generic;

public class Add<T extends Number> extends GBinaryOperator<T> {

	public Add(GExpression<T> left, GExpression<T> right) {
		super(left, right);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> left, Numeric<T> right) {
		left.add(right);
		return left;
	}	
}
