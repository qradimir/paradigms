package expression.generic.boxes;

import expression.generic.AbstractNumeric;
import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class DoubleBox extends AbstractNumeric<Double> {

	public static NFactory<Double> factory = new NFactory<Double>(DoubleBox::new, Double::parseDouble);
	public static GParser<Double> parser = new GParser<Double>(factory);
	
	public DoubleBox(Double value) {
		super(value);
	}

	@Override
	public void set(int value) {
		this.value = Double.valueOf(value);
	}

	@Override
	public void add(Numeric<Double> arg) {
		value += arg.value();
	}

	@Override
	public void mul(Numeric<Double> arg) {
		value *= arg.value();
	}

	@Override
	public void div(Numeric<Double> arg) {
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
	public void trunc() {
		value = (double) value.intValue();		
	}

	@Override
	public NFactory<Double> factory() {
		return factory;
	}
}
