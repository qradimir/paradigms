package expression.generic;

public abstract class AbstractNumeric<T extends Number> implements Numeric<T> {
	protected T value;
	
	public AbstractNumeric(T value) {
		this.value = value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	public T value() {
		return value;
	}
	
	protected abstract boolean isZeroOrMore();
	
	public void sub(Numeric<T> arg) {
		arg.neg();
		add(arg); 
	}
	
	public void mod(Numeric<T> arg) {
		Numeric<T> a = arg.factory().create(this.value);
		a.div(arg);
		a.trunc();
		a.mul(arg);
		sub(a);
	}
	
	public void abs() {
		if (!isZeroOrMore()) {
			neg();
		}
	}
	
	public void square() {
		mul(this);
	}
}
