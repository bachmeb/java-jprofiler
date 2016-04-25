# run.conf

## References
* 

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
/* 
/opt/jboss/gc.log
*/
```

### Read the gc.log file
```
cat /opt/jboss/gc.log
```
```c
/*
9.699: [Full GC (System) 9.699: [CMS: 0K->45093K(3022848K), 0.5122730 secs] 604048K->45093K(4910336K), [CMS Perm : 25035K->25020K(25088K)], 0.5124600 secs]
*/
```
```c
/*
9.699: [Full GC (System) 9.699: [CMS: 0K->45093K(3022848K), 0.5122730 secs] 604048K->45093K(4910336K), [CMS Perm : 25035K->25020K(25088K)], 0.5124600 secs] [Times: user=0.43 sys=0.06, real=0.52 secs]
399.611: [GC 399.611: [ParNew: 1677824K->142198K(1887488K), 0.4720480 secs] 1722917K->187292K(4910336K), 0.4721650 secs] [Times: user=0.70 sys=0.09, real=0.47 secs]
418.734: [GC 418.734: [ParNew: 1820022K->176414K(1887488K), 1.4365710 secs] 1865116K->344190K(4910336K), 1.4366900 secs]
*/
```
