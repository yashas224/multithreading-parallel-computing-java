package concurrent.collections;

import java.util.concurrent.Exchanger;

public class ExchangerApp {

	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		new Thread(new SideOne(exchanger)).start();
		new Thread(new SideTwo(exchanger)).start();
	}

}

class SideOne implements Runnable {
	private int counter;
	private Exchanger<Integer> exchanger;

	public SideOne(Exchanger<Integer> exchanger) {
		super();
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		while (true) {
			counter++;
			System.out.println(Thread.currentThread().getName() + " counter val incremented :" + counter);

			if (counter == 5) {	
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					counter = exchanger.exchange(counter);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}

class SideTwo implements Runnable {
	private int counter = 5;
	private Exchanger<Integer> exchanger;

	public SideTwo(Exchanger<Integer> exchanger) {
		super();
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		while (true) {
			counter--;
			System.out.println(Thread.currentThread().getName() + " counter val decremented :" + counter);

			if (counter == 0) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					counter = exchanger.exchange(counter);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
