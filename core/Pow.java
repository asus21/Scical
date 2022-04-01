package core;
import matherror.PowError;

public class Pow{
	private Fraction root;
	private Fraction exp;
	
	public Pow() {this.root=new Fraction();this.exp=new Fraction();}
	public Pow(Object root,Object exp)
	{
		this.root=Fraction.parseToFraction(String.valueOf(root));
		this.exp=Fraction.parseToFraction(String.valueOf(exp));
	}
	public static void main(String[] args) 
	{
		Pow aPow=new Pow(1,2);
		Pow aRadical2=new Pow(2,2);
		System.out.println(((Add)aPow.add(aRadical2)).evalf());
	}
	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		if(exp.isInteger())
		{
			if(exp.equals(1))
			{	
				buffer.append(root);
				return buffer.toString();
			}
			else if(exp.equals(0))
			{
				buffer.append(1);
				return buffer.toString();
			}
		}
		if(exp.evalf()<=1&&exp.evalf()>=-1&&exp.getBottom()%2==0)
		{
			if(root.evalf()<0) throw new PowError("the root is negative for exp's Denomeric is even");
		}
		if(root.getTop()==0)
		{
			if(exp.evalf()<0)throw new PowError("the root is zero for exp is negative");	
			buffer.append(0);
			return buffer.toString();
		}
		buffer.append(root+"^"+exp);
		return buffer.toString();
	}
	public void setRoot(Object root) {
		if(!(root instanceof String)||root.getClass().isPrimitive())
			throw new PowError("unsupported object type");
		this.root=Fraction.parseToFraction(String.valueOf(root));
	}
	public void setExp(Object exp) {
		if(!(exp instanceof String)||root.getClass().isPrimitive())
			throw new PowError("unsupported object type");
		this.exp=Fraction.parseToFraction(String.valueOf(exp));
	}
	public double getRoot() {
		return this.root.evalf();
	}
	public double getExp() {
		return this.exp.evalf();
	}
	public Object add(Pow aPow)
	{
		if(this.equals(aPow))
		{
			return new Mul(2,this);
		}
		else 
		{
			return new Add(this,aPow);
		}
	}
	public Object sub(Pow aPow)
	{
		if(this.equals(aPow))
		{
			return new Add(0);
		}
		else {
			return new Sub(this,aPow);
		}
	}
	public Object mul(Pow radical) {
		Fraction temp=null;
		if(exp.equals(radical.exp)&&root.equals(radical.root))
		{
			return new Pow(root,1);
		}
		else if(exp.equals(radical.exp))
		{
			temp=this.root.mul(radical.root);
			return new Pow(root,temp);
		}
		else if(root.equals(radical.root))
		{
			temp=exp.add(radical.exp);
			return new Pow(root,temp);
		}
		else {
			return new Mul(this,radical);
		}
	}
	public double evalf()
	{
		return Scical.pow(root.evalf(),exp.evalf());
	}
	@Override
	public boolean equals(Object radical)
	{
	if(radical==null)throw new PowError("the compared object is null");
	if(radical instanceof Pow)
	{
		Pow aPow=(Pow)radical;
		if(root.equals(aPow.root)&&exp.equals(aPow.exp))
		{
			return true;
		}
		else {
			return false;
		}
	}
	else {
	 throw new PowError("unsupported comparable type "+radical.getClass());
	}
	}
}
