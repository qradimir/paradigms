package def;

import def.Queue;

import java.util.function.Predicate;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue {
	protected int size;

	public abstract void enqueue(Object element);

	public abstract Object dequeue();

	public abstract Object element();

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}
	
	
	public abstract void clear();
	
	public abstract Queue construct();
	
	public Queue filter(Predicate<Object> predicate) {
		 Queue res = construct();
		 for(int i = 0; i < size; i++) {
			 Object e = dequeue();
			 if (predicate.test(e)) {
				 res.enqueue(e);
			 }
			 enqueue(e);
		 }
		 return res;
	}
	
	public Queue map(Function<Object, Object> function) {
		Queue res = construct();
		for(int i = 0; i < size; i++) {
			 Object e = dequeue();
			 res.enqueue(function.apply(e));
			 enqueue(e);
		 }
		return res;
	}
}
