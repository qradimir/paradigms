package expression.generic;

public class Const<T extends Number> implements GExpression<T> {
	
	Numeric<T> value;

	public Const(Numeric<T> value) {
		this.value = value;
	}

	@Override
	public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
		return value.factory().create(value.value());
	}

}
