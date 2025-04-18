package nbradham.uniStack;

import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * Performs identically to a normal {@link java.util.Stack} except if you try to
 * push a item that already exists on the stack, it just moves the existing item
 * to the top of the stack. Utilizes {@link HashMap} for constant-time speed on
 * stack management and searching.
 * 
 * @param <E> Type of component elements.
 * 
 * @see java.util.Stack
 * 
 * @author Nickolas S. Bradham
 */
public class UniqueStack<E> {

	private final HashMap<E, Node<E>> map = new HashMap<>();
	private Node<E> head;

	/**
	 * Pushes {@code item} onto the top of the stack if it doesn't already exist or
	 * moves it to the top if it does.
	 * 
	 * @param item The item to push on top.
	 * @return the {@code item} argument.
	 */
	public E push(E item) {
		Node<E> n = map.get(item);
		if (n == null) {
			if ((head = new Node<E>(item, head)).prev != null)
				head.prev.next = head;
			map.put(item, head);
		} else if (n != head) {
			if (n.prev != null)
				n.prev.next = n.next;
			n.next.prev = n.prev;
			(head.next = n).prev = head;
			head = n;
		}
		return item;
	}

	/**
	 * Retrieves the top item of the stack without removing it.
	 * 
	 * @return The top element of the stack.
	 * @throws EmptyStackException if this stack is empty.
	 */
	public synchronized E peek() {
		if (head == null)
			throw new EmptyStackException();
		return head.v;
	}

	/**
	 * Removes the top element of the stack.
	 * 
	 * @return The element removed.
	 * @throws EmptyStackException if this stack is empty.
	 */
	public synchronized E pop() {
		if (head == null)
			throw new EmptyStackException();
		E ret = head.v;
		head = head.prev;
		map.remove(ret);
		return ret;
	}

	/**
	 * Tests if the stack is empty.
	 * 
	 * @return {@code true} if the stack contains at least one item; {@code false}
	 *         otherwise.
	 */
	public boolean hasItems() {
		return head != null;
	}

	/**
	 * Tests if this stack contains {@code item}.
	 * 
	 * @param item The item to look for.
	 * @return {@code true} if {@code item} exists in the stack; {@code false}
	 *         otherwise.
	 */
	public boolean contains(E item) {
		return map.containsKey(item);
	}

	/**
	 * Used in creation of the double linked list used by the stack.
	 * 
	 * @param <E> Type of component elements.
	 */
	private static class Node<E> {

		private final E v;
		private Node<E> prev, next;

		/**
		 * Constructs a new Node.
		 * 
		 * @param item     The item stored in this node.
		 * @param previous The previous Node in the chain.
		 */
		public Node(E item, Node<E> previous) {
			v = item;
			prev = previous;
		}
	}
}