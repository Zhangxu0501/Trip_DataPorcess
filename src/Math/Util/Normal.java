package Math.Util;

public class Normal {
	public double u;//��ֵ
	public double o;//����
	public Normal(Double u,Double o)
	{
		this.u=u;
		this.o=o;
	}
	public  double compute(double x)
	{
		double result = (1/Math.sqrt(Math.PI*2*o))*Math.pow(Math.E,-(x-u)*(x-u)/(2*o));
	
		return result;
	}
	public static void main(String [] args)
	{
		
	}

}
