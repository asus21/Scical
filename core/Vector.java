package core;

public class Vector extends Matrix{
	private static final long serialVersionUID = 1L;
	private double[] vec;
	private int length;
	public Vector(double[] array) {
		super(1,array.length);
		this.length=array.length;
		vec=new double[length];
		for(int i=0;i<length;i++)
		{	
			vec[i]=array[i];
		}
	}
	public Vector(int length)
	{
		super(1, length);
		this.vec=new double[length];
		this.length=length;
	}
	@Override
	public void setValue(int row, int col, double value) {
		setVec(col, value);
	}
	public void setVec(int col,double value)
	{
		vec[col]=value;
	}
	@Override
	public double getValue(int row, int col) 
	{
		return getVec(col);
	}
	public double getVec(int col)
	{
		return vec[col];
	}
	public double dot(Vector vector)
	{
        double dot=0.0;
		for(int i=0;i<length;i++)
			dot+=vector.vec[i]*this.vec[i];
		return dot;
	}
	public static void main(String[] args) {
	  Vector aVector=new Vector(new double[]{1,2,3});
	  Vector bVector=new Vector(new double[]{2,3,4});
	  System.out.println(aVector.dot(bVector));
	}
}
