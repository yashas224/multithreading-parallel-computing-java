package demo.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	private static int counter = 0;
	private static Lock lock = new ReentrantLock();

	public static void increment() {
		lock.lock();
		try {
			for (int i = 0; i < 10000; i++) {
				counter++;
			}
		} catch (Exception e) {
			// handle exception
		} finally {
			unLockMethod();
		}

	}

	public static void unLockMethod() {
		lock.unlock();
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				increment();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				increment();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(counter);

	}

}
