/* *
 * Luke McDougall
 *
 * Implementation of double ended double linked linked list.
 *
 * Last updated 20/03/2019
 *
 * Previously submitted as part of Prac ##
 */
import java.util.*;
public class DSALinkedList<T> implements Iterable<T>
{
	//INNER CLASSES 
	
	/* DSALinkedListIterator
	 * Iterator object for linked list allows elements in the list
	 * to be iterated over in a for each loop etc.
	 * 
	 * No need to parameterize because DSALinkedListIterator class 
	 * has access to type parameter T from enclosing class
	 */
	private class DSALinkedListIterator implements Iterator<T> 
	{
		private DSALinkedList<T>.DSAListNode cursor; 
		
		public DSALinkedListIterator(DSALinkedList<T> list)
		{
			cursor = list.head;
		}
		
		public boolean hasNext()
		{
			return cursor != null;
		}
		
		public T next()
		{
			T value = null;
			if(this.hasNext())
			{
				value = cursor.value;
				cursor = cursor.next;
			}
			return value;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException("Not supported");
		}
	}
	
	/* DSAListNode
	 * Container for elements in the linked list, stores pointer
	 * to both previous and next nodes in the list.
	 * 
	 * No need to parameterize because DSAListNode class has access
	 * to type parameter T from enclosing class
	 */
	private class DSAListNode
	{
		//class fields
		public T value;
		public DSAListNode next;
		public DSAListNode prev;
		
		//Constructor
		public DSAListNode(T value)
		{
			this.value = value;
			next = null;
			prev = null;
		}
	}
	
	//END INNER CLASSES
	
	//class fields
	private DSAListNode head;
	private DSAListNode tail;
	
	//constructors
	//default
	public DSALinkedList()
	{
		head = null;
		tail = null;
	}
	
	//alternate
	public DSALinkedList(T value)
	{
		DSAListNode node = new DSAListNode(value);
		head = node;
		tail = node;
	}
	
	/* iterator
	 * Implementation of iterator method from Iterable interface
	 * returns an iterator object.
	 */
	public Iterator<T> iterator()
	{
		return new DSALinkedListIterator(this);
	}
	
	/* insertFirst
	 * add a new link node to the head of the list holding the passed object
	 */
	public void insertFirst(T value)
	{
		DSAListNode newNode = new DSAListNode(value);
		if(!this.isEmpty())
		{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		else
		{
			head = newNode;
			tail = newNode;
		}
	}
	
	/* insertLast
	 * add a new link node to the end of the list holding the passed value
	 */
	public void insertLast(T value)
	{
		DSAListNode newNode = new DSAListNode(value);
		if(!this.isEmpty())
		{
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		else
		{
			tail = newNode;
			head = newNode;
		}
	}
	
	/* isEmpty
	 * return true if list has no nodes, false otherwise
	 */
	public boolean isEmpty()
	{
		return head == null; //could also be tail == null
	}
	
	/* peekFirst
	 * return the value of head if list isn't empty
	 */
	public T peekFirst()
	{
		T obj = null;
		if(!this.isEmpty())
		{
			obj = head.value;
		}
		else
		{
			throw new IllegalArgumentException("Error: List is empty");
		}
		return obj;
	}
	
	/* peekLast
	 * return value of tail if list isn't empty
	 */
	public T peekLast()
	{
		T obj = null;
		if(!this.isEmpty())
		{
			obj = tail.value;
		}
		else
		{
			throw new IllegalArgumentException("Error: List is empty");
		}
		return obj;
	}
	
	/* removeFirst
	 * returns value of head and remove that node from the list
	 * if list isn't empty
	 */
	public T removeFirst()
	{
		T obj = null;
		if(!this.isEmpty() && head.next != null) // number of nodes > 1
		{
			obj = head.value;
			head = head.next;
			head.prev = null;
		}
		else if(!this.isEmpty() && head.next == null) // number of nodes == 1
		{
			obj = head.value;
			head = null;
			tail = null;
		}
		else
		{
			throw new IllegalArgumentException("Error: List is empty");
		}
		return obj;
	}
	
	/* removeLast
	 * returns value of tail and removes that node from the list
	 * if list isn't empty
	 */
	public T removeLast()
	{
		T obj = null;
		if(!this.isEmpty() && tail.prev != null) // number of nodes > 1
		{
			obj = tail.value;
			tail = tail.prev;
			tail.next = null;
		}
		else if(!this.isEmpty() && tail.prev == null) // number of nodes == 1
		{
			obj = tail.value;
			tail = null;
			head = null;
		}
		else
		{
			throw new IllegalArgumentException("Error: List is empty");
		}
		return obj;
	}

}
