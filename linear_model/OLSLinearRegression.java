package linear_model;
import java.io.Serializable;

import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.linalg;


public class OLSLinearRegression implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Matrix W;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getRange(25, 76, 0, 4);
		Matrix yMatrix=data.getCol(4).getRowRange(25, 76);
		LogisticRegression logisticRegression=new LogisticRegression(600, 0.03);
		logisticRegression.fit(xMatrix, yMatrix);
		Matrix aMatrix=data.getRow(1).getColRange(0, 4);
		System.out.println(logisticRegression.predict(aMatrix));
		double[] err=logisticRegression.error();
		for(int i=0;i<err.length;i++)
		{
			System.out.println(err[i]);
		}
		System.out.println(logisticRegression.coef());
	}
	public OLSLinearRegression()
	{
	
	}
	protected Matrix ols(Matrix X,Matrix y)
	{
		Matrix temp=linalg.mul(X.T(),X);
		temp=linalg.inv(temp);
		
		temp=linalg.mul(temp,X.T());
		temp=linalg.mul(temp,y);
		return temp;
	}
	protected Matrix processX(Matrix X)
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
	protected Matrix processY(Matrix y)
	{
		double[] feature=linalg.unique(y);
		Matrix y_=new Matrix(y.row(),feature.length);
		for(int i=0;i<y_.row();i++)
		{
			y_.setValue(i,(int)y.getValue(i, 0),y.getValue(i, 0));
		}
		
		return y_;
	}
	public void fit(Matrix X,Matrix y)
	{
	/*
	Parameter:
	----------
	X:2D array shape=[n_samples,n_features]
	y:1D array shape=[1,n_samples]
	*/
		Matrix X_train=processX(X);
		
		W=ols(X_train,y);
		
	}
	public Matrix predict(Matrix X)
	{
	/*
	Parameter:
	----------
	X:2D array shape=[n_samples,n_features];
	y:1D array shape=[1,n_samples];
	*/
		Matrix X_=processX(X);
		return linalg.mul(X_,W);
	}
	public Matrix coef()
	{
		return this.W.T();
	}
}