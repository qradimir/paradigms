package expression.generic;

public class Absolute<T extends Number> extends GUnaryOperator<T> {

	public Absolute(GExpression<T> arg) {
		super(arg);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> arg) {
		arg.abs();
		return arg;
	}

}
