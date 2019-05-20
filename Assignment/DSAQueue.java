/* *
 * Luke McDougall
 *
 * Implementation of a generic queue
 *
 * Last updated 19/05/2019
 *
 * Previously submitted as part of Prac ##
 * */

public class DSAQueue<T>
{
	//class fields
	private DSALinkedList<T> queue;
	private int size;
    
	//constructors
	//default
	public DSAQueue()
	{
		size = 0;
		queue = new DSALinkedList<T>();
	}
	
	//alternate
	public DSAQueue(T value)
	{
		size = 1;
		queue = new DSALinkedList<T>(value);
	}
	
	public int getSize()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public void enqueue(T value)
	{
		size++;
		queue.insertFirst(value);
	}
	
	public T dequeue()
	{
		T value = null;
		if(size > 0)
		{
			value = queue.removeLast();
			size--;
		}
		else
		{
			throw new IllegalArgumentException("Error: Queue is empty");
		}
		return value;
	}
	
	public T peek()
	{
		T value = null;
		if(size > 0)
		{
			value = queue.peekLast();
		}
		else
		{
			throw new IllegalArgumentException("Error: Queue empty");
		}
		return value;
	}
	
	public String toString()
	{
		String result = "";
		for(T s : queue)
		{
			result += s.toString() + "\n";
		}
		return result;
	}
}
