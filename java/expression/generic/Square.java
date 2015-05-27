package expression.generic;

public class Square<T extends Number> extends GUnaryOperator<T> {

	public Square(GExpression<T> arg) {
		super(arg);
	}

	@Override
	protected Numeric<T> calc(Numeric<T> arg) {
		arg.square();
		return arg;
	}

}
