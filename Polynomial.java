import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	int[] poly_deg;
	double[] poly_coeff;
	
	public Polynomial() {
		poly_deg = new int[0];
		poly_coeff = new double[0];
		
	}
	public Polynomial(double[] coeff, int[] deg) {
		poly_coeff = coeff;
		poly_deg = deg;
	}
	
	public Polynomial(File file_name) throws FileNotFoundException {
		Scanner reader = new Scanner(file_name);
		String poly_line = reader.nextLine();
		
		// split at the signs, using positive lookahead to keep signs	
		String[] new_poly = poly_line.split("((?=[-+]))");
		int counter = 0;
		poly_deg = new int[new_poly.length];
		poly_coeff = new double[new_poly.length];
		for (int i = 0; i < new_poly.length; i++) {
			String[] temp = new_poly[i].split("[xX]");
			// no x value, only coefficient term
			if (temp.length == 1) {
				poly_deg[counter] = 0;
				poly_coeff[counter] = Double.parseDouble(temp[0]);
			}
			else {
				poly_deg[counter] = Integer.parseInt(temp[1]);
				poly_coeff[counter] = Double.parseDouble(temp[0]);
			}
			counter += 1;	
		}
		
		reader.close();
	}
	
	// used to calculate the largest degree
	public double max_deg(double[] arr) {
		double max = 0;
		for (int i = 0; i < arr.length; i++) {
			double curr = arr[i];
			if (curr > max) 
				max = curr;
		}
		return max;
	}
	
	// checks if an element exists in an array, returns the array index if yes
	// otherwise returns -1
	private int contains(double[] arr, double element) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == element) 
				return i;
		}
		
		return -1;
	}
	// overload method for type integer array
	private int contains(int[] arr, double element) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == element) 
				return i;
		}
		
		return -1;
	}
	
	
	// used to find new length of sum array
	private int unique_degs(int[] arr1, int[] arr2) {
		int length = 0;
		int arr1_size = arr1.length;
		int arr2_size = arr2.length;
		// upper bound number on unique degrees is the sum
		double[] checked = new double[arr1_size + arr2_size];
		
		// since checked array gets initialized to 0
		if (contains(arr1, 0) != -1 || contains(arr2, 0) != -1)
			length += 1;
		
		// add unique elements from arr1
		for (int i = 0; i < arr1_size; i++) {
			// if the current element is unique
			if (contains(checked, arr1[i]) == -1){
				checked[i] = arr1[i];
				length += 1;
			}
		}
		// add unique elements from arr2
		for (int i = arr1_size; i < arr1_size + arr2_size; i++) {
			// if the current element is unique
			if (contains(checked, arr2[i - arr1_size]) == -1){
				checked[i] = arr2[i - arr1_size];
				length += 1;
			}
		}
		
		return length;
	}
	
	public Polynomial add(Polynomial arr) {
		
		int size = unique_degs(this.poly_deg, arr.poly_deg);
		
		double[] sum_coeff = new double[size];
		int[] sum_deg = new int[size];
		
		// initialize sum_deg to have -1 as values instead of 0
		for (int i = 0; i < sum_deg.length; i++) {
			sum_deg[i] = -1;
		}
		
		int new_arr_count = 0;
		
		// assuming at this point there are no redundant exponents
		for (int i = 0; i < this.poly_deg.length; i++) {
			sum_deg[new_arr_count] = this.poly_deg[i];
			// if the argument polynomial has a term with the same degree
			int arr_match = contains(arr.poly_deg, this.poly_deg[i]);
			if (arr_match != -1) 		
				sum_coeff[new_arr_count] = this.poly_coeff[i] + arr.poly_coeff[arr_match];
			
			else {
				sum_coeff[new_arr_count] = this.poly_coeff[i];
			}
			
			// increment the new array index
			new_arr_count += 1;
		}
		// check if the argument array has terms not yet added
		
		for (int i = 0; i < arr.poly_deg.length; i++) {
			// if the current degree is not in the new polynomial degree array
			if (contains(sum_deg, arr.poly_deg[i]) == -1) {
				sum_deg[new_arr_count] = arr.poly_deg[i];
				sum_coeff[new_arr_count] = arr.poly_coeff[i];
				new_arr_count += 1;
			}
		}
		
		Polynomial new_poly = new Polynomial(sum_coeff, sum_deg);
		return new_poly;
	}
	
	public double evaluate(double value) {
		double result = 0;
		
		for (int i = 0; i < poly_coeff.length; i++) {
			// sum the passed in x-value to the current power multiplied by the coefficient
			result += poly_coeff[i] * Math.pow(value, poly_deg[i]);
		}
		
		return result;
	}
	
	public boolean hasRoot(double value) {
		if (evaluate(value) == 0) {
			return true;
		}
		return false;
	}
	
	public Polynomial multiply(Polynomial poly) {
		Polynomial result = new Polynomial();
		for (int i = 0; i < this.poly_deg.length; i++) {
			for (int j = 0; j < poly.poly_deg.length; j++) {
				double[] term_coeff = {this.poly_coeff[i] * poly.poly_coeff[j]};
				int[] term_deg = {this.poly_deg[i] + poly.poly_deg[j]};
	
				Polynomial temp = new Polynomial(term_coeff, term_deg);
				result = result.add(temp);
			}
		}
		
		return result;
	}
	
	// save to file method
	public void saveToFile(String name) throws IOException {
		String result = "";
		for (int i = 0; i < poly_deg.length; i++) {

			// include + sign if not at start of string
			if (i > 0 && poly_coeff[i] > 0)

				result = result.concat("+");
				
			result = result.concat(Double.toString(poly_coeff[i]));
			
			if (poly_deg[i] > 0) {

				result = result.concat("x");
				result = result.concat(Integer.toString(poly_deg[i]));
			}	
		}

		FileWriter output = new FileWriter(name, false);
		output.write(result);
		output.close();
	}
	
	// print polynomial
	public void print_poly() {
		for (int i = 0; i < this.poly_deg.length; i++) {
			System.out.print(this.poly_coeff[i] + "x^" + this.poly_deg[i] + " ");
		}
		System.out.println();
	}
}
