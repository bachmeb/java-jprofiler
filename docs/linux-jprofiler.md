# linux jprofiler

## References
* http://sahuly.blogspot.com/2012/02/how-to-connect-jprofiler-in-remote.html
* http://stackoverflow.com/questions/26751876/how-to-jprofile-on-linux-system-no-gui
* https://www.ej-technologies.com/download/jprofiler/files
* https://docs.oracle.com/cd/E15289_01/doc.40/e15062/optionx.htm
* http://docs.oracle.com/javase/7/docs/technotes/tools/windows/java.html
* https://community.oracle.com/thread/1177566?db=5
* https://developer.jboss.org/thread/206532?db=5
* http://stackoverflow.com/questions/30772662/error-while-running-jprofile8
* http://stackoverflow.com/questions/26607482/jbosss-fails-to-start-with-jdk1-7

### Install

##### Get a link to download JProfiler TAR.GZ Archive (69 MB) for Linux
* https://www.ej-technologies.com/download/jprofiler/files

##### Connect to the target machine using Putty with X11 forwarding enabled
* https://github.com/bachmeb/linux-install/blob/master/docs/common/xming.md

##### Download JProfiler
```
cd Downloads/
wget http://download-keycdn.ej-technologies.com/jprofiler/jprofiler_linux_9_1_1.tar.gz
```

##### Make an installation directory 
```
sudo mkdir /opt/jprofiler
```

##### Give your account permission to write to the installation directory
```
sudo chown -R [YOUR USER ACCOUNT] /opt/jprofiler
```

##### Unzip the installation package
```
cd /opt/jprofiler
tar -xvf ~/Downloads/jprofiler_linux_9_1_1.tar.gz
```

##### List the contents of the jprofiler9 directory
```
ls -l jprofiler9/
```

### Client
##### Start jprofiler
```
cd /opt/jprofiler/jprofiler9/bin
./jprofiler
```

### Remote Connection
#### JBoss
##### Confirm the Linux and JVM version (x64, x86, ARM?)
```
cat /proc/cpuinfo
java --version
```

##### Edit JBOSS_HOME/bin/run.conf
```
sudo nano $JBOSS_HOME/bin/run.conf
```

##### Add the agent path
*This value (/opt/jprofiler/jprofiler9/bin/...) should point to the JProfiler installation on the Linux machine.*
```
# Enable Remote JProfiler Connection
JAVA_OPTS="$JAVA_OPTS -agentpath:/opt/jprofiler/jprofiler9/bin/linux-x64/libjprofilerti.so"
```

##### Start JBoss
```
$JBOSS_HOME/bin/run.sh
```

##### Run the JProfiler client from a remote machine and provide the hostname of the Linux machine to connect
