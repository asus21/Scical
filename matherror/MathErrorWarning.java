package matherror;

public class MathErrorWarning{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	 public MathErrorWarning(String error) {
		// TODO Auto-generated constructor stub
		 StackTraceElement[] aElements=new Throwable().getStackTrace();
			System.err.println("Warning in thread \"main\""+getClass()+":"+error);
			for(int i=1;i<aElements.length;i++)
			{
				System.err.println("\t at "+aElements[i]);
			}	
	}
}
