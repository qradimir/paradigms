package expression.generic.boxes;

import expression.generic.AbstractNumeric;
import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class UIntBox extends AbstractNumeric<Integer> {
	
	public static NFactory<Integer> factory = new NFactory<Integer>(UIntBox::new, Integer::parseInt);
	public static GParser<Integer> parser = new GParser<Integer>(factory);
	
	public UIntBox(Integer value) {
		super(value);
	}

	@Override
	public void add(Numeric<Integer> arg) {
		value += arg.value();
	}

	@Override
	public void mul(Numeric<Integer> arg) {
		value *= arg.value();
	}

	@Override
	public void div(Numeric<Integer> arg) {
		value /= arg.value();
	}

	@Override
	public void neg() {	
		value = -value;
	}

	@Override
	protected boolean isZeroOrMore() {
		return value >= 0;
	}
	
	@Override
	public void trunc() {}
	
	@Override
	public NFactory<Integer> factory() {
		return factory;
	}

	@Override
	public void set(int value) {
		this.value = Integer.valueOf(value);
		
	}
}
