package me.salimm.ez.profiler.core.profilers;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Median;

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

	/**
	 * indicates if the profiler should trace changes from the initial usage or
	 * the total memory usage
	 */
	private boolean traceChange;

	/**
	 * sets if the the profiler should try to force System.gc() execution before
	 * every sample of memory usage or not
	 */
	private boolean aggressiveClean;

	public MemoryProfiler(long waitTime, boolean traceChange, boolean aggressiveClean) {
		this.waitTime = waitTime;
		this.traceChange = traceChange;
		this.aggressiveClean = aggressiveClean;
	}

	/**
	 * Starts profiling memory usage
	 */
	public void start() {
		// if (worker == null) {
		worker = new MemoryProfilerWorker(waitTime, traceChange, aggressiveClean);
		// }
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

	public boolean isTraceChange() {
		return traceChange;
	}

	public void setTraceChange(boolean traceChange) {
		this.traceChange = traceChange;
	}

	public boolean isAggressiveClean() {
		return aggressiveClean;
	}

	public void setAggressiveClean(boolean aggressiveClean) {
		this.aggressiveClean = aggressiveClean;
	}

	public long maxMemoryUsage() throws ProfilerNotStartedException {
		return NumberUtils.max(values());
	}

	public long minMemoryUsage() throws ProfilerNotStartedException {
		return NumberUtils.min(values());
	}

	public double avgMemoryUsage() throws ProfilerNotStartedException {
		Mean mean = new Mean();
		return mean.evaluate(doubleValues());
	}

	public double medianMemoryUsage() throws ProfilerNotStartedException {
		Median median = new Median();
		return median.evaluate(doubleValues());
	}

	public double varianceMemoryUsage() throws ProfilerNotStartedException {
		Variance var = new Variance();
		return var.evaluate(doubleValues());
	}

	public long latest() throws ProfilerNotStartedException {
		int idx = getMemoryUsages().size();
		return getMemoryUsages().get(idx - 1);
	}

	/**
	 * used to get primitive array for arithmatics
	 * 
	 * @return
	 * @throws ProfilerNotStartedException
	 */
	private long[] values() throws ProfilerNotStartedException {
		long[] values = new long[getMemoryUsages().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = getMemoryUsages().get(i);
		}
		return values;
	}

	private double[] doubleValues() throws ProfilerNotStartedException {
		double[] values = new double[getMemoryUsages().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = getMemoryUsages().get(i);
		}
		return values;
	}

}
