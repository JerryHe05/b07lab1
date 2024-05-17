public class Polynomial {

		double[] poly;
		
		public Polynomial() {
			poly = new double[1];
			poly[0] = 0;
			
		}
		public Polynomial(double arr[]) {
			poly = arr;
		}
		
		public Polynomial add(Polynomial arr) {
			int large = Math.max(poly.length, arr.poly.length);
			double[] sum = new double[large];
			
			int small = Math.min(poly.length, arr.poly.length);
			
			int counter = 0;
			for (int i = 0; i < small; i++) {
				sum[i] = poly[i] + arr.poly[i];
				counter++;
			}
			
			// if the calling object is smaller
			if (poly.length == small) {
				// add the rest of the argument polynomial to the sum polynomial as is
				while (counter < arr.poly.length) {
					sum[counter] = arr.poly[counter];
					counter++;
				}
			}
			
			else {
				while (counter < poly.length) {
					sum[counter] = poly[counter];
					counter++;
				}
			}
			
			Polynomial new_poly = new Polynomial(sum);
			return new_poly;
		}
		
		public double evaluate(double value) {
			double result = 0;
			
			for (int i = 0; i < poly.length; i++) {
				// sum the passed in x-value to the current power multiplied by the coefficient
				result += poly[i] * Math.pow(value, i);
			}
			
			return result;
		}
		
		public boolean hasRoot(double value) {
			if (evaluate(value) == 0) {
				return true;
			}
			return false;
		}
		
}
