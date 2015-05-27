package expression.generic.boxes;

import expression.generic.AbstractNumeric;
import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class ByteBox extends AbstractNumeric<Byte> {
	
	public static NFactory<Byte> factory = new NFactory<Byte>(ByteBox::new, Byte::parseByte);
	public static GParser<Byte> parser = new GParser<Byte>(factory);
	
	public ByteBox(Byte value) {
		super(value);
	}
	
	@Override
	public void set(int value) {
		this.value = (byte)value;
	}
	
	@Override
	public void add(Numeric<Byte> arg) {
		value = (byte) (value + arg.value());
	}

	@Override
	public void mul(Numeric<Byte> arg) {
		value = (byte) (value * arg.value()); 
	}

	@Override
	public void div(Numeric<Byte> arg) {
		value = (byte) (value / arg.value());
	}

	@Override
	public void neg() {
		value = (byte) -value;
	}

	@Override
	protected boolean isZeroOrMore() {
		return value >= 0;
	}

	@Override
	public void trunc() {}

	@Override
	public NFactory<Byte> factory() {
		return factory;
	}

}
