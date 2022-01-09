package udemy.fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursionTaskApp {

	public static void main(String[] args) {
		List<Integer> list = createArray(10000000);
		System.out.println();
		long start = System.currentTimeMillis();
		int max = getMax(list);
		long end = System.currentTimeMillis();
		System.out.println("Sequential Result " + max);
		System.out.println("Sequential Approach Time Taken in milisecnds:" + (end - start));
		System.out.println();

		ForkJoinPool pool = ForkJoinPool.commonPool();
		start = System.currentTimeMillis();
		max = pool.invoke(new ReturnWorker(list));
		end = System.currentTimeMillis();
		System.out.println("Fork Join Approach Result " + max);
		System.out.println("Fork Join  Approach Time Taken in milisecnds:" + (end - start));
	}

	private static List<Integer> createArray(int size) {
		List<Integer> list = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			list.add(rand.nextInt(size * 2));
		}
		return list;
	}

	private static int getMax(List<Integer> list) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			max = Math.max(max, list.get(i));
		}
		return max;
	}

}

class ReturnWorker extends RecursiveTask<Integer> {
	List<Integer> list;

	public ReturnWorker(List<Integer> list) {
		this.list = list;
	}

	@Override
	protected Integer compute() {
		if (list.size() < 8000000) {
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < list.size(); i++) {
				max = Math.max(max, list.get(i));
			}
			// System.out.println("Sequential Execution , Thread " +
			// Thread.currentThread().getName() + " for list "
			// + list + " Max Val: " + max);
			return max;
		} else {
			// System.out.println("Splitting work , Thread " +
			// Thread.currentThread().getName() + " for list " + list);

			int m = list.size() / 2;

			ReturnWorker obj1 = new ReturnWorker(list.subList(0, m));
			ReturnWorker obj2 = new ReturnWorker(list.subList(m, list.size()));

			obj1.fork();

			int max1 = obj2.compute();
			int max2 = obj1.join();
			int max = Math.max(max1, max2);
			// System.out
			// .println("Thread " + Thread.currentThread().getName() + " for list " + list +
			// " Max Val: " + max);
			return max;
		}

	}

}