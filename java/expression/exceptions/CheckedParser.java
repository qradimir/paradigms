package expression.exceptions;

import java.util.Stack;

import expression.Const;
import expression.Lexem;
import expression.Parser;
import expression.TripleExpression;
import expression.UExpression;
import expression.Variable;
import expression.exceptions.UnboxingExpr.CheckerDecidion;


public class CheckedParser implements Parser{
	
	private  UnboxingExpr unboxed;

	
	private UExpression values() throws ConstOverflowException{
		UExpression e;
		switch (unboxed.curLexem()) {
		case OBR : 
			unboxed.toNext();
			e = expr();
			unboxed.toNext();
			break;
		case VAR :
			e = new Variable(unboxed.curWord());
			unboxed.toNext();
			break;
		case CONST :
			try {
				e = new Const(Integer.parseInt(unboxed.curWord()));
			} catch (NumberFormatException ex) {
				throw new ConstOverflowException(unboxed.curWord());
			}
			unboxed.toNext();
			break;
			default :
				e = null;
			break; 
		}
		return e;
	}
	
	private UExpression exponented() throws ConstOverflowException {
		UExpression e;
		Stack<Lexem> Unaries = new Stack<Lexem>();
		while (unboxed.curLexem().isAt(Lexem.UNARY_OP)) {
			Unaries.push(unboxed.curLexem());
			unboxed.toNext();
		}
		e = values();
		while (!Unaries.empty()) {
			Lexem lex = Unaries.pop();
			if (lex == Lexem.NEG) {
				e = new CheckedNegate(e);
			} else if (lex == Lexem.ABS) {
				e = new CheckedAbs(e);
			} else {
				e = new CheckedSqrt(e);
			}
		}
		return e;
	}
	
	private UExpression mult() throws ConstOverflowException {
		UExpression e = exponented();
		while (true) {
			if (unboxed.curLexem() == Lexem.EXP) {
				unboxed.toNext(); 
				e = new CheckedExponent(e, exponented());
			} else if (unboxed.curLexem() == Lexem.LOG){
				unboxed.toNext(); 
				e = new CheckedLogarithm(e, exponented());
			} else {
				break;
			}
		}
		return e;
	}
	
	private UExpression item() throws ConstOverflowException {
		UExpression e = mult();
		while (true) {
			if (unboxed.curLexem() == Lexem.MUL) {
				unboxed.toNext(); 
				e = new CheckedMultiply(e, mult());
			} else if (unboxed.curLexem() == Lexem.DIV) {
				unboxed.toNext(); 
				e = new CheckedDivide(e, mult());
			} else {
				break;
			}
		}
		return e;
	}
	
	private UExpression expr() throws ConstOverflowException {
		UExpression e = item();
		while (true) {
			if (unboxed.curLexem() == Lexem.ADD) {
				unboxed.toNext(); 
				e = new CheckedAdd(e, item());
			} else if (unboxed.curLexem() == Lexem.SUB){
				unboxed.toNext();
				e = new CheckedSubtract(e, item());
			} else {
				break;
			}
		}
		return e;
	}
	

	public TripleExpression parse(String expr) throws ParserException {
		unboxed = new UnboxingExpr(expr);
		CheckerDecidion decidion = unboxed.check();
		if (decidion != CheckerDecidion.OK) {
			throw new ParserException(decidion);
		}
		unboxed.toNext();
		TripleExpression e  = expr();
		return e;
	}
}
