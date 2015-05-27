package expression.generic;

public class Negate<T extends Number> extends GUnaryOperator<T> {

	public Negate(GExpression<T> arg) {
		super(arg);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> arg) {
		arg.neg();
		return arg;
	}	
}