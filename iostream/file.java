package iostream;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;


import matherror.FileError;

public class file
{
	private static BufferedReader bReader;
	private static BufferedWriter bWriter;
	private static File f;
	private static FileInputStream fin;
	private static FileOutputStream fout;
	private static BufferedWriter bw;
	private static BufferedReader bd;
	public static Boolean open(String filename,String flags) throws Exception
	{
		if(!filename.contains(":")) filename=os.getcwd()+"/"+filename;
		f=new File(filename);
		if(flags.contains("w"))
		{
			fout=new FileOutputStream(f);
		}
		if(flags.contains("a")||flags.contains("wa"))
		{
			fout=new FileOutputStream(f,true);
		}
		if(flags.contains("r"))
		{
			fin=new FileInputStream(f);
		}
		else
		{
			throw new FileError("the flags mast be \"'r','w','rw','wa','a'\"");
		}
		return true;
	}
	public static String read() throws Exception
	{
		byte[] b=new byte[(int)f.length()];
		fin.read(b);
		String string=new String(b);
		return string;
	}
	public static String read(int start,int stop) throws Exception
	{
		byte[] b=new byte[(int)f.length()];
		fin.read(b, start, stop);
		String string=new String(b);
		return string;
	}
	public static void write(Object string) throws Exception
	{
		byte[] b=string.toString().getBytes();
		fout.write(b);
	}
	public static void write(Object string,int start,int length) throws Exception
	{
		byte[] b=string.toString().getBytes();
		fout.write(b, start, length);
	}
	public static String readline() throws Exception
	{
		if(fin==null)throw new FileError("the file doesn't open!");
		String line;
		bReader=new BufferedReader(new InputStreamReader(fin));
		line=bReader.readLine();
		if(line==null)bReader.reset();
		return line;
	}
	public static void writeline(String string) throws Exception
	{
		bWriter=new BufferedWriter(new FileWriter(f));
		bWriter.write(string);
	}
	public static void saveCSV(Object[] content,String filename,String flags) throws Exception
	{
		String extent=".csv";
		if(!filename.contains(".")) filename+=extent;
		if(!filename.contains(":"))	filename=os.getcwd()+"/"+filename;
		File file=new File(filename);
		StringBuffer sBuffer=new StringBuffer();
		bw = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<content.length;i++)
		{
			if(content[i]!=null)
			sBuffer.append(content);
			if(i!=content.length-1)sBuffer.append(",");
		}
		bw.write(sBuffer.toString());
		if(flags=="a") bw.append(sBuffer.toString());
		else 
			bw.write(sBuffer.toString());
		bw.close();
	}
	public static void saveCSV(Object[][] content,String filename,String flags) throws Exception
	{
		String extent=".csv";
		if(!filename.contains(".")) filename+=extent;
		if(!filename.contains(":"))	filename=os.getcwd()+"/"+filename;
		File file=new File(filename);
		StringBuffer sBuffer=new StringBuffer();
		bw = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<content.length;i++)
		{
			for(int j=0;j<content[i].length;j++)
			{ 
				if(content[i][j]!=null)
				sBuffer.append(content);
				if(j!=content[i].length-1)sBuffer.append(",");sBuffer.append(",");
			}
			if(i!=content.length-1)sBuffer.append("\n");
		}
		bw.write(sBuffer.toString());
		if(flags=="a") bw.append(sBuffer.toString());
		else 
			bw.write(sBuffer.toString());
		bw.close();
	}
	public static void saveCSV(Object[] content,String filename) throws Exception
	{
		String extent=".csv";
		if(!filename.contains(".")) filename+=extent;
		if(!filename.contains(":"))	filename=os.getcwd()+"/"+filename;
		File file=new File(filename);
		StringBuffer sBuffer=new StringBuffer();
		bw = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<content.length;i++)
		{
			if(content[i]!=null)
			sBuffer.append(content[i]);
			if(i!=content.length-1)sBuffer.append(",");
		}
		bw.write(sBuffer.toString());
		bw.close();
	}
	public static void saveCSV(Object[][] content,String filename) throws IOException
	{
		String extent=".csv";
		if(!filename.contains(".")) filename+=extent;
		if(!filename.contains(":"))	filename=os.getcwd()+"/"+filename;
		
		File file=new File(filename);
		StringBuffer sBuffer=new StringBuffer();
		bw = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<content.length;i++)
		{
			for(int j=0;j<content[i].length;j++)
			{ 
				if(content[i][j]!=null)
				sBuffer.append(content[i][j]);
				if(j!=content[i].length-1)sBuffer.append(",");
			}
			if(i!=content.length-1)sBuffer.append("\n");
		}
		bw.write(sBuffer.toString());
		bw.close();
	}
	public static LinkedHashMap<?, ?> readCSV(String filename) throws Exception
	{
		String extent=".csv";
		if(!filename.contains(".")) filename+=extent;
		if(!filename.contains(":"))	filename=os.getcwd()+"/"+filename;
		File file=new File(filename);
		bd = new BufferedReader(new FileReader(file));
		String line;
		String[] head;
		head=bd.readLine().split(",");
		@SuppressWarnings("unchecked")
		ArrayList<String>[] objects=new ArrayList[head.length];
		for(int i=0;i<head.length;i++)
			objects[i]=new ArrayList<String>();
		while(true)
		{
			line=bd.readLine();
			if(line==null)break;
			String[] aStrings;
			aStrings=line.split(",");	
			for(int i=0;i<aStrings.length;i++)
			{
				objects[i].add(aStrings[i]);
			}
			
		}
		LinkedHashMap<String,ArrayList<String>> aMap=new LinkedHashMap<String,ArrayList<String>>();
		for(int i=0;i<head.length;i++)
		{
			aMap.put(head[i], objects[i]);
		}
		return aMap;
	}
	public static void close() throws Exception
	{
		if(fin!=null)fin.close();
		if(fout!=null)fout.close();
		if(bd!=null)bd.close();
		if(bReader!=null)bReader.close();
		if(bw!=null)bw.close();
		if(bWriter!=null)bWriter.close();
	}
}
