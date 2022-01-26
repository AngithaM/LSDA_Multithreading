/**
 * **Modify your code for Q1 so that the comparator for strings and the comparator for Double numbers are provided as Lambda 
Expressions. Hint: this requires only very little code.
Do not change the sorting algorithm otherwise and do not use any other sorting routine (such as one of Java's built-in sorting 
approaches).
[25 marks]

 */
package LSDA;

import java.util.Arrays;
import java.util.Comparator;

public class Sorting<T> { // Class Sort is a generic class with type parameter T
	T[] array; // The array of objects of type T we want to sort
	Comparator<T> comp; // A Comparator instance suitable for comparing objects of type T

	public static void main(String[] args) {
		// Custom Thread class with constructors

		// A comparator for objects of type String:
		Comparator<String> compString = new Comparator<String>() {
			public int compare(String a, String b) {
				if (a.compareTo(b) > 0)
					return 1;
				else
					return 0;
			}
		};
		// A comparator for objects of type Double: Start
		Comparator<Double> compForDouble = new Comparator<Double>() {
			public int compare(Double a, Double b) {
				return (a.compareTo(b));

			}
		};
		// A comparator for objects of type Double: End
		Sorting<String> sortStrings = new Sorting<String>();
		// Array length must be a power of 2.
		String[] arrayOfStrings = { "Blue", "Yellow", "Almond", "Onyx", "Peach", "Gold", "Red", "Melon", "Lava",
				"Beige", "Aqua", "Lilac", "Capri", "Orange", "Mauve", "Plum" };
		System.out.println("Original array: " + Arrays.toString(arrayOfStrings));
		// Sorting the array by calling the sort-method
		sortStrings.sort(arrayOfStrings, compString);
		System.out.println("Sorted array: " + Arrays.toString(arrayOfStrings));
		//Solution for the Question : Start
		Sorting<Double> sortDouble = new Sorting<Double>();
		// Arraylist of Double
		Double[] arrayOfDouble = { 1.2, 3.2, -0.1, 2.3333, 0.11, -0.88, 0.112, 1.99 };
		System.out.println("Original array: " + Arrays.toString(arrayOfDouble));
		// Sorting the array by calling the sort-method
		sortDouble.sort(arrayOfDouble, compForDouble);
		System.out.println("Sorted array: " + Arrays.toString(arrayOfDouble));
		//Solution for the Question : End
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
