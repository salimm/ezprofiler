# ezprofiler
Easy Profiler is an easy java memory profiler to use in your code to trace memory usage of different parts of your program

ezprofiler currently contains:

## MemoryProfiler
  This profiler creates a paralel thread that allows the profiler to sample memory usage at different times. Memory Profiler can be executed in different modes
* **traceChange:** this mode tells the proofiler to only sample memory usage changes since the start of the profiler. Therefore, this mode can have negative samples which means the code is currently using less memory than when the profiler was started

* **aggressiveClean:** This modes forces the profiler to clean memory by calling gc(). It is impotant to warn that this mode can slow down the exection of the code as it is forcing garbagge collection to execute before each sample to assure that unecessary used memory is cleaned before sampling memory usage

## Examples
