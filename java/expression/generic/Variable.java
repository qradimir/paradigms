package expression.generic;

public class Variable<T extends Number> implements GExpression<T> {

	private final String name;
	
	public Variable(String name) {
		this.name = name;
	}

	@Override
	public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z) {
		switch (name) {
			case "x" : return x;
			case "y" : return y;
			case "z" : return z;
			default : return null;
		}
	}

}
