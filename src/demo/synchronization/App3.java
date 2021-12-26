package demo.synchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App3 {

	public static void main(String[] args) {
		// Lock
		// ReentrantLock
		// Object
		final Worker worker = new Worker();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					worker.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					worker.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}

}

class Worker {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	// private List<Integer> list = new ArrayList<>();

	public void produce() throws InterruptedException {
		this.lock.lock();
		System.out.println("Producer method...");
		this.condition.await();
		System.out.println("Producer method got the Lock  again...");
		this.lock.unlock();
	}

	public void consume() throws InterruptedException {
		Thread.sleep(1000);
		this.lock.lock();
		Thread.sleep(2000);
		System.out.println("Consumer method...");
		Thread.sleep(3000);
		this.condition.signal();
		this.lock.unlock();
	}
}
