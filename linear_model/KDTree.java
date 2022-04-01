package linear_model;

import java.util.Stack;
import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.Scical;
import core.linalg;

public class KDTree {

	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	private Matrix x_train;
	private Matrix y_train;
	@SuppressWarnings("unused")
	private int k_neighbors;
	private BSTree tree;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getColRange(0, 4);
		Matrix yMatrix=data.getCol(4);
		KDTree logisticRegression=new KDTree();
		logisticRegression.fit(xMatrix, yMatrix);
		for(int i=0;i<20;i++)
		{
			Matrix aMatrix=data.getRow(i).getColRange(0, 4);
			System.out.println(logisticRegression.predict(aMatrix));
		}
	}
    public KDTree()
    {
    	k_neighbors=5;
    }
    public KDTree(int k_neighbors)
    {
    	this.k_neighbors=k_neighbors;
    }
    private BSTree kd_tree_build(Matrix x,int depth,int length)
    {
    	
       	BSTree nodeTree=new BSTree();
        double mid=linalg.median(x.getCol(depth));
        int[] index=linalg.index(x,mid);
        nodeTree.value=x.getRow(index[0]);
        if(x.row()>6)
        {
	        int[] left=linalg.lt(x.getCol(depth),mid);
	        int[] right=linalg.gt(x.getCol(depth), mid);
	        nodeTree.lefTree=kd_tree_build(x.getRowChoice(left), (depth+1)%length,length);
	        nodeTree.rightTree=kd_tree_build(x.getRowChoice(right), (depth)%length,length);
        }
        return nodeTree;
    }
    public void fit(Matrix x,Matrix y)
    {
    	this.x_train=x;
    	this.y_train=y;
    	this.tree=kd_tree_build(x,0,x.col());
    }
    private int predict_one(Matrix x)
    {
    	Matrix nearest=tree.searchNearest(x).value;
    	int pos=linalg.index(x_train, nearest);
    	return (int) y_train.getValue(pos, 0);
    }
    public Matrix predict(Matrix x)
	{
		Matrix temp=new Matrix(1,x.row());
		for(int i=0;i<x.row();i++)
		{
			temp.setValue(0,i ,predict_one(x.getRow(i)));
		}
		return temp;
	}
    class BSTree
    {
    	public Matrix value;
    	public BSTree lefTree;
    	public BSTree rightTree;
    	int i=0;
    	BSTree searchNearest(Matrix key)
    	{
    		BSTree tree=this;
    		Stack<BSTree>  search=new Stack<>();
    		while(tree!=null)
    		{
    		   search.add(tree);
    		   if(key.getValue(0,i)<=tree.value.getValue(0, i))
    		   {
    			   tree=tree.lefTree;
    			   i=(i+1)/key.col();
    		   }
    		   else 
    		   {
    			   tree=tree.rightTree;
    			   i=(i+1)/key.col();
    		   }
    		}
			BSTree nearest=search.pop();
			BSTree temp;
			double distance=linalg.l2(nearest.value.sub(key));
			int j=0;
			while(!search.isEmpty())
			{
				temp=search.pop();
				if(temp.lefTree==null&&temp.rightTree==null)
				{
					if(linalg.l2(nearest.value.sub(key))>linalg.l2(temp.value.sub(key)))
					{
						nearest=temp;
					}
				}
				else {
					if(Scical.abs(temp.value.getValue(0, i)-key.getValue(0, j))<distance)
					{
						if(linalg.l2(nearest.value.sub(key))>linalg.l2(temp.value.sub(key)))
						{
							nearest=temp;
							distance=linalg.l2(temp.value.sub(key));
						}
						if(key.getValue(0, j)<=temp.value.getValue(0, j))
						{
							tree=temp.rightTree;
						}
						else {
							tree=temp.lefTree;
						}
						if(tree!=null)
							search.add(tree);
					}
				}
			}
    		return nearest;
    	}
    }
}
