package def;

public class LinkedQueue extends AbstractQueue {
	
	private class Node {
		Node next;
		Object value;
		
		public Node(Node next, Object value) {
			assert (value != null);
			
			this.next = next;
			this.value = value;
		}
	}
	
	private Node head, tail;
	
	public void enqueue(Object element) {
			assert (element != null);
			if (size > 0)
			{
				tail.next = new Node(null,  element);
				tail = tail.next;
			}
			else
			{
				head = tail = new Node(null,  element); 
			}
			size++;
	}  
	
	public Object dequeue() {
		assert (size > 0);
		Object res = head.value;
		head = head.next;
		size--;
		return res;
	}
	
	public Object element() {
		assert (size > 0);
		return head.value;
	}
	
	public void clear() {
		size = 0;
		head = tail = null;
	}
	
	public LinkedQueue construct() {
		return new LinkedQueue();
	}
}
