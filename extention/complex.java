package extention;

import core.Fraction;
import core.Scical;
public class complex {

	private Fraction real;
	private Fraction imag;
	public static void main(String[] args) 
	{
		complex aRadical=new complex(new Fraction(2,1),new Fraction(3,3));
		complex bComplex=new complex(new Fraction(3,1),new Fraction(4,1));
		System.out.println(bComplex.add(aRadical));
	}
	public complex() {
		this.real=new Fraction(0,0);
		this.imag=new Fraction(0,0);
	}
    public complex(Fraction real,Fraction imag)
    {
    	this.real=real;
    	this.imag=imag;
    }
	public Double abs() {
		Fraction res=this.real.mul(this.real).add(this.imag.mul(this.imag));
		return Scical.sqrt(res.evalf());
		
	}
	public Fraction getReal() {
		return this.real;
	}

	public Fraction getImage() {
		return this.imag;
	}
	public void setReal(Fraction real) {
		this.real=real;
	}
	public void setImag(Fraction imag) {
		this.imag=imag;
	}
	public complex add(complex complex) {
		Fraction real=this.real.add(complex.real);
		Fraction imag=this.imag.add(complex.imag);
		return new complex(real,imag);
	}
	public complex sub(complex complex) {
		Fraction real=this.real.sub(complex.real);
		Fraction imag=this.imag.sub(complex.imag);
		return new complex(real,imag);
	}	
	public complex mul(complex complex) {
		Fraction real=this.real.mul(complex.real).sub(this.imag.mul(complex.imag));
		Fraction imag=this.real.mul(complex.imag).add(this.imag.mul(complex.real));
		return new complex(real,imag);
	}
	public complex div(complex complex) {
		complex top=this.mul(complex.conjugate());
		complex bottom=complex.mul(complex.conjugate());
		return new complex(top.real.div(bottom.real),top.imag.div(bottom.real));
	}
	public complex conjugate()
	{
		return new complex(this.real,new Fraction(-this.imag.getTop(),this.imag.getBottom()));
	}
	public boolean equals(complex obj) {
		return obj.real==this.real&&obj.imag==this.imag;
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

