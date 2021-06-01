package demo.synchronization;

import java.util.Map;

public class TestDemo {
	public static int counter = 0;
//	Object
	// private synchronized static void increment() {
	// counter++;
	// }

	// OR
	// both are same class level locking

	private static void increment() {
		synchronized (TestDemo.class) {
			counter++;
		}
	}

	public static void process() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				TestDemo.increment();
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				TestDemo.increment();
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
		System.out.println("Counter valure is :" + TestDemo.counter);
	}

	public static void main(String[] args) throws InterruptedException {
		process();
	}

}
