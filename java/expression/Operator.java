package expression;

public abstract class Operator  implements UExpression{
	private UExpression[] args; 
	
	protected abstract int calc(int... values);
	
	public int evaluate(int x, int y, int z) {
		int[] values = new int[args.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = args[i].evaluate(x, y, z);
		}
		return calc(values);
	}
	
	public int evaluate(int x) {
		int[] values = new int[args.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = args[i].evaluate(x);
		}
		return calc(values);
	}
	
	public Operator(UExpression[] args) {
		this.args = args;
	}
}
