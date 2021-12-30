package concurrent.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueApp {

	public static void main(String[] args) {
		BlockingQueue queue = new PriorityBlockingQueue<String>();
		new Thread(new PriorityBlockingQueueApp().new worker(queue)).start();
		new Thread(new PriorityBlockingQueueApp().new worker1(queue)).start();

	}

	private class worker implements Runnable {

		private BlockingQueue<String> pq;

		public worker(BlockingQueue<String> pq) {
			super();
			this.pq = pq;
		}

		@Override
		public void run() {
			for (char c : "BHFAZ".toCharArray()) {
				System.out.println("adding String :" + c + "");
				try {
					pq.put(c + "");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private class worker1 implements Runnable {
		
		private BlockingQueue<String> pq;

		public worker1(BlockingQueue<String> pq) {
			super();
			this.pq = pq;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (!pq.isEmpty()) {
				try {
					System.out.println("Removing String :" + pq.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
