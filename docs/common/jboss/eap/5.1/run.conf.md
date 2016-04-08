# run.conf

## References
* http://openjdk.java.net/groups/hotspot/docs/HotSpotGlossary.html
* http://www.javaperformancetuning.com/news/qotm026.shtml
* http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html
* https://docs.oracle.com/cd/E13209_01/wlcp/wlss30/configwlss/jvmgc.html
* https://blogs.oracle.com/poonam/entry/uselargepages_on_linux
* http://jose-manuel.me/2012/11/how-to-enable-the-jvm-option-uselargepages-in-redhat/
* http://stackoverflow.com/questions/20498651/xxuseconcmarksweepgc-what-is-default-young-generation-collector
* http://www.fasterj.com/articles/oraclecollectors1.shtml
* https://blog.codecentric.de/en/2013/10/useful-jvm-flags-part-7-cms-collector/
* http://www.oracle.com/technetwork/java/javase/memorymanagement-whitepaper-150215.pdf
* http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/java.html

### Settings

##### Initial Java heap size
* *Specify the initial size, in bytes, of the memory allocation pool. This value must be a multiple of 1024 greater than 1MB. Append the letter k or K to indicate kilobytes, or m or M to indicate megabytes. The default value is chosen at runtime based on system configuration.* (http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/java.html)
* *On server-class machines running either VM (client or server) with the parallel garbage collector (-XX:+UseParallelGC) the initial heap size and maximum heap size have changed as follows. Initial heap size: Larger of 1/64th of the machine's physical memory on the machine or some reasonable minimum. Before J2SE 5.0, the default initial heap size was a reasonable minimum, which varies by platform. You can override this default using the -Xms command-line option.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
```
JAVA_OPTS="$JAVA_OPTS -Xms5000m"
```

##### Maximum Java heap size
* *Specify the maximum size, in bytes, of the memory allocation pool. This value must a multiple of 1024 greater than 2MB. Append the letter k or K to indicate kilobytes, or m or M to indicate megabytes. The default value is chosen at runtime based on system configuration. * (http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/java.html)
* *On server-class machines running either VM (client or server) with the parallel garbage collector (-XX:+UseParallelGC) the initial heap size and maximum heap size have changed as follows. Maximum heap size: Smaller of 1/4th of the physical memory or 1GB. Before J2SE 5.0, the default maximum heap size was 64MB. You can override this default using the -Xmx command-line option.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
* *Do not choose a maximum value for the heap unless you know that the heap is greater than the default maximum heap size. Choose a throughput goal that is sufficient for your application.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
* *In an ideal situation the heap will grow to a value (less than the maximum) that will support the chosen throughput goal.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
* *If the heap grows to its maximum, the throughput cannot be met within that maximum. Set the maximum heap as large as you can, but no larger than the size of physical memory on the platform, and execute the application again. If the throughput goal can still not be met, then it is too high for the available memory on the platform.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
* ** (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
```
JAVA_OPTS="$JAVA_OPTS -Xmx5000m"
```

##### Maximum Java permanent space size
```
JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=512m"
```

##### Thread-local allocation buffer
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseTLAB"
```

##### The (original) copying collector
* *When this collector kicks in, all application threads are stopped, and the copying collection proceeds using one thread (which means only one CPU even if on a multi-CPU machine). This is known as a stop-the-world collection, because basically the JVM pauses everything else until the collection is completed.* (http://www.javaperformancetuning.com/news/qotm026.shtml)
```
(Enabled by default)
```

##### The parallel copying collector
* *Like the original copying collector, this is a stop-the-world collector. However this collector parallelizes the copying collection over multiple threads, which is more efficient than the original single-thread copying collector for multi-CPU machines (though not for single-CPU machines). This algorithm potentially speeds up young generation collection by a factor equal to the number of CPUs available, when compared to the original singly-threaded copying collector.* (http://www.javaperformancetuning.com/news/qotm026.shtml)*
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
```

##### The parallel scavenge collector
* *This is like the parallel copying collector, but the algorithm is tuned for gigabyte heaps (over 10GB) on multi-CPU machines. This collection algorithm is designed to maximize throughput while minimizing pauses. It has an optional adaptive tuning policy which will automatically resize heap spaces. If you use this collector, you can only use the the original mark-sweep collector in the old generation (i.e. the newer old generation concurrent collector cannot work with this young generation collector).* (http://www.javaperformancetuning.com/news/qotm026.shtml)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelGC"
```

##### Parallel Compaction
* *Parallel compaction complements the existing parallel collector by performing full GCs in parallel to take advantage of multiprocessor (or multi-threaded) hardware. As the name suggests, it is best suited to platforms that have two or more CPUs or two or more hardware threads. It was first made available in JDK 5.0 update 6; the implementation in JDK 6 contains significant performance improvements. Prior to the availability of parallel compaction, the parallel collector would perform young generation collections (young GCs) in parallel, but full GCs were performed single-threaded. (During a young GC, only the young generation is collected; during a full GC the entire heap is collected.) Parallel compaction performs full GCs in parallel, resulting in lower garbage collection overhead and better application performance, particularly for applications with large heaps running on multiprocessor hardware.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/par-compaction-6.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelOldGC"
```



##### Use large pages
* [Configure Linux to use large pages.](/docs/common/linux/use.large.pages.md)  
* *Beginning with Java SE 5.0 there is a cross-platform flag for requesting large memory pages: -XX:+UseLargePages (on by default for Solaris, off by default for Windows and Linux). The goal of large page support is to optimize processor Translation-Lookaside Buffers. A Translation-Lookaside Buffer (TLB) is a page translation cache that holds the most-recently used virtual-to-physical address translations. TLB is a scarce system resource. A TLB miss can be costly as the processor must then read from the hierarchical page table, which may require multiple memory accesses. By using bigger page size, a single TLB entry can represent larger memory range. There will be less pressure on TLB and memory-intensive applications may have better performance. However please note sometimes using large page memory can negatively affect system performance. For example, when a large mount of memory is pinned by an application, it may create a shortage of regular memory and cause excessive paging in other applications and slow down the entire system. Also please note for a system that has been up for a long time, excessive fragmentation can make it impossible to reserve enough large page memory. When it happens, either the OS or JVM will revert to using regular pages.* (http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseLargePages"
```

##### Activate the CMS Collector
* *This flag is needed to activate the CMS Collector in the first place. By default, HotSpot uses the Throughput Collector instead.* (https://blog.codecentric.de/en/2013/10/useful-jvm-flags-part-7-cms-collector/)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
```

##### No Class Garbage Collection
* *This option switches off garbage collection of storage associated with Java™ technology classes that are no longer being used by the JVM. The default behavior is as defined by -Xclassgc. Enabling this option is not recommended except under the direction of the IBM support team. The reason is the option can cause unlimited native memory growth, leading to out-of-memory errors.* (https://www.ibm.com/support/knowledgecenter/SSYKE2_7.0.0/com.ibm.java.win.71.doc/diag/appendixes/cmdline/Xnoclassgc.html)
```
JAVA_OPTS="$JAVA_OPTS -Xnoclassgc"
```

##### Survivor Ratio
* *The SurvivorRatio parameter controls the size of the two survivor spaces. For example, -XX:SurvivorRatio=6 sets the ratio between each survivor space and eden to be 1:6, each survivor space will be one eighth of the young generation. The default for Solaris is 32. If survivor spaces are too small, copying collection overflows directly into the old generation. If survivor spaces are too large, they will be empty. At each GC, the JVM determines the number of times an object can be copied before it is tenured, called the tenure threshold. This threshold is chosen to keep the survivor space half full.* (https://docs.oracle.com/cd/E19159-01/819-3681/abeil/index.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:SurvivorRatio=8"
```

```
JAVA_OPTS="$JAVA_OPTS -XX:NewSize=2048m"
```

```
JAVA_OPTS="$JAVA_OPTS -XX:MaxNewSize=2048m"
```

```
JAVA_OPTS="$JAVA_OPTS -Dorg.jboss.resolver.warning=true"
```

```
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=3600000"
```

```
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.server.gcInterval=3600000"
```

```
JAVA_OPTS="$JAVA_OPTS -Dsun.lang.ClassLoader.allowArraySyntax=true"
```

```
JAVA_OPTS="$JAVA_OPTS -verbose:gc"
```

```
JAVA_OPTS="$JAVA_OPTS -Xloggc:gc.log"
```

```
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
```

```
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"
```

### Script  

```bash

## -*- shell-script -*- ######################################################
##                                                                          ##
##  JBoss Bootstrap Script Configuration                                    ##
##                                                                          ##
##############################################################################

### $Id: run.conf 91533 2009-07-22 01:20:05Z gbadner $

#
# This shell script is sourced by run.sh to initialize the environment
# variables that run.sh uses. It is recommended to use this file to
# configure these variables, rather than modifying run.sh itself.
#

#
# Specify the maximum file descriptor limit, use "max" or "maximum" to use
# the default, as queried by the system.
#
# Defaults to "maximum"
#
#MAX_FD="maximum"

#
# Specify the JBoss Profiler configuration file to load.
#
# Default is to not load a JBoss Profiler configuration file.
#
#PROFILER=""

#
# Specify the location of the Java home directory.  If set then $JAVA will
# be defined to $JAVA_HOME/bin/java, else $JAVA will be "java".
#
#JAVA_HOME="/usr/java/jdk1.6.0"

#
# Specify the exact Java VM executable to use.
#
#JAVA=""

#
# Specify options to pass to the Java VM.
#
if [ "x$JAVA_OPTS" = "x" ]; then
#   JAVA_OPTS="-Xms1303m -Xmx1303m -XX:MaxPermSize=256m -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.lang.ClassLoader.allowArraySyntax=true"
fi

#
JAVA_OPTS="$JAVA_OPTS -Xms1303m"

JAVA_OPTS="$JAVA_OPTS -Xmx1303m"

JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=256m"

JAVA_OPTS="$JAVA_OPTS -Dorg.jboss.resolver.warning=true"

JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=3600000"

JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.server.gcInterval=3600000"

JAVA_OPTS="$JAVA_OPTS -Dsun.lang.ClassLoader.allowArraySyntax=true"

## Specify the Security Manager options
#JAVA_OPTS="$JAVA_OPTS -Djava.security.manager -Djava.security.policy=$POLICY"

# Sample JPDA settings for remote socket debugging
#JAVA_OPTS="$JAVA_OPTS -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

# Sample JPDA settings for shared memory debugging
#JAVA_OPTS="$JAVA_OPTS -Xrunjdwp:transport=dt_shmem,address=jboss,server=y,suspend=n"

```
