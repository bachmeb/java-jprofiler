# linux jprofiler

## References
* http://sahuly.blogspot.com/2012/02/how-to-connect-jprofiler-in-remote.html
* http://stackoverflow.com/questions/26751876/how-to-jprofile-on-linux-system-no-gui
* https://www.ej-technologies.com/download/jprofiler/files

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

##### Start jprofiler
```
cd /opt/jprofiler/jprofiler9/bin
./jprofiler
```
