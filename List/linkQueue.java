package List;
import List.Node;
import java.io.*;

import matherror.ListEmptyError;
public class linkQueue<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length;
	private Node<T> head;
	private Node<T> tail;
	public linkQueue()
	{
		head=tail=new Node<T>(null);
	}
	public T head()
	{
		return (T)head.getNext().getNode();
	}
	public T tail()
	{
		return (T)tail.getNode();
	}
	public Boolean isEmpty()
	{
		return this.length==0;
	}
	public void inqueue(T a)
	{
		Node<T> temp=new Node<T>(a);
		tail.setNext(temp);
		tail=temp;
		this.length++;
	}
	public T dequeue()
	{
		Node<T> temp=head.getNext();
		T a=(T)temp.getNode();
		if(!isEmpty())
		{
			if(temp!=tail)
				head.setNext(temp.getNext());
			else
				tail=head;
			this.length--;
			return a;	
		}
		else 
			throw new ListEmptyError("the queue is empty");
	}
	public int length()
	{return this.length;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public linkQueue<T> copy()
	{
		linkQueue<T> newList=new linkQueue<T>();
		newList.length=this.length;
		Node<T> temp=this.head.getNext();
		Node<T> tmp=newList.head;
		for(int i=0;i<this.length;i++)
		{
			tmp.setNext(new Node(temp.getNode()));
		}
		return newList;
	}
}