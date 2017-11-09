package me.salimm.ez.profiler.core.profilers;

import java.util.List;

import me.salimm.ez.profiler.core.errors.ProfilerNotStartedException;

/**
 * This class enables
 * 
 * @author salimm
 *
 */
public class MemoryProfiler {
	/**
	 * worker to execute parallel
	 */
	private MemoryProfilerWorker worker = null;

	/**
	 * wait time between samples
	 */
	private long waitTime;
	

	public MemoryProfiler(long waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * Starts profiling memory usage
	 */
	public void start() {
		if (worker == null) {
			worker = new MemoryProfilerWorker(waitTime);
		}
		Thread thread = new Thread(worker);
		thread.start();
	}

	/**
	 * Stops profiling memory usage
	 * 
	 * @throws ProfilerNotStartedException
	 */
	public void stop() throws ProfilerNotStartedException {
		if (worker == null) {
			throw new ProfilerNotStartedException();
		}
		worker.stop();
	}

	/**
	 * 
	 * Indicates if the profiler is stopped
	 * 
	 * @return
	 * @throws ProfilerNotStartedException
	 */
	public boolean isStopped() throws ProfilerNotStartedException {
		if (worker == null) {
			throw new ProfilerNotStartedException();
		}
		return worker.isStopped();
	}

	/**
	 * Return the temporal list of memory usages
	 * 
	 * @return
	 * @throws ProfilerNotStartedException
	 */
	public List<Long> getMemoryUsages() throws ProfilerNotStartedException {
		if (worker == null) {
			throw new ProfilerNotStartedException();
		}
		return worker.getValues();
	}

	/**
	 * Return list of times samples has been taken
	 * 
	 * @return
	 * @throws ProfilerNotStartedException
	 */
	public List<Long> getTimes() throws ProfilerNotStartedException {
		if (worker == null) {
			throw new ProfilerNotStartedException();
		}
		return worker.getTimes();
	}
}
