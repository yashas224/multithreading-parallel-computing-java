package multithreading.concepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
	// not thread safe
	// private int counter = 0;
	// private volatile int counter = 0;

	// volatile abd thread safe- internally uses locks
	private AtomicInteger counter = new AtomicInteger(0);

	public void inc() {
		for (int i = 0; i < 100000; i++)
			// this.counter++;
			this.counter.getAndIncrement();
	}

	public static void main(String[] args) {
		AtomicDemo obj = new AtomicDemo();

		Thread t1 = new Thread(obj::inc);
		t1.start();
		Thread t2 = new Thread(obj::inc);
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("counter vlue :" + obj.counter);
	}

}
// volatile vs atomic
// https://www.youtube.com/watch?v=WH5UvQJizH0&list=WL&index=76&t=1s
