package fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class DemoApp {

	public static void main(String[] args) {
		// ForkJoinPool
		// ForkJoinTask<V>
		// RecursiveTask<V
		// RecursiveAction
		// ForkJoinWorkerThread
		// CountedCompleter

		// runRecursionAction();
		runRecursiontask();
	}

	private static void runRecursiontask() {
		List<Long> list = getArray(10);
		System.out.println(list);
		SumReturnWorker Firstworker = new SumReturnWorker(list);
		runInPool(Firstworker);
	}

	private static void runRecursionAction() {
		List<Long> list = getArray(10);
		System.out.println(list);
		SumWorker Firstworker = new SumWorker(list);
		runInPool(Firstworker);
	}

	private static void runInPool(ForkJoinTask task) {
		ForkJoinPool pool = ForkJoinPool.commonPool();

		// This would not garantee Execution of all ForkJoinWorkerthreads as
		// All ForkJoinWorkerthreads of the Pool are Daemon Threads with isDaemon True.
		// And execute methiod is (asynchronous) execution of Task Sumbitted
		// where as invoke method is blocked until the result is obtained
		// pool.execute(Firstworker);

		// invoke method is blocked until the result is obtained
		// main thread that is Non-Demon thread is blocked until the result is obtained
		pool.invoke(task);

		System.out.println(
				Thread.currentThread().getName() + " Demon check " + Thread.currentThread().isDaemon() + " Ended");
	}

	public static List<Long> getArray(int size) {
		List<Long> list = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			list.add((long) random.nextInt(size));
		}
		return list;
	}

}

class SumReturnWorker extends RecursiveTask<Long> {
	List<Long> list;

	public SumReturnWorker(List<Long> list) {
		this.list = list;
	}

	private long computeSumDirectly() {
		long sum = 0;
		for (Long l : this.list) {
			sum += l;
		}
		return sum;
	}

	@Override
	protected Long compute() {
		if (this.list.size() <= Constants.SEQUENTIAL_THRESHOLD) {

			long retVal = computeSumDirectly();
			System.out.println(" Direct Computation Thread " + Thread.currentThread().getName() + " Demon check "
					+ Thread.currentThread().isDaemon() + "  sum of " + this.list + " Result= " + retVal);
			return retVal;
		} else {
			int m = this.list.size() / 2;
			SumReturnWorker obj1 = new SumReturnWorker(this.list.subList(0, m));
			SumReturnWorker obj2 = new SumReturnWorker(this.list.subList(m, this.list.size()));

			obj1.fork();

			long returnVal = obj2.compute() + obj1.join();
			System.out.println("  Computation Thread " + Thread.currentThread().getName() + " Demon check "
					+ Thread.currentThread().isDaemon() + "  sum of " + this.list + " Result= " + returnVal);
			return returnVal;
		}
	}
}

class SumWorker extends RecursiveAction {
	List<Long> list;

	public SumWorker(List<Long> list) {
		this.list = list;
	}

	private long computeSumDirectly() {
		long sum = 0;
		for (Long l : this.list) {
			sum += l;
		}
		return sum;
	}

	@Override
	protected void compute() {
		if (this.list.size() <= Constants.SEQUENTIAL_THRESHOLD) {
			System.out.println(" Direct Computation Thread " + Thread.currentThread().getName() + " Demon check "
					+ Thread.currentThread().isDaemon() + "  sum of " + this.list + " Result= " + computeSumDirectly());
		} else {
			int m = this.list.size() / 2;
			SumWorker obj1 = new SumWorker(this.list.subList(0, m));
			SumWorker obj2 = new SumWorker(this.list.subList(m, this.list.size()));

			obj1.fork();
			obj2.compute();

			System.out.println("Thread " + Thread.currentThread().getName() + " Demon check "
					+ Thread.currentThread().isDaemon() + "  sum of " + this.list + " Result= " + obj1.join());
		}
	}
}

class Constants {
	public static final int SEQUENTIAL_THRESHOLD = 3;
}
