package me.salimm.ez.profiler.test;

import org.junit.Test;

import me.salimm.ez.profiler.core.errors.ProfilerNotStartedException;
import me.salimm.ez.profiler.core.profilers.MemoryProfiler;

public class BasicsMemoryProfilerTest {

	@Test
	public void testMemoryTest() throws ProfilerNotStartedException {
		MemoryProfiler mem = new MemoryProfiler(10,false,false);
		
		mem.start();
		mem.stop();

	}
}
