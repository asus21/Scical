package iostream;
import java.io.*;

import matherror.FileError;
public class os
{
	private static String defaultPath=System.getProperty("java.class.path");
	private static File f1=new File(defaultPath);
	public static String name=System.getProperty("sun.desktop");
	public static String path=f1.getPath();
	public static void chdir(String filename)
	{
		File file;
		if(!filename.contains(":")){filename=path+"/"+filename;}
		if(exist(filename))
		{
		file=new File(filename);
		f1=file;
		path=f1.getPath();
		}
		else
		{
			throw new FileError(filename+" don't exist");
		}
	}
	public static String getcwd()
	{	
		return f1.getAbsolutePath();
	}
	public static String getParent()
	{
		return f1.getParent();
	}
    public static Boolean mkdir(String filename)
    {
    	File file = null;
    	Boolean success = false;
    	if(!filename.contains(":")) filename=path+"/"+filename;
    	if(!exist(filename))
    	{
    		file=new File(filename);
    		success=file.mkdir();
    	}
    	else
    		throw new FileError(filename+" exist");
    	return success;
    }
    public static Boolean rmdir(String filename)
    {
    	File file;
    	Boolean success=false;
    	if(!filename.contains(":")) filename=path+"/"+filename;
    	if(exist(filename))
    	{
    		file=new File(filename);
    		success=file.delete();
    	}   
    	else 
    		throw new FileError(filename+" don't exist");
    	return success;
    }
    public static Boolean exist(String filename)
    {	
    	File file=new File(filename);
    	return file.exists();
    }   	
}