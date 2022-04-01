package linear_model;

import java.util.Map.Entry;
import java.util.TreeMap;
import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.linalg;

public class KNN {

	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	private int k_neighbor;
	private Matrix X;
	private Matrix y;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getColRange(0, 4);
		Matrix yMatrix=data.getCol(4);
		KNN logisticRegression=new KNN();
		logisticRegression.fit(xMatrix, yMatrix);
		for(int i=0;i<20;i++)
		{
			Matrix aMatrix=data.getRow(i).getColRange(0, 4);
			System.out.println(logisticRegression.predict(aMatrix));
		}
	}
	public KNN() {
		k_neighbor=5;
	}
	public KNN(int k_neighbor)
	{
		this.k_neighbor=k_neighbor;
	}
	public void fit(Matrix X,Matrix y)
	{
		this.X=X;
		this.y=y;
	}
	private int predict_one(Matrix x)
	{
		TreeMap<Double,Integer> map = new TreeMap<>();
		for(int i=0;i<X.row();i++)
		{
			map.put(linalg.l2(x.sub(X.getRow(i))),i);
		}
		int[] pos=new int[k_neighbor];
		java.util.Iterator<Entry<Double, Integer>> it = map.entrySet().iterator();
		for(int i=0;i<k_neighbor;i++)
		{
            Entry<Double, Integer> entry = it.next(); 
           	pos[i]=entry.getValue();
        }
        Matrix counts=linalg.elementCount(y.getRowChoice(pos));
        return linalg.argmax(counts)[0];
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
}
