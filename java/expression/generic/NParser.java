package expression.generic;

public interface NParser<T extends Number> {
	public T parse(String value);
}
