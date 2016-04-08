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
* https://docs.oracle.com/cd/E19900-01/819-4742/abeik/index.html
* https://developer.jboss.org/blogs/acoliver/2006/03/21/if-you-dont-do-this-jboss-will-run-really-slowly?_sscc=t
* http://scn.sap.com/people/desiree.matas/blog/2011/07/19/how-to-read-the-garbage-collector-output-for-sun-jvm
* https://www.doag.org/formes/pubfiles/7497939/docs/Konferenz/2015/vortraege/Middleware/2015-K-MW-Daniel_Joray-Haben_Sie_Ihre_Weblogic-Umgebung_im_Griff_-Manuskript.pdf

### Settings

##### Initial Java heap size  
* *Specify the initial size, in bytes, of the memory allocation pool. This value must be a multiple of 1024 greater than 1MB. Append the letter k or K to indicate kilobytes, or m or M to indicate megabytes. The default value is chosen at runtime based on system configuration.*   (http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/java.html)

* *On server-class machines running either VM (client or server) with the parallel garbage collector (-XX:+UseParallelGC) the initial heap size and maximum heap size have changed as follows. Initial heap size: Larger of 1/64th of the machine's physical memory on the machine or some reasonable minimum. Before J2SE 5.0, the default initial heap size was a reasonable minimum, which varies by platform. You can override this default using the -Xms command-line option.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)

* *Java uses the new operator to create objects and memory for new objects is allocated on the heap at run time.* (http://www.javavillage.in/permgen-vs-heap-space.php)
```
JAVA_OPTS="$JAVA_OPTS -Xms5000m"
```

##### Maximum Java heap size
* *Specify the maximum size, in bytes, of the memory allocation pool. This value must be a multiple of 1024 greater than 2MB. Append the letter k or K to indicate kilobytes, or m or M to indicate megabytes. The default value is chosen at runtime based on system configuration.* (http://docs.oracle.com/javase/6/docs/technotes/tools/solaris/java.html)

* *On server-class machines running either VM (client or server) with the parallel garbage collector (-XX:+UseParallelGC) the initial heap size and maximum heap size have changed as follows. Maximum heap size: Smaller of 1/4th of the physical memory or 1GB. Before J2SE 5.0, the default maximum heap size was 64MB. You can override this default using the -Xmx command-line option.*
* *Do not choose a maximum value for the heap unless you know that the heap is greater than the default maximum heap size. Choose a throughput goal that is sufficient for your application.*
* *In an ideal situation the heap will grow to a value (less than the maximum) that will support the chosen throughput goal.*
* *If the heap grows to its maximum, the throughput cannot be met within that maximum. Set the maximum heap as large as you can, but no larger than the size of physical memory on the platform, and execute the application again. If the throughput goal can still not be met, then it is too high for the available memory on the platform.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)

```
JAVA_OPTS="$JAVA_OPTS -Xmx5000m"
```

##### Maximum Java permanent space size
* *Size of the Permanent Generation.  [5.0 and newer: 64 bit VMs are scaled 30% larger; 1.4 amd64: 96m; 1.3.1 -client: 32m.]* (http://www.oracle.com/technetwork/articles/java/vmoptions-jsp-140102.html)

* *Options that are specified with -XX are not stable and are not recommended for casual use. These options are subject to change without notice.* (http://indrayanblog.blogspot.com/2011/03/cxv.html)

* *The permanent generation is special because it holds meta-data describing user classes (classes that are not part of the Java language).* 
* *If you set the initial size and maximum size to equal values you may be able to avoid some full garbage collections that may occur if/when the permanent generation needs to be resized.* (http://www.freshblurbs.com/blog/2005/05/19/explaining-java-lang-outofmemoryerror-permgen-space.html)

* *Beside the heap memory containing the living data, the JVM needs additional information, especially classes descriptions or metadata. All this stuff is stored in a dedicated area named Permanent generation.* (http://blog.java-hoster.com/2011/java-course/performance/jvm-memory-and-java-heap-space)
```
JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=512m"
```

##### Maximum Garbage Collection Pause Time
* *A hint to the virtual machine that pause times of nnn milliseconds or less are desired. The VM will adjust the java heap size and other GC-related parameters in an attempt to keep GC-induced pauses shorter than nnn milliseconds. Note that this may cause the VM to reduce overall throughput, and in some cases the VM will not be able to meet the desired pause time goal. By default there is no pause time goal. There are definite limitations on how well a pause time goal can be met. The pause time for a GC depends on the amount of live data in the heap. The minor and major collections depend in different ways on the amount of live data. This parameter should be used with caution. A value that is too small will cause the system to spend an excessive amount of time doing garbage collection.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
```
-XX:MaxGCPauseMillis=nnn
```

##### Garbage Time Ratio
* *A hint to the virtual machine that it's desirable that not more than 1 / (1 + nnn) of the application execution time be spent in the collector. For example -XX:GCTimeRatio=19 sets a goal of 5% of the total time for GC and throughput goal of 95%. That is, the application should get 19 times as much time as the collector. By default the value is 99, meaning the application should get at least 99 times as much time as the collector. That is, the collector should run for not more than 1% of the total time. This was selected as a good choice for server applications. A value that is too high will cause the size of the heap to grow to its maximum.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/gc-ergonomics.html)
```
-XX:GCTimeRatio=nnn
```
##### Thread-local allocation buffer (New generation)
* *Thread-local allocation buffer. Used to allocate heap space quickly without synchronization. Compiled code has a "fast path" of a few instructions which tries to bump a high-water mark in the current thread's TLAB, successfully allocating an object if the bumped mark falls before a TLAB-specific limit address.* (http://openjdk.java.net/groups/hotspot/docs/HotSpotGlossary.html)

* *When new objects are allocated on the heap, if TLAB ( Thread Local Allocation Buffers ) are enabled, the object will first be placed in the TLAB, this buffer only exists within eden space. Each thread has its own TLAB to allow faster memory allocation, as the thread is able to allocate additional memory within the buffer without a lock. The TLAB is pre allocated for each thread. As a thread uses memory within the TLAB it moves a pointer accordingly.*  
* *To enable TLAB set ‐XX:+UseTLAB, You can set the size allocated to the the TLAB via ‐XX:TLABSize, its default size is 0, which means use dynamic calculation.*  
* *Using TLAB, uses more of your Eden space, but you may get a slight performance benefit when creating objects. The amount of memory allocated to all your TLAB's will be proportional to the number of threads in your application.*  (http://robsjava.blogspot.com/2013/03/what-are-thread-local-allocation-buffers.html)  
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseTLAB"
```

##### The (original) copying collector
* *When this collector kicks in, all application threads are stopped, and the copying collection proceeds using one thread (which means only one CPU even if on a multi-CPU machine). This is known as a stop-the-world collection, because basically the JVM pauses everything else until the collection is completed.* (http://www.javaperformancetuning.com/news/qotm026.shtml)
```
(Enabled by default)
```

##### Use Parallel New Garbage Collection (New generation)
* *The parallel copying collector. Like the original copying collector, this is a stop-the-world collector. However this collector parallelizes the copying collection over multiple threads, which is more efficient than the original single-thread copying collector for multi-CPU machines (though not for single-CPU machines). This algorithm potentially speeds up young generation collection by a factor equal to the number of CPUs available, when compared to the original singly-threaded copying collector.* (http://www.javaperformancetuning.com/news/qotm026.shtml)*
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
```

##### Use Parallel Garbage Collection (New generation)
* *The parallel scavenge collector. This is like the parallel copying collector, but the algorithm is tuned for gigabyte heaps (over 10GB) on multi-CPU machines. This collection algorithm is designed to maximize throughput while minimizing pauses. It has an optional adaptive tuning policy which will automatically resize heap spaces. If you use this collector, you can only use the the original mark-sweep collector in the old generation (i.e. the newer old generation concurrent collector cannot work with this young generation collector).* (http://www.javaperformancetuning.com/news/qotm026.shtml)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelGC"
```

##### Use Parallel Old Garbage Collection (Old generation)
* *Parallel compaction complements the existing parallel collector by performing full GCs in parallel to take advantage of multiprocessor (or multi-threaded) hardware.*
* *As the name suggests, it is best suited to platforms that have two or more CPUs or two or more hardware threads.*
* *It was first made available in JDK 5.0 update 6; the implementation in JDK 6 contains significant performance improvements.*
* *Prior to the availability of parallel compaction, the parallel collector would perform young generation collections (young GCs) in parallel, but full GCs were performed single-threaded. (During a young GC, only the young generation is collected; during a full GC the entire heap is collected.)*
* *Parallel compaction performs full GCs in parallel, resulting in lower garbage collection overhead and better application performance, particularly for applications with large heaps running on multiprocessor hardware.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/par-compaction-6.html)

* *Enabling this option automatically sets -XX:+UseParallelGC.* (http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelOldGC"
```

##### Use Large Pages
* [Configure Linux to use large pages.](/docs/common/linux/use.large.pages.md)  

* *Beginning with Java SE 5.0 there is a cross-platform flag for requesting large memory pages: -XX:+UseLargePages (on by default for Solaris, off by default for Windows and Linux). The goal of large page support is to optimize processor Translation-Lookaside Buffers. A Translation-Lookaside Buffer (TLB) is a page translation cache that holds the most-recently used virtual-to-physical address translations. TLB is a scarce system resource. A TLB miss can be costly as the processor must then read from the hierarchical page table, which may require multiple memory accesses. By using bigger page size, a single TLB entry can represent larger memory range. There will be less pressure on TLB and memory-intensive applications may have better performance. However please note sometimes using large page memory can negatively affect system performance. For example, when a large mount of memory is pinned by an application, it may create a shortage of regular memory and cause excessive paging in other applications and slow down the entire system. Also please note for a system that has been up for a long time, excessive fragmentation can make it impossible to reserve enough large page memory. When it happens, either the OS or JVM will revert to using regular pages.* (http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseLargePages"
```

##### Use Concurrent Mark Sweep Garbage Collector (Old generation)
* *This flag is needed to activate the CMS Collector in the first place. By default, HotSpot uses the Throughput Collector instead.* (https://blog.codecentric.de/en/2013/10/useful-jvm-flags-part-7-cms-collector/)

* *The concurrent mark sweep collector, also known as the concurrent collector or CMS, is targeted at applications that are sensitive to garbage collection pauses. It performs most garbage collection activity concurrently, i.e., while the application threads are running, to keep garbage collection-induced pauses short.* (http://docs.oracle.com/javase/6/docs/technotes/guides/vm/cms-6.html)

* *The concurrent collector (Enabled using -XX:+UseConcMarkSweepGC). This collector tries to allow application processing to continue as much as possible during the collection. Splitting the collection into six phases described shortly, four are concurrent while two are stop-the-world:* 
  0. *the initial-mark phase (stop-the-world, snapshot the old generation so that we can run most of the rest of the collection concurrent to the application threads);*
  0. *the mark phase (concurrent, mark the live objects traversing the object graph from the roots);*
  0. *the pre-cleaning phase (concurrent);*
  0. *the re-mark phase (stop-the-world, another snapshot to capture any changes to live objects since the collection started);*
  0. *the sweep phase (concurrent, recycles memory by clearing unreferenced objects);*
  0. *the reset phase (concurrent).*
* *If "the rate of creation" of objects is too high, and the concurrent collector is not able to keep up with the concurrent collection, it falls back to the traditional mark-sweep collector.*  (http://www.javaperformancetuning.com/news/qotm026.shtml)

```
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
```

##### No Class Garbage Collection
* *This option disables garbage collection of classes. Using -XnoClassGC can save some garbage collection time, which will shorten interruptions during the application run.* 
* *When you specify -XnoClassGC at startup, the class objects in the application specified by myApp will be left untouched during garbage collection and will always be considered live. This can result in more memory being permanently occupied which, if not used carefully, will throw an out of memory exception.* (https://docs.oracle.com/cd/E13150_01/jrockit_jvm/jrockit/jrdocs/refman/optionX.html)

* *The -XnoClassGC option is deprecated in Oracle JRockit R28. The option works in R28, but Oracle recommends that you use -XX:-UseClassGC instead. * (https://docs.oracle.com/cd/E15289_01/doc.40/e15062/optionx.htm)

* *This option switches off garbage collection of storage associated with Java™ technology classes that are no longer being used by the JVM. The default behavior is as defined by -Xclassgc. Enabling this option is not recommended except under the direction of the IBM support team. The reason is the option can cause unlimited native memory growth, leading to out-of-memory errors.* (https://www.ibm.com/support/knowledgecenter/SSYKE2_7.0.0/com.ibm.java.win.71.doc/diag/appendixes/cmdline/Xnoclassgc.html)


```
JAVA_OPTS="$JAVA_OPTS -Xnoclassgc"
```

##### Survivor Ratio
* *The SurvivorRatio parameter controls the size of the two survivor spaces. For example, -XX:SurvivorRatio=6 sets the ratio between each survivor space and eden to be 1:6, each survivor space will be one eighth of the young generation. The default for Solaris is 32. If survivor spaces are too small, copying collection overflows directly into the old generation. If survivor spaces are too large, they will be empty. At each GC, the JVM determines the number of times an object can be copied before it is tenured, called the tenure threshold. This threshold is chosen to keep the survivor space half full.* (https://docs.oracle.com/cd/E19159-01/819-3681/abeil/index.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:SurvivorRatio=8"
```

##### Default size of new generation (in bytes)
* *[5.0 and newer: 64 bit VMs are scaled 30% larger; x86: 1m; x86, 5.0 and older: 640k]* (http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)

* *The NewSize and MaxNewSize parameters control the new generation’s minimum and maximum size. Regulate the new generation size by setting these parameters equal. The bigger the younger generation, the less often minor collections occur. The size of the young generation relative to the old generation is controlled by NewRatio. For example, setting -XX:NewRatio=3 means that the ratio between the old and young generation is 1:3, the combined size of eden and the survivor spaces will be fourth of the heap.* (https://docs.oracle.com/cd/E19900-01/819-4742/abeik/index.html)

* *By default, NewRatio for the Server JVM is 2, the old generation occupies 2/3 of the heap while the new generation occupies 1/3* (https://www.doag.org/formes/pubfiles/7497939/docs/Konferenz/2015/vortraege/Middleware/2015-K-MW-Daniel_Joray-Haben_Sie_Ihre_Weblogic-Umgebung_im_Griff_-Manuskript.pdf)

* *To size the Java heap:*
  * *Decide the total amount of memory you can afford for the JVM. Accordingly, graph your own performance metric against young generation sizes to find the best setting.*
  * *Make plenty of memory available to the young generation. The default is calculated from NewRatio and the -Xmx setting.*
  * *Larger eden or younger generation spaces increase the spacing between full GCs. But young space collections could take a proportionally longer time. In general, keep the eden size between one fourth and one third the maximum heap size. The old generation must be larger than the new generation.* (https://docs.oracle.com/cd/E19900-01/819-4742/abeik/index.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:NewSize=2048m"
```

##### Maximum size of new generation (in bytes)
* *Since 1.4, MaxNewSize is computed as a function of NewRatio. [1.3.1 Sparc: 32m; 1.3.1 x86: 2.5m.]* (http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)

```
JAVA_OPTS="$JAVA_OPTS -XX:MaxNewSize=2048m"
```

##### JBoss Resolver Warning
* *This option warns when an XML entity is defined as SYSTEM with protocol is not "file://" or "vfsfile://", which is most likely something not expected.* (https://access.redhat.com/solutions/58381)
```
JAVA_OPTS="$JAVA_OPTS -Dorg.jboss.resolver.warning=true"
```

##### Periodic full collection frequency
* *The Sun ONE Application Server uses RMI in the Administration module for monitoring. Garbage cannot be collected in RMI based distributed applications without occasional local collections, so RMI forces a periodic full collection. The frequency of these collections can be controlled with the property -sun.rmi.dgc.client.gcInterval. For example, - java -Dsun.rmi.dgc.client.gcInterval=3600000 specifies explicit collection once per hour instead of the default rate of once per minute.* ()

* *By default, the RMI subsystem forces a full garbage collection once per minute. This means that all threads are paused and the entire heap is scanned. This takes a good while, especially under load.* (https://developer.jboss.org/blogs/acoliver/2006/03/21/if-you-dont-do-this-jboss-will-run-really-slowly?_sscc=t)

* *Apparently when your application either exposes its services via RMI or consumes any services over RMI, you are bound to have an additional garbage collection cycle. As the RMI documentation says: "When it is necessary to ensure that unreachable remote objects are unexported and garbage collected in a timely fashion, the value of this property represents the maximum interval (in milliseconds) that the Java RMI runtime will allow between garbage collections of the local heap. The default value is 3600000 milliseconds (one hour)."* (https://plumbr.eu/blog/garbage-collection/rmi-enforcing-full-gc-to-run-hourly)

* *Java Remote Method Invocation (Java RMI) enables the programmer to create distributed Java technology-based to Java technology-based applications, in which the methods of remote Java objects can be invoked from other Java virtual machines*, possibly on different hosts. RMI uses object serialization to marshal and unmarshal parameters and does not truncate types, supporting true object-oriented polymorphism.* (http://www.oracle.com/technetwork/articles/javaee/index-jsp-136424.html)
```
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=3600000"
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.server.gcInterval=3600000"
```

##### Allow Array Syntax
* *As Sun JDK (Oracle JDK) 6 class loading implementation was changed1, java.lang.ClassNotFoundException is thrown when Array class name is specified to an argument of ClassLoader.loadClass(classname). -Dsun.lang.ClassLoader.allowArraySyntax=true is the workaround for this change. The option was added to run.conf by JBPAPP-907 since EAP 4.x/5.x support JDK 6* (https://access.redhat.com/solutions/33387)
```
JAVA_OPTS="$JAVA_OPTS -Dsun.lang.ClassLoader.allowArraySyntax=true"
```

##### Verbose Garbage Collection
* *The -verbose:gc option enables logging of garbage collection (GC) information. It can be combined with other HotSpot VM specific options such as -XX:+PrintGCDetails and -XX:+PrintGCTimeStamps to get further information about the GC. The information output includes the size of the generations before and after each GC, total size of the heap, the size of objects promoted, and the time taken.* (http://www.oracle.com/technetwork/java/javase/clopts-139448.html#gbmpt)
```
JAVA_OPTS="$JAVA_OPTS -verbose:gc"
```

##### Verbose Garbage Collection Log File
* *If you simply use the verbose:gc flag, you'll have GC log output sent to the stdout console. Now, if you use the -Xloggc:[filename] switch, the GC data will be sent to a log file which you can grep through later. But either way, you get the the same GC data ...right? Wrong. The -Xloggc:[filename] switch has the additional effect of turning on the -XX:+PrintGCTimeStamps switch and hence gives your log files the added benefit of time stamps.* (https://blogs.oracle.com/moazam/entry/logging_gc_output_to_a)
``` 
JAVA_OPTS="$JAVA_OPTS -Xloggc:gc.log"
```

##### Print Garbage Collection Details
* *Print a long message with more details after each garbage collection is done.* (http://www.herongyang.com/JVM/Memory-PrintGCDetails-Garbage-Collection-Logging.html)
```
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
```

##### Print Garbage Collection Time Stamps
* *Print a timestamp relative to the JVM start time when a garbage collection occurs.* (http://www.herongyang.com/JVM/Memory-PrintGCDetails-Garbage-Collection-Logging.html)
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

### Find the gc.log file
```
sudo find / -name gc.log
```
```c
// /opt/jboss/gc.log
```

### Read the gc.log file
```
cat /opt/jboss/gc.log
```
```c
/*
9.699: [Full GC (System) 9.699: [CMS: 0K->45093K(3022848K), 0.5122730 secs] 604048K->45093K(4910336K), [CMS Perm : 25035K->25020K(25088K)], 0.5124600 secs][bachmeb@99700hlzx6g1 ~]$
*/
```
