package expression;

public class Const implements UExpression {
	
	private int arg;
	
	public Const(int arg) {
		this.arg = arg;
	}
	
	public int evaluate(int x, int y, int z) {
		return arg;
	}
	
	public int evaluate(int value) {
		return arg;
	}
}
