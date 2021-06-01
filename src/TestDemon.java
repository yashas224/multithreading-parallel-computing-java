
public class TestDemon {

	public static void main(String[] args) {

		Thread t1 = new Thread(() -> {
			while (true) {
				System.out.println("demon thread");
			}
		});
		t1.setDaemon(true);

		Thread t2 = new Thread(() -> System.out.println("non demon thread- worker"));

		t1.start();
		t2.start();
	}

}
