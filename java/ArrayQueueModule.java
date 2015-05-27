package def;

//Inv: size >= 0 && elements[0..size-1] != NULL
public class ArrayQueueModule {
	private static int first, last, size;
	private static Object[] elements = new Object[5];
	
	//PRE : true
	//POST : elements.length >= capacity
	private static void ensureCapacity(int capacity) {
		if (capacity <= elements.length) {
			return;
		}
		Object[] newElements = new Object[2 * capacity];
		for (int i = 0; i < 2*capacity; i++) {
			newElements[i] = elements[(first + i) % elements.length];
		}
		first = 0;
		last = size;
		elements = newElements;
	}
	
	//PRE: element != NULL
    //POST: size = size' + 1 && elements[size - 1] = element
	public static void enqueue(Object element) {
		assert (element != null);
		
		ensureCapacity(size + 1);
		elements[last] = element;
		last = (last + 1) % elements.length;
		size++;
	}
	
	//PRE: size > 0 
	//POST: size = size' - 1 && R = elements'[0] && elements[i]=elements'[i-1] для i: 1..size
	public static Object dequeue() {
		assert (size > 0);
		int x = first;
		first = (first + 1) % elements.length;
		size--;
		return elements[x];
	}

	//PRE: size > 0
	//POST: R = elements[0]
	public static Object element() {
		assert (size > 0);
		
		return elements[first];
	}
	
	//PRE: element != NULL
    //POST: size = size' + 1 && elements[0] = element && elements[i] = elements'[i-1] для i: 0..size'	
	public static void push(Object element) {
		assert (element != null);
		
		ensureCapacity(size + 1);
		first = (first > 0) ? (first - 1) : (elements.length - 1);
		elements[first] = element;
		size++;		
	}
	
	//PRE: size > 0 
	//POST: size = size' - 1 && R = elements'[size] 	
	public static Object remove() {
		assert (size > 0);
		last = (last > 0) ? (last - 1) : (elements.length - 1);
		size--;
		return elements[last];
	}

	//PRE: size > 0
	//POST: R = elements[size - 1]
	public static Object peek() {
		return elements[(last > 0) ? (last - 1) : (elements.length - 1)];
	}
	
	//PRE: true
	//POST: R = size 
	public static int size() {
		return (size);
	}
	
	//PRE: true
	//POST: R = (0 == size)
	public static boolean isEmpty() {
		return (size == 0);
	}
	
	//PRE: true
	//POST: size = 0 && elements[i] = NULL  
	public static void clear()
	{
		while (!isEmpty()) {
			dequeue();
		}
		first = 0;
		last = 0;
	}
}
