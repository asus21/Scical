package linear_model;



import jexcel.JCSV;
import core.Matrix;
import core.linalg;

public class softLineaLogisticRegression extends OLSLinearRegression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Matrix W;
	public softLineaLogisticRegression() {
		// TODO Auto-generated constructor stub
		
	}
	public static void main(String[] args) throws Exception {
		Matrix data=JCSV.read_csv("iris.csv").data();
		Matrix X=data.getColRange(0, 4);
		Matrix y=data.getCol(4);
		softLineaLogisticRegression softLineaLogisticRegression=new softLineaLogisticRegression();
		softLineaLogisticRegression.fit(X, y);
		Matrix yMatrix=softLineaLogisticRegression.predict(X);
		System.out.println(yMatrix.getRowRange(50, 80));
		
		//System.out.println(yMatrix);
	}
	@Override
	public void fit(Matrix X, Matrix y) {
		// TODO Auto-generated method stub
		Matrix _xMatrix=processX(X);
		Matrix _yMatrix=processY(y);
		W=ols(_xMatrix, _yMatrix);
	}
	@Override
	public Matrix predict(Matrix X) {
		// TODO Auto-generated method stub
		Matrix X_=super.processX(X);
		Matrix y_predict= linalg.mul(X_,W);
		int[] pos=linalg.argmax(y_predict);
		for(int i=0;i<y_predict.row();i++)
			for(int j=0;j<y_predict.col();j++)
			{
				if(j==pos[i])
					y_predict.setValue(i, j, 1.0);
				else 
					y_predict.setValue(i, j, 0.0);	
			}
		 
		return y_predict;
	}
	
}
