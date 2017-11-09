package me.salimm.ez.profiler.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.salimm.ez.profiler.core.errors.ProfilerNotStartedException;
import me.salimm.ez.profiler.core.profilers.MemoryProfiler;

public class BasicsMemoryProfilerTest {

	@Before
	public void clean() {
		System.gc();
	}

	@Test
	public void testIfItWorks() throws ProfilerNotStartedException {
		MemoryProfiler mem = new MemoryProfiler(10, false, false);

		mem.start();
		mem.stop();

	}

	@SuppressWarnings("unused")
	@Test
	public void testMemoryTest() throws ProfilerNotStartedException, InterruptedException {
		MemoryProfiler mem = new MemoryProfiler(100, false, false);
		int[] data = getData(100000);
		mem.start();
		Thread.sleep(10);
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Thread.sleep(10);
		mem.stop();

		Assert.assertEquals(1, mem.getMemoryUsages().size());
		Assert.assertEquals(mem1 / 1000, mem.getMemoryUsages().get(0).longValue() / 1000);

	}

	@SuppressWarnings("unused")
	@Test
	public void testMemoryMultipleSamplesTest() throws ProfilerNotStartedException, InterruptedException {
		MemoryProfiler mem = new MemoryProfiler(100, false, false);
		int[] data = getData(100000);
		mem.start();
		Thread.sleep(10);
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Thread.sleep(10);
		int[] data2 = getData(100000);
		Thread.sleep(100);
		long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		mem.stop();

		Assert.assertEquals(2, mem.getMemoryUsages().size());
		Assert.assertEquals(mem1/100, mem.getMemoryUsages().get(0).longValue()/100);
		Assert.assertEquals(mem2/100, mem.getMemoryUsages().get(1).longValue()/100);

	}

	@SuppressWarnings("unused")
	@Test
	public void testMemoryMemChangeTest() throws ProfilerNotStartedException, InterruptedException {
		MemoryProfiler mem = new MemoryProfiler(100, true, false);
		long mem0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		int[] data = getData(100000);
		mem.start();
		Thread.sleep(10);
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Thread.sleep(10);
		int[] data2 = getData(100000);
		Thread.sleep(100);
		long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		mem.stop();

		Assert.assertEquals(2, mem.getMemoryUsages().size());
		Assert.assertEquals(mem1 - mem0, mem.getMemoryUsages().get(0).longValue());
		Assert.assertEquals(mem2 - mem0, mem.getMemoryUsages().get(1).longValue());

	}

	@SuppressWarnings("unused")
	@Test
	public void testMemoryMemAgrressiveCleanTest() throws ProfilerNotStartedException, InterruptedException {
		MemoryProfiler mem = new MemoryProfiler(100, false, false);
		long mem0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		int[] data = getData(100000);
		mem.start();
		Thread.sleep(10);
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Thread.sleep(10);
		int[] data2 = getData(100000);
		long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		Thread.sleep(100);
		mem.stop();

		Assert.assertEquals(2, mem.getMemoryUsages().size());
		Assert.assertEquals(mem1/100, mem.getMemoryUsages().get(0).longValue()/100);
		Assert.assertEquals(mem2/1000, mem.getMemoryUsages().get(1).longValue()/1000);

	}

	private int[] getData(int size) {
		int[] data = new int[size];
		for (int i = 0; i < size; i++) {
			data[i] = 1;
		}
		return data;
	}

	private long getDataSize(int[] data) {
		return data.length * Integer.SIZE / Byte.SIZE;
	}
}
