import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.Provider.Service;
import java.util.Scanner;
import core.Matrix;
import core.Scical;
import core.linalg;
public class test
{
	public static double factoral()
	{
		Matrix left = null,right = null,result = null;
		Boolean flag=true;
		 String fixString="";
		 String[] xStrings = null;
		 String aString =new String("100,200,3;4,4,7;1,3,4+1,5,4;5,6,5;7,8,9");
		 if(aString.contains("det"))
		 {
			 fixString="det";
			 xStrings=aString.split("det");
			 xStrings[0]=xStrings[1];
		 }
		 if(aString.contains("+"))
		 {
			 fixString="+";
			 xStrings=aString.split("\\+");
		 }
		if(aString.contains("-"))
		{
		fixString="-";
		xStrings=aString.split("\\-");
		}
		if(aString.contains("*"))
		{
		fixString="*";
		xStrings=aString.split("\\*");
		}
		 for(String x:xStrings)
		 {
			 
			 String[] xStrings2=x.split(";");
		     int m=xStrings2.length;
		     int n1=xStrings2[0].split(",").length;
			 double[][] a =new double[m][n1];
			 for(int i=0;i<m;i++)
			 {
				 String[] temp=xStrings2[i].split(",");
				 for(int j=0;j<n1;j++)
				 {
					 a[i][j]=Double.valueOf(temp[j]);
					 
				 }
				
			 }
			 if(flag)
				 {
				 	
				 	left=new Matrix(a);
				 	flag=false;
				 }
			 else {
				right=new Matrix(a);
			 }
		}
			switch(fixString)
			{
			case "+":result=left.add(right);break;
			case "-":result=left.sub(right);break;
			case "*":result=left.mul(right);break;
			case "det":System.out.println(linalg.det(left));break;
			}
			System.out.println(result);
			return 0;	
		}
	@SafeVarargs
	public  static <T> Boolean timeit (Method cFunction,T...aT) throws Exception, IllegalAccessException
	{
		long a2=System.nanoTime();
		cFunction.invoke(null,aT);
		long b=System.nanoTime();
		System.out.println("程序运行时间： "+(b-a2)+"ns");
		return null;  
	}
	public static void main(String[] args)throws Exception
	{
		Matrix aMatrix=new Matrix(new double[][]{{1,2},{1}});
		int z,l,b,c;
		double x,n;
		Scanner scanner =new Scanner(System.in);
		n=scanner.nextDouble();
		z=(int)n;
		BigDecimal pBigDecimal=new BigDecimal(n);
		BigDecimal qBigDecimal=new BigDecimal(z);
		BigDecimal yBigDecimal=pBigDecimal.subtract(qBigDecimal);
		x=yBigDecimal.setScale(8,BigDecimal.ROUND_HALF_UP).doubleValue();
		String aString=""+x;
		l=aString.length()-2;
		b=(int)Math.pow(10,1);
		c=(int)(b*x);
		if(c==0)System.out.println(n);
		else if(c==1) System.out.println(z+" "+c+" "+b);
		else if(c!=0) {
			for(int i=c;i>1;i--)
			{
				if(b%i==0&&c%i==0)
				{
					b=b/i;
					c=c/i;
					System.out.println(z+" "+c+" "+b);
					break;
				}
				else if((b%i!=0||c%i!=0)&&i==2)
				{
					System.out.println(z+" "+c+" "+b);
					break;
				}
				else {
					continue;
				}
			}
		}
	}
}