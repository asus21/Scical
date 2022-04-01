package linear_model;

import core.Matrix;
import core.Scical;
import core.linalg;

public class KMean {

	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	private int k;
	private double tol;
	private int max_iter;
	private Matrix center;
	public static void main(String[] args) throws Exception {
		Matrix xMatrix=new Matrix(new double[][]{{1,1},{0,0},{2,2},{2,0},{0,2},{100,100},{101,101},{100,101}});
		KMean logisticRegression=new KMean(2);
		Matrix aMatrix=xMatrix;
		System.out.println(logisticRegression.predict(aMatrix));
	}
	public KMean(int k) {
	this.k=k;
	this.tol=0.0001;
	this.max_iter=100;
	}
	public KMean(int k,double tol,int max_iter)
	{
		this.k=k;
		this.tol=tol;
		this.max_iter=max_iter;
	}
	private Matrix random_point(Matrix x)
	{
		Matrix xmin=x.min(1);
		Matrix xmax=x.max(1);
		Matrix temp=Matrix.random(this.k,x.col());
		for(int i=0;i<k;i++)
		{
			temp.setRow(i,temp.getRow(i).dot(xmax.sub(xmin)).add(xmin));
		}
		return temp;
	}
	private Matrix kmean(Matrix x)
	{
		this.center=random_point(x);
		Matrix distance=new Matrix(x.row(),k);
		Matrix center_old=new Matrix(k,x.col());
		Matrix label = null;
		for(int i=0;i<max_iter;i++)
		{
			for(int j=0;j<x.row();j++)
			{
				for(int k=0;k<this.k;k++)
				{
					distance.setValue(j, k,Scical.pow(center.getRow(k).sub(x.getRow(j)),2).sum());
				}
			}
			label=new Matrix(linalg.argmin(distance));
			
			center_old=center.copy();
			for(int j=0;j<this.k;j++)
			{
				int[] pos=linalg.eq(label,j);
				if(pos.length!=0)
				{
				Matrix cluster=x.getRowChoice(pos);
				if(cluster.size()==0)
					return null;
				center.setRow(j,linalg.mean(cluster,1));
				}
				
			}
			Matrix prec=Scical.sqrt(Scical.pow(center.sub(center_old),2));
			if(prec.gt(tol)==null)
				break;
		}
		return label;
	}
	public Matrix predict(Matrix x)
	{
		Matrix res=null;
		while(res==null)
		{
			res=kmean(x);
			//System.out.println(res);
		}
		return res;
	}
}
