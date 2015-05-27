package expression.generic;

public class NFactory<T extends Number> {
	public NCreator<T> creator;
	public NParser<T> parser;
	
	public NFactory(NCreator<T> creator, NParser<T> parser) {
		this.creator = creator;
		this.parser = parser;
	}
	
	public Numeric<T> create(String s) {
		return  creator.create(parser.parse(s));
	}
	
	public Numeric<T> create(T value) {
		return creator.create(value);
	}
}
