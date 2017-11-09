package me.salimm.ez.profiler.core.profilers;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is instantiated for every execution of the profiler
 * 
 * @author salimm
 *
 */
public class MemoryProfilerWorker implements Runnable {

	/**
	 * the private list of memory usage
	 */
	private final List<Long> values;

	/**
	 * list of time of samples
	 */
	private final List<Long> times;

	/**
	 * thread still working or stopped
	 */
	private boolean isStopped = false;

	/**
	 * wait time between samples
	 */
	private long waitTime;

	public MemoryProfilerWorker(long waitTime) {
		this.waitTime = waitTime;
		values = new ArrayList<Long>();
		times = new ArrayList<Long>();
	}

	public void run() {
		while (!isStopped()) {
			// thread wait for the time
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (isStopped()) {
				return;
			}
			// add current time
			getTimes().add(System.currentTimeMillis());
			// get running memory
			Runtime runtime = Runtime.getRuntime();
			long usedMemory = runtime.totalMemory() - runtime.freeMemory();
			// add current used memory
			getValues().add(usedMemory);
		}
	}

	public boolean isStopped() {
		return isStopped;
	}

	public void stop() {
		this.isStopped = false;
	}

	public List<Long> getValues() {
		return values;
	}

	public List<Long> getTimes() {
		return times;
	}
}