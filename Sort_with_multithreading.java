
package LSDA;

import java.util.Arrays;
import java.util.Comparator;

public class Sort_with_multithreading extends Thread {
	public String[] array;
	public Comparator<String> comp;
	int low;
	int n;

	public static void main(String[] args) {
		// Custom Thread class with constructors

		// A comparator for objects of type String:
		Comparator<String> compString = (String a, String b) -> a.compareTo(b);
		Sort_with_multithreading sortStrings = new Sort_with_multithreading();

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
			Sort_with_multithreading FirstThread = new Sort_with_multithreading(array, comp, 0, array.length);// Calling sort
			FirstThread.start();
			FirstThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Sort_with_multithreading(String[] arr, Comparator<String> comp, int low, int n) {// Constructor to pass the values needed
																				// for the thread
		this.array = arr;
		this.comp = comp;
		this.low = low;
		this.n = n;
		System.out.println("Thread" + Sort_with_multithreading.currentThread() + "  Intermediate Array" + Arrays.toString(arr));
		// partially sorted using multi-threading. Both halves are sorted in  parallel
	}

	public Sort_with_multithreading() {
		// Constructor for sortStrings object
	}

	public void run() {// enclosing the logic of the sort method in the run()
		if (n > 1) {
			int mid = n >> 1;
			Sort_with_multithreading firstHalf = new Sort_with_multithreading(array, comp, low, mid);// recursively calling sort method to split
																			// and sort the first half
			Sort_with_multithreading nextHalf = new Sort_with_multithreading(array, comp, low + mid, mid);// recursively calling sort method to
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
