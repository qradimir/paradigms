package expression;

public enum  Lexem{
	CONST, VAR,
	SHL, SHR,ADD, SUB, MUL, DIV, MOD, EXP, LOG,
	NEG, ABS, SQRT, SQR,
	OBR, CBR,
	WRONG, START, END;
	
	public static final Lexem[] BIT_OP = new Lexem[]{SHL, SHR};
	public static final Lexem[] SUM_OP = new Lexem[]{ADD, SUB};
	public static final Lexem[] MUL_OP = new Lexem[]{MUL, DIV};
	public static final Lexem[] EXP_OP = new Lexem[]{EXP, LOG};
	public static final Lexem[] UNARY_OP = new Lexem[]{NEG, ABS, SQRT, SQR};
	public static final Lexem[] BINARY_OP = new Lexem[]{ADD, SUB, MUL, DIV, EXP, LOG};
	public static final Lexem[] NUMERIC = new Lexem[]{CONST, VAR};
	
	public boolean isAt(Lexem[] lexems) {
		for (Lexem lexem : lexems) {
			if (this == lexem) {
				return true;
			}
		}
		return false;
	}
}
