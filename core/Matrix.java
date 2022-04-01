package core;

import matherror.*;

import java.io.*;
import java.util.Arrays;
public class Matrix implements Serializable
{
	/**
	 * <p>A Matrix libraray realizes multiply,addition,substraction and some operation associated with
	 * Matrix</p>
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private double[][] mat;
	public Matrix(double... array)
	{
		if(array==null) throw new MatrixError("The double type array is null");
		if(array.length==0) throw new MatrixError("missing required argument array or the array is empty");
		this.mat=new double[array.length][1];
		for(int i=0;i<array.length;i++)
		{
			this.mat[i][0]=array[i];
		}
		this.row=array.length;
		this.col=1;
	}
	public Matrix(double[][] a)
	{
		if(a==null)throw new MatrixError("The double type array is null");
		if(a.length==0) throw new MatrixError("missing required argument array or the array is empty");
		this.mat=new double[a.length][a[0].length];
		this.row=a.length;
		this.col=a[0].length;
		try
		{
			for(int i=0;i<a.length;i++)
			{
				for(int j=0;j<a[i].length;j++)
				{
					this.mat[i][j]=a[i][j];
				}
			}
		}
		catch(Exception e)
		{
			throw new MatrixError("Each row's length is not equal");
		}
	}
	public Matrix(int row,int col)
	{
		if(row<=0) throw new MatrixError("Row must be greater than zero");
		if(col<=0) throw new MatrixError("Column must be greater than zero");
		this.row=row;
		this.col=col;
		this.mat=new double[row][col];
	}
	public Matrix(int row,int col,double[] array)
	{
		if(array==null)throw new MatrixError("the double type array is null");
		if(array.length!=row*col) throw new MatrixError("The double type array's length("+array.length+") doesn't match production of row*col("+row*col+")");
		this.row=row;
		this.col=col;
		mat=new double[row][col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				mat[i][j]=array[i*col+j];
			}
		}
	}
	public Matrix(int row,int col,int[] array) {
		if(array==null)throw new MatrixError("The double type array is null");
		if(array.length==0) throw new MatrixError("the length of array must be greater than zero");
		this.mat=new double[array.length][1];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				mat[i][j]=array[i*col+j];
			}
		}
		this.row=array.length;
		this.col=1;
	}
	public Matrix(int[] array) {
		if(array==null)throw new MatrixError("The double type array is null");
		if(array.length==0) throw new MatrixError("the length of array must be greater than zero");
		this.mat=new double[array.length][1];
		for(int i=0;i<array.length;i++)
		{
			this.mat[i][0]=array[i];
		}
		this.row=array.length;
		this.col=1;
	}
	public static Matrix random(int row,int col)
	{
		Matrix matrix=new Matrix(row,col);
	    for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				matrix.mat[i][j]=Math.random();
			}
		}
		return matrix;
	}
	public static Matrix diag(double... a)
	{
		if(a==null)throw new MatrixError("The double type array is null");
		if(a.length==0) throw new MatrixError("missing required argument array or the array is empty");
		Matrix b=new Matrix(a.length,a.length);
		for(int i=0;i<b.row();i++)
		{
			for(int j=0;j<b.col();j++)
			{
				if(i==j)
				{
				  b.mat[i][j]=a[i];
				}
			}
		}
		return b;
	}
	public static Matrix full(int row,int col,double alpha)
	{
		Matrix a=new Matrix(row,col);
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				a.mat[i][j]=alpha;
			}
		}
		return a;
	}
	public static Matrix ones(int row,int col)
	{
		Matrix a=new Matrix(row,col);
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				a.mat[i][j]=1;
			}
		}
		return a;
	}
	public static Matrix eyes(int row,int col)
	{
		Matrix matrix=new Matrix(row,col);
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
			{
				if(i==j)
					matrix.mat[i][j]=1;
			}
		return matrix;
	}
	
	public static Matrix zeros(int row,int col)
	{
		Matrix matrix=new Matrix(row,col);
		return matrix;
	}
	public int row()
	{
		return this.row;
	}
	public int col()
	{
		return this.col;
	}
	public int size()
	{
		return this.row*this.col;
	}
	public int[] shape()
	{
		int[] a=new int[2];
		a[0]=this.row;
		a[1]=this.col;
		return a;
	}
	public Matrix reshape(int m,int n)
	{
		if(m*n!=this.row*this.col)throw new MatrixError("Cannot reshape Matrix("+this.row+","+this.col+") to Matrix("+m+","+n+")");
		Matrix b=new Matrix(m,n);
		for(int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				int temp=this.col*i+j+1;
				int y=(temp-1)/b.col;
				int x=temp-b.col*y-1;
				b.mat[y][x]=this.mat[i][j];
			}
		}
		return b;
	}
	public double max()
	{
		double temp=-1000000;
		for(int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				if(this.getValue(i, j)>temp)
				{
					temp=this.getValue(i, j);
				}
			}
		}
		return temp;
	}
	public Matrix max(int axis)
	{
		if(axis==0)
		{
		Matrix b=new Matrix(this.row,1);
		for(int i=0;i<this.row;i++)
		{
			double temp=-1000000;
			for(int j=0;j<this.col;j++)
			{
				if(this.getValue(i, j)>temp)
				{
					temp=this.getValue(i, j);
				}
			}
			b.setValue(i,0,temp);
		}
		return b;
		}
		else if(axis==1)
		{
			Matrix b=new Matrix(1,this.col);
			for(int i=0;i<this.col;i++)
		    {
				double temp=-1000000;
				for(int j=0;j<this.row;j++)
				{
					if(this.getValue(j, i)>temp)
					{
						temp=this.getValue(j, i);
					}
				}
				b.setValue(0,i,temp);
			}
			return b;
		}
		else 
		{
			throw new MatrixError("the axis="+axis+" is out of shape("+this.row+","+this.col+") axis in {0,1}");
		}
	}
	public double min()
	{
		double temp=1000000;
		for(int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				if(this.getValue(i, j)<temp)
				{
					temp=this.getValue(i, j);
				}
			}
		}
		return temp;
	}
	public Matrix min(int axis)
	{
		if(axis==0)
		{
		Matrix b=new Matrix(this.row,1);
		for(int i=0;i<this.row;i++)
		{
			double temp=1000000;
			for(int j=0;j<this.col;j++)
			{
				if(this.getValue(i, j)<temp)
				{
					temp=this.getValue(i, j);
				}
			}
			b.setValue(i,0,temp);
		}
		return b;
		}
		else if(axis==1)
		{
			Matrix b=new Matrix(1,this.col);
		for(int i=0;i<this.col;i++)
		{
			double temp=1000000;
			for(int j=0;j<this.row;j++)
			{
				if(this.getValue(j, i)<temp)
				{
					temp=this.getValue(j, i);
				}
			}
			b.setValue(0,i,temp);
		}
		return b;
		}
		else 
		{
			throw new MatrixError("the axis="+axis+" is out of shape("+this.row+","+this.col+") axis in {0,1}");
		}
	}
	public Matrix mean()
	{
		if(this.col==1&&row!=1)return linalg.mean(this, 1);
		else if(this.col!=1&&row==1)return linalg.mean(this, 0);
		else return linalg.mean(this, 0);
	}
	public Matrix mean(int axis)
	{
	 return linalg.mean(this, axis);
	}
	public Matrix var()
	{
		if(this.col==1&&row!=1)return linalg.var(this,1);
		else if(this.col!=1&&row==1)return linalg.var(this,0);
		else return linalg.var(this, 0);
	}
    public Matrix var(int axis)
    {
    	 return linalg.var(this, axis);
    }
	public Matrix std()
	{
		if(this.col==1&&row!=1)return linalg.std(this,1);
		else if(this.col!=1&&row==1)return linalg.std(this,0);
		else return linalg.std(this, 0);
	}
	public Matrix std(int axis)
	{
	 return linalg.std(this, axis);
	}
	public double sum()
	{
		double temp=0;
		for(int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				temp=temp+this.mat[i][j];
			}
		}
		return temp;
	}
	public Matrix getRowChoice(int... pos)
	{
		if(pos==null)  throw new MatrixError("The position argument is null");
		if(pos.length==0) throw new MatrixError("missing required argument position or the position argument's length is zero");
		Matrix temp=new Matrix(pos.length,this.col);
		for(int k=0;k<pos.length;k++)
		{
			for(int j=0;j<col;j++)
			{
				temp.setValue(k,j,getValue(pos[k], j));
			}
		}
		return temp;
	}
	public Matrix getColChoice(int... pos)
	{
		if(pos==null)  throw new MatrixError("The position argument is null");
		if(pos.length==0) throw new MatrixError("missing required argument position or the position argument's length is zero");
		Matrix temp=new Matrix(row,pos.length);
		for(int j=0;j<row;j++)
		{
			for(int k=0;k<pos.length;k++)
			{
				temp.setValue(j,k,getValue(j,pos[k]));
			}
		}
		return temp;
	}
	public double[][] getArrayMat()
	{
		return this.mat;
	}
	public double getValue(int i,int j)
	{
		if(i<0) throw new MatrixError("Row must be greater than zero");
		if(j<0) throw new MatrixError("Column must be greater than zero");		
		if(i>=this.row)throw new MatrixError("the ("+i+","+j+") is out of range("+this.row+","+this.col+") dim(1)");
		if(j>=this.col)throw new MatrixError("the ("+i+","+j+") is out of range("+this.row+","+this.col+") dim(2)");
		return this.mat[i][j];
	}
	public void setValue(int i,int j,double value)
	{
		if(i<0) throw new MatrixError("Row must be greater than zero");
		if(j<0) throw new MatrixError("Column must be greater than zero");		
		if(i>=this.row)throw new MatrixError("the ("+i+","+j+") is out of range("+this.row+","+this.col+") dim(1)");
		if(j>=this.col)throw new MatrixError("the ("+i+","+j+") is out of range("+this.row+","+this.col+") dim(2)");
		this.mat[i][j]=value;
	}
	public void setCol(int col,double... value)
	{
		if(value==null) throw new MatrixError("The value is null");
		if(value.length==0) throw new MatrixError("missing required argument value or value is empty");
		if(col<0)throw new MatrixError("the column must be greater zero");
		if(col>=this.col)throw new MatrixError("the ("+col+") is out of range("+this.row+","+this.col+")");
		for(int i=0;i<row;i++)
		{
			this.mat[i][col]=value[i];
		}
	}
	public void setCol(int col,Matrix x)
	{
		if(x==null) throw new MatrixError("the matrix is null");
		if(col<0)throw new MatrixError("the column must be greater zero");
		if(col>=this.col)throw new MatrixError("the ("+col+") is out of range("+this.row+","+this.col+")");
		for(int i=0;i<this.row;i++)
		{
			this.mat[i][col]=x.mat[i][0];
		}
	}
	public void setRow(int row,Matrix x)
	{
		if(x==null) throw new MatrixError("the matrix is null");
		if(row<0)throw new MatrixError("the column must be greater zero");
		if(row>=this.row)throw new MatrixError("the ("+row+") is out of range("+this.row+","+this.col+")");
		this.mat[row]=x.mat[0];
	}
	public void setRow(int row,double... value)
	{
		if(value==null) throw new MatrixError("the value is null");
		if(value.length==0) throw new MatrixError("missing required argument value or value is empty");
		if(row<0)throw new MatrixError("the row must be greater zero");
		if(row>=this.row)throw new MatrixError("the ("+row+") is out of range("+this.row+","+this.col+")");
		this.mat[row]=value;
	}
	public Matrix getRow(int row)
	{
		if(row<0)throw new MatrixError("the row must be greater zero");
		if(row>=this.row)throw new MatrixError("the ("+row+") is out of range("+this.row+","+this.col+")");
		Matrix a=new Matrix(1,this.col);
		for(int i=0;i<this.col;i++)
		{
			a.mat[0][i]=this.mat[row][i];
		}
		return a;
	}
	public Matrix getRowRange(int rowStart,int rowEnd)
	{
		int len=rowEnd-rowStart;
		if(rowStart>=rowEnd)
			throw new MatrixError("start number must be lower then end");
		if(rowStart<0||rowEnd>row)
			throw new MatrixError("the ("+rowStart+","+rowEnd+") is out of range("+this.row+","+this.col+")");
		Matrix a=new Matrix(len,col);
		for(int i=rowStart;i<rowEnd;i++)
		{
			for(int j=0;j<col;j++)
			{
				a.mat[i-rowStart][j]=this.mat[i][j];
			}
		}
		return a;
	}
	public Matrix getRange(int rowStart,int rowEnd,int colStart,int colEnd)
	{
		if(colStart>=colEnd||rowStart>=rowEnd)
			throw new MatrixError("start must be lower then end");
		int rowLen=rowEnd-rowStart;
		int colLen=colEnd-colStart;
		Matrix a=new Matrix(rowLen,colLen);
		for(int i=rowStart;i<rowEnd;i++)
		{
		for(int j=colStart;j<colEnd;j++)
		{
			a.mat[i-rowStart][j-colStart]=this.mat[i][j];
		}
		}
		return a;
	}
	public Matrix getCol(int col)
	{
		if(col<0)throw new MatrixError("the column must be greater zero");
		if(col>=this.col)throw new MatrixError("the ("+col+") is out of range("+this.row+","+this.col+")");
		Matrix a=new Matrix(this.row,1);
		for(int i=0;i<this.row;i++)
		{
			a.setValue(i,0,this.getValue(i, col));
		}
		return a;
	}
	public Matrix getColRange(int colStart,int colEnd)
	{
		int len=colEnd-colStart;
		if(col<0)throw new MatrixError("the column must be greater zero");
		if(colStart>=colEnd)
			throw new MatrixError("start must be lower then end");
		if(colStart<0||colEnd>col)
			throw new MatrixError("the ("+colStart+","+colEnd+") is out of range("+this.row+","+this.col+")");
		Matrix a=new Matrix(row,len);
		for(int i=colStart;i<colEnd;i++)
		{
			for(int j=0;j<row;j++)
			{
				a.mat[j][i-colStart]=this.mat[j][i];
			}
		}
		return a;
	}
	public Matrix add(Matrix a)
	{
		if(a==null)throw new MatrixError("(add)the Matrix is null");
		Matrix b=new Matrix(this.row,this.col);
		if(this.col==a.col&&a.row==this.row)
		{
			for(int i=0;i<this.row;i++)
			{
				for(int j=0;j<this.col;j++)
				{
					b.setValue(i, j,this.getValue(i, j)+a.getValue(i, j));
				}
			}
			return b;
		}
		else throw new MatrixError("shape("+this.row+","+this.col+") !+ shape("+a.row+","+a.col+")");
	}
	public Matrix sub(Matrix a)
	{
		if(a==null)throw new MatrixError("(sub)the Matrix is null");
		Matrix b=new Matrix(this.row,this.col);
		if(this.col==a.col&&this.row==a.row)
		{
			for(int i=0;i<this.row;i++)
			{
				for(int j=0;j<this.col;j++)
				{
					b.setValue(i, j,this.getValue(i, j)-a.getValue(i, j));
				}
			}
			return b;
		}
		else 
			throw new MatrixError("shape("+this.row+","+this.col+") !- shape("+a.row+","+a.col+")");
		
	}
	public Matrix dot(Matrix a)
	{
		if(a==null)throw new MatrixError("(dot)the Matrix is null");
		Matrix b=new Matrix(this.row,this.col);
		if(this.col==a.col&&this.row==a.row)
		{
			for(int i=0;i<b.row;i++)
			{
				for(int j=0;j<b.col;j++)
				{
					b.setValue(i, j,this.getValue(i, j)*a.getValue(i, j));
				}
			}
			return b;
		}
		else {
			throw new MatrixError("shape("+this.row+","+this.col+") !. shape("+a.row+","+a.col+")");
		}
	}
	public Matrix dot(double x)
	{
		Matrix a=new Matrix(this.row,this.col);
		for(int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				a.setValue(i,j,this.getValue(i, j)*x);
			}
		}
		return a;
	}
	public Matrix mul(Matrix a)
	{
		if(a==null)throw new MatrixError("(Mul) the Matrix is null");
		Matrix b=new Matrix(this.row,a.col);
		if(this.col==a.row)
		{
			for(int k=0;k<this.col;k++)
			{
				for(int i=0;i<b.row;i++)
				{
					double r=this.getValue(i, k);
					for(int j=0;j<b.col;j++)
					{
						b.setValue(i, j,b.getValue(i, j)+r*a.getValue(k, j));
					}
				}
			}
			return b;
		}
		else 
			throw new MatrixError("shape("+this.row+","+this.col+") !* shape("+a.row+","+a.col+")");
	}
	public Matrix T()
	{
		Matrix b=new Matrix(this.col,this.row);
		for(int i=0;i<b.row;i++)
		{
			for(int j=0;j<b.col;j++)
			{
				b.mat[i][j]=this.mat[j][i];
			}
		}
		return b;
	}
	public Matrix copy()
	{
		Matrix b=new Matrix(this.row,this.col);
		for(int i=0;i<b.row;i++)
		{
			for(int j=0;j<b.col;j++)
			{
				b.setValue(i, j,this.getValue(i, j));
			}
		}
		return b;
	}
	@Override
	public boolean equals(Object obj)
	{
		Matrix a=null;
		if(obj==null)throw new MatrixError("the Matrix is null");
		if(obj instanceof Matrix)
		{
			a=(Matrix)obj;
			boolean isequal=true;
			if(this.row==a.row&&this.col==a.col)
			{
				for(int i=0;i<this.row;i++)
				{
					for(int j=0;j<this.col;j++)
					{
						if(this.getValue(i, j)!=a.getValue(i, j))
							isequal=false;
					}
				}
			}
			return isequal;
		}
		else {
			throw new MatrixError(this.getClass().getSimpleName()+" and "+obj.getClass().getSimpleName()+" cannot be compared");
		}
	}
	public Matrix gt(double value)
	{
		double[] bool=new double[row*col];
		int count=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(getValue(i, j)>value)
				{
					bool[count++]=getValue(i, j);
				}
			}
		}
		if(count==0) return null;
		return new Matrix(Arrays.copyOfRange(bool, 0, count)).T();
	}
	public Matrix lt(double value)
	{
		double[] bool=new double[row*col];
		int count=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(getValue(i, j)<value)
				{
					bool[count++]=getValue(i, j);
				}
			}
		}
		if(count==0) return null;
		return new Matrix(Arrays.copyOfRange(bool, 0, count)).T();
	}
	public Matrix eq(double value)
	{
		double[] bool=new double[row*col];
		int count=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(getValue(i, j)==value)
				{
					bool[count++]=getValue(i, j);
				}
			}
		}
		if(count==0) return null;
		return new Matrix(Arrays.copyOfRange(bool, 0, count)).T();
	}
	public Matrix ge(double value)
	{
		double[] bool=new double[row*col];
		int count=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(getValue(i, j)>=value)
				{
					bool[count++]=getValue(i, j);
				}
			}
		}
		if(count==0) return null;
		return new Matrix(Arrays.copyOfRange(bool, 0, count)).T();
	}
	public Matrix le(double value)
	{
		double[] bool=new double[row*col];
		int count=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(getValue(i, j)<=value)
				{
					bool[count++]=getValue(i, j);
				}
			}
		}
		if(count==0) return null;
		return new Matrix(Arrays.copyOfRange(bool, 0, count)).T();
	}
	public double[] toArray()
	{
		double[] array=new double[row*col];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				array[i*col+j]=mat[i][j];
			}
		}
		return array;
	}
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		if(row==1)
		{
			sb.append("[");
			for(int i=0;i<this.row;i++)
			{
			   
				for(int j=0;j<this.col;j++)
				{
					sb.append(String.format("%.5f",this.getValue(i, j)));	
					if(j!=this.col-1)sb.append(",");
				}
			}
			sb.append("]");
		}
		else
		{
			sb.append("[");
			if(this.row>=50)
			{
				for(int i=0;i<50;i++)
				{
				    sb.append("[");
					for(int j=0;j<this.col;j++)
					{
						if(i<45)
						{
							sb.append(String.format("%.5f",this.getValue(i, j)));
						}
						else
						{
							sb.append("   .   ");
						}
						if(j!=this.col-1)sb.append(",");
						else sb.append("]");
					}
					if(i!=50-1)
						sb.append("\n ");
					else sb.append("]");
				}
			}
			else {
				for(int i=0;i<this.row;i++)
				{
				    sb.append("[");
					for(int j=0;j<this.col;j++)
					{
						sb.append(String.format("%.5f",this.getValue(i, j)));	
						if(j!=this.col-1)sb.append(",");
						else sb.append("]");
					}
					if(i!=this.row-1)
						sb.append("\n ");
					else sb.append("]");
				}
			}
		}
		return sb.toString();
	}
}