package expression.exceptions;

public class OverflowCalcException extends CalcException {
	static final long serialVersionUID = 0;
	public OverflowCalcException() {
		super("overflow");
	}
}
