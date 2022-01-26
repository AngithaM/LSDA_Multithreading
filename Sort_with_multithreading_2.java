
package codes;

import java.util.Arrays;
import java.util.Comparator;


public class Sort_with_multithreading_2<T> { // Class Sort is a generic class with type parameter T
	T[] array; // The array of objects of type T we want to sort
	Comparator<T> comp; // A Comparator instance suitable for comparing objects of type T

	public static void main(String[] args) {
		// Custom Thread class with constructors

		// A comparator for objects of type String:
		Comparator<String> compString = (String a, String b) -> a.compareTo(b);

		// A comparator for objects of type Double: Start
		Comparator<Double> compForDouble = (Double a, Double b) -> a.compareTo(b);

		// A comparator for objects of type Double: End
		
		
		//Sort<String> sortStrings = new Sort<String>(); -- No longer needed as we have a different class to do the sorting
		// Array length must be a power of 2.
		String[] arrayOfStrings = { "Blue", "Yellow", "Almond", "Onyx", "Peach", "Gold", "Red", "Melon", "Lava",
				"Beige", "Aqua", "Lilac", "Capri", "Orange", "Mauve", "Plum" };
		System.out.println("Original array: " + Arrays.toString(arrayOfStrings));
		// Sorting the array by calling the sort-method
		//Creating an instance of a task class and passing the necessary arguments to the new class.
		ThreadClass taskString= new ThreadClass(arrayOfStrings,compString);
		Thread taskThread1String= new Thread(taskString);// create a thread by passing the class object as argument
		taskThread1String.start();//Start the thread
		//sortStrings.sort(arrayOfStrings, compString);-- No longer required as we're passing the arguments in the class object
															//and the body is executed in run.

		// Arraylist of Double
		Double[] arrayOfDouble = { 1.2, 3.2, -0.1, 2.3333, 0.11, -0.88, 0.112, 1.99 };
		System.out.println("Original array: " + Arrays.toString(arrayOfDouble));
		//Creating an instance of a task class for Double and passing the necessary arguments to the new class.
		ThreadClass taskDouble= new ThreadClass(arrayOfDouble,compForDouble);
		Thread taskThread2Double= new Thread(taskDouble);// create a thread by passing the class object as argument
		taskThread2Double.start();//Start the thread
		try {
			taskThread1String.join();//wait for the other thread to complete execution
			taskThread2Double.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	System.out.println("Sorted array: " + Arrays.toString(arrayOfStrings));	
	System.out.println("Sorted array: " + Arrays.toString(arrayOfDouble));
	}
}

class ThreadClass<T> implements Runnable{// create new class that has 
	T[] array; 
	Comparator<T> comp;
	
	public ThreadClass(String[] arr, Comparator<String> comp) {// Constructor to pass the values needed
		// for the thread
		this.array = (T[]) arr;
		this.comp = (Comparator<T>) comp;
		// partially sorted using multi-threading. Both arrays are sorted in  parallel
}
	public ThreadClass(Double[] arr, Comparator<Double> comp) {// Constructor to pass the values needed
																// for the thread
		this.array = (T[]) arr;
		this.comp = (Comparator<T>) comp;
		// partially sorted using multi-threading. Both arrays are sorted in  parallel
}
	public void run(){
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
