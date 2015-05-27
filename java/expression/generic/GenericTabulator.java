package expression.generic;

import expression.generic.boxes.*;


public class GenericTabulator implements Tabulator {

	private int x1, x2, y1, y2, z1, z2;

	public <T extends Number> Object[][][] getTabulate(GExpression<T> expr,
			NFactory<T> factory) {
		Numeric<T> x = factory.create("0");
		Numeric<T> y = factory.create("0");
		Numeric<T> z = factory.create("0");
		Object[][][] ans = new Object[x2 - x1 + 1][][];
		for (int i = x1; i <= x2; i++) {
			ans[i - x1] = new Object[y2 - y1 + 1][];
			for (int j = y1; j <= y2; j++) {
				ans[i - x1][j - y1] = new Object[z2 - z1 + 1];
				for (int k = z1; k <= z2; k++) {
					x.set(i);
					y.set(j);
					z.set(k);
					try {
						ans[i - x1][j - y1][k - z1] = expr.evaluate(x, y, z).value();
					} catch (ArithmeticException e) {
						ans[i - x1][j - y1][k - z1] = null;
					}
				}
			}
		}
		return ans;
	}

	@Override
	public Object[][][] tabulate(String mode, String expression, int x1,
			int x2, int y1, int y2, int z1, int z2) throws Exception {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.z1 = z1;
		this.z2 = z2;
		switch (mode) {
			case "i" :
				return getTabulate(IntBox.parser.parse(expression), IntBox.factory);
			case "d" :
				return getTabulate(DoubleBox.parser.parse(expression), DoubleBox.factory);
			case "bi" :
				return getTabulate(BigIntegerBox.parser.parse(expression), BigIntegerBox.factory);
			case "u" :
				return getTabulate(UIntBox.parser.parse(expression), UIntBox.factory);
			case "f" :
				return getTabulate(FloatBox.parser.parse(expression), FloatBox.factory);
			case "b" :
				return getTabulate(ByteBox.parser.parse(expression), ByteBox.factory);
			default :
				return null;
		}
	}
}
