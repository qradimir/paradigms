package def;

public class ArrayQueue {
	private int first, last, size;
	private Object[] elements = new Object[5];
	
	private void ensureCapacity(int capacity) {
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
	
	public void enqueue(Object element) {
		assert (element != null);
		
		ensureCapacity(size + 1);
		elements[last] = element;
		last = (last + 1) % elements.length;
		size++;
	}
	
	public Object dequeue() {
		assert (size > 0);
		int x = first;
		first = (first + 1) % elements.length;
		size--;
		return elements[x];
	}

	public Object element() {
		assert (size > 0);
		
		return elements[first];
	}
	
	public void push(Object element) {
		assert (element != null);
		
		ensureCapacity(size + 1);
		first = (first > 0) ? (first - 1) : (elements.length - 1);
		elements[first] = element;
		size++;		
	}
	
	public Object remove() {
		assert (size > 0);
		last = (last > 0) ? (last - 1) : (elements.length - 1);
		size--;
		return elements[last];
	}
	
	public Object peek() {
		return elements[(last > 0) ? (last - 1) : (elements.length - 1)];
	}
	
	public int size() {
		return (size);
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public void clear()
	{
		while (!isEmpty()) {
			dequeue();
		}
		first = 0;
		last = 0;
	}
}

