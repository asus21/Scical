package linear_model;

import core.Array;
import core.Matrix;
import core.Scical;
import core.linalg;

public class NavieBayes {
	private double alpha;
	private double[] classes;
	private Matrix prior_proba;
	private Array conditional_proba;
	public static void main(String[] args) throws Exception {
		Matrix x=new Matrix(new double[][]{{0,0,0,1},{0,0,1,1},{0,1,1,1}});
		Matrix yMatrix=new Matrix(1,1,2);
		NavieBayes aBayes=new NavieBayes();
		aBayes.fit(x, yMatrix);
		System.out.println(new Matrix(aBayes.predict(x)));
	}
	public NavieBayes() {
		this.alpha=1;
	}
	public NavieBayes(double alpha)
	{
		this.alpha=alpha;
	}
	private Matrix prior_proba(Matrix y)
	{
		Matrix p=linalg.nonzeroCount(y, 0);
		for(int i=0;i<p.size();i++)
		{
			p.setValue(i,0,(p.getValue(i, 0)+alpha)/(y.size()+p.size()*alpha));
		}
		return Scical.ln(p);
	}
	private Array conditional_proba(Matrix x,Matrix y)
	{
		double[] classes=linalg.unique(y); 
		Array p_log=new Array(2,classes.length,x.col());
		for(int i=0;i<classes.length;i++)
		{
			Matrix x_c=x.getRowChoice(linalg.eq(y,classes[i]));
			Matrix count=linalg.nonzeroCount(x_c,1).T();
			Matrix p1=(count.add(Matrix.full(count.row(),count.col(),alpha)).dot(1/(x_c.row()+2*alpha)));
			p_log.setValue(new int[]{1,i},Scical.ln(p1).toArray());
			p_log.setValue(new int[]{0,i},Scical.ln(Matrix.ones(p1.row(),p1.col()).sub(p1)).toArray());
		}
		return p_log;
	}
	public void fit(Matrix x,Matrix y)
	{
		classes=linalg.unique(y);
		prior_proba=prior_proba(y);
		conditional_proba=conditional_proba(x,y);
	}
	public double predict_one(Matrix x)
	{
		int k=classes.length;
		double[] p_log=new double[k];
		int[] pos1=linalg.eq(x,1);
		int[] pos0=linalg.eq(x,0);
		for(int i=0;i<k;i++)
		{
			Matrix log1=new Matrix(conditional_proba.getValue(new int[]{1,i})).getRowChoice(pos1);		
			Matrix log2=new Matrix(conditional_proba.getValue(new int[]{0,i})).getRowChoice(pos0);
			p_log[i]=prior_proba.getValue(i,0)+log1.sum()+log2.sum();
		}
		return linalg.argmax(new Matrix(p_log).T())[0];
	}
	public double[] predict(Matrix x)
	{
		double[] a=new double[x.row()];
		for(int i=0;i<x.row();i++)
		{
			a[i]=predict_one(x.getRow(i));
		}
		return a;
	}
}
