package multithreading.concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	public static void main(String[] args) {
		DeadLock obj = new DeadLock();
		new Thread(() -> obj.worker1()).start();

		new Thread(obj::worker2).start();
	}

	public void worker1() {
		this.lock1.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK1");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK2");
		this.lock2.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK2");
		this.lock1.unlock();
		this.lock2.unlock();

	}

	public void worker2() {
		this.lock2.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK2");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK1");
		this.lock1.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK1");

		this.lock2.unlock();
		this.lock1.unlock();

	}
}

// lock are locked and unlocked in order
class PreventDeadlock {

	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	public static void main(String[] args) {
		DeadLock obj = new DeadLock();
		new Thread(() -> obj.worker1()).start();

		new Thread(obj::worker2).start();
	}

	public void worker1() {
		this.lock1.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK1");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK2");
		this.lock2.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK2");
		this.lock1.unlock();
		this.lock2.unlock();

	}

	public void worker2() {
		this.lock1.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK1");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK2");
		this.lock2.lock();
		System.out.println(Thread.currentThread().getName() + " " + "locked LOCK2");

		this.lock2.unlock();
		this.lock1.unlock();

	}
}
