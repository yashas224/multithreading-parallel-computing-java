package executor.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

	public static void main(String[] args) {
		ExecutorService exe = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 10; i++) {
			exe.execute(new Task(i));
		}
		exe.shutdown();

	}

}
