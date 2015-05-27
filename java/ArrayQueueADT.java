package def;

public class ArrayQueueADT {
	private int first, last, size;
	private Object[] elements = new Object[5];
	
	private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
		if (capacity <= queue.elements.length) {
			return;
		}
		Object[] newElements = new Object[2 * capacity];
		for (int i = 0; i < 2*capacity; i++) {
			newElements[i] = queue.elements[(queue.first + i) % queue.elements.length];
		}
		queue.first = 0;
		queue.last = queue.size;
		queue.elements = newElements;
	}
	
	public static void enqueue(ArrayQueueADT queue, Object element) {
		assert (element != null);
		
		ensureCapacity(queue, queue.size + 1);
		queue.elements[queue.last] = element;
		queue.last = (queue.last + 1) % queue.elements.length;
		queue.size++;
	}
	
	public static Object dequeue(ArrayQueueADT queue) {
		assert (queue.size > 0);
		int x = queue.first;
		queue.first = (queue.first + 1) % queue.elements.length;
		queue.size--;
		return queue.elements[x];
	}
	public static Object element(ArrayQueueADT queue) {
		assert (queue.size > 0);
		
		return queue.elements[queue.first];
	}
	
	public static void push(ArrayQueueADT queue, Object element) {
		assert (element != null);
		
		ensureCapacity(queue, queue.size + 1);
		queue.first = (queue.first > 0) ? (queue.first - 1) : (queue.elements.length - 1);
		queue.elements[queue.first] = element;
		queue.size++;		
	}
	
	public static Object remove(ArrayQueueADT queue) {
		assert (queue.size > 0);
		queue.last = (queue.last > 0) ? (queue.last - 1) : (queue.elements.length - 1);
		queue.size--;
		return queue.elements[queue.last];
	}
	
	public static Object peek(ArrayQueueADT queue) {
		return queue.elements[(queue.last > 0) ? (queue.last - 1) : (queue.elements.length - 1)];
	}

	public static int size(ArrayQueueADT queue) {
		return (queue.size);
	}
	
	public static boolean isEmpty(ArrayQueueADT queue) {
		return (queue.size == 0);
	}
	
	public static void clear(ArrayQueueADT queue) {
		while (!isEmpty(queue)) {
			dequeue(queue);
		}
		queue.first = 0;
		queue.last = 0;
	}
}
