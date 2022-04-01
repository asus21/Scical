package linear_model;
import jexcel.JCSV;
import jexcel.Table;
import core.Matrix;
import core.linalg;

public class OLSLogisticRegression extends OLSLinearRegression{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) throws Exception {
		Table aTable=JCSV.read_csv("iris.csv");
		Matrix data=aTable.data();
		Matrix xMatrix=data.getRange(25, 76, 0, 4);
		Matrix yMatrix=data.getCol(4).getRowRange(25, 76);
		OLSLogisticRegression logisticRegression=new OLSLogisticRegression();
		logisticRegression.fit(xMatrix, yMatrix);
		Matrix aMatrix=data.getRow(3).getColRange(0, 4);
		System.out.println(logisticRegression.predict(aMatrix));
//		double[] err=logisticRegression.error();
//		for(int i=0;i<err.length;i++)
//		{
//			System.out.println(err[i]);
//		}
		System.out.println(logisticRegression.coef());
	}
	public OLSLogisticRegression() {
		
	}
	@Override
	public Matrix predict(Matrix X) {
		//super.predict(X);
		Matrix X_=super.processX(X);
		Matrix y_predict= linalg.mul(X_,super.W);
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
}
