package linear_model;

import java.util.Arrays;

import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.linalg;

public class CartRegression {

	private double mse_threshold;
	private double mse_dec_threshold;
	private int min_sample_split;
	private Node treeNode;
	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	public CartRegression()
	{
		mse_threshold=0.01;
		mse_dec_threshold=0;
		min_sample_split=2;
	}
	public CartRegression(double gini_threshold,double gini_dec_threshold,int min_sample_split)
	{
		this.mse_threshold=gini_threshold;
		this.mse_dec_threshold=gini_dec_threshold;
		this.min_sample_split=min_sample_split;
	}
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris");
		Matrix xMatrix=aTable.data().getColRange(0,4).getRowRange(0, 148);
		Matrix yMatrix=aTable.data().getCol(4).getRowRange(0,148);
		CartRegression aCartClassifier=new CartRegression();
		aCartClassifier.fit(xMatrix, yMatrix);
		System.out.println(new Matrix(aCartClassifier.predict(xMatrix.getRowRange(0, 2))));
		System.out.println(aCartClassifier);
	}
	private double mse(Matrix y)
	{
		return y.var().getValue(0, 0);
	}
	private double bestPoint(Matrix x,Matrix y,double value)
	{
		int[] posleft=new int[x.size()];
		int[] posright=new int[x.size()];
		int countleft=0,countrighht=0;
		for(int i=0;i<x.size();i++)
		{
			if(x.getValue(i, 0)<value)
				posleft[countleft++]=i;
			else
			{
				posright[countrighht++]=i;
			}
		}
		posleft=Arrays.copyOfRange(posleft, 0, countleft);
		posright=Arrays.copyOfRange(posright, 0, countrighht);
		double res=mse(y.getRowChoice(posleft))*posleft.length+mse(y.getRowChoice(posright))*posright.length;
		return res/y.size();
	}
	private Node getBestpoint(Matrix x,Matrix y)
	{	
		Node aNode=new Node();
		double[] label=linalg.unique(x);
		double[] bin_label=null;
		if(label.length!=1)
		{
			bin_label=new double[label.length-1];
			for(int i=0;i<bin_label.length;i++)
			{
				bin_label[i]=(label[i]+label[i+1])/2;
			}
			double temp=Double.MAX_VALUE;
			double gini=0.0;
			for(int i=0;i<bin_label.length;i++)
			{
				gini=bestPoint(x, y,bin_label[i]);
				if(gini<temp)
				{
					aNode.value=gini;
					aNode.bestPoint=bin_label[i];
					temp=gini;
				}	
			}
		}
		return aNode;
	}
	private Node select(Matrix x,Matrix y)
	{		
		
		Node aNode=new Node();
		Integer index=null;
		double tmp=Double.MAX_VALUE;
		Node[] temp=new Node[x.col()];
	    for(int i=0;i<x.col();i++)
	    {
	    	temp[i]=getBestpoint(x.getCol(i), y);
	    }
	    for(int i=0;i<x.col();i++)
	    {
	    	if(temp[i].value!=null&&tmp>temp[i].value)
	    	{
	    		index=i;
	    		tmp=temp[i].value;
	    	}
	    }
	    aNode.feature_index=index;
    	aNode.value=temp[index].bestPoint;
    	
	    if(mse(y)-temp[index].value<mse_dec_threshold)
	    {
	    	aNode.feature_index=null;
	    	aNode.value=null;
	    }
	    return aNode;
	}
	private Node buildTree(Matrix X,Matrix Y)
	{ 
	   Node node=new Node();
	   node.value=Y.mean().getValue(0,0);
	   if(Y.size()<min_sample_split)
	   {
		   return node;
	   }
	  
	   else if(mse(Y)<mse_threshold)
		   return node;
	   else 
	   {
		   	Node tempNode=select(X, Y);
		   	if(tempNode.feature_index!=null)
		   	{
		   		node.feature_index=tempNode.feature_index;
		   		node.bestPoint=tempNode.value;
		   		int[] posleft=new int[X.size()];
				int[] posright=new int[X.size()];
				int countleft=0,countrighht=0;
				Matrix temp=X.getCol(node.feature_index);
				for(int i=0;i<temp.row();i++)
				{
					if(temp.getValue(i, 0)>node.bestPoint)
						posleft[countleft++]=i;
					else if(temp.getValue(i, 0)<node.bestPoint)
					{
						posright[countrighht++]=i;
					}
				}
				posleft=Arrays.copyOfRange(posleft, 0, countleft);
				posright=Arrays.copyOfRange(posright, 0, countrighht);
				node.leftNodes=buildTree(X.getRowChoice(posleft),Y.getRowChoice(posleft));
				node.rightNode=buildTree(X.getRowChoice(posright),Y.getRowChoice(posright));
		   	}
	   }
	return node;
	}
	public void fit(Matrix X,Matrix  y)
	{
		treeNode=buildTree(X, y);
	}
	private double predict_one(Matrix X)
	{
		Node node=this.treeNode;
		while(node.leftNodes!=null)
		{	
			if(X.getValue(0,node.feature_index)>node.bestPoint)
			{
				node=node.leftNodes;
			}
			else
			{
				node=node.rightNode;
			}
		}
		return node.value;
	}
	public double[] predict(Matrix X)
	{
		double[] res=new double[X.row()];
		for(int i=0;i<X.row();i++)
		{
			res[i]=predict_one(X.getRow(i));
		}
		return res;
	}
	@Override
	public String toString() {
			return this.treeNode.toString();
		}
	class Node
	{
		public Double value;
		public Integer feature_index;
		public Double bestPoint;
		public Node leftNodes;
		public Node rightNode; 
		@Override
		public String toString() {
			StringBuffer bf=new StringBuffer();
			if(this.leftNodes!=null&&this.rightNode!=null)
			{
				bf.append(String.format("内部节点<%s>:\n",this.feature_index));
				String aString=leftNodes.toString();
				String bString=rightNode.toString();
				bf.append("\tleft:"+aString.replace("\n", "\n\t")+"\t");
				bf.append("\n");
				bf.append("\tright:"+bString.replace("\n", "\n\t")+"\t");
			}
			else
			{
				bf.append(String.format("叶节点(%s)",value));
			}	
			return bf.toString();
		}
	}
}
