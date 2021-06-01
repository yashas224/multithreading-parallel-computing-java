package multithreading.concepts;

public class App1 {
	public static void main(String[] args) throws InterruptedException {
		Worker obj = new Worker();
		Thread t1 = new Thread(obj);
		t1.start();
		
		Thread.sleep(100);

		obj.setTerminated(true);
		System.out.println("Terminated the worker from main thread");
	}
}

class Worker implements Runnable {
	private boolean terminated;

	@Override
	public void run() {
		while (!this.terminated) {
			System.out.println("Worker runing...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

}
/*
 * Lakshmeepathi · Lecture 24 · 4 years ago 2 threads - Main and t1 working on
 * the same object worker . Main thread changes the flag which means Main thread
 * decides when to stop the other thread t1 (that is the requirement of this
 * program).
 * 
 * Assume the following interleaving happens (without volatile):
 * 
 * 1) Main - worker.setTerminated (true)
 * 
 * // may write to RAM or stay in CPU cache and so there is no guarantee that t1
 * will be able to see the change in the variable immediately. The new value can
 * be pushed to RAM any time.
 * 
 * 2) Main - Printing "Finished"
 * 
 * 3) t1 checking while(!isTerminated) . t1 can't see the new value as it is not
 * pushed to RAM (assumed in this case not pushed to RAM). Hence
 * Sysout("hello from worker") runs.
 * 
 * If this had happened in the example run, it would have explained the effect
 * of NOT having volatile keyword. But it didn't happen unfortunately to explain
 * the concept.
 * 
 * Reasons it didn't happen:
 * 
 * 1) Both Main and t1 would have run in the same core/cpu and hence both would
 * read/write from the same CPU cache (no involvement of RAM here). OR
 * 
 * 2) Main and t1 running in different cores/cpus but always written/pushed to
 * RAM from CPU cache, even though volatile is not there. (Remember volatile
 * guarantees pushing to RAM, but pushing to RAM can happen even without
 * volatile).
 */

