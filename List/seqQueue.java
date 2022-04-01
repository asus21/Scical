package List;
import java.io.*;

import matherror.IndexOutOfRangeError;
import matherror.ListEmptyError;
public class seqQueue<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length=0;
	private int head,tail;
	private T[] data;
	private int max=100;
	@SuppressWarnings("unchecked")
	public seqQueue()
	{
		head=tail=0;
		data=(T[])new Object[max];
	}
	@SuppressWarnings("unchecked")
	public seqQueue(int max)
	{
		head=tail=0;
		this.max=max;
		data=(T[])new Object[max];
	}
	public T head()
	{
		return data[head];
	}
	public T tail()
	{
		return data[tail];
	}
	public Boolean isFull()
	{
		return this.length==this.max;
	}
	public Boolean isEmpty()
	{
		return this.length==0;
	}
	public void inqueue(T a)
	{
		if(!isFull())
		{
			data[tail]=a;
			tail=(tail+1)%this.max;
			this.length++;
		}
		else
			throw new IndexOutOfRangeError("the queue is full");
	}
	public T dequeue()
	{
		T temp=data[head];
		if(!isEmpty())
		{
			head=(head+1)%max;
			this.length--;
			return temp;				
		}
		else 
			throw new ListEmptyError("the queue is empty");
	}
	public int length()
	{
		return this.length;
	}
	public seqQueue<?> copy()
	{
		seqQueue<T> newQueue=new seqQueue<T>();
		newQueue.length=this.length;
		newQueue.head=this.head;
		newQueue.tail=this.tail;
		newQueue.max=this.max;
		for(int i=0;i<this.max;i++)
		{
			newQueue.data[i]=this.data[i];
		}
		return newQueue;
	}
}