package def;

public class SumLongHex {
	public static void main(String[] args) {
		long sum = 0;
		for (String s : args) {
			s = s.toUpperCase();
			String[] values = s.split("[\\p{javaWhitespace}]+");
			for (String value : values) {
				if (!value.isEmpty()) {
					if (value.startsWith("0X")) {
						value = value.substring(2);
						sum += Long.parseUnsignedLong(value, 16);
					} else {
						sum += Long.parseLong(value, 10);
					}
				}	
			}
		}
		System.out.print(sum);
	}
}