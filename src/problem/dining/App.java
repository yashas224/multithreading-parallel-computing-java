package problem.dining;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
	public static int NUMBER_OF_PHILOSOPHER = 5;
	public static int NUMBER_OF_CHOPSTICKS = 5;
	public static int SIMULATION_RUN_TIME = 5 * 1000;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = null;

		Philosopher[] philosophers = null;
		ChopStick[] chopSticks;
		try {
			philosophers = new Philosopher[NUMBER_OF_PHILOSOPHER];
			chopSticks = new ChopStick[NUMBER_OF_CHOPSTICKS];
			for (int i = 0; i < NUMBER_OF_CHOPSTICKS; i++) {
				chopSticks[i] = new ChopStick(i);
			}
			executorService = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHER);
			for (int i = 0; i < NUMBER_OF_PHILOSOPHER; i++) {
				philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % NUMBER_OF_CHOPSTICKS]);
				executorService.submit(philosophers[i]);
			}

			Thread.sleep(SIMULATION_RUN_TIME);
			for (Philosopher philosopher : philosophers) {
				philosopher.setFull(true);
			}
		} finally {
			executorService.shutdown();
			while (!executorService.isTerminated()) {
				Thread.sleep(1000);
			}

			for (Philosopher philosopher : philosophers) {
				System.err.println("philosopher " + philosopher + "ate " + philosopher.getEatingCounter());
			}

		}
	}

}

class ChopStick {
	int id;
	Lock lock;

	public ChopStick(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}

	public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {
		if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println("philosopher " + philosopher + "picked up " + state.toString() + " -" + this);
			return true;
		}
		return false;

	}

	public void putDown(Philosopher philosopher, State state) {
		lock.unlock();
		System.out.println("philosopher" + philosopher + "put down " + state.toString() + "-" + this);

	}

	@Override
	public String toString() {
		return "chopStick [id=" + id + "]";
	}

}

class Philosopher implements Runnable {
	int id;
	private volatile boolean full;
	private ChopStick left;
	private ChopStick right;
	private int eatingCounter;
	private Random random;

	public Philosopher(int id, ChopStick left, ChopStick right) {
		super();
		this.id = id;
		this.left = left;
		this.right = right;
		this.random = new Random();
	}

	@Override
	public void run() {

		try {

			while (!isFull()) {
				think();

				if (left.pickUp(this, State.LEFT)) {
					if (right.pickUp(this, State.RIGHT)) {
						eat();
						right.putDown(this, State.RIGHT);
					}
					left.putDown(this, State.LEFT);
				}

			}
		} catch (Exception e) {
		}
	}

	private void think() throws InterruptedException {
		System.out.println(this + " is waiting...");
		Thread.sleep(random.nextInt(1000));
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating ...");
		this.eatingCounter++;
		Thread.sleep(random.nextInt(1000));

	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}

	@Override
	public String toString() {
		return "Philosopher id=" + id + " ";
	}

	public int getEatingCounter() {
		return eatingCounter;
	}

	public void setEatingCounter(int eatingCounter) {
		this.eatingCounter = eatingCounter;
	}

}

enum State {
	LEFT, RIGHT;

}

class myEx extends Exception {

}
