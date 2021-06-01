package demo.synchronization;

public class App1 {

	public static void main(String[] args) {
		Processor processor = new Processor();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}

}

class Processor {

	public void produce() throws InterruptedException {

		synchronized (this) {
			System.out.println("We are in the producer method...");
			Thread.sleep(2000);
			this.wait();
			System.out.println("Again producer method got the lock back...");
		}
	}

	public void consume() throws InterruptedException {

		Thread.sleep(2000);

		synchronized (this) {
			System.out.println("Consumer method...");
			this.notify();
			// notifyAll();
			Thread.sleep(5000);
		}
	}
}
