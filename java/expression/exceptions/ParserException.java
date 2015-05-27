package expression.exceptions;

import expression.exceptions.UnboxingExpr.CheckerDecidion;

public class ParserException extends Exception {
	static final long serialVersionUID = 0;
	private static String checkerError(CheckerDecidion error) {
		switch (error) {
			case NO_CLOSING_BR : return "No clossing bracket";
			case NO_FIRST_ARGUMENT : return "No first argument";
			case NO_LAST_ARGUMENT : return "No last argument";
			case NO_MIDDLE_ARGUMENT : return "No middle argument";
			case NO_OPENNING_BR : return "No openning bracket";
			case NO_OPERATOR : return "No operator";
			case UNKNOWN_START_SYMBOL : return  "Unknown start symbol";
			case UNKNOWN_END_SYMBOL : return  "Unknown end symbol";
			case UNKNOWN_MIDDLE_SYMBOL : return "Unknown middle symbol";
			default : return "OK!";
		} 
	}
	
	public ParserException(CheckerDecidion error) {
		super("Parsing error found: " + checkerError(error));
	}
	public ParserException(String msg) {
		super("Parsing error found: " + msg);
	}
}
