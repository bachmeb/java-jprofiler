
##### run.conf
```
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

##### jps
```
jps
```

##### jstat
```
jstat -gccapacity 15888
```
```c
 NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC      PGCMN    PGCMX     PGC       PC     YGC    FGC
2097152.0 2097152.0 2097152.0 209664.0 209664.0 1677824.0  3022848.0  3022848.0  3022848.0  3022848.0  21248.0 524288.0  61920.0  61920.0      0     1
```
