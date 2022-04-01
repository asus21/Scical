package linear_model;

import java.util.ArrayList;

import core.Matrix;
import core.Scical;
import core.linalg;

public class KMeans {

	private int k_neighbor;
	private double tol;
	private int max_iter;
	private Matrix center;
	private int n;
	public static void main(String[] args) {
		Matrix xMatrix=new Matrix(new double[][]{{1,1},{0,0},{2,2},{2,0},{0,2},{100,100},{101,101},{100,101}});
		KMeans logisticRegression=new KMeans(2);
		Matrix aMatrix=xMatrix;
		System.out.println(logisticRegression.predict(aMatrix));


	}
	public KMeans(int k) {
		this.k_neighbor=k;
		this.tol=0.001;
		this.max_iter=300;
		this.n=5;
	}
	private Matrix randompoint(Matrix x)
	{
		Matrix distance=new Matrix(x.row(),k_neighbor-1);
		Matrix center=new Matrix(k_neighbor,x.col());
		center.setRow(0,x.getRow(Scical.randint(x.col())));
		for(int i=1;i<k_neighbor;i++)
		{
			for(int j=0;j<i;j++)
			{
				for(int k=0;k<x.row();k++)
				{
					distance.setValue(k,j,Scical.pow(center.getRow(j).sub(x.getRow(k)),2).sum());
				}
			}
			Matrix temp=distance.min(0);
			double r=temp.sum();
			int l;
			for(l=0;l<x.row();l++)
			{
				r-=temp.getValue(l, 0);
				if(r<=0) break;
			}
			center.setRow(i,x.getRow(l));
		}
		return center;
	}
	private ArrayList<Object> kmeans(Matrix x)
	{
		Matrix label=new Matrix(x.row(),1);
		Matrix distance=new Matrix(x.row(),k_neighbor);
		Matrix old_center=new Matrix(k_neighbor,x.col());
		center=randompoint(x);
		for(int i=0;i<max_iter;i++)
		{
			for(int j=0;j<x.row();j++)
			{
				for(int k=0;k<k_neighbor;k++)
				{
					distance.setValue(j, k,Scical.pow(center.getRow(k).sub(x.getRow(j)),2).sum());
				}
			}
			label=new Matrix(linalg.argmin(distance));
			old_center=center.copy();
			for(int j=0;j<this.k_neighbor;j++)
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
			Matrix prec=Scical.sqrt(Scical.pow(center.sub(old_center),2));
			if(prec.gt(tol)==null)
				break;
		}
		double sse=distance.getRowRange(0, x.row()).getColChoice(linalg.argmin(distance)).sum();
		ArrayList<Object> list=new ArrayList<>();
		list.add(center);
		list.add(label);
		list.add(sse);
		return list;
		}
		public Matrix predict(Matrix x)
		{
			ArrayList<ArrayList<Object>> result=new ArrayList<>();
			for(int i=0;i<n;i++)
			{
				ArrayList<Object> res=null;
				while(res==null)
					res=kmeans(x);
				result.add(res);
			}
			int index=0;
			double minsse=Double.MAX_VALUE;
			for(int i=0;i<result.size();i++)
			{
				if((double)result.get(i).get(2)<minsse)
				{
					minsse=(double)result.get(i).get(2);
					index=i;
				}
			}
			center=(Matrix)result.get(index).get(0);
			return (Matrix)result.get(index).get(1);
			
		}
}
	
