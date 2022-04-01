package List;
import java.io.*;
public class Node<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T data;
	private Node<?> next;
	public Node(T a)
	{
		this.data=a;
		this.next=null;
	}
	public void setNode(T a)
	{
		this.data=a;
	}
	public T getNode()
	{
		return this.data;
	}
	public void setNext(Node<T> next)
	{
		this.next=next;
	}
	@SuppressWarnings("unchecked")
	public Node<T> getNext()
	{
		return (Node<T>) this.next;
	}
}
