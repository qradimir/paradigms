package expression.generic;

public class Mod<T extends Number> extends GBinaryOperator<T> {

	public Mod(GExpression<T> left, GExpression<T> right) {
		super(left, right);
	}
	
	@Override
	protected Numeric<T> calc(Numeric<T> left, Numeric<T> right) {
		left.mod(right);
		return left;
	}

}
