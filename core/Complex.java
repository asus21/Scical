package core;

import java.io.Serializable;

import matherror.ComplexError;

public class Complex implements Serializable{

	/**
	 * @Field serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;
	private Fraction real;
	private Fraction imag;
	public static void main(String[] args) 
	{
		Complex aRadical=new Complex(1,"2");
		Complex bComplex=new Complex("4","-1");
		System.out.println(aRadical.mul(bComplex));
	}
	public Complex() {
		this.real=new Fraction();
		this.imag=new Fraction();
	}
    public Complex(Object real,Object imag)
    {
    	this.real=Fraction.parseToFraction(String.valueOf(real));
    	this.imag=Fraction.parseToFraction(String.valueOf(imag));
    }
    public Complex(Fraction real,Fraction imag)
    {
    	this.real=real;
    	this.imag=imag;
    }
	public double abs() {
		Fraction res=this.real.mul(this.real).add(this.imag.mul(this.imag));
		return Scical.sqrt(res.evalf());
	}
	public Double getReal() {
		return this.real.evalf();
	}
	public Double getImage() {
		return this.imag.evalf();
	}
	public void setReal(double real) {
		this.real=Fraction.parseToFraction(String.valueOf(real));
	}
	public void setImag(double imag) {
	
		this.imag=Fraction.parseToFraction(String.valueOf(imag));
	}
	public Complex add(Complex complex) {
		if(complex==null) throw new ComplexError("Addend is null");
		Fraction real=this.real.add(complex.real);
		Fraction imag=this.imag.add(complex.imag);
		return new Complex(real,imag);
	}
	public Complex sub(Complex complex) {
		if(complex==null) throw new ComplexError("Minuend is null");
		Fraction real=this.real.sub(complex.real);
		Fraction imag=this.imag.sub(complex.imag);
		return new Complex(real,imag);
	}	
	public Complex mul(Complex complex) {
		if(complex==null) throw new ComplexError("Multiplier is null");
		Fraction real=this.real.mul(complex.real).sub(this.imag.mul(complex.imag));
		Fraction imag=this.real.mul(complex.imag).add(this.imag.mul(complex.real));
		return new Complex(real,imag);
	}
	public Complex div(Complex complex) {
		if(complex==null) throw new ComplexError("Divisor is null");
		if(complex.abs()==0)throw new ComplexError("Divied By Zero");
		Complex top=this.mul(complex.conjugate());
		Complex bottom=complex.mul(complex.conjugate());
		return new Complex(top.real.div(bottom.real),top.imag.div(bottom.real));
	}
	public Complex conjugate()
	{
		return new Complex(this.real,this.imag.mul(new Fraction(-1,1)));
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null){return false;}
		if(obj instanceof Complex)
		{
		Complex temp=(Complex)obj;
		return temp.real.equals(this.real)&&temp.imag.equals(this.imag);
		}
		else {
			return false;
		}
	}
	@Override
	public String toString() {
		StringBuffer aBuffer=new StringBuffer();
		if(imag.evalf()<0)
			aBuffer.append(real+""+imag+"j");
		else {
			aBuffer.append(real+"+"+imag+"j");
		}
		return aBuffer.toString();
	}
}
