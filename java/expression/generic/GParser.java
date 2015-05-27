package expression.generic;

import java.util.Stack;

import expression.Lexem;
import expression.exceptions.ConstOverflowException;
import expression.exceptions.ParserException;

public class GParser<T extends Number> {

	private String expr;
	private int index;
	private Lexem curLex;
	private Numeric<T> lastConst;
	private String lastVar;
	private NFactory<T> factory;
	 
	public GParser(NFactory<T> factory) {
		this.factory = factory; 
	}
	
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
	
	private Lexem readConst() throws ParserException {
		int i;
		for (i = index + 1; i < expr.length() && Character.isDigit(expr.charAt(i)); i++) ;
		String s = expr.substring(index, i--);
		try {
		lastConst = factory.create(s);
		} catch (Exception e) {
			throw new ConstOverflowException(s);
		}
		index = i;
		return Lexem.CONST;
	}
	
	private void nextLex() throws ParserException {
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
	
	private GExpression<T> values() throws ParserException {
		GExpression<T> e;
		switch (curLex) {
		case OBR : 
			nextLex();
			e = expr();
			nextLex();
			break;
		case VAR :
			e = new Variable<T>(lastVar);
			nextLex();
			break;
		case CONST :
			e = new Const<T>(lastConst);
			nextLex();
			break;
		default : 
			e = null;
		}
		return e;
	}
	
	private GExpression<T> mult() throws ParserException {
		GExpression<T> e;
		Stack<Lexem> Unaries = new Stack<Lexem>();
		while (curLex.isAt(Lexem.UNARY_OP)) {
			Unaries.push(curLex);
			nextLex();
		}
		e = values();
		while (!Unaries.empty()) {
			Lexem lex = Unaries.pop();
			if (lex == Lexem.NEG) {
				e = new Negate<T>(e);
			} else if (lex == Lexem.ABS) {
				e = new Absolute<T>(e);
			} else {
				e = new Square<T>(e);
			}
		}
		return e;
	}
	
	private GExpression<T> item() throws ParserException {
		GExpression<T> e = mult();
		while (true) {
			if (curLex == Lexem.MUL) {
				nextLex(); 
				e = new Multiply<T>(e, mult());
			} else if (curLex == Lexem.DIV){
				nextLex(); 
				e = new Divide<T>(e, mult());
			} else if (curLex == Lexem.MOD) {
				nextLex();
				e = new Mod<T>(e, mult());
			} else {
				break;
			}
		}
		return e;
	}
	
	private GExpression<T> expr() throws ParserException {
		GExpression<T> e = item();
		while (true) {
			if (curLex == Lexem.ADD) {
				nextLex(); 
				e = new Add<T>(e, item());
			} else if (curLex == Lexem.SUB){
				nextLex(); 
				e = new Subtract<T>(e, item());
			} else {
				break;
			}
		}
		return e;
	}

	public GExpression<T> parse(String expr) throws ParserException {
		this.expr = expr;
		lastVar = "";
		index = -1;
		nextLex();
		return expr();
	}


}
