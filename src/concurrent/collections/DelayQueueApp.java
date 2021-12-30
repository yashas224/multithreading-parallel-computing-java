package concurrent.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueApp {

	public static void main(String[] args) throws InterruptedException {
		// DelayQueue<Delayed>

		BlockingQueue<DelayedWorker> queue = new DelayQueue<>();
		queue.put(new DelayedWorker(2000, "first content"));
		queue.put(new DelayedWorker(10000, "second content"));
		queue.put(new DelayedWorker(4500, "third content"));
		long start	= System.currentTimeMillis();
		while (!queue.isEmpty()) {
			System.out.println(queue.take());
			System.out.println((System.currentTimeMillis() - start) / 1000);
		}
	}

}

class DelayedWorker implements Delayed {
	private long duration;
	private String message;

	public DelayedWorker(long duration, String message) {
		super();
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
	}

	@Override
	public int compareTo(Delayed o) {
		if (this.duration < ((DelayedWorker) o).getDuration()) {
			return -1;
		} else if (this.duration > ((DelayedWorker) o).getDuration()) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "DelayedWorker [ message=" + message + "]";
	}

}