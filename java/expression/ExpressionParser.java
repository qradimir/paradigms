package expression;

import java.util.Stack;

public class ExpressionParser implements Parser{
	
	private String expr;
	private int index;
	private Lexem curLex;
	private int lastConst;
	private String lastVar;
	
	private Lexem readWord() {
		int i;
		for (i = index; i < expr.length() && Character.isLetter(expr.charAt(i)); i++) ;
		String s = expr.substring(index, i--);
		Lexem lex;
		switch (s) {
		case "x" :
			lex = Lexem.VAR;
			lastVar = s;			
			break;
		case "y" :
			lex = Lexem.VAR;
			lastVar = s;
			break;
		case "z" :
			lex = Lexem.VAR;
			lastVar = s;			
			break;
		case "abs" :
			lex = Lexem.ABS;
			break;
		case "mod" :
			lex = Lexem.MOD;
			break;
		case "square" :
			lex = Lexem.SQR;
			break;
		default :
			lex = Lexem.WRONG;
		}
		index = i;
		return lex;		
	}
	
	private Lexem readConst() {
		int i;
		for (i = index + 1; i < expr.length() && Character.isDigit(expr.charAt(i)); i++) ;
		lastConst = Integer.parseInt(expr.substring(index, i--));
		index = i;
		return Lexem.CONST;
	}
	
	private void nextLex() {
		index++;
		while (index < expr.length() && Character.isWhitespace(expr.charAt(index))) {
			index++;
		}
		if (index == expr.length()) {
			curLex = Lexem.END;
			return;
		}
		Lexem lex;
		switch (expr.charAt(index)) {
		case '+' :
			lex = Lexem.ADD;
			break;
		case '-' :
			if (curLex == Lexem.CONST || curLex == Lexem.VAR || curLex == Lexem.CBR) {
				lex = Lexem.SUB;
			} else if (Character.isDigit(expr.charAt(index+1))){
				lex = Lexem.CONST;
				readConst();
			} else {
				lex = Lexem.NEG;
			}
			break;
		case '/' :
			lex = Lexem.DIV;
			break;
		case '*' :
			lex = Lexem.MUL;
			break;
		case '(' :
			lex = Lexem.OBR;
			break;
		case ')' :
			lex = Lexem.CBR;
			break;
		case '<' :
			if (expr.charAt(index+1) == '<') {
				index++;
				lex = Lexem.SHL;
			} else {
				lex = Lexem.WRONG;
			}
			break;
		case '>' :
			if (expr.charAt(index+1) == '>') {
				index++;
				lex = Lexem.SHR;
			} else {
				lex = Lexem.WRONG;
			}
			break;
		default :
			if (Character.isLetter(expr.charAt(index))) {
				lex = readWord();
			} else  if (Character.isDigit(expr.charAt(index))){
				lex = readConst();
			} else {
				lex = Lexem.WRONG;
			}
		}
		curLex = lex;
	}
	
	private UExpression values() {
		UExpression e;
		switch (curLex) {
		case OBR : 
			nextLex();
			e = expr();
			nextLex();
			break;
		case VAR :
			e = new Variable(lastVar);
			nextLex();
			break;
		case CONST :
			e = new Const(lastConst);
			nextLex();
			break;
		default : 
			e = null;
		}
		return e;
	}
	
	private UExpression mult() {
		UExpression e;
		Stack<Lexem> Unaries = new Stack<Lexem>();
		while (curLex.isAt(Lexem.UNARY_OP)) {
			Unaries.push(curLex);
			nextLex();
		}
		e = values();
		while (!Unaries.empty()) {
			Lexem lex = Unaries.pop();
			if (lex == Lexem.NEG) {
				e = new Negate(e);
			} else if (lex == Lexem.ABS) {
				e = new Absolute(e);
			} else {
				e = new Square(e);
			}
		}
		return e;
	}
	
	private UExpression item() {
		UExpression e = mult();
		while (curLex.isAt(Lexem.MUL_OP)) {
			if (curLex == Lexem.MUL) {
				nextLex(); 
				e = new Multiply(e, mult());
			} else if (curLex == Lexem.DIV){
				nextLex(); 
				e = new Divide(e, mult());
			} else {
				nextLex();
				e = new Mod(e, mult());
			}
		}
		return e;
	}
	
	private UExpression term() {
		UExpression e = item();
		while (curLex.isAt(Lexem.SUM_OP)) {
			if (curLex == Lexem.ADD) {
				nextLex(); 
				e = new Add(e, item());
			} else {
				nextLex(); 
				e = new Substract(e, item());
			}
		}
		return e;
	}
	
	private UExpression expr() {
		UExpression e = term();
		while (curLex.isAt(Lexem.BIT_OP)) {
			if (curLex == Lexem.SHL) {
				nextLex(); 
				e = new ShiftLeft(e, term());
			} else {
				nextLex(); 
				e = new ShiftRight(e, term());
			}
		}
		return e;
	}

	public TripleExpression parse(String expr) {
		this.expr = expr;
		lastVar = "";
		lastConst = 0;
		index = -1;
		nextLex();
		return expr();
	}
}
