import java.util.Comparator;

public class TestThread {

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 20; i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out
						.println(Thread.currentThread().getName() + "---" + i + "--" + Thread.currentThread().toString()
								+ "--" + Thread.currentThread().isDaemon() + "--- " + Thread.currentThread().isAlive());
			}
		});
		t1.start();

		// new Thread(() -> {
		// for (int i = 0; i < 100; i++) {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(Thread.currentThread().getName() + "---" + i);
		// }
		// }).start();

		t1.join();
		System.out.println(t1.isAlive());

		System.out.println("Main Opetation after Thred finishes");
		
	}
}
