package iostream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class jserial
{
	private static String extent=".jsl";
	private static File file;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static FileOutputStream fout;
	private static FileInputStream fin;
	public static <T> Boolean save(T a,String filename) throws Exception
	{
		if(!filename.contains("."))
		{
			filename+=extent;
		}
		if(!filename.contains(":"))
			filename=os.getcwd()+"/"+filename;
		file=new File(filename);
		fout=new FileOutputStream(file);
		out=new ObjectOutputStream(fout);
		out.writeObject(a);
		close();
		return true;
	}
	public static  Object dummy(String filename) throws Exception
	{
		if(!filename.contains("."))
		{
			filename+=extent;
		}	
		if(!filename.contains(":"))
			filename=os.getcwd()+"/"+filename;
		file=new File(filename);
		fin=new FileInputStream(file);
		in=new ObjectInputStream(fin);
		Object obj =in.readObject();
		close();
		return obj;
	}
	public static void close() throws Exception
	{
		if(fin!=null)fin.close();
		if(fout!=null)fout.close();
		if(out!=null)out.close();
		if(in!=null)in.close();
	}
}