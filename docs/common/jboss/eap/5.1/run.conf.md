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

### Settings


##### Initial Java heap size
```
JAVA_OPTS="$JAVA_OPTS -Xms5000m"
```

##### Maximum Java heap size
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
*[When this collector kicks in, all application threads are stopped, and the copying collection proceeds using one thread (which means only one CPU even if on a multi-CPU machine). This is known as a stop-the-world collection, because basically the JVM pauses everything else until the collection is completed.](http://www.javaperformancetuning.com/news/qotm026.shtml)*
```
(Enabled by default)
```

##### The parallel copying collector
*[Like the original copying collector, this is a stop-the-world collector. However this collector parallelizes the copying collection over multiple threads, which is more efficient than the original single-thread copying collector for multi-CPU machines (though not for single-CPU machines). This algorithm potentially speeds up young generation collection by a factor equal to the number of CPUs available, when compared to the original singly-threaded copying collector.](http://www.javaperformancetuning.com/news/qotm026.shtml)*
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
```

##### Use large pages
* [Configure Linux to use large pages.](/docs/common/linux/use.large.pages.md)  

*[Beginning with Java SE 5.0 there is a cross-platform flag for requesting large memory pages: -XX:+UseLargePages (on by default for Solaris, off by default for Windows and Linux). The goal of large page support is to optimize processor Translation-Lookaside Buffers. A Translation-Lookaside Buffer (TLB) is a page translation cache that holds the most-recently used virtual-to-physical address translations. TLB is a scarce system resource. A TLB miss can be costly as the processor must then read from the hierarchical page table, which may require multiple memory accesses. By using bigger page size, a single TLB entry can represent larger memory range. There will be less pressure on TLB and memory-intensive applications may have better performance. However please note sometimes using large page memory can negatively affect system performance. For example, when a large mount of memory is pinned by an application, it may create a shortage of regular memory and cause excessive paging in other applications and slow down the entire system. Also please note for a system that has been up for a long time, excessive fragmentation can make it impossible to reserve enough large page memory. When it happens, either the OS or JVM will revert to using regular pages.](http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html)*
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseLargePages"
```

##### Activate the CMS Collector
*[This flag is needed to activate the CMS Collector in the first place. By default, HotSpot uses the Throughput Collector instead.](https://blog.codecentric.de/en/2013/10/useful-jvm-flags-part-7-cms-collector/)*
```
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
```

```
JAVA_OPTS="$JAVA_OPTS -Xnoclassgc"
```

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
