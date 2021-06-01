package multithreading.concepts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	public static void main(String[] args) {
		LiveLock obj = new LiveLock();
		new Thread(() -> obj.worker1()).start();

		new Thread(obj::worker2).start();
	}

	public void worker1() {
		while (true) {
			try {
				lock1.tryLock(50, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " " + "locked LOCK 1");
			System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK2");

			if (lock2.tryLock()) {
				System.out.println(Thread.currentThread().getName() + " " + "locked LOCK 2");
			} else {
				System.out.println(Thread.currentThread().getName() + " " + " cannot Lock LOCK 2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}

			break;
		}

		lock1.unlock();
		lock2.unlock();
	}

	public void worker2() {
		while (true) {
			try {
				lock2.tryLock(50, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " " + "locked LOCK 2");
			System.out.println(Thread.currentThread().getName() + " " + "trying to lock LOCK 1");

			if (lock1.tryLock()) {
				System.out.println(Thread.currentThread().getName() + " " + "locked LOCK 1");
			} else {
				System.out.println(Thread.currentThread().getName() + " " + " cannot Lock LOCK 1");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;

			}

			break;
		}

		lock1.unlock();
		lock2.unlock();
	}
}
