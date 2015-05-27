package expression.exceptions;

public class CalcException extends ArithmeticException {
	static final long serialVersionUID = 0;
	public CalcException(String message) {
		super("Calculating Error: " + message);
	}
}
