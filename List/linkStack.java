package List;
import List.Node;
import java.io.*;

import matherror.ListEmptyError;
public class linkStack<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length=0;
	private Node<T> head;
	public linkStack()
	{
		this.length=0;
		head=new Node<T>(null);
	}
	public Boolean isEmpty()
	{return this.length==0;}
	public void push(T a)
	{
	  Node <T>temp=new Node<T>(a);
	  temp.setNext(head.getNext());
	  head.setNext(temp);
	  this.length++;
	}
	public T pop()
	{
	  Node<T> temp=this.head.getNext();
	  if(!isEmpty())
	  {
		  
		  this.head.setNext(head.getNext().getNext());
		  this.length--;
		  return temp.getNode();	
	  }		
		else
			throw new ListEmptyError("the stack is empty");
	}
	public T peek()
	{
		if(!isEmpty())
		{
		Node<T> temp=(Node<T>)this.head.getNext();
		return temp.getNode();
		}
		else
			throw new ListEmptyError("the stack is empty");
	}
	public int length()
	{
		return this.length;
	}
	public linkStack<T> copy()
	{
		linkStack<T> newList=new linkStack<T>();
		newList.length=this.length;
		Node<T> temp=this.head.getNext();
		Node<T> tmp=newList.head;
		for(int i=0;i<length;i++)
		{
			newList.head.setNext(new Node<T>(temp.getNode()));
			temp=temp.getNext();
			tmp=tmp.getNext();
		}
		return newList;
	}
}