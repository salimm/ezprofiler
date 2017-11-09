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

	/**
	 * What memory usage was at start
	 */
	private final long memoryUsageAtStart;

	/**
	 * indicates if the profiler should try to force clean before every sample
	 */
	private final boolean aggressiveClean;

	/**
	 * indicates if the memory usage should traces from the initial memory usage
	 */
	private boolean traceChange;

	public MemoryProfilerWorker(long waitTime, boolean traceChange, boolean aggressiveClean) {
		this.waitTime = waitTime;
		this.setTraceChange(traceChange);
		this.aggressiveClean = aggressiveClean;
		values = new ArrayList<Long>();
		times = new ArrayList<Long>();
		if (traceChange)
			this.memoryUsageAtStart = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		else
			this.memoryUsageAtStart = 0;
	}

	public void run() {
		while (!isStopped()) {
			if (aggressiveClean)
				System.gc();
			// add current time
			getTimes().add(System.currentTimeMillis());
			// get running memory
			Runtime runtime = Runtime.getRuntime();
			long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) - memoryUsageAtStart;
			// add current used memory
			getValues().add(usedMemory);
			// thread wait for the time
			if (isStopped()) {
				return;
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

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

	public boolean isAggressiveCleanClean() {
		return aggressiveClean;
	}

	public boolean isTraceChange() {
		return traceChange;
	}

	public void setTraceChange(boolean traceChange) {
		this.traceChange = traceChange;
	}
}