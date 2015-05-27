package expression.generic;

public interface Numeric<T extends Number> {
	
	public NFactory<T> factory();
	
	public T value(); 
	public void set(T value);
	public void set(int value);
	
	public void add(Numeric<T> arg);
	public void neg();
	public void mul(Numeric<T> arg);
	public void div(Numeric<T> arg);
	
	public void trunc();
	
	public void sub(Numeric<T> arg);
	public void mod(Numeric<T> arg);	
	public void abs();
	public void square();
	
}
