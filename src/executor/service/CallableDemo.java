package executor.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {

	public static void main(String[] args) throws Exception {
		ExecutorService exe = Executors.newCachedThreadPool();

		Future<String> futureResult = exe.submit(new Process(1));
		System.out.println(futureResult.get());
		exe.shutdown();
	}

}

class Process implements Callable<String> {
	int id;

	public Process(int id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return "id :" + id + " from Thread : " + Thread.currentThread().getId();
	}

}
