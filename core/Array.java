package core;

import java.util.Arrays;
import matherror.ArrayError;
import matherror.MatrixError;

public class Array {

	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	private double[] array;
	private int[] dim;
	public static void main(String[] args) {
		Array array=new Array(4,3,4);
		array.setValue(new int[]{0,0},1,2,3,2);
		System.out.println(array);
	}
	public Array(double...array)
	{
		if(array==null) throw new MatrixError("The double type array is null");
		if(array.length==0) throw new MatrixError("the length of array must be greater than zero");
		dim=new int[array.length];
		for(int i=0;i<array.length;i++)
		{
			this.array[i]=array[i];
		}
	}
	public Array(int...dim) {
		if(dim.length==0) 
		{
			throw new ArrayError("missing required argument dimension or dimention's length is empty");
		}
		int temp = 1;
		this.dim=dim;
		for(int x:dim)
		{
			if(x==0) throw new ArrayError("dimension cann't be zero");
				temp*=x;
		}
		array=new double[temp];
		
	}
	public Array add(Array a)
	{
		if(a==null) throw new ArrayError("the Array is null");
		Array resArray=new Array(this.dim);
		if(compareToDim(a)!=0)
		{
			for(int i=0;i<array.length;i++)
			{
				resArray.array[i]=this.array[i]+a.array[i];
			}
		}
		else {
			throw new ArrayError("cannot add shape "+new Matrix(dim).T()+" to shape"+new Matrix(a.dim).T());
		}
		return resArray;
	}
	public Array sub(Array a)
	{
		if(a==null) throw new ArrayError("the Array is null");
		Array resArray=new Array(this.dim);
		if(compareToDim(a)!=0)
		{
			for(int i=0;i<array.length;i++)
			{
				resArray.array[i]=this.array[i]-a.array[i];
			}
		}
		else {
			throw new ArrayError("cannot add shape "+new Matrix(dim).T()+" to shape"+new Matrix(a.dim).T());
		}
		return resArray;
	}
	public double[] getValue(int ...pos)
	{
		if(pos.length==0) throw new ArrayError("missing required argument position or the position's length is empty");
		if(pos.length>dim.length) throw new ArrayError("cannot find the position"+new Matrix(pos).T()+"in shape"+new Matrix(dim).T());
		int temp=0;
		double[] res=null;
		double[] count=new double[dim.length];
		int length=1;
		for(int i=pos.length;i<dim.length;i++)
		{
			length*=dim[i];
		}
		res=new double[length];
		 Arrays.fill(count,1);
		 for(int i=0;i<dim.length;i++)
		 {
			 for(int j=i+1;j<dim.length;j++)
			 {
				 count[i]*=dim[j];
			 }
		 }
		 for(int i=0;i<pos.length;i++)
		 {
			 temp+=pos[i]*count[i];
		 }
		 for(int i=0;i<res.length;i++)
		 {
			 res[i]=array[temp+i];
		 }
		 return res;
	}
	public void setValue(int[] pos,double...value)
	{
		if(pos.length==0) throw new ArrayError("missing required argument position or the position's length is empty");
		if(pos.length>dim.length) throw new ArrayError("cannot find the position"+new Matrix(pos).T()+"in shape"+new Matrix(dim).T());
		int temp=0;
		double[] count=new double[dim.length];
		int length=1;
		for(int i=pos.length;i<dim.length;i++)
		{
			length*=dim[i];
		}
		if(value.length!=length) throw new ArrayError("the value's length("+value.length+") doesn't match the size("+length+")");
		 Arrays.fill(count,1);
		 for(int i=0;i<dim.length;i++)
		 {
			 for(int j=i+1;j<dim.length;j++)
			 {
				 count[i]*=dim[j];
			 }
		 }
		 for(int i=0;i<pos.length;i++)
		 {
			 temp+=pos[i]*count[i];
		 }
		 for(int i=0;i<value.length;i++)
		 {
			 array[temp+i]=value[i];
		 }
	}
	public int[] shape()
	{
		return dim;
	}
	public int size()
	{
		int temp=1;
		for(int x:dim)
			temp*=x;
		return temp;
	}
	private void string(StringBuffer bf,int left,int right,int i)
	{
		if(i>=dim.length) return;
		if(right>array.length)return;
		int gap=(right-left)/dim[i];
		if(i==1&&left!=0&&left%(array.length/dim[0])==0) bf.append(" ");
		bf.append("[");
		if(i==dim.length-1)
		{
			int j=0;
			for(j=left;j<right;j++)
			{
				bf.append(String.format("%.5f",array[j]));
				if(j!=right-1) bf.append(",");		
			}
			bf.append("]");
			if(right%(array.length/dim[0])!=0) bf.append(",");
			return ;
		}
		for(int j=gap;j<=right-left;j+=gap)
		{
			string(bf, j-gap+left, j+left, i+1);
		}
		bf.append("]");
		if(i==1&&right!=array.length) bf.append(",\n");
	}
	public int compareToDim(Object obj)
	{
		Array array=null;
		if(obj==null) throw new ArrayError("the Array is null");
		if(obj instanceof Array)
		{
			array=(Array)obj;
			int isequal=0;
			if(this.dim.length==array.dim.length)
			{
				for(int i=0;i<dim.length;i++)
					if(dim[i]<array.dim[i])
					{
						isequal=-1;
						return isequal;
					}
					else {
						isequal=1;
					}
			}
			return isequal;
		}
		else throw new ArrayError(this.getClass().getSimpleName()+" and "+obj.getClass().getSimpleName()+" cannot be compared");
	}
	@Override
	public boolean equals(Object obj) {
		Array a=null;
		if(obj==null)throw new ArrayError("the Array is null");
		if(obj instanceof Array)
		{
			a=(Array)obj;
			boolean isequal=true;
			if(this.dim.length==a.dim.length)
			{
				for(int i=0;i<dim.length;i++)
					if(dim[i]!=a.dim[i])
					{
						isequal=false;
						return isequal;
					}
				for(int i=0;i<this.array.length;i++)
				{
						if(this.array[i]!=a.array[i])
							isequal=false;
				}
			}
			return isequal;
		}
		else {
			throw new MatrixError(this.getClass().getSimpleName()+" and "+obj.getClass().getSimpleName()+" cannot be compared");
		}
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		if(dim.length<=2)
		{
			if(dim.length==1)
			{
				sb.append("[");
				for(int i=0;i<this.array.length;i++)
				{
					sb.append(String.format("%.5f",array[i]));	
					if(i!=this.array.length-1)sb.append(",");
				}
				sb.append("]");
			}
			else
			{
				sb.append("[");
				if(this.dim[0]>=50)
				{
					for(int i=0;i<50;i++)
					{
					    sb.append("[");
						for(int j=0;j<this.dim[1];j++)
						{
							if(i<45)
							{
								sb.append(String.format("%.5f",array[i*dim[1]]));
							}
							else
							{
								sb.append("   .   ");
							}
							if(j!=this.dim[1]-1)sb.append(",");
							else sb.append("]");
						}
						if(i!=50-1)
							sb.append("\n ");
						else sb.append("]");
					}
				}
				else {
					for(int i=0;i<this.dim[0];i++)
					{
					    sb.append("[");
						for(int j=0;j<this.dim[1];j++)
						{
							sb.append(String.format("%.5f",array[i*dim[1]+j]));	
							if(j!=this.dim[1]-1)sb.append(",");
							else sb.append("]");
						}
						if(i!=this.dim[0]-1)
							sb.append("\n ");
						else sb.append("]");
					}
				}
			}
		}
		else string(sb,0,array.length,0);
		return sb.toString();
	}
}
