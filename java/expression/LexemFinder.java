package expression;

import java.util.ArrayList;

class LexemFinder {
	ArrayList<Finds> graph;
	String lastFindValue;
	
	class Finds {
		Lexem found;
		ArrayList<Integer> e;
		
		public Finds() {
			e = new ArrayList<>(128);
			for (int i  = 0; i<128; i++) {
				e.add(0);
			}
			found = Lexem.WRONG;
		}
	}
	
	void add(String s, Lexem lex) {
		int t = 0;
		for (int i = 0; i < s.length(); i++) {
			if (graph.get(t).e.get(s.charAt(i)) == 0) {
				graph.get(t).e.add(s.charAt(i), graph.size());
				Finds f = new Finds();
				graph.add(f);
				
			}
			t = graph.get(t).e.get(s.charAt(i));
		}
		graph.get(t).found = lex;	
	}
	
	void addDigits() {
		Finds f = new Finds();
		f.e.add(Integer.valueOf('0'));
		for (int i = '1';i <= '9'; i++) {
			graph.get(0).e.add(i,graph.size());
			f.e.add(i,graph.size());
		}
		f.found = Lexem.CONST;
		graph.add(f);
	}
	
	Lexem find(String s, int index) {
		lastFindValue = "";
		String valueEnding = "";
		Lexem lex = Lexem.WRONG;
		int t = 0;
		for (int i = index; i < s.length(); i++) {
			if (s.charAt(i) >= 128) {
				break;
			}
			int x = graph.get(t).e.get(s.charAt(i));
			if (x == 0) {
				break;
			}
			valueEnding += s.charAt(i);
			if (graph.get(x).found != Lexem.WRONG) {
				lex = graph.get(x).found ; 
				lastFindValue += valueEnding;
				valueEnding = "";
			}
		}
		return lex;
	}
	
	void construct() {
		add("+", Lexem.ADD);
		add("-", Lexem.SUB);
		add("*", Lexem.MUL);
		add("/", Lexem.DIV);
		add("(", Lexem.OBR);
		add(")", Lexem.CBR);
		add("x", Lexem.VAR);
		add("y", Lexem.VAR);
		add("z", Lexem.VAR);
		add("abs", Lexem.ABS);
		add("sqrt", Lexem.SQRT);
		add(""+Integer.MIN_VALUE, Lexem.CONST);
		addDigits();
	}
	
	public LexemFinder() {
		graph = new ArrayList<>();
		graph.add(new Finds());
		construct();
	}
	
}
