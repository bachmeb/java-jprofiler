# jvmstat

## References
* http://www.oracle.com/technetwork/java/jvmstat-142257.html
* http://docs.oracle.com/javase/6/docs/technotes/tools/share/jstat.html

##### Tool Name
* jps	
  * Experimental: JVM Process Status Tool - Lists instrumented HotSpot Java virtual machines on a target system. (formerly jvmps)
* jstat	
  * Experimental: JVM Statistics Monitoring Tool - Attaches to an instrumented HotSpot Java virtual machine and collects and logs performance statistics as specified by the command line options. (formerly jvmstat)
* jstatd
  * Experimental: JVM jstat Daemon - Launches an RMI server application that monitors for the creation and termination of instrumented HotSpot Java virtual machines and provides a interface to allow remote monitoring tools to attach to Java virtual machines running on the local system. (formerly perfagent)
* visualgc
  * Experimental: Visual Garbage Collection Monitoring Tool - a graphical tool for monitoring the HotSpot Garbage Collector, Compiler, and class loader. It can monitor both local and remote JVMs.
* *Source: http://www.oracle.com/technetwork/java/jvmstat-142257.html*

### jps
##### See where is jps
```
whereis jps
```
```c
/*
jps: /usr/bin/jps /usr/share/man/man1/jps.1.gz
*/
```

##### Get a list of lvmids
```
jps
```
```c
/*
4345 Main
14519 Jps
*/
```
### jstat
##### See where is jstat
```
whereis jstat
```
```c
/*
jstat: /usr/bin/jstat /usr/share/man/man1/jstat.1.gz
*/
```
##### Get a list of jstat options
```
jstat -options
```
```c
/*
-class
-compiler
-gc
-gccapacity
-gccause
-gcnew
-gcnewcapacity
-gcold
-gcoldcapacity
-gcpermcapacity
-gcutil
-printcompilation
*/
```

##### Read the jstat help message
```
jstat -help
```
```c
/*
Usage: jstat -help|-options
       jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]

Definitions:
  <option>      An option reported by the -options option
  <vmid>        Virtual Machine Identifier. A vmid takes the following form:
                     <lvmid>[@<hostname>[:<port>]]
                Where <lvmid> is the local vm identifier for the target
                Java virtual machine, typically a process id; <hostname> is
                the name of the host running the target Java virtual machine;
                and <port> is the port number for the rmiregistry on the
                target host. See the jvmstat documentation for a more complete
                description of the Virtual Machine Identifier.
  <lines>       Number of samples between header lines.
  <interval>    Sampling interval. The following forms are allowed:
                    <n>["ms"|"s"]
                Where <n> is an integer and the suffix specifies the units as
                milliseconds("ms") or seconds("s"). The default units are "ms".
  <count>       Number of samples to take before terminating.
  -J<flag>      Pass <flag> directly to the runtime system.
*/
```

##### Show statistics of the behavior of the garbage collected heap
```
jstat -gc 14958
```
```c
/*
S0C     S1C      S0U      S1U   EC       EU        OC         OU        PC      PU           YGC  YGCT    FGC    FGCT     GCT
55552.0 110464.0 55551.6  0.0   224128.0 131690.4  889536.0   114546.0  61824.0 61709.4      6    0.853   1      0.327    1.180
*/
```
```c
/*
S0C	    Current survivor space 0 capacity (KB).
S1C	    Current survivor space 1 capacity (KB).
S0U	    Survivor space 0 utilization (KB).
S1U	    Survivor space 1 utilization (KB).
EC	    Current eden space capacity (KB).
EU	    Eden space utilization (KB).
OC	    Current old space capacity (KB).
OU	    Old space utilization (KB).
PC	    Current permanent space capacity (KB).
PU	    Permanent space utilization (KB).
YGC	    Number of young generation GC Events.
YGCT	Young generation garbage collection time.
FGC	    Number of full GC events.
FGCT	Full garbage collection time.
GCT	    Total garbage collection time.
*/
```

#####	Show statistics of the capacities of the generations and their corresponding spaces.
```
jstat -gccapacity 14958
```
```c
/*
 NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC      PGCMN    PGCMX     PGC       PC     YGC    FGC
444736.0 445056.0 445056.0 55552.0 110464.0 224128.0   889536.0   890240.0   889536.0   889536.0  21248.0 262144.0  61824.0  61824.0      6     1
*/
```
```c
/*
NGCMN   Minimum new generation capacity (KB).
NGCMX   Maximum new generation capacity (KB).
NGC     Current new generation capacity (KB).
S0C	    Current survivor space 0 capacity (KB).
S1C	    Current survivor space 1 capacity (KB).
EC	    Current eden space capacity (KB).
OGCMN   Minimum old generation capacity (KB).
OGCMX   Maximum old generation capacity (KB).
OGC	    Current old generation capacity (KB).
OC	    Current old space capacity (KB).
PGCMN	Minimum permanent generation capacity (KB).
PGCMX	Maximum Permanent generation capacity (KB).
PGC	    Current Permanent generation capacity (KB).
PC	    Current Permanent space capacity (KB).
YGC	    Number of Young generation GC Events.
FGC	    Number of Full GC Events.
*/
```

##### Display the same summary of garbage collection statistics as the -gcutil option and also include the causes of the last garbage collection event and (if applicable) the current garbage collection event
```
jstat -gccause 14958
```
```c
/*
  S0    S1      E     O      P      YGC  YGCT      FGC  FGCT     GCT    LGCC                 GCC
  0.00  50.32   7.76  12.88  99.84  7    0.982     1    0.327    1.309  Allocation Failure   No GC
*/
```
```c
/*
LGCC    Cause of last Garbage Collection.
GCC     Cause of current Garbage Collection.
*/
```

##### Show the old generation capacity (OGC) and the old space capacity (OC) increasing as the heap expands to meet allocation and/or promotion demands. 
```
jstat -gcoldcapacity 14958
```
```c
/*
   OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT
   889536.0    890240.0    889536.0    889536.0     6     1    0.327    1.180
*/
```
```c
/*
OGCMN	Minimum old generation capacity (KB).
OGCMX   Maximum old generation capacity (KB).
OGC	    Current old generation capacity (KB).
OC	    Current old space capacity (KB).
YGC     Number of young generation GC events.
FGC	    Number of full GC events.
FGCT    Full garbage collection time.
GCT	    Total garbage collection time.
*/
```

### Download
##### Get a link to download the jvmstat 3.0 Maintenance Release
* http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-jvm-419420.html#jvmstat-3_0-mr-oth-JPR

##### Download jvmstat 3.0 Maintenance Release
```
cd ~/Downloads
wget http://download.oracle.com/otn-pub/java/jvmstat/3.0-mr/jvmstat-3_0.zip?AuthParam=SomeRandomCode
```

##### Unzip the package
```
unzip jvmstat-3_0.zip\?AuthParam\=SomeRandomCode
```
```c
/*
Archive:  jvmstat-3_0.zip?AuthParam=SomeRandomCode
   creating: jvmstat/
   creating: jvmstat/jars/
  inflating: jvmstat/jars/jvmstat_util.jar
  inflating: jvmstat/jars/jvmstat_graph.jar
  inflating: jvmstat/jars/visualgc.jar
   creating: jvmstat/bin/
  inflating: jvmstat/bin/visualgc
   creating: jvmstat/bat/
  inflating: jvmstat/bat/visualgc.cmd
   creating: jvmstat/etc/
  inflating: jvmstat/etc/linux.magic
  inflating: jvmstat/etc/solaris.magic
   creating: jvmstat/docs/
   creating: jvmstat/docs/images/
 extracting: jvmstat/docs/images/sunlogo64x30.gif
  inflating: jvmstat/docs/images/visualheap.jpg
  inflating: jvmstat/docs/images/visualgraph.jpg
 extracting: jvmstat/docs/images/javalogo52x88.gif
  inflating: jvmstat/docs/images/bullet-round.gif
  inflating: jvmstat/docs/index.html
   creating: jvmstat/docs/install/
  inflating: jvmstat/docs/install/solaris.html
  inflating: jvmstat/docs/install/windows.html
   creating: jvmstat/docs/instrumentation/
   creating: jvmstat/docs/instrumentation/1.4.1/
   creating: jvmstat/docs/instrumentation/1.4.2/
  inflating: jvmstat/docs/instrumentation/1.4.2/index.html
   creating: jvmstat/docs/instrumentation/5.0/
   creating: jvmstat/docs/tooldocs/
   creating: jvmstat/docs/tooldocs/solaris/
   creating: jvmstat/docs/tooldocs/linux/
   creating: jvmstat/docs/tooldocs/windows/
   creating: jvmstat/docs/tooldocs/share/
  inflating: jvmstat/docs/tooldocs/share/visualgc.html
  inflating: jvmstat/docs/tooldocs/share/notes.html
  inflating: jvmstat/docs/tooldocs/tools.html
  inflating: jvmstat/README
  inflating: jvmstat/LICENSE
*/
```
