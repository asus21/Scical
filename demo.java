import matherror.FractionError;


public class demo {

	/**
	 *@Title: main
	 *@Description: TODO
	 *@param @param args void
	 *@throws
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fraction(1.728,0.0001,100,100);
	}
	 private static void Fraction(double value, double epsilon, int maxDenominator, int maxIterations)
		    {
		 		int numerator=0;
		 		int denominator=0;
		        long overflow = Integer.MAX_VALUE;
		        double r0 = value;
		        long a0 = (long)Math.floor(r0);
		        if (a0 > overflow) {
		            throw new FractionError("error");
		        }

		        // check for (almost) integer arguments, which should not go
		        // to iterations.
		        if (Math.abs(a0 - value) < epsilon) {
		        	numerator = (int) a0;
		            denominator = 1;
		            return;
		        }

		       long p0 = 1;
		        long q0 = 0;
		        long p1 = a0;
		        long q1 = 1;

		        long p2 = 0;
		        long q2 = 1;

		        int n = 0;
		        boolean stop = false;
		        do {
		            ++n;
		            double r1 = 1.0 / (r0 - a0);
		            long a1 = (long)Math.floor(r1);
		            p2 = (a1 * p1) + p0;
		            q2 = (a1 * q1) + q0;
		            if ((p2 > overflow) || (q2 > overflow)) {
		                throw new FractionError("error");
		            }
		            
		            double convergent = (double)p2 / (double)q2;
		            if (n < maxIterations && Math.abs(convergent - value) > epsilon && q2 < maxDenominator) {
		                p0 = p1;
		                p1 = p2;
		                q0 = q1;
		                q1 = q2;
		                a0 = a1;
		                r0 = r1;
		            } else {
		                stop = true;
		            }
		        } while (!stop);

		        if (n >= maxIterations) {
		            throw new FractionError("error");
		        }
		        
		        if (q2 < maxDenominator) {
		            numerator = (int) p2;
		            denominator = (int) q2;
		        } else {
		           numerator = (int) p1;
		           denominator = (int) q1;
		        }
		        System.out.println(numerator+","+denominator);
		    }
}
