package core;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import List.linkList;
import matherror.MatrixError;
import core.Matrix;

/**
 * {@code linalg} is not a class,but contains lots of static functions used for operation associated with matrix operation,
 * such as calculate determinate,addition,substract and so on.
 * You can use it as example:
 * <p>
 * <blockquote><pre>
 *     import SciCal.linalg.*;
 *     import SciCal.Matrix.*;
 *     Matrix x=new Matrix{new double[][]{{1,2},{3,4}}};
 *     Matrix y=new Matrix{new double[][]{{1,2},{3,4}}};
 *     Matrix add=add(x,y);//addition
 *     Matrix sub=sub(x,y);//substaction
 *     Matrix mul=mul(x,y);//multipl
 *     double det=det(x);//calculate determinate
 * </pre></blockquote><p>
 * @author asus
 */
public class linalg implements Serializable
{
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 1L;
	
	/**
	 * matrix addition
	 * 
	 * @param x left operand
	 * @param y right operand
	 * @return A {@code Matrix} that is x+y;
	 * @exception MatrixException if the row and column of
	 * 			 {@code a} and {@code b} are not equal  
	 * */
	public static Matrix add(Matrix x,Matrix y)
	{
		return x.add(y);
	}
	/**
	 * adjoint matrix
	 * 
	 * @param x  {@code Matrix} type value
	 * @return A {@code Matrix} that is adjoint of a;
	 * */
	public static Matrix adjoint(Matrix x)
	{
		Matrix A_1=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				A_1.setValue(j,i,Math.pow(-1,i+j)*det(subMatrix(x,i,j)));
			}
		}
		return A_1;
	}
	/**
	 * find the position of the maxinum value in each rows;
	 * 
	 * @param x {@code Matrix} type value;
	 * @return A {@code int[]} type array contains the position of the maxinum value in each rows;  
	 * */
	public static int[] argmax(Matrix x)
	{
		int[] b=new int[x.row()];
		for(int i=0;i<x.row();i++)
		{
			double temp=-1000000;
			int flag=0;
			for(int j=0;j<x.col();j++)
			{
				if(x.getValue(i, j)>temp)
				{
					temp=x.getValue(i, j);
					flag=j;
				}
			}
			b[i]=flag;
		}
		return b;
	}
	/**
	 * find the position of the mininum value in each rows;
	 * 
	 * @param x  {@code Matrix} type value;
	 * @return A {@code int[]} type array contains the position of the minium value in each rows;  
	 * */
	public static int[] argmin(Matrix x)
	{
		if(x.col()<=1) throw new MatrixError("the Matrix's column is must greater than 1");
		int[] b=new int[x.row()];
		for(int i=0;i<x.row();i++)
		{
			double temp=1000000;
			int flag=0;
			for(int j=0;j<x.col();j++)
			{
				if(x.getValue(i, j)<temp)
				{
					temp=x.getValue(i, j);
					flag=j;
				}
			}
			b[i]=flag;
		}
		return b;
	}
	/**
	 * calculate correlation
	 * 
	 * @param x a {@code Matrix} type value;
	 * @return correlation {@code Matrix} 
	 * @exception MatrixError if x is null;
	 * */
	public static Matrix cov(Matrix x)
	{
		if(x==null) throw new MatrixError("the Matrix is null");
		Matrix cov=new Matrix(x.row(),x.row());
		Matrix mean=x.mean();
		Matrix var=var(x, 0,"sample");
		for(int i=0;i<cov.row();i++)
		{
			for(int j=0;j<cov.col();j++)
			{
				if(i==j)
				{
					cov.setValue(i, j,var.getValue(i,0));
				}
				else {
					double sum=0.0;
					for(int k=0;k<x.col();k++)
						sum+=(x.getRow(i).getValue(0, k)-mean.getValue(i, 0))*(x.getRow(j).getValue(0,k)-mean.getValue(i, 0));
					cov.setValue(i, j, sum/(x.col()-1));
				}
			}
		}
		return cov;
	}
	/**
	 * calculate correlation
	 * 
	 * @param x a {@code Matrix} type value;
	 * @param y a {@code Matrix} type value;
	 * @return correlation {@code Matrix} 
	 * @exception MatrixError if x/y is null or the shape isn't equal between x and y;
	 * */
	public static Matrix cov(Matrix x,Matrix y)
	{
		if(x==null||y==null||x.row()!=y.row())
		{
			throw new MatrixError("the Matrix is null");
		}
		if(x.row()!=y.row()||x.col()!=y.col())
			throw  new MatrixError("two matrix is different from shape");
		Matrix cov=new Matrix(x.row()+y.row(),x.row()+y.row());
		Matrix xcov=cov(x);
		Matrix ycov=cov(y);
		for(int i=0;i<cov.row();i++)
		{
			for(int j=0;j<cov.col();j++)
			{
				if(i<x.row()&&j<x.row())
					cov.setValue(i, j,xcov.getValue(i, j));
				else if(i>=x.row()&&j>=x.row())
					cov.setValue(i, j,ycov.getValue(i-x.row(), j-x.row()));
				else if(j>=i){
					double sum=0.0;
					Matrix xmean=x.mean();
					Matrix ymean=y.mean();
					for(int k=0;k<x.col();k++)
					{
						double a=(x.getRow(i<x.row()?i:i-x.row()).getValue(0, k)-xmean.getValue(i<x.row()?i:i-x.row(), 0));
						double b=(y.getRow(j<y.row()?j:j-y.row())).getValue(0, k)-ymean.getValue(j<y.row()?j:j-y.row(), 0);
						sum+=a*b;
					}
					cov.setValue(i, j, sum/(x.row()-1));
				}
				else
				{
					double sum=0.0;
					Matrix xmean=x.mean();
					Matrix ymean=y.mean();
					for(int k=0;k<x.col();k++)
					{
						double a=(x.getRow(j<x.row()?j:j-x.row()).getValue(0, k)-xmean.getValue(j<x.row()?j:j-x.row(), 0));
						double b=(y.getRow(i<y.row()?i:i-y.row())).getValue(0, k)-ymean.getValue(i<y.row()?i:i-y.row(), 0);
						sum+=a*b;
					}
					cov.setValue(i, j, sum/(x.row()-1));
				}
			}
		}
		return cov;
	}
	/**
	 * @Title calculate determinate of matrix
	 * 
	 * @param x {@code Matrix} type value;
	 * @return  determinate value {@code double} 
	 * @exception MatrixError if a isn't square matrix;
	 * */
	public static double det(Matrix x)
	{
		if(x.col()!=x.row())
			throw new MatrixError("row must equals to column");
   		else if(x.row()==1&&x.col()==1)
			return x.getValue(0,0);
	 	else if(x.row()==2&&x.col()==2)
			return x.getValue(0,0)*x.getValue(1,1)-x.getValue(0,1)*x.getValue(1,0);
		else 
		{
		int n=x.row();
		double res=1.0;
		int col,postion=0,flag=0;
		double k;
		for(int row=0;row<n;row++)
		{
			if(flag==0)
			{
				col=row;
			}
			else {
				col=row-flag;
			}
			if(x.getValue(row,col)==0)
			{
				while(x.getValue(row,col)==0)
				{	
					col++;
					postion++;
					if(col==n)
					{
						res=0.0;
						break;
					}
				}
				flag++;
			}
			if(col!=n)
			{
			res*=x.getValue(row, col);
			for(int i=row+1;i<n;i++)
			{
				if(x.getValue(i,col)!=0)
				{
				k=-x.getValue(i,col)/x.getValue(row,col);		
				for(int j=col;j<n;j++)
				{
					x.setValue(i,j,x.getValue(i,j)+x.getValue(row,j)*k);
				}
				}
			}
			}
			else {
				res=0.0;
			}
		}
		res=res*Scical.pow(-1, postion);
		res= BigDecimal.valueOf(res)
			    .setScale(5, RoundingMode.HALF_UP)
			    .doubleValue();
		return res;
		}
	}
	/**
	 * search 
	 * 
	 * @param father a {@code Matrix} type value;
	 * @return correlation {@code Matrix} 
	 * @exception MatrixError if x is null;
	 * */
	/**
	 *@Title: subMatrix
	 *@Description: get subMatrix by kicking off (m,n)
	 *@param @param father
	 *@param @param m
	 *@param @param n
	 *@param @return
	 *@reture Matrix
	 *@throws
	 */
	public static Matrix subMatrix(Matrix father,int m,int n)
	{
			Matrix b=new Matrix(father.row()-1,father.col()-1);
			int x=0,y=0,temp=0;
			for(int i=0;i<father.row();i++)
			{
				for(int j=0;j<father.col();j++)
				{
					if(i!=m&&j!=n)
					{
						temp++;
						y=(temp-1)/b.col();
						x=temp-1-y*b.col();
						b.setValue(y,x,father.getValue(i,j));
					}
				} 
			}	
			return b;
	}
	/**
	 *@Title: dot
	 *@Description: matrix dot product
	 *@param @param x
	 *@param @param y
	 *@reture Matrix
	 *@throws MatrixError if the shape of x and y is different
	 */
	public static Matrix dot(Matrix x,Matrix y)
	{
		return x.dot(y);
	}
	/**
	 *@Title: eig
	 *@Description: Eigenvector and Eigenvalue
	 *@param x Matrix type
	 *@reture {@code Matrix} the first is eigenvalue and then is eigenvector
	 */
	public static Matrix eig(Matrix x)
	{
		int row=x.row();
		Matrix v=Matrix.ones(row,1);
		Matrix u=Matrix.ones(row,1);
		double tol=1.0e-6;
		int maxIter=500;
		Matrix vOld;
		double eVal=0;
		for(int k=1;k<=maxIter;k++)
		{
			vOld=v.copy();
			for(int i=0;i<row;i++)
			{
					u.setValue(i,0,(x.getRow(i).mul(v)).getValue(0,0));
			}
			eVal=u.max();
			v=u.dot(1.0/eVal);
			if(Math.sqrt(dot(vOld.sub(v),vOld.sub(v)).sum())<tol)
			{
				k=k+1;
				break;
			}
		}
		Matrix result=new Matrix (1,1+row);
		result.setValue(0,0,eVal);
		for(int i=0;i<row;i++)
		{
			result.setValue(0,i+1,v.getValue(i,0));
		}
		return result;
	}
	public static Matrix elementCount(Matrix x)
	{
		double[] feature=linalg.unique(x);
		Matrix p=new Matrix(1,(int)x.max()+1);
		for(int i=0;i<x.size();i++)
		{
			for(int j=0;j<feature.length;j++)
			{
				if(x.getValue(i, 0)==feature[j])
					p.setValue(0,(int)feature[j],p.getValue(0,(int)feature[j])+1);
			}
		}
		return p;
	}
	public static int[] eq(Matrix x,double value)
	{
		
		if(x.row()==1)
		{
		int[] pos=new int[x.col()];
		int count=0;
		for(int i=0;i<x.col();i++)
		{
			if(x.getValue(0,i)==value)
			{
				pos[count++]=i;
			}
		}
		return Arrays.copyOfRange(pos,0,count);
		}
		else if(x.col()==1)
		{
			int[] pos=new int[x.row()];
			int count=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i,0)==value)
				{
					pos[count++]=i;
				}
			}
			return Arrays.copyOfRange(pos,0,count);
		}
		else throw new MatrixError("the matrix must be one dimension");	
	}
	/**
	 *@Title: eyes
	 *@Description: unitMatrix
	 *@param @param row {@code int}
	 *@param @param col {@code int}
	 *@return unitMatrix
	 */
	public static Matrix eyes(int row,int col)
	{
		return Matrix.eyes(row,col);
	}
	public static int[] ge(Matrix x,double value)
	{
		
		if(x.row()==1)
		{
		int[] pos=new int[x.col()];
		int count=0;
		for(int i=0;i<x.col();i++)
		{
			if(x.getValue(0,i)>=value)
			{
				pos[count++]=i;
			}
		}
		return Arrays.copyOfRange(pos,0,count);
		}
		else if(x.col()==1)
		{
			int[] pos=new int[x.row()];
			int count=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i,0)>=value)
				{
					pos[count++]=i;
				}
			}
			return Arrays.copyOfRange(pos,0,count);
		}
		else throw new MatrixError("the matrix must be one dimension");	
	}
	public static int[] gt(Matrix x,double value)
	{
		
		if(x.row()==1)
		{
		int[] pos=new int[x.col()];
		int count=0;
		for(int i=0;i<x.col();i++)
		{
			if(x.getValue(0,i)>value)
			{
				pos[count++]=i;
			}
		}
		return Arrays.copyOfRange(pos,0,count);
		}
		else if(x.col()==1)
		{
			int[] pos=new int[x.row()];
			int count=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i,0)>value)
				{
					pos[count++]=i;
				}
			}
			return Arrays.copyOfRange(pos,0,count);
		}
		else throw new MatrixError("the matrix must be one dimension");	
	}
	
	public static int[] index(Matrix x,double value)
	{
		int[] temp=new int[2];
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				if(x.getValue(i, j)==value)
				{
					temp[0]=i;
					temp[1]=j;
					break;
				}
			}
		}
		return temp;
	}
	public static int index(Matrix x,Matrix subMatrix)
	{
		int temp=-1;
		for(int i=0;i<x.row();i++)
		{
			if(x.getRow(i).equals(subMatrix))
			{
					temp=i;
					break;
			}
		}
		return temp;
	}
	/**
	 *@Title: inv
	 *@Description: inverse Matrix
	 *@param @param x
	 *@param @return Matrix
	 *@throws MatrixError if x is null
	 */
	public static Matrix inv(Matrix x)
	{
		/**
		 * inverse Matrix
		 */
		if(x==null)throw new MatrixError("the Matrix is null");
		Matrix inv=adjoint(x).dot(1.0/det(x));
		return inv;
	}
	/**
	 *@Title: l1
	 *@Description: 1-norm 
	 *@param @param x Matrix type
	 *@param @return Double
	 *@throws MatrixError if x is null
	 */
	public static double l1(Matrix x)
	{
		if(x==null) throw new MatrixError("the Matrix is null");
		if(x.row()==1)
		{
			double l1=0.0;
			for(int c=0;c<x.col();c++)
			{
				l1+=Math.abs(x.getValue(0,c));
			}
			return l1;
		}
		else if(x.col()==1)
		{
			double l1=0.0;
			for(int c=0;c<x.row();c++)
			{
				l1+=Math.abs(x.getValue(c,0));
			}
			return l1;
		}
		else throw new MatrixError("x must be vector");
	}
	/**
	 *@Title: l2
	 *@Description: 2-norm
	 *@param @param x {@code Matrix}
	 *@param @return Double
	 */
	public static double l2(Matrix x)
	{
		if(x.row()==1)
		{
			double l2=0.0;
			for(int c=0;c<x.col();c++)
			{
				l2+=x.getValue(0,c)*x.getValue(0,c);
			}
			return Math.sqrt(l2);
		}
		else if(x.col()==1)
		{
			double l2=0.0;
			for(int c=0;c<x.row();c++)
			{
				l2+=x.getValue(c,0)*x.getValue(c,0);
			}
			return Math.sqrt(l2);
		}
		else throw new MatrixError("x must be vector");
	}
	public static int[] le(Matrix x,double value)
	{
		
		if(x.row()==1)
		{
		int[] pos=new int[x.col()];
		int count=0;
		for(int i=0;i<x.col();i++)
		{
			if(x.getValue(0,i)<=value)
			{
				pos[count++]=i;
			}
		}
		return Arrays.copyOfRange(pos,0,count);
		}
		else if(x.col()==1)
		{
			int[] pos=new int[x.row()];
			int count=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i,0)<=value)
				{
					pos[count++]=i;
				}
			}
			return Arrays.copyOfRange(pos,0,count);
		}
		else throw new MatrixError("the matrix must be one dimension");	
	}
	public static int[] lt(Matrix x,double value)
	{
		
		if(x.row()==1)
		{
		int[] pos=new int[x.col()];
		int count=0;
		for(int i=0;i<x.col();i++)
		{
			if(x.getValue(0,i)<value)
			{
				pos[count++]=i;
			}
		}
		return Arrays.copyOfRange(pos,0,count);
		}
		else if(x.col()==1)
		{
			int[] pos=new int[x.row()];
			int count=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i,0)<value)
				{
					pos[count++]=i;
				}
			}
			return Arrays.copyOfRange(pos,0,count);
		}
		else throw new MatrixError("the matrix must be one dimension");	
	}
	/**
	 *@Title: mean
	 *@Description: TODO
	 *@param x {@code Matrix} 
	 *@param axis {@code int}
	 *@throws MatrixError if x is null
	 */
	public static Matrix mean(Matrix x,int axis)
	{
		if(x==null)
		{
			throw new MatrixError("the Matrix is null");
		}
		if(axis==1)
		{
			Matrix newMat=new Matrix(1,x.col());
			for(int c=0;c<x.col();c++)
			{
				double mean=0.0;
				for(int i=0;i<x.row();i++)
				{
					mean+=x.getValue(i,c);
				}
				mean=mean*(1.0/x.row());
			    newMat.setValue(0,c,mean);
			}
			return newMat;
		}
		else if(axis==0)
		{
			Matrix newMat=new Matrix(x.row(),1);
			
			for(int c=0;c<x.row();c++)
			{
				double mean=0.0;
				for(int i=0;i<x.col();i++)
				{
					mean+=x.getValue(c,i);
				}
				mean=mean*(1.0/x.col());
				newMat.setValue(c,0,mean);
			}
			return newMat;
		}
		else 
		{
			throw new MatrixError("the axis="+axis+" is out of dim("+x.row()+","+x.col()+")");
		}
	}
	/**
	 *@Title: merge
	 *@Description:merge matrix a with matrix b at the postion where row/col is equal
	 *@param @param x Matrix
	 *@param @param y Matrix
	 *@param @return Matrix
	 *@throws MatrixError is the row/col of x and y is differen
	 */
	public static Matrix merge(Matrix x,Matrix y)
	{
		if(x.row()==y.row())
		{
			Matrix c=new Matrix(x.row(),x.col()+y.col());
			for(int i=0;i<c.row();i++)
				for(int j=0;j<c.col();j++)
				{
					if(j<x.col())
					{
						c.setValue(i,j,x.getValue(i,j));
					}
					else
					{
						c.setValue(i,j,y.getValue(i,j-x.col()));
					}
				}
				return c;
		}
		else if(x.col()==y.col())
		{
			Matrix c=new Matrix(x.row()+y.col(),x.col());
			for(int i=0;i<c.row();i++)
				for(int j=0;j<c.col();j++)
				{
					if(i<x.row())
					{
						c.setValue(i,j,x.getValue(i,j));
					}
					else
					{
						c.setValue(i,j,y.getValue(i-x.row(),j));
					}
				}
				return c;
		}
		else
		{
			throw new MatrixError("the dim("+x.row()+","+x.col()+") cannot merge with dim("+y.row()+","+y.col()+")");
		}
	}
	/**
	 *@Title: merge
	 *@Description: merge matrix a with matrix b at given direction
	 *@param @param x {@code Matrix}
	 *@param @param y {@code Matrix} 
	 *@param @param axis {@code int} direction: 0-x axis 1-y axis
	 *@param @return Matrix
	 *@throws 
	 */
	public static Matrix merge(Matrix x,Matrix y,int axis)
	{
		if(axis==0)
		{
			Matrix c=new Matrix(x.row(),x.col()+y.col());
			for(int i=0;i<c.row();i++)
				for(int j=0;j<c.col();j++)
				{
					if(j<x.col())
					{
						c.setValue(i,j,x.getValue(i,j));
					}
					else
					{
						c.setValue(i,j,y.getValue(i,j-x.col()));
					}
				}
				return c;
		}
		else if(axis==1)
		{
			Matrix c=new Matrix(x.row()+y.col(),x.col());
			for(int i=0;i<c.row();i++)
				for(int j=0;j<c.col();j++)
				{
					if(i<x.row())
					{
						c.setValue(i,j,x.getValue(i,j));
					}
					else
					{
						c.setValue(i,j,y.getValue(i-x.row(),j));
					}
				}
				return c;
		}
		else
		{
			throw new MatrixError("the axis="+axis+" is out of dim("+x.row()+","+x.col()+")");
		}
	}
	public static double median(Matrix x)
	{
		double[] temp=new double[x.size()];
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				temp[i*x.col()+j]=x.getValue(i, j);
			}	
		}
		Arrays.sort(temp);
		return temp[temp.length/2];
	}
	/**
	 *@Title: mul
	 *@Description: matrix multiply
	 *@param @param x {@code Matrix}
	 *@param @param y {@code Matrix}
	 *@param @return Matrix
	 *@throws MatrixError
	 */
	public static Matrix mul(Matrix x,Matrix y)
	{
		return x.mul(y);
	}
	public static void main(String[] args) {
		Matrix xMatrix=new Matrix(0,1,0,6).T();
		System.out.println(nonzero(xMatrix));
	}
	public static Matrix nonzero(Matrix x)
	{
		if(x.row()==1)
		{
			double[] a=new double[x.col()];
			int count=0,countskip=0;
			for(int j=0;j<x.col();j++)
			{
				if(x.getValue(0, j)==0)
				{
					countskip++;
					continue;
				}
				a[count]=count+countskip;
				count++;
			}
			return new Matrix(Arrays.copyOfRange(a, 0, count));
		}
		else if(x.col()==1)
		{
			double[] a=new double[x.row()];
			int count=0,countskip=0;
			for(int i=0;i<x.row();i++)
			{
				if(x.getValue(i, 0)==0)
				{
					countskip++;
					continue;
				}
				a[count]=count+countskip;
				count++;
			}
			return new Matrix(Arrays.copyOfRange(a, 0, count)).T();
		}
		else {
			throw new MatrixError("the dim of matrix must be one");
		}
	}
    public static Matrix nonzeroCount(Matrix a,int axis)
    {
    	int[] count=null;
    	if(axis==0)
    	{
    		count=new int[a.row()];
    		for(int i=0;i<a.row();i++)
    		{
    			for(int j=0;j<a.col();j++)
    			{
    				if(a.getValue(i, j)!=0)
    				count[i]+=1;
    			}
    		}
    		return new Matrix(count);
    	}
    	else if(axis==1)
    	{
    		count=new int[a.col()];
    		for(int i=0;i<a.col();i++)
    		{
    			for(int j=0;j<a.row();j++)
    			{
    				if(a.getValue(j, i)!=0)
    				count[i]+=1;
    			}
    		}
    		return new Matrix(count).T();
    	}
    	else {
			throw new MatrixError("the axis must be one of the value in {0,1}");
		}
    }
	/**
	 *@Title: normalize
	 *@Description: rescale a matrix or a vector at default direction(x axis);
	 *@param @param x Matrix
	 */
	public static void normalize(Matrix x)
	{
		Matrix max=x.max(0);
		Matrix min=x.min(0);
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				x.setValue(i, j, (x.getValue(i, j)-min.getValue(i,0))/(max.getValue(i, 0)-min.getValue(i, 0)));
			}
		}
	}
	/**
	 *@Title: normalize
	 *@Description:rescale data at given direction
	 *@param @param x {@code Matrix}
	 *@param @param axis {@code int} direction:0-x axis,1-y axis
	 *@throws MatrixError
	 */
	public static void normalize(Matrix x,int axis)
	{
		/**
		 * rescale data at given axis
		 */
		if(x==null) throw new MatrixError("the Matrix is null");
		if(axis==0)
		{
			Matrix maxMat=x.max(0);
			Matrix minMat=x.min(0);
			for(int r=0;r<x.row();r++)
			{
			for(int c=0;c<x.col();c++)
			{
				double a=maxMat.getValue(0,c);
				double b=minMat.getValue(0,c);
				x.setValue(r,c,(x.getValue(r,c)-b)/(a-b));
			}
			}
		}
		if(axis==1)
		{
			Matrix maxMat=x.max(1);
			Matrix minMat=x.min(1);
			for(int r=0;r<x.col();r++)
			{
			for(int c=0;c<x.row();c++)
			{
				double a=maxMat.getValue(c,0);
				double b=minMat.getValue(c,0);
				x.setValue(c,r,(x.getValue(c,r)-b)/(a-b));
			}
			}
		}
		else throw new MatrixError("the axis="+axis+" is out of dim("+x.row()+","+x.col()+")");
	}
	/**
	 *@Title: ones
	 *@Description: create matrix filled with ones
	 *@param @param row {@code int}
	 *@param @param col {@code int}
	 *@param @return Matrix
	 *@throws MatrixError
	 */
	public static Matrix ones(int row,int col)
	{
		/**String
		 * create matrix filled with ones
		 */
		return Matrix.ones(row,col);
	}
	
	/**
	 *@Title: orth
	 *@Description: orthogonalization
	 *@param @param x Matrix
	 *@param @return Matrix
	 *@throws MatrixError
	 */
	public static Matrix orth(Matrix x)
	{
		if(x==null) throw new MatrixError("the Matrix is null");
		Matrix b=new Matrix(x.row(),x.col());
		for(int i=0;i<x.col();i++)
		{
			Matrix ai=x.getCol(i);
			Matrix bi=ai.copy();
			for(int j=0;j<i;j++)
			{
				Matrix bj=b.getCol(j);
				double temp=linalg.mul(bj,ai).getValue(0,0)*(1.0/(linalg.mul(bj,ai)).getValue(0,0));
				bi.sub(bj.dot(temp));
			}
			b.setCol(i,bi);
		}
		for(int i=0;i<x.col();i++)
		{
			b.setCol(i,linalg.unit(b.getCol(i)));
		}
		return b;
	} 
	
	/**
	 *@Title: regular
	 *@Description: normernalize
	 *@param @param x {@code Matrix}
	 *@param @param strings optional
	 *@throws MatrixError
	 */
	public static void regular(Matrix x,String ...strings)
	{
	   String norm="l1";
	   for(String str:strings)
		   norm=str;
	   if(norm=="l1")
	   {
		   for(int i=0;i<x.row();i++)
		   {
			   double temp=l1(x.getRow(i));
			   for(int j=0;j<x.col();j++)
			   {
				   x.setValue(i, j, x.getValue(i, j)/temp);
			   }
		   }
	   }
	   else if(norm=="l2") {
		   for(int i=0;i<x.row();i++)
		   {
			   double temp=l2(x.getRow(i));
			   for(int j=0;j<x.col();j++)
			   {
				   x.setValue(i, j, x.getValue(i, j)/temp);
			   }
		   }
	}
	}
	/**
	 *@Title: reshape
	 *@Description: change the shape of the matrix and return shaped matrix
	 *@param @param x {@code Matrix}
	 *@param @param row {@code int}
	 *@param @param col {@code int}
	 *@param @return Matrix
	 *@throws MatrixError
	 */
	public static Matrix reshape(Matrix x,int row,int col)
	{
  		/**
  		 * change the shape of the matrix and return shaped matrix.
  		 */
  		return x.reshape(row,col);
	}
	
	/**
	 *@Title: shape
	 *@Description: get the shape of the matrix 
	 *@param @param matrix 
	 *@param @return int[] 
	 *@throws MatrixError
	 */
	public static int[] shape(Matrix matrix)
	{
		int[] a=new int[2];
		a[0]=matrix.row();
		a[1]=matrix.col();
		return a;
	}
	/**
	 *@Title: shuffle
	 *@Description: shuffle the element of matrix
	 *@param @param x 
	 *@throws MatrixError
	 */
	public static void shuffle(int[] x)
	{
		ArrayList<Integer> arrayList=new ArrayList<>();
		for(int i=0;i<x.length;i++)
			arrayList.add(x[i]); 
	    for(int i=0;i<x.length;i++)
	    {
	    	int pos=Scical.randrangeint(0,arrayList.size());
	    	x[i]=arrayList.get(pos);
	    	arrayList.remove(pos);
	    }
	}
	/**
	 *@Title: shuffle
	 *@Description: Equivalent to shuffle(int[])
	 *@param @param x void
	 *@throws
	 */
	public static void  shuffle(Matrix x)
	{
		ArrayList<Matrix> arrayList=new ArrayList<>();
		for(int i=0;i<x.row();i++)
			arrayList.add(x.getRow(i)); 
	    for(int i=0;i<x.row();i++)
	    {
	    	int pos=Scical.randrangeint(0,arrayList.size());
	    	x.setRow(i,arrayList.get(pos));
	    	arrayList.remove(pos);
	    }
	}
	/**
	 *@Title: solve
	 *@Description: solve linear equation
	 *@param @param coef equation's coefficient
	 *@param @param constant
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix solve(Matrix coff,Matrix constant)
	{
		return inv(coff).mul(constant);
	}
	/**
	 *@Title: stander
	 *@Description: Standerlize a vector or A matrix;
	 *@param @param x
	 *@throws MatrixError
	 */
	public static void stander(Matrix x)
	{
		Matrix mean=x.mean(0);
		Matrix var=x.std(0);
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				x.setValue(i, j, (x.getValue(i, j)-mean.getValue(i,0))/var.getValue(i, 0));
			}
		}
	}
	/**
	 *@Title: std
	 *@Description: calculate the stander deviation(SD)
	 *@param @param x
	 *@param @param axis
	 *@param @param strings
	 *@param @return Matrix
	 *@throws MatrixError
	 */
	public static Matrix std(Matrix x,int axis,String ...strings)
	{
		if(x==null) throw new MatrixError("the Matrix is null");
		Matrix mean=mean(x, axis);
		Double sum=0.0;
		String flag="all";
		int n;
		for(String string:strings)	flag=string;
		if(axis==1)
		{
			if(flag=="all")	n=x.row();
			else if(flag=="sample") n=x.row()-1;
			else throw new MatrixError("the keyword isn't contained ");
			Matrix temp=new Matrix(1,x.col());
			for(int i=0;i<x.col();i++)
			{
				for(int j=0;j<x.row();j++)
				{
					sum+=Scical.pow(x.getValue(j, i)-mean.getValue(0, i),2);
					if(j==x.row()-1)
					{
						temp.setValue(0, i, Scical.sqrt(sum/n));
						sum=0.0;
					}
				}
			}
			return temp;
		}
		else if(axis==0)
		{
			if(flag=="all")	n=x.col();
			else if(flag=="sample") n=x.col()-1;
			else throw new MatrixError("the keyword isn't contained ");
			Matrix temp=new Matrix(x.row(),1);
			for(int i=0;i<x.row();i++)
			{
				for(int j=0;j<x.col();j++)
				{
					sum+=Scical.pow(x.getValue(i, j)-mean.getValue(i, 0),2);
					if(j==x.col()-1)
					{
						temp.setValue(i,0, Scical.sqrt(sum/n));
						sum=0.0;
					}
				}
			}
			return temp;		
		}
		else {
			throw new MatrixError("the axis="+axis+" is out of dim("+x.row()+","+x.col()+")");
		}
	}
	/**
	 *@Title: sub
	 *@Description:matrix substract
	 *@param @param x
	 *@param @param y
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix sub(Matrix x,Matrix y)
	{
		/**
		 * matrix substract
		 */
		return x.sub(y);
	}
	/**
	 *@Title: sum
	 *@Description: sum matrix's all element
	 *@param @param x
	 *@param @return Double
	 *@throws Matrix
	 */
	public static Double sum(Matrix x)
	{
		/**
		 * sum matrix's all element
		 */
		return x.sum();
	}
	/**
	 *@Title: T
	 *@Description: transpose matrix
	 *@param @param x
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix T(Matrix x)
	{
		/**
		 * transpose matrix
		 */
		return x.T();
	}	
	/**
	 *@Title: unique
	 *@Description: get the feature of a matrix;
	 *@param @param x
	 *@param @return int[]
	 *@throws MatrixErrot
	 */
	public static double[] unique(Matrix x)
	{
		double[] temp=new double[x.size()];
		linkList<Double> link=new linkList<Double>();
		int length = 0;
		for(int n=1;n<=x.size();n++)
		{
			int y=(n-1)/x.col();
			int z=n-x.col()*y-1;
			double tmp=x.getValue(y, z);
			if(link.find(tmp)<0)
			{
				link.append(tmp);
				temp[length]=tmp;
				length++;
			}
		}
		temp=Arrays.copyOfRange(temp, 0, length);
		Arrays.sort(temp);
		return temp;
	}
	/**
	 *@Title: unit
	 *@Description: unitilize Matrix
	 *@param @param x
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix unit(Matrix x)
	{
		/**
		 * µ¥Î»»¯
		 */
		if(x==null) throw new MatrixError("the Matrix is null");
		if(x.row()==1)
		{
			Matrix unitizedVector=new Matrix(x.row(),x.col());
			for(int c=0;c<x.col();c++)
			{
				unitizedVector.setValue(0,c,(x.getValue(0,c))/linalg.l2(x));
			}
			return unitizedVector;
		}
		else if(x.col()==1)
		{
			Matrix unitizedVector=new Matrix(x.row(),x.col());
			for(int c=0;c<x.row();c++)
			{
				unitizedVector.setValue(c,0,(x.getValue(c,0))/linalg.l2(x));
			}
			return unitizedVector;
		}
		else throw new MatrixError("the dim is out of dim(1)");
	}
	/**
	 *@Title: var
	 *@Description: calculate the variance
	 *@param @param x
	 *@param @param axis
	 *@param @param strings
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix var(Matrix x,int axis,String ...strings)
	{
		if(x==null) throw new MatrixError("the Matrix is null");
		Matrix mean=mean(x, axis);
		Double sum=0.0;
		String flag="all";
		int n;
		for(String string:strings)	flag=string;
		if(axis==1)
		{
			if(flag=="all")	n=x.row();
			else if(flag=="sample") n=x.row()-1;
			else throw new MatrixError("the keyword isn't contained ");
			Matrix temp=new Matrix(1,x.col());
			for(int i=0;i<x.col();i++)
			{
				for(int j=0;j<x.row();j++)
				{
					sum+=Scical.pow(x.getValue(j, i)-mean.getValue(0, i),2);
					if(j==x.row()-1)
					{
						temp.setValue(0, i,sum/n);
						sum=0.0;
					}
				}
			}
			return temp;
		}
		else if(axis==0)
		{
			if(flag=="all")	n=x.col();
			else if(flag=="sample") n=x.col()-1;
			else throw new MatrixError("the keyword isn't contained ");
			Matrix temp=new Matrix(x.row(),1);
			for(int i=0;i<x.row();i++)
			{
				for(int j=0;j<x.col();j++)
				{
					sum+=Scical.pow(x.getValue(i, j)-mean.getValue(i, 0),2);
					if(j==x.col()-1)
					{
						temp.setValue(i,0, sum/n);
						sum=0.0;
					}
				}
			}
			return temp;		
		}
		else {
			throw new MatrixError("the axis="+axis+" is out of dim("+x.row()+","+x.col()+")");
		}
	}
	/**
	 *@Title: zeros
	 *@Description: create zero matrix
	 *@param @param row
	 *@param @param col
	 *@param @return Matrix
	 *@throws Matrix
	 */
	public static Matrix zeros(int row,int col)
	{
		return Matrix.zeros(row,col);
	}
}