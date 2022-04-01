package List;
import List.Node;
import java.io.*;

import matherror.IndexOutOfRangeError;
import matherror.ListEmptyError;
public class linkList<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length=0;
	private Node<T> head;
	public linkList()
	{
		head=new Node<T>(null);
	}
	public Boolean isEmpty()
	{
		return this.length==0;
	}
	public void append(T a)
	{
	  Node <T>temp=new Node<T>(a);
	  temp.setNext(head.getNext());
	  head.setNext(temp);
	  length++;
	}
	public void set(int pos,T a)
	{
		if(pos>=this.length||pos<0)
			throw new IndexOutOfRangeError("this index is out of range");
		Node<T> temp=head.getNext();
		int count=0;
		while(temp!=null)
		{
			if(count==pos)
			{
				temp.setNode(a);
				break;
			}
			temp=temp.getNext();
			count++;
		}
	}
	public void insert(int pos,T a)
	{
		if(pos>this.length||pos<0)
			throw new IndexOutOfRangeError("this index is out of range");
		Node<T> p=new Node<T>(a);
		Node<T> temp=this.head;
		int count=0;
		while(temp!=null)
		{
			if(count==pos)
			{
				p.setNext(temp.getNext());
				temp.setNext(p);
				this.length++;
				break;
			}
			temp=temp.getNext();
			count++;
		}
	}
	public int length()
	{
		return this.length;
	}
	public int find(T a)
	{
		Node<T> temp=this.head.getNext();
		int count=0;
		while(temp!=null)
		{
			if(temp.getNode().equals(a))
				return count;
			else
			{
				count++;
				temp=temp.getNext();
			}
		}
		return -1;
	}
	public Object get(int pos)
	{
		if(pos>=this.length||pos<0)
			throw new IndexOutOfRangeError("this index is out of range");
		Node<T> temp=this.head.getNext();
		int count=0;
		while(temp!=null)
		{
			if(count==pos)
			{
				return temp.getNode();
			}
			else
			{
				temp=temp.getNext();
				count++;
			}
		}
		throw new IndexOutOfRangeError("the object isn't not in the list");
	}
	public void remove(int pos)
	{
		if(pos>=this.length||pos<0)
			throw new IndexOutOfRangeError("this index is out of range");
		if(!isEmpty())
		{
		Node<T> temp=this.head;
		int count=0;
		while(temp!=null)
		{
			if(count==pos)
			{
				temp.setNext(temp.getNext().getNext());
				length--;
				break;
			}
			else
			{
				temp=temp.getNext();
				count++;
			}
		}
		}
		else
			throw new ListEmptyError("the list is empty");
	}
	public String toString()
	{
	StringBuilder sb=new StringBuilder();
	sb.append("[");
	for(int j=1;j<this.length+1;j++)
	{
		sb.append(this.get(j-1));
		if(j%6==0)sb.append("\n");
		else if(j!=length) sb.append(",");
	}
	sb.append("]");
	return sb.toString();
	}
	public linkList<T> copy()
	{
		linkList<T> newList=new linkList<T>();
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