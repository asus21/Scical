package core;

import java.util.ArrayList;

import matherror.AddError;
import matherror.MulError;

public class Mul {

	/**
	 * @param args
	 */
	private ArrayList<Object> result=new ArrayList<Object>();
	public static void main(String[] args) {
		Add add=new Add(1,2,new Matrix(1,2));
		System.out.println(add);
	}
	public Mul() {

	}
	public Mul(Object...objects)
	{
		for(Object x:objects)
		{
			result.add(x);
			if(x instanceof Matrix)
				throw new MulError("unsupported Matrix type object");
			if(x instanceof String)
				throw new MulError("unsupported String type object");
			if(x.getClass().isArray())
				throw new MulError("unsupported Array type object");
		}
	}
	public double evalf()
	{
		int res=0;
		for(Object x:result)
		{
			if(x instanceof Pow)
			{
				res+=((Pow)x).evalf();
			}
			if(x instanceof Fraction)
			{
				res+=((Fraction)x).evalf();
			}
			if(x instanceof Complex)
			{
				throw new AddError("unsupported Complex type object");
			}
		}
		return res;
	}
	public Object getElement(int index)
	{
		return result.get(index);
	}
	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<result.size();i++)
		{
			buffer.append(result.get(i));
			if(i!=result.size()-1)
			{
				buffer.append("*");
			}
		}
		return buffer.toString();
	}
}
