package linear_model;

import java.awt.print.Printable;

import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.Scical;
import core.linalg;

public class SoftLogisticRegression {

	private int max_iter;
	private double eta;
	private double tol;
	private double[] error;
	private Matrix wMatrix;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getRange(0, 149, 0, 4);
		Matrix yMatrix=data.getCol(4);
		linalg.stander(xMatrix);
		SoftLogisticRegression logisticRegression=new SoftLogisticRegression(100, 0.001);
		logisticRegression.fit(xMatrix, yMatrix);
		Matrix aMatrix=data.getRowRange(80,100).getColRange(0, 4);
		System.out.println(logisticRegression.predict(aMatrix));
		double[] err=logisticRegression.error();
		for(int i=0;i<err.length;i++)
		{
			//System.out.println(err[i]);
		}
		//System.out.println(logisticRegression.coef());
	}
    public Matrix coef() {
		return wMatrix;
	}
	public double[] error() {
		return error;
	}
	public SoftLogisticRegression(int max_iter,double eta,double tol)
    {
    	this.max_iter=max_iter;
    	this.eta=eta;
    	this.tol=tol;
    	error=new double[max_iter];
    }
    public SoftLogisticRegression(int max_iter,double eta)
    {
    	this(max_iter, eta,0.0001);
    }
    public SoftLogisticRegression()
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
    private Matrix processY(Matrix y)
	{
		double[] feature=linalg.unique(y);
		Matrix y_=new Matrix(y.row(),feature.length);
		for(int i=0;i<y_.row();i++)
		{
			y_.setValue(i,(int)y.getValue(i, 0),1);
		}
		return y_;
	}
    private Matrix active(Matrix z)
	{
		Matrix aMatrix=new Matrix(z.row(),z.col());
		for(int i=0;i<z.row();i++)
		{
			for(int j=0;j<z.col();j++)
				aMatrix.setValue(i,j, 1.0/(1+Scical.exp(-z.getValue(i,j))));
		}
		return aMatrix;
	}
    private Matrix grad(Matrix x,Matrix y,Matrix y_preb)
	{
    	Matrix y_bin=new Matrix(1,y_preb.size());
    	int pos=(int)linalg.nonzero(y).getValue(0, 0);
    	y_bin.setValue(0,pos,1);
		Matrix aMatrix=(x.T().mul(y_preb.sub(y_bin)));
		return aMatrix;
	}
    public void fit(Matrix x,Matrix y)
	{
		Matrix X_=processX(x);
		Matrix Y_=processY(y);
		this.wMatrix=Matrix.random(X_.col(),Y_.col()).dot(0.05);
		int [] a=new int[y.size()];
		double loss_old=Double.MAX_VALUE;
	    for(int i=0;i<a.length;i++)
	    {
	    	a[i]=i;
	    }
		for(int i=0;i<max_iter;i++)
		{
			linalg.shuffle(a);
			for(int j:a)
			{
				Matrix y_preb=active(X_.getRow(j).mul(wMatrix));
				Matrix grad=grad(X_.getRow(j),Y_.getRow(j),y_preb);
				this.wMatrix=wMatrix.sub(grad.dot(eta));
			}
			double loss=Scical.pow(active(X_.mul(wMatrix)).sub(Y_),2).sum()/y.row();
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
    	Matrix X_=processX(x);
    	Matrix y_predict=active(X_.mul(wMatrix));
    	int[] pos=linalg.argmax(y_predict);
 		for(int i=0;i<y_predict.row();i++)
 			for(int j=0;j<y_predict.col();j++)
 			{
 				if(j==pos[i])
 					y_predict.setValue(i, j, 1.0);
 				else 
 					y_predict.setValue(i, j, 0.0);	
 			}
 		 
 		return new Matrix(linalg.argmax(y_predict));
    }
}
