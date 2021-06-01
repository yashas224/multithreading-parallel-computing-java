package demo.synchronization;

import java.util.ArrayList;
import java.util.List;

class ProcessorClass {
	private List<Integer> list = new ArrayList();
	private final int LIMIT = 5;
	private final int BOTTOM = 0;
	int val = 0;

	public void producer() throws Exception {
		synchronized (this) {
			while (true) {
				if (list.size() == LIMIT) {
					System.out.println("List FULLL, Waiting for removing items from the list...");
					this.wait();
				} else {
					System.out.println("Adding: " + val);
					list.add(val++);
					this.notify();
				}
				Thread.sleep(500);
			}
		}
	}

	public void consumer() throws Exception {
		Thread.sleep(1000);
		synchronized (this) {
			while (true) {
				if (list.size() == BOTTOM) {
					System.out.println("List EMPTY, Waiting for adding  items to the list...");
					this.wait();
				} else {
					System.out.println("removing: " + list.remove(--val));
					this.notify();
				}
				Thread.sleep(500);

			}
		}
	}
}

public class App2 {

	public static void main(String[] args) {
		ProcessorClass obj = new ProcessorClass();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					obj.producer();
				} catch (Exception e) {
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					obj.consumer();
				} catch (Exception e) {
				}
			}
		});

		t1.start();
		t2.start();
	}

}
