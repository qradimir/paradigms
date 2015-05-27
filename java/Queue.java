package def;

import java.util.function.Function;
import java.util.function.Predicate;

	//Inv: (size >= 0) && (elements[0..size-1] != null)
public interface Queue {
		
		//Pred: (element != null)
		//Post: (size = size' + 1) && (elements[size - 1] = element)
	public void enqueue(Object element);
	
		//Pred: (size > 0)
		//Post: (size = size' - 1) && (R = elements'[0]) && (elements[i] = elements'[i + 1] для i : 0..size-1)
	public Object dequeue();
	
		//Pred: (size > 0)
		//Post:	(R = elements'[0])
	public Object element();
	
		//Pred: true
		//Post: R = size
	public int size();
	
		//Pred: true
		//Post:	R = (size == 0)
	public boolean isEmpty();
	
		//Pred: true
		//Post: R = def.Queue : def.Queue.elements = {x | x in this.elements && predicate(x) == true}
	public Queue filter(Predicate<Object> predicate);
	
		//Pred: true
		//Post: R = def.Queue : def.Queue.elements[i] = function.apply(this.elements[i]) для  i: 0..size-1
	public Queue map(Function<Object, Object> function);
} 
