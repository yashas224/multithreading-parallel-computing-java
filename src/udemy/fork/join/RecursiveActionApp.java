package udemy.fork.join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RecursiveActionApp {

	public static void main(String[] args) {
		// RecursiveAction
		// ForkJoinPool

		ForkJoinPool pool = ForkJoinPool.commonPool();
		pool.invoke(new Worker(400));
	}

}

class Worker extends RecursiveAction {
	private int work;

	public Worker(int work) {
		this.work = work;
	}

	@Override
	protected void compute() {
		if (work < 100) {
			System.out.println("Sequential Execution , Thread " + Thread.currentThread().getName() + " value " + work);
		} else {

			System.out.println("Splitting work , Thread " + Thread.currentThread().getName() + " value " + work);
			int m = work / 2;
			Worker obj1 = new Worker(m);
			Worker obj2 = new Worker(m);

			obj1.fork();
			obj2.compute();
			obj1.join();
		}
	}

}