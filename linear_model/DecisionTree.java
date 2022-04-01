package linear_model;

import java.util.Arrays;
import core.Matrix;
import core.Scical;
import core.linalg;

public class DecisionTree {
	private double threshold;
	private Node treeNode;
	public static void main(String[] args) {
		   Matrix y=new Matrix(new double[]{3,2,3,1,3,2,3,1,3,2,3,1,3,2,3,3,3,3,3,1,3,2,3,3});
		Matrix x=new Matrix(new double[][]{{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3},
										   {1,1,1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,1,1,2,2,2,2},
										   {1,1,2,2,1,1,2,2,1,1,2,2,1,1,2,2,1,1,2,2,1,1,2,2},
										   {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2}});
		x=x.T();
		DecisionTree tree=new DecisionTree();
		tree.fit(x, y);
		System.out.println(tree);
		System.out.println(new Matrix(tree.predict(x)));
	}
	public DecisionTree()
	{
		threshold=0.01;
	}
	public DecisionTree(double threshold)
	{
		this.threshold=threshold;
	}
	private static double entropy(Matrix Y)
	{
		double[] feature=linalg.unique(Y);
		Matrix p=new Matrix(1,feature.length);
		for(int i=0;i<Y.size();i++)
		{
			for(int j=0;j<feature.length;j++)
			{
				if(Y.getValue(i, 0)==feature[j])
					p.setValue(0,j,p.getValue(0,j)+1);
			}
		}
		p=p.dot(1.0/Y.size());
		return -p.dot(Scical.log2(p)).sum();
	}
	private double condition_entropy(Matrix X,Matrix Y)
	{
	   double res=0.0;
	   double[] feature=linalg.unique(X);

	   double[][] q=new double[feature.length][Y.size()];
	   for(int j=0;j<feature.length;j++)
	   {
		   int count=0;
		   for(int i=0;i<X.size();i++)
		   {
			   if(X.getValue(i, 0)==feature[j])
				   q[j][count++]=Y.getValue(i, 0);
		   }
		   res+=(double)count/Y.size()*entropy(new Matrix(Arrays.copyOfRange(q[j],0,count)));
	   }
	  return res;
	}
	private double information_gain(Matrix X,Matrix Y)
	{
		return entropy(Y)-condition_entropy(X, Y);
	}
	private Integer select(Matrix x,Matrix y)
	{
	     int n=x.shape()[1];
	     Matrix temp=new Matrix(1,n); 
	     for(int i=0;i<n;i++)
	     {
	    	 temp.setValue(0, i, information_gain(x.getCol(i),y));
	     }
	     int index=linalg.argmax(temp)[0];
	     if(temp.getValue(0, index)>threshold)
	    	 return index;
	     else 
	    	 return null;
	}
	private Node buildTree(Matrix X,Matrix Y)
	{ 
	   Node node=new Node();
	   Matrix elementcount=linalg.elementCount(Y);
	   node.value=linalg.argmax(elementcount)[0];
	   if(linalg.nonzero(elementcount).size()!=1)
	   {
		   Integer index=select(X, Y);
		   if(index!=null)
		   {
			   node.feature_index=index;
			   Matrix temp=X.getCol(index);
			   double[] xfeature=linalg.unique(temp);
			   int[][] q=new int[xfeature.length][Y.size()];
			   node.nextNodes=new Node[xfeature.length];
			   
			   for(int j=0;j<xfeature.length;j++)
			   {
				   int coun=0;
				   for(int i=0;i<temp.row();i++)
				   {
					   if(temp.getValue(i, 0)==xfeature[j])
						   q[j][coun++]=i;
				   } 
			   int[] tmp=Arrays.copyOfRange(q[j],0,coun);		
			   node.nextNodes[j]=buildTree(X.getRowChoice(tmp),Y.getRowChoice(tmp));
			   }
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
		while(node.nextNodes!=null)
		{
			Node childNode=node.nextNodes[(int)X.getValue(0,node.feature_index)-1];
			if(childNode==null)
			{
				break;
			}
			node=childNode;
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
	}class Node
	{
		public double value;
		public int feature_index;
		public Node[] nextNodes;
		@Override
		public String toString() {
			StringBuffer bf=new StringBuffer();
			if(this.nextNodes!=null)
			{
				bf.append(String.format("内部节点<%s>:\n",this.feature_index));
				for(int i=1;i<=this.nextNodes.length;i++)
				{
					String aString=String.format("[%s]->%s",i,this.nextNodes[i-1]);
					bf.append("\t"+aString.replace("\n", "\n\t")+"\n");
				}
			}
			else
			{
				bf.append(String.format("叶节点(%s)",value));
			}	
			return bf.toString();
		}
	}
}
