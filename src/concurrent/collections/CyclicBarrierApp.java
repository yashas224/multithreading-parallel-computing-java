package concurrent.collections;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.print.resources.serviceui;

public class CyclicBarrierApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("after all threads are done"));
		ExecutorService exe = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++)
			exe.execute(new CyclicBarrierApp().new Worker(i, cyclicBarrier));

		exe.shutdown();
	}

	private class Worker implements Runnable {
		private int id;
		private CyclicBarrier cyclicBarrier;

		public Worker(int id, CyclicBarrier cyclicBarrier) {
			super();
			this.id = id;
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			doWork();
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Afer wait");
		}

		private void doWork() {
			try {
				System.out.println("Thread id" + id + " starts execution");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
