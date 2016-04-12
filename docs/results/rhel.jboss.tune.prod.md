
##### version
```
cat /proc/version
```
```c
/*
Linux version 2.6.18-409.el5 (mockbuild@x86-031.build.eng.bos.redhat.com) (gcc version 4.1.2 20080704 (Red Hat 4.1.2-55)) #1 SMP Fri Feb 12 06:37:28 EST 2016
*/
```

##### cpuinfo
```
cat /proc/cpuinfo
```
```c
/*
processor       : 0
vendor_id       : GenuineIntel
cpu family      : 6
model           : 15
model name      : Intel(R) Core(TM)2 Duo CPU     E4500  @ 2.20GHz
stepping        : 13
cpu MHz         : 1200.000
cache size      : 2048 KB
physical id     : 0
siblings        : 2
core id         : 0
cpu cores       : 2
apicid          : 0
fpu             : yes
fpu_exception   : yes
cpuid level     : 10
wp              : yes
flags           : fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm syscall nx lm constant_tsc pni monitor ds_cpl est tm2 ssse3 cx16 xtpr lahf_lm
bogomips        : 4388.97
clflush size    : 64
cache_alignment : 64
address sizes   : 36 bits physical, 48 bits virtual
power management:

processor       : 1
vendor_id       : GenuineIntel
cpu family      : 6
model           : 15
model name      : Intel(R) Core(TM)2 Duo CPU     E4500  @ 2.20GHz
stepping        : 13
cpu MHz         : 1200.000
cache size      : 2048 KB
physical id     : 0
siblings        : 2
core id         : 1
cpu cores       : 2
apicid          : 1
fpu             : yes
fpu_exception   : yes
cpuid level     : 10
wp              : yes
flags           : fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm syscall nx lm constant_tsc pni monitor ds_cpl est tm2 ssse3 cx16 xtpr lahf_lm
bogomips        : 4389.00
clflush size    : 64
cache_alignment : 64
address sizes   : 36 bits physical, 48 bits virtual
power management:
*/
```

##### meminfo
```
cat /proc/meminfo
```
```c
/*
MemTotal:      8033836 kB
MemFree:       6848920 kB
Buffers:        209276 kB
Cached:         762584 kB
SwapCached:          0 kB
Active:         731472 kB
Inactive:       333492 kB
HighTotal:           0 kB
HighFree:            0 kB
LowTotal:      8033836 kB
LowFree:       6848920 kB
SwapTotal:     7815580 kB
SwapFree:      7815580 kB
Dirty:               0 kB
Writeback:           0 kB
AnonPages:       93060 kB
Mapped:          14128 kB
Slab:            86924 kB
PageTables:       5640 kB
NFS_Unstable:        0 kB
Bounce:              0 kB
CommitLimit:  11832496 kB
Committed_AS:   207872 kB
VmallocTotal: 34359738367 kB
VmallocUsed:    268816 kB
VmallocChunk: 34359468923 kB
HugePages_Total:     0
HugePages_Free:      0
HugePages_Rsvd:      0
Hugepagesize:     2048 kB
*/
```

##### JBOSS_HOME
```
echo $JBOSS_HOME
```
```c
/*
/opt/jboss/tune/jboss-as
*/
```

##### run.conf
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

JAVA_HOME="/usr/lib/jvm/java-1.6.0"

#
# Specify the exact Java VM executable to use.
#
#JAVA=""

#
# Specify options to pass to the Java VM.
#
# Reset JAVA_OPTS
JAVA_OPTS=""

# Enable Remote JProfiler Connection
#JAVA_OPTS="$JAVA_OPTS -agentpath:/opt/jprofiler/jprofiler9/bin/linux-x64/libjprofilerti.so"

##########################################################
### Current PROD settings

## HEAP ###
# Initial Java heap size
JAVA_OPTS="$JAVA_OPTS -Xms5000m"

# Maximum Java heap size
JAVA_OPTS="$JAVA_OPTS -Xmx5000m"

### NEW ###
# Default size of new generation
# 5000m heap - 2048m new = 2952m old
JAVA_OPTS="$JAVA_OPTS -XX:NewSize=2048m"

# Maximum size of new generation
JAVA_OPTS="$JAVA_OPTS -XX:MaxNewSize=2048m"

# Ratio between the two survivor spaces and eden
# 8=1:8 creates 8 eden spaces and 2 survivors
# 2048 / 10 = 204.8
JAVA_OPTS="$JAVA_OPTS -XX:SurvivorRatio=8"

# Thread-local allocation buffer
JAVA_OPTS="$JAVA_OPTS -XX:+UseTLAB"

### PERM ###
# Maximum Java permanent space size
JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=512m"

### GC ###
# Use Concurrent Mark Sweep Garbage Collector
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"

# Use Parallel New Garbage Collection
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"

# Disable garbage collection of classes
JAVA_OPTS="$JAVA_OPTS -Xnoclassgc"

### JBOSS ###
# JBoss Resolver Warning
JAVA_OPTS="$JAVA_OPTS -Dorg.jboss.resolver.warning=true"

# Periodic full collection frequency required by RMI
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=3600000"
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.server.gcInterval=3600000"

# Workaround to allow Array Syntax
JAVA_OPTS="$JAVA_OPTS -Dsun.lang.ClassLoader.allowArraySyntax=true"

### LOG ###
# Verbose Garbage Collection
JAVA_OPTS="$JAVA_OPTS -verbose:gc"

# Verbose Garbage Collection Log File
JAVA_OPTS="$JAVA_OPTS -Xloggc:gc.log"

# Print Garbage Collection Details
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"

# Print Garbage Collection Time Stamps
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"

########################################################
### Recommended settings
#JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelOldGC"
```

##### Startup
```
$JBOSS_HOME/bin/run.sh -b 0.0.0.0
```
```
/*
=========================================================================

  JBoss Bootstrap Environment

  JBOSS_HOME: /opt/jboss/tune/jboss-as

  JAVA: /usr/lib/jvm/java-1.6.0/bin/java

  JAVA_OPTS: -Dprogram.name=run.sh -server  -Xms5000m -Xmx5000m -XX:NewSize=2048m -XX:MaxNewSize=2048m -XX:SurvivorRatio=8 -XX:+UseTLAB -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xnoclassgc -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.lang.ClassLoader.allowArraySyntax=true -verbose:gc -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Djava.net.preferIPv4Stack=true

  CLASSPATH: /opt/jboss/tune/jboss-as/bin/run.jar:/usr/lib/jvm/java-1.6.0/lib/tools.jar

=========================================================================

Press any key to continue...
14:01:15,823 INFO  [ServerImpl] Starting JBoss (Microcontainer)...
14:01:15,825 INFO  [ServerImpl] Release ID: JBoss [EAP] 5.1.0 (build: SVNTag=JBPAPP_5_1_0 date=201009150028)
14:01:15,825 INFO  [ServerImpl] Bootstrap URL: null
14:01:15,825 INFO  [ServerImpl] Home Dir: /opt/jboss/tune/jboss-as
14:01:15,825 INFO  [ServerImpl] Home URL: file:/opt/jboss/tune/jboss-as/
14:01:15,825 INFO  [ServerImpl] Library URL: file:/opt/jboss/tune/jboss-as/lib/
14:01:15,826 INFO  [ServerImpl] Patch URL: null
14:01:15,826 INFO  [ServerImpl] Common Base URL: file:/opt/jboss/tune/jboss-as/common/
14:01:15,826 INFO  [ServerImpl] Common Library URL: file:/opt/jboss/tune/jboss-as/common/lib/
14:01:15,826 INFO  [ServerImpl] Server Name: default
14:01:15,827 INFO  [ServerImpl] Server Base Dir: /opt/jboss/tune/jboss-as/server
14:01:15,827 INFO  [ServerImpl] Server Base URL: file:/opt/jboss/tune/jboss-as/server/
14:01:15,827 INFO  [ServerImpl] Server Config URL: file:/opt/jboss/tune/jboss-as/server/default/conf/
14:01:15,827 INFO  [ServerImpl] Server Home Dir: /opt/jboss/tune/jboss-as/server/default
14:01:15,827 INFO  [ServerImpl] Server Home URL: file:/opt/jboss/tune/jboss-as/server/default/
14:01:15,827 INFO  [ServerImpl] Server Data Dir: /opt/jboss/tune/jboss-as/server/default/data
14:01:15,827 INFO  [ServerImpl] Server Library URL: file:/opt/jboss/tune/jboss-as/server/default/lib/
14:01:15,827 INFO  [ServerImpl] Server Log Dir: /opt/jboss/tune/jboss-as/server/default/log
14:01:15,827 INFO  [ServerImpl] Server Native Dir: /opt/jboss/tune/jboss-as/server/default/tmp/native
14:01:15,828 INFO  [ServerImpl] Server Temp Dir: /opt/jboss/tune/jboss-as/server/default/tmp
14:01:15,828 INFO  [ServerImpl] Server Temp Deploy Dir: /opt/jboss/tune/jboss-as/server/default/tmp/deploy
14:01:16,632 INFO  [ServerImpl] Starting Microcontainer, bootstrapURL=file:/opt/jboss/tune/jboss-as/server/default/conf/bootstrap.xml
14:01:17,299 INFO  [VFSCacheFactory] Initializing VFSCache [org.jboss.virtual.plugins.cache.CombinedVFSCache]
14:01:17,303 INFO  [VFSCacheFactory] Using VFSCache [CombinedVFSCache[real-cache: null]]
14:01:17,654 INFO  [CopyMechanism] VFS temp dir: /opt/jboss/tune/jboss-as/server/default/tmp
14:01:17,655 INFO  [ZipEntryContext] VFS force nested jars copy-mode is enabled.
14:01:19,053 INFO  [ServerInfo] Java version: 1.6.0_38,Sun Microsystems Inc.
14:01:19,053 INFO  [ServerInfo] Java Runtime: OpenJDK Runtime Environment (build 1.6.0_38-b38)
14:01:19,053 INFO  [ServerInfo] Java VM: OpenJDK 64-Bit Server VM 23.25-b01,Sun Microsystems Inc.
14:01:19,053 INFO  [ServerInfo] OS-System: Linux 2.6.18-409.el5,amd64
14:01:19,054 INFO  [ServerInfo] VM arguments: -Dprogram.name=run.sh -Xms5000m -Xmx5000m -XX:NewSize=2048m -XX:MaxNewSize=2048m -XX:SurvivorRatio=8 -XX:+UseTLAB -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xnoclassgc -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.lang.ClassLoader.allowArraySyntax=true -verbose:gc -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Djava.net.preferIPv4Stack=true -Djava.endorsed.dirs=/opt/jboss/tune/jboss-as/lib/endorsed
14:01:19,093 INFO  [JMXKernel] Legacy JMX core initialized
14:01:24,778 INFO  [WebService] Using RMI server codebase: http://99700hlzx6g1:8083/
14:01:29,910 INFO  [NativeServerConfig] JBoss Web Services - Stack Native Core
14:01:29,910 INFO  [NativeServerConfig] 3.1.2.SP7
14:01:30,950 INFO  [LogNotificationListener] Adding notification listener for logging mbean "jboss.system:service=Logging,type=Log4jService" to server org.jboss.mx.server.MBeanServerImpl@489c34f[ defaultDomain='jboss' ]
14:01:37,270 INFO  [MailService] Mail Service bound to java:/Mail
14:01:38,007 WARN  [JBossASSecurityMetadataStore] WARNING! POTENTIAL SECURITY RISK. It has been detected that the MessageSucker component which sucks messages from one node to another has not had its password changed from the installation default. Please see the JBoss Messaging user guide for instructions on how to do this.
14:01:39,568 INFO  [TransactionManagerService] JBossTS Transaction Service (JTA version - tag:JBOSSTS_4_6_1_GA_CP07) - JBoss Inc.
14:01:39,568 INFO  [TransactionManagerService] Setting up property manager MBean and JMX layer
14:01:39,802 INFO  [TransactionManagerService] Initializing recovery manager
14:01:39,926 INFO  [TransactionManagerService] Recovery manager configured
14:01:39,926 INFO  [TransactionManagerService] Binding TransactionManager JNDI Reference
14:01:39,960 INFO  [TransactionManagerService] Starting transaction recovery manager
14:01:40,420 INFO  [AprLifecycleListener] The Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.38.x86_64/jre/lib/amd64/server:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.38.x86_64/jre/lib/amd64:/usr/lib/jvm/java-1.6.0-openjdk-1.6.0.38.x86_64/jre/../lib/amd64:/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
14:01:40,529 INFO  [Http11Protocol] Initializing Coyote HTTP/1.1 on http-0.0.0.0-8080
14:01:40,530 INFO  [AjpProtocol] Initializing Coyote AJP/1.3 on ajp-0.0.0.0-8009
14:01:40,559 INFO  [StandardService] Starting service jboss.web
14:01:40,563 INFO  [StandardEngine] Starting Servlet Engine: JBoss Web/2.1.10
14:01:40,623 INFO  [Catalina] Server startup in 93 ms
14:01:40,644 INFO  [TomcatDeployment] deploy, ctxPath=/jbossws
14:01:41,315 INFO  [TomcatDeployment] deploy, ctxPath=/web-console
14:01:41,578 INFO  [TomcatDeployment] deploy, ctxPath=/invoker
14:01:41,712 INFO  [RARDeployment] Required license terms exist, view vfsfile:/opt/jboss/tune/jboss-as/server/default/deploy/jboss-local-jdbc.rar/META-INF/ra.xml
14:01:41,727 INFO  [RARDeployment] Required license terms exist, view vfsfile:/opt/jboss/tune/jboss-as/server/default/deploy/jboss-xa-jdbc.rar/META-INF/ra.xml
14:01:41,737 INFO  [RARDeployment] Required license terms exist, view vfsfile:/opt/jboss/tune/jboss-as/server/default/deploy/jms-ra.rar/META-INF/ra.xml
14:01:41,751 INFO  [RARDeployment] Required license terms exist, view vfsfile:/opt/jboss/tune/jboss-as/server/default/deploy/mail-ra.rar/META-INF/ra.xml
14:01:41,776 INFO  [RARDeployment] Required license terms exist, view vfsfile:/opt/jboss/tune/jboss-as/server/default/deploy/quartz-ra.rar/META-INF/ra.xml
14:01:41,875 INFO  [SimpleThreadPool] Job execution threads will use class loader of thread: main
14:01:41,909 INFO  [QuartzScheduler] Quartz Scheduler v.1.5.2 created.
14:01:41,913 INFO  [RAMJobStore] RAMJobStore initialized.
14:01:41,913 INFO  [StdSchedulerFactory] Quartz scheduler 'DefaultQuartzScheduler' initialized from default resource file in Quartz package: 'quartz.properties'
14:01:41,913 INFO  [StdSchedulerFactory] Quartz scheduler version: 1.5.2
14:01:41,915 INFO  [QuartzScheduler] Scheduler DefaultQuartzScheduler_$_NON_CLUSTERED started.
14:01:42,407 INFO  [ConnectionFactoryBindingService] Bound ConnectionManager 'jboss.jca:service=DataSourceBinding,name=DefaultDS' to JNDI name 'java:DefaultDS'
14:01:42,943 INFO  [ServerPeer] JBoss Messaging 1.4.7.GA server [0] started
14:01:43,047 INFO  [QueueService] Queue[/queue/DLQ] started, fullSize=200000, pageSize=2000, downCacheSize=2000
14:01:43,049 INFO  [QueueService] Queue[/queue/ExpiryQueue] started, fullSize=200000, pageSize=2000, downCacheSize=2000
14:01:43,121 INFO  [ConnectionFactory] Connector bisocket://99700hlzx6g1:4457 has leasing enabled, lease period 10000 milliseconds
14:01:43,121 INFO  [ConnectionFactory] org.jboss.jms.server.connectionfactory.ConnectionFactory@53baf3c4 started
14:01:43,122 INFO  [ConnectionFactoryJNDIMapper] supportsFailover attribute is true on connection factory: jboss.messaging.connectionfactory:service=ClusteredConnectionFactory but post office is non clustered. So connection factory will *not* support failover
14:01:43,122 INFO  [ConnectionFactoryJNDIMapper] supportsLoadBalancing attribute is true on connection factory: jboss.messaging.connectionfactory:service=ClusteredConnectionFactory but post office is non clustered. So connection factory will *not* support load balancing
14:01:43,124 INFO  [ConnectionFactory] Connector bisocket://99700hlzx6g1:4457 has leasing enabled, lease period 10000 milliseconds
14:01:43,124 INFO  [ConnectionFactory] org.jboss.jms.server.connectionfactory.ConnectionFactory@65bc3a1 started
14:01:43,125 INFO  [ConnectionFactory] Connector bisocket://99700hlzx6g1:4457 has leasing enabled, lease period 10000 milliseconds
14:01:43,126 INFO  [ConnectionFactory] org.jboss.jms.server.connectionfactory.ConnectionFactory@ca5a6ee started
14:01:43,227 INFO  [ConnectionFactoryBindingService] Bound ConnectionManager 'jboss.jca:service=ConnectionFactoryBinding,name=JmsXA' to JNDI name 'java:JmsXA'
14:01:43,380 INFO  [TomcatDeployment] deploy, ctxPath=/admin-console
14:01:43,484 INFO  [config] Initializing Mojarra (1.2_13-b01-FCS) for context '/admin-console'
14:01:46,181 INFO  [TomcatDeployment] deploy, ctxPath=/
14:01:46,234 INFO  [TomcatDeployment] deploy, ctxPath=/jmx-console
14:01:46,318 INFO  [ProfileServiceBootstrap] Loading profile: ProfileKey@1d8e3ba4[domain=default, server=default, name=default]
14:01:46,332 INFO  [Http11Protocol] Starting Coyote HTTP/1.1 on http-0.0.0.0-8080
14:01:46,358 INFO  [AjpProtocol] Starting Coyote AJP/1.3 on ajp-0.0.0.0-8009
14:01:46,398 INFO  [ServerImpl] JBoss (Microcontainer) [5.1.0 (build: SVNTag=JBPAPP_5_1_0 date=201009150028)] Started in 30s:567ms
*/
```

##### jps
```
jps
```
```c
/*
16154 Main
16226 Jps
*/
```

##### jstat
```
jstat -gccapacity 16154
```
```c
/*
 NGCMN    NGCMX     NGC     S0C       S1C       EC         OGCMN      OGCMX       OGC         OC      PGCMN    PGCMX     PGC       PC     YGC    FGC
2097152.0 2097152.0 2097152.0 209664.0 209664.0 1677824.0  3022848.0  3022848.0  3022848.0  3022848.0  21248.0 524288.0  61856.0  61856.0      0     1
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

```
jstat -gcnewcapacity
```
```
/*
  NGCMN      NGCMX       NGC      S0CMX     S0C     S1CMX     S1C       ECMX        EC      YGC   FGC
 2097152.0  2097152.0  2097152.0 209664.0 209664.0 209664.0 209664.0  1677824.0  1677824.0     0     1
*/
```
```c
/*
NGCMN   Minimum new generation capacity (KB).
NGCMX   Maximum new generation capacity (KB).
NGC     Current new generation capacity (KB).
S0CMX   Maximum survivor space 0 capacity (KB).
S0C	    Current survivor space 0 capacity (KB).
S1CMX   Maximum survivor space 1 capacity (KB).
S1C	    Current survivor space 1 capacity (KB).
ECMX    Maximum eden space capacity (KB).
EC	    Current eden space capacity (KB).
YGC	    Number of young generation GC events.
FGC	    Number of Full GC Events.
*/
```

```
jstat -gcoldcapacity
```
```c
/*
   OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT
  3022848.0   3022848.0   3022848.0   3022848.0     0     1    0.490    0.490
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

```
jstat -gcnew
```
```c
/*
 S0C    S1C    S0U    S1U   TT MTT  DSS      EC       EU     YGC     YGCT
209664.0 209664.0    0.0    0.0 15  15    0.0 1677824.0 1610865.0      0    0.000
*/
```

```
jstat -gcold
```
```c
/*
   PC       PU        OC          OU       YGC    FGC    FGCT     GCT
 61856.0  61715.2   3022848.0     44974.3      0     1    0.490    0.490
*/
```

