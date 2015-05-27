package expression.generic.boxes;

import java.math.BigInteger;

import expression.generic.AbstractNumeric;
import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class BigIntegerBox extends AbstractNumeric<BigInteger> {

	public static NFactory<BigInteger> factory = new NFactory<BigInteger>(BigIntegerBox::new, BigIntegerBox::parse);
	public static GParser<BigInteger> parser = new GParser<BigInteger>(factory);
	
	public BigIntegerBox(BigInteger value) {
		super(value);
	}
	
	@Override
	public NFactory<BigInteger> factory() {
		return factory;
	}

	@Override
	public void set(int value) {
		this.value = BigInteger.valueOf(value);
	}

	@Override
	public void add(Numeric<BigInteger> arg) {
		value = value.add(arg.value());
	}

	@Override
	public void mul(Numeric<BigInteger> arg) {
		value = value.multiply(arg.value());
	}

	@Override
	public void div(Numeric<BigInteger> arg) {
		value = value.divide(arg.value());
	}

	@Override
	public void neg() {
		value = value.negate();
	}

	@Override
	protected boolean isZeroOrMore() {
		
		return value.signum() > -1;
	}
	
	@Override
	public void trunc() {}
	
	public static BigInteger parse(String s) {
		return BigInteger.valueOf(Long.parseLong(s));
	}

}
