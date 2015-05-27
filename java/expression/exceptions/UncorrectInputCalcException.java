package expression.exceptions;

public class UncorrectInputCalcException extends CalcException {
	static final long serialVersionUID = 0;
	public UncorrectInputCalcException(String msg) {
		super("Uncorrect input arguments: "+ msg);
	}
}
