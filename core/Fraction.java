package core;

import java.util.Arrays;
import matherror.FractionError;

public class Fraction
{
	private int bottom;
	private int top;
	public Fraction() {bottom=1;top=0;}
	public Fraction(int num,int den)
	{
		if(den==0)
			throw new FractionError("Denominator cannot be Zero");
		this.bottom=den;
		this.top=num;
	}
	public static void main(String[] args)
	{
		Fraction a=Fraction.parseToFraction("1.6");
		System.out.println(a);
	}
	private static int gcd(int a,int b)
	{
		int p,q;
		if(a==0||b==0)return 0;
		else if(a>=b){p=a;q=b;}
		else if(a<b){p=b;q=a;}
		else return 1;
		while(p%q!=0){a=p;b=q;p=b;q=a%b;}
		return q;
	}
	private void simplify()
	{
		int m=gcd(top, bottom);
		if(m!=0)
		{
			top=top/m;
			bottom=bottom/m;
		}
	}
	public static Fraction parseToFraction(String frac)
	{
		if(frac=="")throw new FractionError("The parsing string is null");
		if(frac.contains("/"))
		{
		String[] aStrings=frac.split("/");
		int up=Integer.parseInt(aStrings[0]);
		int down=Integer.parseInt(aStrings[1]);
		return new Fraction(up,down);
		}
		else {
			return recDecToFra(frac);
		}
	}
	public Fraction add(Fraction frac) {
		if(frac==null)throw new FractionError("Addend is null");
		Fraction resFraction=new Fraction();
		if(this.bottom==frac.bottom)
		{
			resFraction.top=this.top+frac.top;
			resFraction.bottom=this.bottom;
		}
		else{
			resFraction.bottom=this.bottom*frac.bottom;
			resFraction.top=this.top*frac.bottom+frac.top*this.bottom;
		}
		resFraction.simplify();
		return resFraction;
	}
	public Fraction sub(Fraction frac) {
		if(frac==null)throw new FractionError("Minuend is null");
		Fraction resFraction=new Fraction();
		if(this.bottom==frac.bottom)
		{
			resFraction.top=this.top-frac.top;
			resFraction.bottom=this.bottom;
		}
		else{
			resFraction.bottom=this.bottom*frac.bottom;
			resFraction.top=this.top*frac.bottom-frac.top*this.bottom;
		}
		resFraction.simplify();
		return resFraction;
	}
	public Fraction mul(Fraction frac) {
		if(frac==null) throw new FractionError("Multiplier is null");
		Fraction resFraction=new Fraction();
		resFraction.bottom=this.bottom*frac.bottom;
		resFraction.top=this.top*frac.top;
		resFraction.simplify();
		return resFraction;
	}
	public Fraction div(Fraction frac) {
		if(frac==null) throw new FractionError("Divisor is null");
		if(frac.top==0)
			throw new FractionError("Divided by Zero");
		Fraction resFraction=new Fraction();
		resFraction.bottom=this.bottom*frac.top;
		resFraction.top=this.top*frac.bottom;
		resFraction.simplify();
		return resFraction;
	}
	public boolean isInteger() {
		return this.bottom==1||this.top==0;
	}
	public double evalf()
	{
		return (double)top/bottom;
	}
	public int getTop() {
		return this.top;
	}
	public int getBottom() {
		return this.bottom;
	}
	public void setTop(int num){
		this.top=num;
	}
	public void setBottom(int den) {
		if(den==0)
			throw new FractionError("Denominator cannot be Zero");
		this.bottom=den;
	}
	@Override
	public String toString() {
		StringBuffer bf=new StringBuffer();
		if(bottom==1)
		{
			bf.append(top);
		}
		else if(bottom==-1){
			bf.append(-top);
		}
		else if(top==bottom)
		{
			bf.append("1");
		}
		else if(top==0)
		{
			bf.append("0");
		}
		else if(top*bottom>0){
			bf.append(top+"/"+bottom);
		}
		else{
			bf.append("-"+(top>0?top:-top)+"/"+(bottom>0?bottom:-bottom));
		}
		return bf.toString();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null){return false;}
		if(obj instanceof Fraction)
		{
			Fraction frac=(Fraction)obj;
			return frac.top*frac.bottom==this.top*this.bottom;
		}
		else if(obj.getClass().isPrimitive()||obj instanceof String||obj instanceof Double ||obj instanceof Integer
				||obj instanceof Boolean||obj instanceof Float || obj instanceof Character ||obj instanceof Long
				||obj instanceof Byte){
			Fraction fraction=Fraction.parseToFraction(String.valueOf(obj));
			return fraction.top*fraction.bottom==this.top*this.bottom;
		}
		else {
			throw new FractionError("unsupported object type for comparing");
		}
	}
	public String fraToRecDec(){
		int numInt=top;
		int denoInt=bottom;
		int divider = numInt / denoInt;
		numInt=(numInt>0)?numInt:-numInt;
		denoInt=denoInt>0?denoInt:-denoInt;
		numInt = numInt % denoInt;
		int lcm =gcd(numInt,denoInt);
		if(lcm != 1 && lcm != 0){
			numInt = numInt / lcm;
			denoInt = denoInt / lcm;
		}
		int markArr[] = new int[denoInt-1];
		int resuArr[] = new int[denoInt];
		Arrays.fill(markArr, -1);
		Arrays.fill(resuArr, -1);
		int index = -1;
		int startIndex=0;
		numInt = numInt * 10;
		while(true){
			index++;
			int remainder = numInt %denoInt;
			if(remainder == 0)
			{
				resuArr[index]=numInt/denoInt;
				startIndex=index+1;
				break;		
			}
			if(markArr[numInt/10-1]!=-1)
			{
				startIndex=markArr[numInt/10-1];
				break;
			}
			else{
				markArr[numInt/10-1] = index;
			}			
			resuArr[index] = numInt / denoInt;
			numInt = remainder * 10;
		}
		StringBuffer resuSB = new StringBuffer();
		resuSB.append(divider);
		resuSB.append(".");
		for(int i=0;i<startIndex;i++)
		{
			if(resuArr[i]!=-1)
				resuSB.append(resuArr[i]);
		}
		resuSB.append("[");
		for(int i=startIndex;i<denoInt;i++){	
			if(resuArr[i] == -1)
			{
				if(i==startIndex){resuSB.append(0);}
				break;
			}
			resuSB.append(resuArr[i]);
		}
		resuSB.append("]");
		return resuSB.toString();
	}
	
	public static Fraction recDecToFra(String recDecStr){
		if(recDecStr=="")throw new FractionError("The parsing string is null");
		if(!recDecStr.contains("[")) 
		{
			if(!recDecStr.contains("."))
			{
				recDecStr+=".";
			}
			recDecStr+="[0]";
		}
		boolean isNeg=recDecStr.contains("-");
		int dotPos = recDecStr.indexOf(".");
		int leftBrcPos = recDecStr.indexOf("[");
		int rightBrcPos = recDecStr.indexOf("]");
		if(leftBrcPos+1==rightBrcPos)
			throw new FractionError("the part of decimal is empty");
		int prelength = leftBrcPos - dotPos - 1 ;  // 非循环节长度
		int reclength = rightBrcPos - leftBrcPos - 1;//循环节长度
		int intData = Integer.parseInt(recDecStr.substring(0, recDecStr.indexOf(".")));//整数部分
		intData=intData>0?intData:-intData;
		int preData = 0;
		if(prelength != 0)
			preData = Integer.parseInt(recDecStr.substring(dotPos+1,leftBrcPos));   //非循环节
		int recData = Integer.parseInt(recDecStr.substring(leftBrcPos+1,rightBrcPos));//循环节
		int up=Scical.TenPow(reclength)*preData+recData-preData;
		int down=Scical.TenPow(prelength+reclength)-Scical.TenPow(prelength);
		up=intData*down+up;
		if(isNeg)up=-up;
		int lcm=gcd(up, down);
		if(lcm!=0&&lcm!=1)
		{
			up=up/lcm;
			down=down/lcm;
		}
		return new Fraction(up,down);
		
	}
}
