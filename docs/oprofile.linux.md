# oprofile.linux

## References
* https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/5/pdf/Performance_Tuning_Guide/JBoss_Enterprise_Application_Platform-5-Performance_Tuning_Guide-en-US.pdf
* http://oprofile.sourceforge.net/

##### Install OProfile
Complete this task to install OProfile and its dependencies.

    yum install oprofile oprofile-jit

##### For all enabled yum repositories (/etc/yum.repos.d/*.repo), replace enabled=0 with enabled=1
in each file's debuginfo section. If there is no debug section, add it, using the example below as a
template.
```
[rhel-debuginfo]
name=Red Hat Enterprise Linux $releasever - $basearch - Debug
baseurl=ftp://ftp.redhat.com/pub/redhat/linux/enterprise/$releasever/en/os/$b
asearch/Debuginfo/
enabled=1
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-redhat-release
```
##### In a terminal, enter the following command, modifying the JDK version number to match the version
installed:
    yum install yum-plugin-auto-update-debug-info java-1.6.0-openjdk-debuginfo

##### Enable OProfile
*Add the following to the JVM's start parameters, usually standalone.conf or domain.conf in the platform's JBOSS_EAP_DIST/bin directory.*
```
# 64-bit JVM
-agentpath:/usr/lib64/oprofile/libjvmti_oprofile.so
# 32-bit JVM
-agentpath:/usr/lib/oprofile/libjvmti_oprofile.so
```
For example:
```
# Specify options to pass to the Java VM.
#
if [ "x$JAVA_OPTS" = "x" ]; then
JAVA_OPTS="-Xms10240m -Xmx10240m -XX:+UseLargePages -XX:+UseParallelOldGC"
JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -
Dorg.jboss.resolver.warning=true"
JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.dgc.client.gcInterval=3600000 -
Dsun.rmi.dgc.server.gcInterval=3600000"
JAVA_OPTS="$JAVA_OPTS -agentpath:/usr/lib64/oprofile/libjvmti_oprofile.so"
fi
```

##### Controlling OProfile
*OProfile runs as a daemon and must be running while profiling data is collected. The command-line utility opcontrol is used to manage OProfile's state.

##### Start the OProfile daemon. This can be done at any time prior to collecting profile data.
    opcontrol --start-daemon

##### Start the collection of profiling data. This command must be given before starting the workload to be profiled.
    opcontrol --start
    
##### Stop the collection of profiling data.
    opcontrol --stop
    
##### Dump the profiling data to the default file.
    opcontrol --dump
    
##### Shut down the OProfile daemon.
    opcontrol --shutdown

*Once it is running, each profiling session typically consists of the following steps:*

1. Start capturing profiling data
2. Start/run the workload
3. Stop capturing profiling data
4. Dump profiling data
5. Generate the profiling report

##### Create a simple profiling report

    opreport -l --output-file=<filename>

This command produces a list of processes and their CPU cycles, from highest to lowest. Note that
OProfile monitors the entire system, not just the Java processes. 
