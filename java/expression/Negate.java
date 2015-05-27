package expression;

public class Negate extends Operator {
	
	public Negate(UExpression arg){
		super(new UExpression[]{arg});
	}
	
	protected int calc(int... values) {
		return -values[0];
	}

}
