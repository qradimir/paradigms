package expression;

public class Mod extends Operator {
	
	public Mod( UExpression arg0, UExpression arg1) {
		super( new UExpression[]{arg0, arg1});
	}
	
	protected int calc(int... values) {
		return values[0] % values[1];
	}

}
