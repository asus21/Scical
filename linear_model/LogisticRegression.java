package linear_model;

import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.Scical;
import core.linalg;

public class LogisticRegression {
	private int max_iter;
	private double eta;
	private double tol;
	private double[] error;
	private Matrix wMatrix;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getColRange(0, 4);
		Matrix yMatrix=data.getCol(4);
		LogisticRegression logisticRegression=new LogisticRegression(600, 0.1);
		logisticRegression.fit(xMatrix, yMatrix);
		Matrix aMatrix=data.getRowRange(100,120).getColRange(0,4);
		System.out.println(logisticRegression.predict(aMatrix));
//		double[] err=logisticRegression.error();
//		for(int i=0;i<err.length;i++)
//		{
//			System.out.println(err[i]);
//		}
//		System.out.println(logisticRegression.coef().T());
	}
	public LogisticRegression(int max_iter,double eta,double tol)
	{
	  this.max_iter=max_iter;
	  this.eta=eta;
	  this.tol=tol;
	  this.error=new double[max_iter];
	}
	public LogisticRegression(int max_iter,double eta)
	{
		this(max_iter,eta,0.0001);
	}
	public LogisticRegression()
	{
		this(100,0.01,0.0001);
	}
	private Matrix processX(Matrix X)
	{
		int[] shape=X.shape();
		int m=shape[0];
		int n=shape[1];
		Matrix X_=new Matrix(m,n+1);
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n+1;j++)
			{
				if(j==0)
					X_.setValue(i,j,1.0);
				else
					X_.setValue(i,j,X.getValue(i,j-1));
			}
		}
		return X_;
	}
	private Matrix active(Matrix z)
	{
		Matrix aMatrix=new Matrix(z.row(),z.col());
		for(int i=0;i<z.row();i++)
		{
			aMatrix.setValue(i, z.col()-1, 1.0/(1+Scical.exp(-z.getValue(i, z.col()-1))));
		}
		return aMatrix;
		
	}
	private Matrix grad(Matrix x,Matrix y_preb,Matrix y)
	{
		Matrix aMatrix=(x.T().mul(y_preb.sub(y))).dot(1.0/y.row());
		return aMatrix;
	}
	public void fit(Matrix x,Matrix y)
	{
		Matrix X_=processX(x);
		this.wMatrix=Matrix.random(1+x.col(),1);
		double loss_old=Double.MAX_VALUE;
		for(int i=0;i<max_iter;i++)
		{
			Matrix y_preb=active(X_.mul(wMatrix));
			Matrix grad=grad(X_, y_preb,y);
			this.wMatrix=wMatrix.sub(grad.dot(eta));
			double loss=linalg.l2(y_preb.sub(y))/y.row();
			if(loss_old-loss<=tol)
			{
				break;
			}
			else {
				loss_old=error[i]=loss;
			}
		}
	}
	public Matrix predict(Matrix x)
	{
		Matrix y_predict=active(processX(x).mul(wMatrix));
		for(int i=0;i<y_predict.row();i++)
 			for(int j=0;j<y_predict.col();j++)
 			{
 				if(y_predict.getValue(i, j)>=0.5)
 					y_predict.setValue(i, j, 1.0);
 				else 
 					y_predict.setValue(i, j, 0.0);	
 			}
		return y_predict;
	}
	public double[] error()
	{
		return error;
	}
	public Matrix coef()
	{
		return this.wMatrix;
	}
}
