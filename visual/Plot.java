package visual;



import core.Matrix;

public class Plot {
	public static final int	BLACK=0;
	public static final int BLUE	=0xAA0000;
	public static final int	GREEN=0x00AA00;
	public static final int	CYAN=0xAAAA00;
	public static final int	RED	=0x0000AA;
	public static final int	MAGENTA=0xAA00AA;
	public static final int	BROWN=	0x0055AA;
	public static final int	LIGHTGRAY=0xAAAAAA;
	public static final int	DARKGRAY=0x555555;
	public static final int	LIGHTBLUE=0xFF5555;
	public static final int	LIGHTGREEN=0x55FF55;
	public static final int	LIGHTCYAN=0xFFFF55;
	public static final int	LIGHTRED=0x5555FF;
	public static final int	LIGHTMAGENTA=0xFF55FF;
	public static final int	YELLOW=0x55FFFF;
	public static final int	WHITE=0xFFFFFF;
	public native static void xlabel(String xlabel);
	public native static void ylabel(String ylabel);
	public native static void xlim(double xmin,double xmax);
	public native static void ylim(double ymin,double ymax);
	public native static void text(double x,double y,String text,int Color);
	public native static void legend(String legend,int Color);
	public native static void title(String title);
	public native static void scatter(double[] x,double[] y,int Color);
	public native static void scatter(Matrix x,Matrix y,int Color);
	public native static void show();
	static
	{
		System.loadLibrary("Plot");
	}
	public static void main(String[] args) {
	  text(3, 3,"hello",GREEN);
	  title("中国地质大学（武汉）");
	  xlim(0, 100);
	  scatter(new double[]{1,2,3},new double[]{1,2,3},RED);
	  legend("hello", RED);
	  show();
	}
}
