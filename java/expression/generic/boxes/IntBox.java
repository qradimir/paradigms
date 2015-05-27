package expression.generic.boxes;

import expression.generic.GParser;
import expression.generic.NFactory;
import expression.generic.Numeric;

public class IntBox extends UIntBox {
	
	public static NFactory<Integer> factory = new NFactory<Integer>(IntBox::new, Integer::parseInt);
	public static GParser<Integer> parser = new GParser<Integer>(factory);
	
	public IntBox(Integer value) {
		super(value);
	}
	
	@Override
	public void add(Numeric<Integer> arg) {
		if (checkAdd(arg.value())) {
			throw new ArithmeticException();
		}
		super.add(arg);
	}
	
	protected boolean checkAdd(Integer a) {
		return a > 0 ? value> Integer.MAX_VALUE - a : value < Integer.MIN_VALUE - a;
	}

	@Override
	public void sub(Numeric<Integer> arg) {
		if (checkSub(arg.value())) {
			throw new ArithmeticException();
		}
		super.sub(arg);
	}
	
	protected boolean checkSub(Integer a) {
		return 	a >= 0 ? value < a - Integer.MAX_VALUE : value > a - Integer.MIN_VALUE;
	}


	@Override
	public void mul(Numeric<Integer> arg) {
		if (checkMul(arg.value())) {
			throw new ArithmeticException();
		}
		super.mul(arg);
	}

	protected boolean checkMul(Integer a) {
		return 	a > 0 ? 
				value > Integer.MAX_VALUE / a ||
				value < Integer.MIN_VALUE / a :
					(a < -1 ?
						value > Integer.MIN_VALUE / a ||
						value < Integer.MAX_VALUE / a :
							a == -1 && value == Integer.MIN_VALUE);
	}
	
	@Override
	public void div(Numeric<Integer> arg) {
		if (checkDiv(arg.value())) {
			throw new ArithmeticException();
		}
		super.div(arg);
	}
	
	public boolean checkDiv(Integer a) {
		return a == 0 || value == Integer.MIN_VALUE && a == -1;
	}

	@Override
	public void neg() {
		if (checkNeg()) {
			throw new ArithmeticException();
		}
		super.neg();
	}
	
	protected boolean checkNeg() {
		return value == Integer.MIN_VALUE;
	}

	@Override
	public void mod(Numeric<Integer> arg) {
		if (checkMod(arg.value())) {
			throw new ArithmeticException();
		}
		super.mod(arg);		
	}
	
	protected boolean checkMod(Integer a) {
		return a == 0 || value == Integer.MIN_VALUE && a == -1;
	}

	@Override
	public void abs() {
		if (checkNeg()) {
			throw new ArithmeticException();
		}
		super.abs();	
	}
	
	@Override
	public void square() {
		if (checkSquare()) {
			throw new ArithmeticException();
		}
		super.square();
	}
	
	protected boolean checkSquare() {
		return value > 0 ? value > Integer.MAX_VALUE / value : value < Integer.MAX_VALUE / value;
	}
	
	@Override
	public NFactory<Integer> factory() {
		return factory;
	}
	
}
