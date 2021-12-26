package executor.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadExecutorDemo {

	public static void main(String[] args) {
		ExecutorService exe = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {
			exe.submit(new Task(i));
		}
		exe.shutdown();

	}

}

class Task implements Runnable {
	int id;

	public Task(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Task id :" + id + " thread id :" + Thread.currentThread().getId());
		long duration = (long) (Math.random() * 5);
		try {
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
		}

	}

}