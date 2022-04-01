package List;
import java.io.*;

import matherror.IndexOutOfRangeError;
import matherror.ListEmptyError;
public class seqStack<T> implements Serializable
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length=0;
	private T[] data;
	private int max=100;
	private int i;
	@SuppressWarnings("unchecked")
	public seqStack(int max)
	{
		this.max=max;
		data=(T[])new Object[max]; 
	}
	@SuppressWarnings("unchecked")
	public seqStack()
	{
		data=(T[])new Object[max]; 
	}
	public Boolean isEmpty(){return this.length==0;}
	public Boolean isFull(){return this.length==max;}
	public void push(T a)
	{
		if(!isFull())
			data[this.length++]=a;
		else
			throw new IndexOutOfRangeError("the stack is full");
	}
	public T peek()
	{
		if(!isEmpty())
			return data[this.length-1];
		else 
			throw new ListEmptyError("the stack is empty");
	}
	public T pop()
	{
		if(!isEmpty())
			return data[--this.length];
	    else 
		    throw new IndexOutOfRangeError("the stack is empty");
	}
	public int length()
	{return this.length;}
	public seqStack<T> copy()
	{
		seqStack<T> newList=new seqStack<T>();
		newList.length=this.length;
		newList.max=this.max;
		
		for(int i=0;i<this.max;i++);
		{
			newList.data[i]=this.data[i];
		}
		return newList;
	}
}