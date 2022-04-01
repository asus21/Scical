package jexcel;
import iostream.file;
import java.io.Serializable;
import java.util.ArrayList;
import core.Matrix;
import core.linalg;
import matherror.TableError;
import matherror.TableIndexError;

public class Table implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Object> head=new ArrayList<Object>();
	private ArrayList<Object> index=new ArrayList<Object>();
	private int maxSize=100;
	private int row=0;
	private int col=0;
	private Object[][] content=new Object[maxSize][maxSize];
	private String[] label=new String[26];
	public Table()
	{
		for(int i=0;i<26;i++)
		{
			label[i]=String.valueOf((char)('A'+1));
		}
	}
	public Table(double[][] data,String[] rowName,String[] colName)
	{
		for(int i=0;i<26;i++)
		{
			label[i]=String.valueOf((char)('A'+i));
		}
		row=data.length;
		col=data[0].length;
		resize();
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				content[i][j]=data[i][j];
			}
		}
		if(colName!=null)
		{
			for(String x:colName)
			{
				head.add(x);
			}
		}
		else {
			for(int i=0;i<col;i++)
			{
				int j=i/26;
				
				head.add(label[i%26]+"_"+j);
			}
		}
		if(rowName!=null)
		{
		for(String x:rowName)
			index.add(x);
		}
		else {
			for(int i=0;i<row;i++)
			{
			 index.add(String.valueOf(i));
			}
		}
	}
	private void resize()
	{
		int newSize=0;
		Object[][] temp = null;
		if(row>maxSize&&col>maxSize){
			while(row>maxSize&&col>maxSize)
			{
				newSize=maxSize=2*maxSize;
				temp=(Object[][])new Object[newSize][newSize];
				for(int i=0;i<maxSize;i++)
				{
					for(int j=0;j<maxSize;j++)
					temp[i][j]=content[i][j];
				}	
				content=temp;
				maxSize=newSize;
			}
		}
		else if(row>maxSize)
		{
			while(row>maxSize)
			{
				newSize=maxSize=2*maxSize;
				temp=new Object[newSize][newSize];
				for(int i=0;i<maxSize;i++)
				{
					for(int j=0;j<col;j++)
					temp[i][j]=content[i][j];
				}
				content=temp;
				maxSize=newSize;
			}
		}
		else if(col>maxSize){
			while(col>maxSize)
			{
				newSize=maxSize=2*maxSize;
				temp=(Object[][])new Object[newSize][newSize];
				for(int i=0;i<row;i++)
				{
					for(int j=0;j<maxSize;j++)
					temp[i][j]=content[i][j];
				}	
				content=temp;
				maxSize=newSize;
			}
		}
	}
	public void addRow(Object[] data,Object name)
	{
		if(data.length>row){col=data.length;changeHead(null);}
		row++;
		resize();
		content[row-1]=data;
		if(name==null)
			index.add(String.valueOf(row));
		else 
			index.add(name);
	}
	private void changeIndex(String[] rowName)
	{
		if(rowName!=null)
		{
		for(String x:rowName)
			index.add(x);
		}
		else {
			for(int i=0;i<row;i++)
			{
			 index.add(String.valueOf(i));
			}
		}
	}
	private void changeHead(String[] colName)
	{
		if(colName!=null)
		{
			for(String x:colName)
			{
				head.add(x);
			}
		}
		else {
			for(int i=0;i<col;i++)
			{
				int j=i/26;
				
				head.add(label[i%26]+"_"+j);
			}
		}
	}
	public void addCol(Object[] data,Object name)
	{
		if(data.length>row){row=data.length;changeIndex(null);}
		resize();
		col++;
		if(name==null)
			head.add(label[col%26]+"_"+col/26);
		else 
			head.add(name);
		for(int j=0;j<row;j++)
		{
			content[j][col-1]=data[j];
		}
	}
	public Matrix getRow(Object rowName)
	{
		Matrix result=new Matrix(1,col);
		int n=index.indexOf(rowName);
		if(n<0)throw new TableIndexError("the object isn't in the table");
		for(int i=0;i<col;i++)
		{
			result.setValue(0, i,Double.valueOf(content[n][i].toString()));
		}
		return  result;
	}
	
	public Matrix getCol(String colName)
	{
		Matrix temp=new Matrix(row,1);
		int n=head.indexOf(colName);
		if(n<0)throw new TableIndexError("the object isn't in the Table");
		for(int i=0;i<row;i++)
		{
			temp.setValue(i, 0, Double.valueOf(content[i][n].toString()));
		}
		return temp;
	}
	public void drop(String name,int axis)
	{
		if(axis==0)
		{
		int pos=index.indexOf(name);
		if(pos<0)
		{
			throw new TableIndexError("cannot find the object");
		}
		for(int i=pos;i<row-1;i++)
		{
			content[i]=content[i+1];
		}
		row--;
		index.remove(name);
		}
		else if(axis==1)
		{
			int pos=head.indexOf(name);
			if(pos<0)
			{
				throw new TableIndexError("cannot find the object");
			}
			for(int j=0;j<row;j++)
			{
			for(int i=pos;i<col-1;i++)
			{
				content[j][i]=content[j][i+1];
			}
			}
			col--;
			head.remove(name);
		}
		else {
			throw new TableError("the axis is out of dim=2");
		}
	}
	public ArrayList<Object> index()
	{
		return index;
	}
	public ArrayList<Object> head()
	{
		return head;
	}
	public Matrix data()
	{
		Matrix temp=new Matrix(row,col);
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				temp.setValue(i, j, Double.valueOf(content[i][j].toString()));
			}
		}
		return temp;
	}
	public Matrix mean()
	{
		Matrix temp=data();
		return linalg.mean(temp, 0);
	}
	public Matrix var()
	{
		Matrix temp=data();
		return temp.std();
	}
	public void to_csv(String filename) throws Exception
	{
		Object[][] aObjects=new Object[row+1][col+1];
		for(int i=0;i<row;i++) aObjects[i][0]=index.get(i);
		for(int j=0;j<col;j++) aObjects[0][j]=head.get(j);
		for(int i=1;i<row+1;i++)
		{
			for(int j=0;j<col;j++)
			{
				aObjects[i][j]=content[i-1][j];
			}
		}
		file.saveCSV(aObjects, filename);
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("\t");
		for(int i=0;i<col;i++)
		{
			sb.append(head.get(i));
			sb.append("\t");
		}
		sb.append("\n");
		if(row>20)
		{
		for(int i=0;i<5;i++)
		{
			sb.append(index.get(i));
			sb.append("\t");
			for(int j=0;j<this.col;j++)
			{
				sb.append(content[i][j]);
				sb.append("\t");
			}
			sb.append("\n");
		}
		for(int i=5;i<10;i++)
		{
			sb.append(".");
			sb.append("\t");
			for(int j=0;j<this.col;j++)
			{
				sb.append(".");
				sb.append("\t");
			}
			sb.append("\n");
		}
		for(int i=this.row-5;i<this.row;i++)
		{
			sb.append(index.get(i));
			sb.append("\t");
			for(int j=0;j<this.col;j++)
			{
				sb.append(content[i][j]);
				sb.append("\t");
			}
			sb.append("\n");
		}
		}
		else {
			for(int i=0;i<row;i++)
			{
				sb.append(index.get(i));
				sb.append("\t");
				for(int j=0;j<this.col;j++)
				{
					sb.append(content[i][j]);
					sb.append("\t");
				}
				sb.append("\n");
			}
		}
		sb.append("\n");
		sb.append("Total data:("+row+","+col+")\n");
		return sb.toString();
	}

}
