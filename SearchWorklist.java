package cse12pa3student;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Class to implement SearchWorklist as a Stack and a Queue.
 * You can use any built-in Java collections for this class.
 */

class StackWorklist implements SearchWorklist
{
	
	Stack<Square> stack = new Stack<>();
	@Override
	public void add( Square c )
	{
		stack.push( c );
	}

	@Override
	public Square remove() 
	{
		return stack.pop();
	}

	@Override
	public boolean isEmpty() 
	{
		return stack.isEmpty();
	}
}

class QueueWorklist implements SearchWorklist
{

	Queue<Square> queue = new LinkedList<>();
	@Override
	public void add( Square c ) 
	{
		queue.add( c );
	}

	@Override
	public Square remove()
	{
		return queue.remove();
	}

	@Override
	public boolean isEmpty() 
	{		
		return queue.isEmpty();
	}
}

public interface SearchWorklist 
{
	void add( Square c );
	Square remove();
	boolean isEmpty();
}
