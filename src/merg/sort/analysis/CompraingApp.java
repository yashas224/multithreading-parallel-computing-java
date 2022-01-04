package merg.sort.analysis;

import java.util.Date;
import java.util.Random;

import sun.reflect.LangReflectAccess;

public class CompraingApp {

	public static void main(String[] args) throws InterruptedException {
		Runtime.getRuntime().traceInstructions(true);
		int nosOfThreds = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of threads :" + nosOfThreds);
		int[] arr = null;
		int numberOFArrayElements=100000000;
		// MultiThreading Sorting
		arr = createArray(numberOFArrayElements);
		ParallelMergeSort obj = new ParallelMergeSort(arr);
		System.out.println();
		long start = System.currentTimeMillis();
		obj.parallelMergSort(0, arr.length - 1, arr, nosOfThreds);
		long end = System.currentTimeMillis();
		System.out.println("MultiThreading Approach Time Taken in milisecnds:" + (end - start));
		System.out.println();

		// sequential Sorting
		arr = createArray(numberOFArrayElements);

		MergeSort obj1 = new MergeSort(arr);
		start = System.currentTimeMillis();
		obj1.sort();
		end = System.currentTimeMillis();
		System.out.println("Sequential Approach Time Taken in milisecnds:" + (end - start));

	}

	private static int[] createArray(int size) {
		int[] arr = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			arr[i] = rand.nextInt(size);
		}
		return arr;
	}

}
