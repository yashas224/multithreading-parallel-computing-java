package executor.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

	public static void main(String[] args) {
		ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);
		// exe.schedule(() -> System.out.println("running allgo from thread :" +
		// Thread.currentThread().getId()), 5,
		// TimeUnit.SECONDS);
		exe.scheduleAtFixedRate(
				() -> System.out.println("running allgo from thread :" + Thread.currentThread().getId()), 5, 5,
				TimeUnit.SECONDS);
//		exe.shutdown();

	}

}
