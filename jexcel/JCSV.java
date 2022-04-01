package jexcel;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import iostream.file;
public class  JCSV{
	public JCSV(){}
	@SuppressWarnings("unchecked")
	public static Table read_csv(String filename) throws Exception
	{
		Table a=new Table();
		LinkedHashMap<?, ArrayList<Object>> b=(LinkedHashMap<?, ArrayList<Object>>)file.readCSV(filename);
		Object[] head=b.keySet().toArray();
		for(int i=0;i<head.length;i++)
		{
			if(!head[i].equals(""))
				a.addCol(b.get(head[i]).toArray(),head[i]);
		}
		return a;
	}
}