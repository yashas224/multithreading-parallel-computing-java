package sum.problem.analysis;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int nosOfThreds = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of threads :" + nosOfThreds);
		int[] arr = null;
		int numberOFArrayElements = 100000000;

		// sequential sum
		arr = createArray(numberOFArrayElements);
//		arr= new int [] {1,2,3,4,5,3,2};
		SequeantialSum obj = new SequeantialSum();
		System.out.println();
		long start = System.currentTimeMillis();
		int sum = obj.sum(arr);
		long end = System.currentTimeMillis();
		System.out.println("SUM: " + sum);
		System.out.println("Sequential Approach Time Taken in milisecnds:" + (end - start));

		// MultiThreaded Aproach to find dum
		MultithreadSum obj2 = new MultithreadSum();
		start = System.currentTimeMillis();
		obj2.parallelSum(0, arr.length - 1, arr, nosOfThreds);
		end = System.currentTimeMillis();
		System.out.println("SUM: " + sum);
		System.out.println("MultiThreaded Approach Time Taken in milisecnds:" + (end - start));

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

class SequeantialSum {
	public int sum(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
}

class MultithreadSum {
	public int sequentialSum(int i, int j, int[] arr) {
		int sum = 0;
		for (int k = i; k < j; k++) {
			sum += arr[i];
		}
		System.out.println(Thread.currentThread().getName() + " done calculating from :" + i + " to " + j);

		return sum;
	}

	public int parallelSum(int i, int j, int[] nums, int nosOfThreads) throws InterruptedException, ExecutionException {
		System.out.println("Thread details " + Thread.currentThread().getName() + " working form :" + i + " to " + j);
		if (nosOfThreads == 1) {
			return sequentialSum(i, j, nums);
		} else {
			int sum = 0;
			int m = (i + j) / 2;
			FutureTask<Integer> leftSum = createThreadAndSum(i, m, nums, nosOfThreads);
			FutureTask<Integer> rightSum = createThreadAndSum(m + 1, j, nums, nosOfThreads);
			new Thread(leftSum).start();
			new Thread(rightSum).start();

			sum += leftSum.get();
			sum += rightSum.get();
			System.out.println(Thread.currentThread().getName() + " done calculating from :" + i + " to " + j);

			return sum;
		}
	}

	private FutureTask<Integer> createThreadAndSum(int i, int j, int[] nums, int nosOfThreads) {
		return new FutureTask<Integer>(() -> parallelSum(i, j, nums, nosOfThreads / 2));
	}
}