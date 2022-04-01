package List;
import java.io.*;

import matherror.IndexOutOfRangeError;
import matherror.ListEmptyError;
public class seqList<T extends Comparable<T>> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length;
	private int max=100;
	private T[] data;
	@SuppressWarnings("unchecked")
	public seqList()
	{
		this.data=(T[])new Comparable[max];
	}
	@SuppressWarnings("unchecked")
	public seqList(int max)
	{
		this.max=max;
		data=(T[])new Comparable[max];
	}
	public Boolean isFull()
	{
		if(this.length==this.max)
			return true;
		else
			return false;
	}
	public Boolean isEmpty()
	{
		if(this.length==0)
		  return true;
	  else
		  return false;
	}
	public void append(T a)
	{
		if(!isFull())
		 data[this.length++]=a;
		else
		  throw new IndexOutOfRangeError("the list is full"); 
	}
	public void insert(int pos,T a)
	{
		if(pos>this.length||pos<0)throw new IndexOutOfRangeError("the index is out of range");
		if(!isFull())
		{
			for(int i=this.length;i>pos;i--)
			{
				data[i]=data[i-1];
			}
			data[pos]=a;
			this.length++;
		}
		else
			throw new IndexOutOfRangeError("the list is full");
	}
	public T get(int pos)
	{
		if(pos>=this.length||pos<0)throw new IndexOutOfRangeError("the index is out of range");
		return data[pos];
	}
	public void set(int pos,T a)
	{
		if(pos>=this.length||pos<0)throw new IndexOutOfRangeError("the index is out of range");
		data[pos]=a;
	}
	public int find(T a)
	{
		for(int i=0;i<this.length;i++)
		{
			if(data[i].equals(a))
			{
				return i;
			}
		}
		return -1;
	}
	public void sort()
	{
		sort.quickSort(data,0,this.length-1);
	}
	public int length()
	{
		return this.length;
	}
	public void remove(int pos)
	{
		if(pos>=this.length||pos<0) throw new IndexOutOfRangeError("this index is out of range");
		if(!isEmpty())
		{
		for(int i=pos;i<this.length;i++)
		{
			data[i]=data[i+1];
		}
		this.length--;
		}
		else 
			throw new ListEmptyError("this list is Empty");
	}
	public String toString()
	{
	StringBuilder sb=new StringBuilder();
	sb.append("[");
	for(int j=1;j<this.length+1;j++)
	{
		sb.append(String.format("%3.5f",this.data[j-1]));
		if(j%6==0)sb.append("\n");
		else if(j!=length) sb.append(",");
	}
	sb.append("]");
	return sb.toString();
	}
	public seqList<T> copy()
	{
		seqList<T> newList=new seqList<T>();
		newList.length=this.length;
		newList.max=this.max;
		int i;
		for(i=0;i<this.max;i++);
		{
			newList.data[i]=this.data[i];
		}
		return newList;
	}
}