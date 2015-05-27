package expression.exceptions;

import java.util.ArrayList;

import expression.Lexem;
class UnboxingExpr {
	String expr;
	ArrayList<String> words;
	ArrayList<Lexem> lexems;
	
	private int indexWord, indexLexem, index;
	
	public String curWord() {
		return words.get(indexWord);
	}
	
	public Lexem curLexem() {
		return lexems.get(indexLexem);
	}
	
	public void toNext() {
		indexLexem++;
		if (lexems.get(indexLexem).isAt(Lexem.NUMERIC)) {
			indexWord++;
		}
	}
	
	
	
	private Lexem readWord() {
		int i;
		for (i = index; i < expr.length() && Character.isLetter(expr.charAt(i)); i++) ;
		String s = expr.substring(index, i--);
		Lexem lex;
		switch (s) {
		case "x" :
			lex = Lexem.VAR;
			words.add(s);			
			break;
		case "y" :
			lex = Lexem.VAR;
			words.add(s);
			break;
		case "z" :
			lex = Lexem.VAR;
			words.add(s);			
			break;
		case "abs" :
			lex = Lexem.ABS;
			break;
		case "sqrt" :
			lex = Lexem.SQRT;
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
		words.add(expr.substring(index, i--));
		index = i;
		return Lexem.CONST;
	}
	
	private void constructNext() {
		index++;
		while (index < expr.length() && Character.isWhitespace(expr.charAt(index))) {
			index++;
		}
		if (index == expr.length()) {
			lexems.add(Lexem.END);
			return;
		}
		Lexem lex;
		switch (expr.charAt(index)) {
		case '+' :
			lex = Lexem.ADD;
			break;
		case '-' :
			Lexem curLex = lexems.get(lexems.size() - 1);
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
			if (expr.charAt(index + 1) == '/') {
				index++;
				lex = Lexem.LOG; 
			} else {
				lex = Lexem.DIV;
			}
			break;
		case '*' :
			if (expr.charAt(index + 1) == '*') {
				index++;
				lex = Lexem.EXP;
			} else {
				lex = Lexem.MUL;
			}
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
		lexems.add(lex);
	} 
	
	
	
	enum CheckerDecidion {
		NO_FIRST_ARGUMENT, NO_MIDDLE_ARGUMENT, NO_LAST_ARGUMENT,
		NO_OPERATOR, NO_CLOSING_BR, NO_OPENNING_BR,
		UNKNOWN_START_SYMBOL, UNKNOWN_MIDDLE_SYMBOL, UNKNOWN_END_SYMBOL,
		OK
	}
	
	public CheckerDecidion check() {
		if (lexems.get(1).isAt(Lexem.BINARY_OP)) {
			return CheckerDecidion.NO_FIRST_ARGUMENT; 
		}
		if (lexems.get(lexems.size() - 2).isAt(Lexem.BINARY_OP) || 
				lexems.get(lexems.size() - 1).isAt(Lexem.UNARY_OP)) {
			return CheckerDecidion.NO_LAST_ARGUMENT; 
		}
		if (lexems.get(1) == Lexem.WRONG) {
			return CheckerDecidion.UNKNOWN_START_SYMBOL; 
		}
		if (lexems.get(lexems.size() - 2) == Lexem.WRONG) {
			return CheckerDecidion.UNKNOWN_END_SYMBOL; 
		}
		Lexem prev, cur = Lexem.START; 
		int dbrackets = 0; 
		for (int i = 1; i < lexems.size(); i++) {
			prev = cur;
			cur = lexems.get(i);
			if (cur == Lexem.WRONG) {
				return CheckerDecidion.UNKNOWN_MIDDLE_SYMBOL;
			}
			if (cur.isAt(Lexem.BINARY_OP)  && prev != Lexem.CONST && prev != Lexem.VAR && prev != Lexem.CBR) {
				return CheckerDecidion.NO_MIDDLE_ARGUMENT;
			}
			if ((cur.isAt(Lexem.UNARY_OP) || cur.isAt(Lexem.NUMERIC)) && prev == Lexem.CBR) {
				return CheckerDecidion.NO_OPERATOR;
			}
			if (cur == Lexem.CBR) {
				if (prev != Lexem.CONST && prev != Lexem.VAR && prev != Lexem.CBR) {
					return CheckerDecidion.NO_MIDDLE_ARGUMENT;
				}
				if (dbrackets == 0) {
					return CheckerDecidion.NO_OPENNING_BR;
				}
				dbrackets--;
			}
			if (cur == Lexem.OBR) {
				if (prev == Lexem.CONST || prev == Lexem.VAR || prev == Lexem.CBR) {
					return CheckerDecidion.NO_OPERATOR;
				}
				dbrackets++;
			}
		}
		if (dbrackets > 0) {
			return CheckerDecidion.NO_CLOSING_BR;
		}
		return CheckerDecidion.OK;
	}


	public UnboxingExpr(String expr) {
		this.expr = expr;
		lexems = new ArrayList<>();
		words = new ArrayList<>();
		lexems.add(Lexem.START);
		index = -1;
		while(lexems.get(lexems.size()-1) != Lexem.END) {
			constructNext();
		}
		indexLexem = 0;
		indexWord = -1;
		
	}
	
}
