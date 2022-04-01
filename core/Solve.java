package core;
import java.io.Serializable;
import java.util.*;
public class Solve implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] basefix={"+","-","*","/","^","(",")"};
	private static final String[] highfix={"!","log","ln","exp","C","A","sin","cos","tan","asin","acos","atan","sinh","cosh","tanh","sqrt"};
	private static TreeMap<String,Integer> fixmap=new TreeMap<String,Integer>();
	private static Boolean find(String[] a,String b)
	{
		Boolean find=false;
		for(int i=0;i<a.length;i++)
		{
			if(a[i].equals(b))
			{
				find=true;
			}
		}
		return find;
	}
	private static void setProit()
	{
	   fixmap.put("+",7);
	   fixmap.put("-",7);
	   fixmap.put("*",6);
	   fixmap.put("/",6);
	   fixmap.put("(",1);
	   fixmap.put(")",1);
	   fixmap.put("!",2);
	   fixmap.put("^",3);
	   fixmap.put("exp",3);                  
	   fixmap.put("lg",5);
	   fixmap.put("ln",5);
	   fixmap.put("C",2);
	   fixmap.put("A",2);
	   fixmap.put("cos",2);
	   fixmap.put("sin",2);
	   fixmap.put("tan",2);
	   fixmap.put("asin",2);
	   fixmap.put("acos",2);
	   fixmap.put("atan",2);
	   fixmap.put("sinh",2);
	   fixmap.put("cosh",2);
	   fixmap.put("tanh",2);
	   fixmap.put("sqrt",2);
	}
	public static String[] postfix(String a)
	{
		setProit();
		Boolean flag=true;
		Stack<String> stack=new Stack<String>();
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<a.length();i++)
		{
			String temp=String.valueOf(a.charAt(i));
			if(i==0&&find(basefix,temp))
			{
				list.add(temp);
				flag=true;
			}
			else if(temp.equals("("))
			{
				stack.add(temp);
			}
			else if(temp.equals(")"))
			{
				while(stack.empty()!=true&&stack.peek().equals("(")!=true)
				{
					list.add(stack.pop());
				}
				if(stack.empty()!=true)
				stack.pop();
				
			}
			else if(i>0&&find(basefix,temp)&&find(basefix,String.valueOf(a.charAt(i-1))))
			{
				list.add(temp);
				flag=true;
			}
			else if(temp.compareTo("0")>=0&&temp.compareTo("9")<=0||temp.equals("."))
			{
				if(list.isEmpty())
					list.add(temp);
				else if(flag)
				{
					temp=list.remove(list.size()-1)+temp;
					list.add(temp);
				}
				else
				{
					list.add(temp);
					flag=true;
				}
			}		
			else
			{
				if(fixmap.containsKey(temp))
				{
				while(stack.isEmpty()!=true&&stack.peek().equals("(")!=true&&fixmap.get(temp)>=fixmap.get(stack.peek()))
				{
					list.add(stack.pop());	
				}
				}
				if(stack.isEmpty()!=true&&fixmap.containsKey(stack.peek())!=true)
				{
					temp=stack.pop()+temp;
				}
				stack.push(temp);
				flag=false;
			}
		}
		while(stack.empty()!=true)
			 list.add(stack.pop());
		String[] postfix=new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			postfix[i]=(String)list.get(i);
		}
		return postfix;
	}
	public static String caculate(String[] expr)
	{
		Stack<String> result=new Stack<String>();
		for(int i=0;i<expr.length;i++)
		{
			String a;
			String b;
			String temp=expr[i];
			if(find(basefix,temp))
			{
				b=result.pop();
				a=result.pop();
				result.push(caculator(a,b,temp));
			}
			else if(find(highfix,temp))
			{
				a=result.pop();
				result.push(caculator(a,temp));
			}
			else
			{
				result.push(temp);
			}
		}
		return result.pop();
 	}
	public static String caculator(String a,String fix)
	{
		double left,result=0;
		left=Double.valueOf(a);
		switch(fix)
		{
			case "!":result=Scical.factoral((int)left);break;
			case "ln":result=Math.log(left);break;
			case "log":result=Math.log10(left);break;
			case "sin":result=Math.sin(left);break;
			case "cos":result=Math.cos(left);break;
			case "tan":result=Math.tan(left);break;
			case "asin":result=Math.asin(left);break;
			case "acos":result=Math.acos(left);break;
			case "atan":result=Math.atan(left);break;
			case "sqrt":result=Math.sqrt(left);break;
			case "exp":result=Math.exp(left);break;
		}
		return String.valueOf(result);
	}
	public static String caculator(String a,String b,String fix)
	{
		Double left,right;
		double result=0.0;
		left=Double.valueOf(a);
		right=Double.valueOf(b);
		switch(fix)
		{
			case "+":result=left+right;break;
			case "-":result=left-right;break;
			case "*":result=left*right;break;
			case "/":result=left/right;break;
			case "^":result=Math.pow(left,right.intValue());break;
			case "C":result=Scical.factoral(left.intValue())/(Scical.factoral((int)(left-right))*Scical.factoral(right.intValue()));break;
			case "A":result=Scical.factoral(left.intValue())/(Scical.factoral(right.intValue()));break;
		}
		return String.valueOf(result);
	}
    public static String solve(String a)
	{
		String[] b=postfix(a);
		return caculate(b);
	}
}