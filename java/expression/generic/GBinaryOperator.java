package expression.generic;


public abstract class GBinaryOperator<T extends Number> implements GExpression<T> {
	
	private GExpression<T> left, right; 

	public GBinaryOperator(GExpression<T> left, GExpression<T> right) {
		this.left = left;
		this.right = right;
	}
	
	protected abstract Numeric<T> calc(Numeric<T> left, Numeric<T> right);
	
	@Override
	public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
		return calc(left.evaluate(x, y, z), right.evaluate(x, y, z));
	}
}
