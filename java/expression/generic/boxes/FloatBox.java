package expression.generic.boxes;

import expression.generic.AbstractNumeric;
import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class FloatBox extends AbstractNumeric<Float> {

	public static NFactory<Float> factory = new NFactory<Float>(FloatBox::new, Float::parseFloat);
	public static GParser<Float> parser = new GParser<Float>(factory);
	
	public FloatBox(Float value) {
		super(value);
	}

	@Override
	public void set(int value) {
		this.value = Float.valueOf(value);

	}

	@Override
	public void add(Numeric<Float> arg) {
		value += arg.value();
	}

	@Override
	public void mul(Numeric<Float> arg) {
		value *= arg.value();
	}

	@Override
	public void div(Numeric<Float> arg) {
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
		value = (float) value.longValue();		
	}
	
	@Override
	public NFactory<Float> factory() {
		return factory;
	}
}
