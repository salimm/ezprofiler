# ezprofiler
Easy Profiler is an easy java memory profiler to use in your code to trace memory usage of different parts of your program

![Build Status](https://travis-ci.org/salimm/ezprofiler.svg?branch=master)](https://travis-ci.org/salimm/ezprofiler) 
![Gitter Chat](https://camo.githubusercontent.com/fdc9898745257bfea85fded0c1d585b6fa97f509/68747470733a2f2f6261646765732e6769747465722e696d2f67697474657248512f73657276696365732e706e67)](https://gitter.im/ezprofiler/Lobby)

ezprofiler currently contains:

## MemoryProfiler
  This profiler creates a paralel thread that allows the profiler to sample memory usage at different times. Memory Profiler can be executed in different modes
* **traceChange:** this mode tells the proofiler to only sample memory usage changes since the start of the profiler. Therefore, this mode can have negative samples which means the code is currently using less memory than when the profiler was started

* **aggressiveClean:** This modes forces the profiler to clean memory by calling gc(). It is impotant to warn that this mode can slow down the exection of the code as it is forcing garbagge collection to execute before each sample to assure that unecessary used memory is cleaned before sampling memory usage

## Examples
In this section multiple examples of creating profilers have been provided.

### Memory Profiler

Create a MemoryProfiler in normal mode ( traceChange and aggressiveClean are both deactivated)
```java
    long cycleWaitTime = 100; //sample every 100ms
    MemoryProfiler mem = new MemoryProfiler(cycleWaitTime, false, false);
```


Create MemoryProfiler in traceChange mode but not aggressive mode
```java
    long cycleWaitTime = 100; //sample every 100ms
    MemoryProfiler mem = new MemoryProfiler(cycleWaitTime, false, false);
```

Create MemoryProfiler in traceChange mode but not aggressive mode
```java
      long cycleWaitTime = 100; //sample every 100ms
  		MemoryProfiler mem = new MemoryProfiler(cycleWaitTime, true, false);
```


Create MemoryProfiler in traceChange mode and  aggressive mode
```java
    long cycleWaitTime = 100; //sample every 100ms
    MemoryProfiler mem = new MemoryProfiler(cycleWaitTime, true, true);
```


Trace the memory of a block of code
```java
    mem.start(); //starts profiling immediately
    ...
    ...
    .your block of code
    ...
    ...
    mem.stop();
    
    List<Long> usage = mem.getgetMemoryUsages(); // memory usage in bytes at every sample
    List<Long> times = mem.getgetMemoryUsages(); // time in system milliseconds of each sample
    
```

Note: The memory usage can also be retrieved while MemoryProfiler has not been stopped. This mean the profiler will continue sampling memory but yet return the current existing samples.


Additonal calculated memory usage informations that can be obtained contains:
* Maximum: maxMemoryUsage()
* Minimum: minMemoryUsage()
* Average: avgMemoryUsage()
* Median: medianMemoryUsage()
* Variance: varianceMemoryUsage()

