package expression.generic;

public abstract class GUnaryOperator<T extends Number> implements GExpression<T>{
	
	private GExpression<T> arg;
	
	public GUnaryOperator(GExpression<T> arg) {
		this.arg = arg;
	}
	
	protected abstract Numeric<T> calc(Numeric<T> arg);
	
	@Override
	public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
		return calc(arg.evaluate(x, y, z));
	}
}
