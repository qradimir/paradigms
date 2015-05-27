package expression;

public class Absolute extends Operator {

	public Absolute(UExpression arg) {
		super(new UExpression[]{arg});
	}
	
	
	protected int calc(int... values) {
		return Math.abs(values[0]);
	}

}
