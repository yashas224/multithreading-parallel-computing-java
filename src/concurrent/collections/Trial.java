package concurrent.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trial {
	public static void method1ExceptinThrown() throws InterruptedException {
		List<Integer> list = new ArrayList<Integer>();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				list.add(i);
			}

		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				list.add(i);
			}

		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println(list.size());
	}

	public static void NoExceptionThrown() throws InterruptedException {
		// intrencis lock is involved
		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				list.add(i);
			}

		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				list.add(i);
			}

		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println(list.size());

	}

	public static void main(String[] args) throws InterruptedException {
//		method1ExceptinThrown();
		NoExceptionThrown();
	}

}
