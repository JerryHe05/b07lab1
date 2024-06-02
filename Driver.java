import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Driver {
	
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		
		int[] arr1_deg = {4, 5, 3};
		double[] arr1_coeff = {3, 6, 2};
		Polynomial p1 = new Polynomial(arr1_coeff, arr1_deg);
		
		int[] arr2_deg = {0, 1, 2, 3};
		double[] arr2_coeff = {1, 2, 3, 4};
		Polynomial p2 = new Polynomial(arr2_coeff, arr2_deg);
		
		Polynomial sum1 = p1.add(p2);
		System.out.println("sum1(1) = " + sum1.evaluate(1));
		
		Polynomial prod1 = p1.multiply(p2);
		prod1.print_poly();
		
		// print polynomial
		//sum1.print_poly();
		// check empty array
		Polynomial empty = new Polynomial();
		//System.out.println(Arrays.toString(empty.poly_coeff));
		
		//test multiply
		int[] arr3_deg = {1, 0};
		double[] arr3_coeff = {1, 1};
		Polynomial p3 = new Polynomial(arr3_coeff, arr3_deg);
		
		int[] arr4_deg = {0, 1};
		double[] arr4_coeff = {1, 1};
		Polynomial p4 = new Polynomial(arr4_coeff, arr4_deg);
		
		Polynomial result = p3.multiply(p4);
		//result.print_poly();

		// Test file, uncomment to use
		/*
		File testFile = new File("test.txt");
		Polynomial file_poly = new Polynomial(testFile);
		file_poly.print_poly();
		
		System.out.println(Arrays.toString(file_poly.poly_coeff));
		System.out.println(Arrays.toString(file_poly.poly_deg));
		
		// Test write
		
		file_poly.saveToFile("remake.txt");
		*/
	}
}
