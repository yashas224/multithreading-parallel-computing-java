package concurrent.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueuesApp {

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
		Worker worker = new BlockingQueuesApp().new Worker(queue, 1);
		Worker1 worker1 = new BlockingQueuesApp().new Worker1(queue, 2);
		new Thread(worker).start();
		new Thread(worker1).start();
	}

	private class Worker implements Runnable {
		private BlockingQueue<Integer> queue;
		int id;

		public Worker(BlockingQueue<Integer> queue, int id) {
			super();
			this.queue = queue;
			this.id = id;
		}

		@Override
		public void run() {
			int counter = 0;
			while (true) {
				try {
					queue.put(counter);
					System.out.println("puttimg item into queue---" + counter++);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private class Worker1 implements Runnable {
		private BlockingQueue<Integer> queue;
		int id;

		public Worker1(BlockingQueue<Integer> queue, int id) {
			super();
			this.queue = queue;
			this.id = id;
		}

		@Override
		public void run() {
			int counter = 0;
			while (true) {
				try {
					int val = queue.take();
					System.out.println("Taking  item from queue---" + val);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
