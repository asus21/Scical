 package List;
public class sort
{	
	public static void main(String[] args)
	{
		int a[]=new int[1000000];
		for(int i=0;i<a.length;i++)
		{
			a[i]=(int)Math.random()%100;
		}
		
		bubbleSort(a);
	}
	
	public static <T extends Comparable<T>> void bubbleSort (T[] array)
	{
		for(int i=0;i<array.length;i++)
		{
			for(int j=0;j<array.length-1;j++)
			{
				if(array[j].compareTo(array[j+1])>0)
				{
					T temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
	}
	public static void bubbleSort (int[] array)
	{
		for(int i=0;i<array.length;i++)
		{
			for(int j=0;j<array.length-1;j++)
			{
				if(array[j]>(array[j+1]))
				{
					int temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
	}
	public static <T extends Comparable<T>> void selectedSort(T[] array)
	{
		int pos=0;
		T temp;
		for(int i=0;i<array.length-1;i++)
		{
			pos=i;
			for(int j=i+1;j<array.length;j++)
			{
				if(array[j].compareTo(array[pos])<0)
					pos=j;
			}
			temp=array[i];
			array[i]=array[pos];
			array[pos]=temp;
		}
	}
	public static <T extends Comparable<T>> void insertionSort(T[] array)
	{
		T temp;
		int pos;
		for(int i=1;i<array.length;i++)
		{
			temp=array[i];
			pos=i;
		while(pos>0&&array[pos-1].compareTo(array[pos])>0)
		{
			array[pos]=array[pos-1];
			pos=pos-1;
		}
		array[pos]=temp;
		}
	}
	public static <T extends Comparable<T>> void shell(T[] array)
	{
		int gap = array.length ;
		while (true)
		{
			gap /= 2;
			for (int i = 0; i < gap; i++)
			{
				for (int j = gap + i; j <array.length; j = j + gap)
				{
					int pos= j - gap;
					while (pos >=0 && array[pos].compareTo(array[gap+pos])>0)
					{
						T temp = array[pos];
						array[pos] = array[pos +gap];
						array[pos+gap] = temp;
						pos = pos - gap;
					}
				}
			}
			if (gap == 1)break;
		}
	}
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> void mergeSort(T[] array)
	{
		
		int pos=array.length/2;
		T[] left,right;
		left=(T[])new Object[pos];
		right=(T[])new Object[array.length-pos];
		int k=0;
		int j=0;
		int i=0;
		for(i=0;i<pos;i++)
		{
			left[i]=array[i];
		}
		for(i=pos;i<pos;i++)
		{
			right[i-pos]=array[i];
		}
		mergeSort(left);
		mergeSort(right);
		while(j<left.length&&i<right.length)
		{
			if(left[i].compareTo(right[j])<0)
			{
				array[k]=left[i];
				k++;i++;
			}
			if(left[i].compareTo(right[j])>0)
			{
				array[k]=right[j];
				k++;j++;
			}
			while(i<left.length)
			{
				array[k]=left[i];
				i++;k++;
			}
			while(j<right.length)
			{
				array[k]=right[j];
				j++;k++;
			}
		}
	}
	public static <T extends Comparable<T>> void quickSort(T[] array)
	{
		quickSort(array,0,array.length-1);
	}
	public static  <T extends Comparable<T>> void quickSort(T[] array,int head,int tail)
	{
		if(array.length<=1||head>=tail||array==null)
		{
			return;
		}
		T provit=array[(head+tail)/2];
		int left=head;
		int right=tail;
		while(left<=right)
		{
			while(array[left].compareTo(provit)<0)
			{
				left++;
			}
			while(array[right].compareTo(provit)>0)
			{
				right--;
			}
			if(left<right)
			{
				T temp=array[left];
				array[left]=array[right];
				array[right]=temp;
				left++;
				right--;
			}
			else if(left==right)
			{
				left++;
			}
		}
		quickSort(array,head,right);
		quickSort(array,left,tail);
	}
}