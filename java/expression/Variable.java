package expression;

public class Variable implements UExpression {

	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public int evaluate(int x, int y , int z) {
		switch (name) {
			case "x": 
				return x; 
			case "y": 
				return y; 
			case "z": 
				return z; 
			default: 
				return 0;
		}
	}
	
	public int evaluate(int value) {
		return value;
	}
}
