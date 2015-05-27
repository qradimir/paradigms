package expression;

public class Square extends Operator {

	public Square(UExpression arg) {
		super(new UExpression[]{arg});
	}
	
	protected int calc(int... values) {
		return values[0]*values[0];
	}

}
