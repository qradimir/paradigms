package expression.exceptions;

public class ConstOverflowException extends ParserException {
	static final long serialVersionUID = 0;
	public ConstOverflowException(String overflowed) {
		super("Constant overflow : "+overflowed);
	}
}
