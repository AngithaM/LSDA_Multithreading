/**
 * 
 * Modify either your code for any of the previous questions or the code provided on this assignment sheet so that it makes 
sensible use of parallel computing (using multithreading) in the body of method sort(int low, int n). 
* Do not change the algorithm otherwise and do not use any other sorting routine (such as one of Java’s built-in sorting 
approaches). 
* Do not change any code outside the body of method sort(int low, int n).
* Use only the "traditional" (Java <= 7) approach to multithreading for this question (using class Thread explicitly); do not use, e.g.,
parallel Java 8 Streams.
* It is not required that your parallelized code runs actually faster than the original code (no need to run any benchmarks) - but it 
should make use of concurrent threads in a sensible way.
[50 Marks]
 */
/**
ANSWER 2: Using multiple threads to run sort of Array of Strings in such a way that the merge is done in parallel within the splits
**/
package LSDA;

import java.util.Arrays;
import java.util.Comparator;

public class Sort_For_Q3_Ans_2 extends Thread {
	public String[] array;
	public Comparator<String> comp;
	int low;
	int n;

	public static void main(String[] args) {
		// Custom Thread class with constructors

		// A comparator for objects of type String:
		Comparator<String> compString = (String a, String b) -> a.compareTo(b);
		Sort_For_Q3_Ans_2 sortStrings = new Sort_For_Q3_Ans_2();

		String[] arrayOfStrings = { "Blue", "Yellow", "Almond", "Onyx", "Peach", "Gold", "Red", "Melon", "Lava",
				"Beige", "Aqua", "Lilac", "Capri", "Orange", "Mauve", "Plum" };
		System.out.println("Original array for Multithreaded Sorting: " + Arrays.toString(arrayOfStrings));
		// Sorting the array by calling the sort-method
		sortStrings.sort(arrayOfStrings, compString);
		System.out.println("Sorted array for Multithreaded Sorting : " + Arrays.toString(arrayOfStrings));

	}

	public void sort(String[] array, Comparator<String> comp) { // Array length must be a power of 2
		this.array = array;
		this.comp = comp;
		try {
			Sort_For_Q3_Ans_2 FirstThread = new Sort_For_Q3_Ans_2(array, comp, 0, array.length);// Calling sort
			FirstThread.start();
			FirstThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Sort_For_Q3_Ans_2(String[] arr, Comparator<String> comp, int low, int n) {// Constructor to pass the values needed
																				// for the thread
		this.array = arr;
		this.comp = comp;
		this.low = low;
		this.n = n;
		System.out.println("Thread" + Sort_For_Q3_Ans_2.currentThread() + "  Intermediate Array" + Arrays.toString(arr));
		// partially sorted using multi-threading. Both halves are sorted in  parallel
	}

	public Sort_For_Q3_Ans_2() {
		// Constructor for sortStrings object
	}

	public void run() {// enclosing the logic of the sort method in the run()
		if (n > 1) {
			int mid = n >> 1;
			Sort_For_Q3_Ans_2 firstHalf = new Sort_For_Q3_Ans_2(array, comp, low, mid);// recursively calling sort method to split
																			// and sort the first half
			Sort_For_Q3_Ans_2 nextHalf = new Sort_For_Q3_Ans_2(array, comp, low + mid, mid);// recursively calling sort method to
																				// split and sort the rest

			try {
				firstHalf.start();// creating new threads to start splitting the array simultaneously
				nextHalf.start();
				firstHalf.join();// waiting for the first partition to finish sorting so that we can start
									// combining
				combine(low, n, 1);
				nextHalf.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		String h = array[i];
		array[i] = array[j];
		array[j] = h;
	}

}
