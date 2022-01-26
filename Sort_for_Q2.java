/**
 * Enhance the code given above so that, in addition to the array of strings, also an array of numbers of type Double (with 16 
elements of your choice) is sorted, using the given sorting algorithm. As a part of this, provide a suitable comparator 
(Comparator instance) for Double numbers.
Your main-method should print both the original and the sorted array of numbers.
[25 marks]
 */
package LSDA;

import java.util.Arrays;
import java.util.Comparator;

public class Sort_for_Q2<T> { // Class Sort is a generic class with type parameter T
	T[] array; // The array of objects of type T we want to sort
	Comparator<T> comp; // A Comparator instance suitable for comparing objects of type T

	public static void main(String[] args) {
		// Custom Thread class with constructors

		// A lambda comparator for objects of type String:
		Comparator<String> compString = (String a, String b) -> a.compareTo(b);
		//Commented out for the lambda expression: Start
		/*
		 * Comparator<String> compString = new Comparator<String>() { public int
		 * compare(String a, String b) { if (a.compareTo(b) > 0) return 1; else return
		 * 0; } };
		 */
		//Commented out for the lambda expression: End
		// A lambda comparator for objects of type Double:
		Comparator<Double> compForDouble = (Double a, Double b) -> a.compareTo(b);
		//Commented out for the lambda expression: Start
		/*
		 * Comparator<Double> compForDouble = new Comparator<Double>() {
		 * 
		 * @Override public int compare(Double a, Double b) { return (a.compareTo(b));
		 * 
		 * } };
		 */
		//Commented out for the lambda expression: End
		Sort_for_Q2<String> sortStrings = new Sort_for_Q2<String>();
		// Array length must be a power of 2.
		String[] arrayOfStrings = { "Blue", "Yellow", "Almond", "Onyx", "Peach", "Gold", "Red", "Melon", "Lava",
				"Beige", "Aqua", "Lilac", "Capri", "Orange", "Mauve", "Plum" };
		System.out.println("Original array: " + Arrays.toString(arrayOfStrings));
		// Sorting the array by calling the sort-method
		sortStrings.sort(arrayOfStrings, compString);
		System.out.println("Sorted array: " + Arrays.toString(arrayOfStrings));

		Sort_for_Q2<Double> sortDouble = new Sort_for_Q2<Double>();

		// Arraylist of Double
		Double[] arrayOfDouble = { 1.2, 3.2, -0.1, 2.3333, 0.11, -0.88, 0.112, 1.99 };
		System.out.println("Original array: " + Arrays.toString(arrayOfDouble));
		// Sorting the array by calling the sort-method
		sortDouble.sort(arrayOfDouble, compForDouble);
		System.out.println("Sorted array: " + Arrays.toString(arrayOfDouble));

	}

	public void sort(T[] array, Comparator<T> comp) { // Array length must be a power of 2
		this.array = array;
		this.comp = comp;
		sort(0, array.length);
	}

	private void sort(int low, int n) {
		if (n > 1) {
			int mid = n >> 1;
			sort(low, mid);
			sort(low + mid, mid);
			combine(low, n, 1);
		}
	}

	private void combine(int low, int n, int st) {
		int m = st << 1;
		if (m < n) {
			combine(low, n, m);
			combine(low + st, n, m);
			for (int i = low + st; i + st < low + n; i += m)
				compareAndSwap(i, i + st);
		} else
			compareAndSwap(low, low + st);
	}

	private void compareAndSwap(int i, int j) {
		if (comp.compare(array[i], array[j]) > 0)
			swap(i, j);
	}

	private void swap(int i, int j) {
		T h = array[i];
		array[i] = array[j];
		array[j] = h;
	}
}
