package concurrent.collections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchApp {

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch latch = new CountDownLatch(11);
		ExecutorService exe = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			exe.execute(new Worker(i, latch));
		}

		System.out.println(Thread.currentThread().getName() + " Thread waiting for all workers to finish");

		latch.await();
		exe.shutdown();
		System.out.println("after all workers are done executing");
	}

}

class Worker implements Runnable {
	private int id;
	private CountDownLatch latch;

	public Worker(int id, CountDownLatch latch) {
		this.id = id;
		this.latch = latch;
	}

	@Override
	public void run() {
		doWork();
		latch.countDown();
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