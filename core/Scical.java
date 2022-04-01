package core;
import matherror.MathErrorWarning;
import matherror.MatrixError;
public class Scical
{
	public static final double Pi=3.14159265359;
	public static final double E=2.71828182846;
	public static final double c=299792458;
	public static final double e=1.6021766208*Math.pow(10,-19);
	public static final double m_e=9.10938356*Math.pow(10,-31);
	public static final double m_p=1.672621898*Math.pow(10,-27);
	public static final double N_A=6.022140857*Math.pow(10,23);
	public static final double R=8.3144598;
	public static void main(String[] args) {
		System.out.println(randint(4));
	}
	public static double abs(double x)
	{
		return Math.abs(x);
	}
	public static double add(double a,double b)
	{
		return a+b;
	}
	public static double sub(double a,double b)
	{
		return a-b;
	}
	public static double mul(double a,double b)
	{
		return a*b;
	}
	public static double div(double a,double b)
	{
		return mul(a,1/b);
	}
	public static int factoral(int n)
	{
		int f;
		if(n<=1) f=1;
		else f=n*factoral(n-1);
		return f;
	}
	public static Matrix cos(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,cos(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double cos(double x)
	{
		return Math.cos(x);
	}
	public static Matrix sin(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,sin(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double sin(double x)
	{
		return Math.sin(x);
	}
	public static Matrix tan(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,tan(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double tan(double x)
	{
		return Math.tan(x);
	}
	public static Matrix asin(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,asin(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double asin(double x)
	{
		return Math.asin(x);
	}
	public static Matrix acos(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,acos(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double acos(double x)
	{
		return Math.acos(x);
	}
	public static Matrix atan(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,atan(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double atan(double x)
	{
		return Math.atan(x);
	}
	public static Matrix sinh(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,sinh(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double sinh(double x)
	{
		return Math.sinh(x);
	}
	public static Matrix cosh(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,cosh(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double cosh(double x)
	{
		return Math.cosh(x);
	}
	public static Matrix tanh(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,tanh(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double tanh(double x)
	{
		return Math.tanh(x);
	}
	public static Matrix log10(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,log10(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double log10(double x)
	{
		return Math.log10(x);
	}
	public static Matrix log2(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,log2(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double log2(double x)
	{
		return Math.log(x)/Math.log(2);
	}
	public static Matrix ln(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,ln(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double ln(double x)
	{
		if(x<0){
			new MathErrorWarning("input is lower than zero");
		}
		return Math.log(x);
	}
	public static Matrix sqrt(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,sqrt(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double sqrt(double x)
	{
		if(x<0){
			new MathErrorWarning(x+"is lower than zero");
		}
		return Math.sqrt(x);
	}
	public static Matrix roots(Matrix x,int n)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,roots(x.getValue(i,j),n));
			}
		}
		return newMat;
	}
	public static double roots(double x,int n)
	{
		return Math.pow(x,1.0/n);
	}
	public static Matrix exp(Matrix x)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,exp(x.getValue(i,j)));
			}
		}
		return newMat;
	}
	public static double exp(double x)
	{
		if(abs(x)>Double.MAX_VALUE||abs(x)<Double.MIN_VALUE){
			new MathErrorWarning(x+"is out of range");
		}
		return Math.exp(x);
	}
	public static Matrix pow(Matrix x,double n)
	{
		Matrix newMat=new Matrix(x.row(),x.col());
		for(int i=0;i<x.row();i++)
		{
			for(int j=0;j<x.col();j++)
			{
				newMat.setValue(i,j,pow(x.getValue(i,j),n));
			}
		}
		return newMat;
	}
	public static double pow(double x,double n)
	{
		return Math.pow(x,n);
	}
	public static Matrix matrixPow(Matrix x,int n)
	{
		  if(n==-1) return linalg.inv(x);
		  if(n<-1) throw new MatrixError("can't computer the nagative power lower than -1");
		  Matrix newMatrix=Matrix.ones(x.row(),x.col());
		  for(int i=0;i<n;i++)
		  {
			  newMatrix=newMatrix.mul(x);
		  }
		  return newMatrix;
	}
	public static int TenPow(int n)
	{
		return (int)Math.pow(10,n);
	}
	public static double round(double a,int n)
	{
		return Math.round(a*Math.pow(10,n))*Math.pow(10,-n);
	}
	public static double random()
	{
		return Math.random();
	}
	public static int randint(int n)
	{
		return (int)(Math.random()*n);
	}
	public static int randrangeint(int min,int max)
	{
	   int a=randint(max);
	   while(a<min)
	   {
		   a=randint(max);
	   }
	   return a;
	}
	public static Matrix mat(double[][] a)
	{
		Matrix b=new Matrix(a);
		return b;
	}
	public static Matrix diag(double[] a)
	{
		return Matrix.diag(a);
	}
	public static Matrix eyes(int row,int col)
	{
	     return Matrix.eyes(row,col);
	}
	public static Matrix zeros(int row,int col)
	{
		return Matrix.zeros(row,col);
	}
	public static double det(Matrix a)
	{
		return linalg.det(a);
	}
	public static Matrix inv(Matrix a)
	{
		return linalg.inv(a);
	}
	public static Matrix adjoint(Matrix a)
	{
		return linalg.adjoint(a);
	}
	public static Matrix add(Matrix a,Matrix b)
	{
		return a.add(b);
	}
	public static Matrix sub(Matrix a,Matrix b)
	{
	    return a.sub(b);
	}
	public static Matrix mul(Matrix a,Matrix b)
	{
		return a.mul(b);
	}
	public static Matrix randmat(int row,int col)
	{
		return Matrix.random(row,col);
	}
	public static Matrix solve(Matrix a,Matrix b)
	{
		return linalg.solve(a,b);
	}
	public static Matrix augment(Matrix a,Matrix b)
	{
		return linalg.merge(a,b);
	}
	public static double caculate(String a)
	{
		return Double.valueOf(Solve.solve(a));
	}
	
}