package expression.generic;

public interface NCreator<T extends Number> {
	public Numeric<T> create(T value);
}
