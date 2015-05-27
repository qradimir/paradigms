package expression.generic;

public interface GExpression<T extends Number> {
	public Numeric<T> evaluate(Numeric<T> x, Numeric<T> y, Numeric<T> z); 
}
