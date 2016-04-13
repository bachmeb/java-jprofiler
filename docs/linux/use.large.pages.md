# use large pages

## References
* http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html
* http://jose-manuel.me/2012/11/how-to-enable-the-jvm-option-uselargepages-in-redhat/
* https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html

##### What is shared memory?
* *Shared memory allows processes to access common structures and data by placing them in shared memory segments. It is the fastest form of inter-process communication available since no kernel involvement occurs when data is passed between the processes. In fact, data does not need to be copied between the processes.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/chap-Oracle_9i_and_10g_Tuning_Guide-Setting_Shared_Memory.html)

##### See all shared memory settings
```
ipcs -lm
```
```c
------ Shared Memory Limits --------
max number of segments = 4096
max seg size (kbytes) = 67108864
max total shared memory (kbytes) = 17179869184
min seg size (bytes) = 1
```


##### Setting SHMMAX
* *Note if you set SHMMAX to 4294967296 bytes (4294967296=4x1024x1024x1024=4GB) on a 32 bit system, then SHMMAX will essentially bet set to 0 bytes since it wraps around the 4GB value. 
* *This means that SHMMAX should not exceed 4294967295 on a 32 bit system.*
* *On x86-64 platforms, SHMMAX can be much larger than 4GB since the virtual address space is not limited by 32 bits.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/chap-Oracle_9i_and_10g_Tuning_Guide-Setting_Shared_Memory.html)

##### Determine the maximum size of a shared memory segment
```
cat /proc/sys/kernel/shmmax
```
```c
/*
68719476736
*/
```

##### Increase SHMMAX value. It must be larger than the Java heap size. 
*On a system with 4 GB of physical RAM (or less) the following will make all the memory sharable:*
```
sudo echo 4294967295 > /proc/sys/kernel/shmmax 
```

##### Reload the changes into the running kernel
```
sysctl -p
```


##### Check if your system can support large page memory
* *Large page support is included in 2.6 kernel. Some vendors have backported the code to their 2.4 based releases.* (http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html)
```
cat /proc/meminfo | grep Huge 
```
```c
/*
HugePages_Total: 0 
HugePages_Free: 0 
Hugepagesize: 2048 kB 
*/
```
* *If the output shows the three "Huge" variables then your system can support large page memory, but it needs to be configured. If the command doesn't print out anything, then large page support is not available.* (http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html)

##### Obtain the size of Huge Pages
*To calculate the number of Huge Pages you first need to know the Huge Page size.*
```
grep Hugepagesize /proc/meminfo
```
```c
/*
Hugepagesize:     2048 kB
*/
```
* *The output shows that the size of a Huge Page on this system is 2MB. This means if a 1GB Huge Pages pool should be allocated, then 512 Huge Pages need to be allocated.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html)

##### Calculate the number of large pages 
* In the following example we want to reserve 3 GB of a 4 GB system for large pages 
* Huge page size: 2048k
* Space reserved in megabytes: 3g = 3 x 1024m = 3072m
* Space reserved in kilobytes: 3072m = 3072 * 1024k = 3145728k
* Huge pages: 3145728k (space for large pages) / 2048k (huge page size) = 1536

##### To allocate 1536 Huge Pages
```
echo 1536 > /proc/sys/vm/nr_hugepages 
```

##### To allocate 512 Huge Pages
```
echo 512 > /proc/sys/vm/nr_hugepages
```

##### Alternatively, you can use sysctl(8) to change it:
```
sysctl -w vm.nr_hugepages=512
```

##### To make the change permanent, add the following line to the file /etc/sysctl.conf. 
```
echo "vm.nr_hugepages=512" >> /etc/sysctl.conf
```
* *This file is used during the boot process. The Huge Pages pool is usually guaranteed if requested at boot time*

##### Verify whether the kernel was able to allocate the requested number of Huge Pages
```
grep HugePages_Total /proc/meminfo
```
```
HugePages_Total:   512
```
* *If HugePages_Total is lower than what was requested with nr_hugepages, then the system does either not have enough memory or there are not enough physically contiguous free pages. In the latter case the system needs to be rebooted which should give you a better chance of getting the memory.*

##### Get the number of free Huge Pages on the system
```
grep HugePages_Free /proc/meminfo
```
* *Free system memory will automatically be decreased by the size of the Huge Pages pool allocation regardless whether the pool is being used.*

##### Check the free system memory
```
grep MemFree /proc/meminfo
```

##### Read the limits.conf file
```
less /etc/security/limits.conf
```
```
# /etc/security/limits.conf
#
#Each line describes a limit for a user in the form:
#
#<domain>        <type>  <item>  <value>
#
#Where:
#<domain> can be:
#        - an user name
#        - a group name, with @group syntax
#        - the wildcard *, for default entry
#        - the wildcard %, can be also used with %group syntax,
#                 for maxlogin limit
#
#<type> can have the two values:
#        - "soft" for enforcing the soft limits
#        - "hard" for enforcing hard limits
#
#<item> can be one of the following:
#        - core - limits the core file size (KB)
#        - data - max data size (KB)
#        - fsize - maximum filesize (KB)
#        - memlock - max locked-in-memory address space (KB)
#        - nofile - max number of open files
#        - rss - max resident set size (KB)
#        - stack - max stack size (KB)
#        - cpu - max CPU time (MIN)
#        - nproc - max number of processes
#        - as - address space limit
#        - maxlogins - max number of logins for this user
#        - maxsyslogins - max number of logins on the system
#        - priority - the priority to run user process with
#        - locks - max number of file locks the user can hold
#        - sigpending - max number of pending signals
#        - msgqueue - max memory used by POSIX message queues (bytes)
#        - nice - max nice priority allowed to raise to
#        - rtprio - max realtime priority
#
#<domain>      <type>  <item>         <value>
#

#*               soft    core            0
#*               hard    rss             10000
#@student        hard    nproc           20
#@faculty        soft    nproc           20
#@faculty        hard    nproc           50
#ftp             hard    nproc           0
#@student        -       maxlogins       4

# End of file
```
* *The memlock setting is specified in KB and must match the memory size of the number of Huge Pages that Oracle should be able to allocate. So if the Oracle database should be able to use 512 Huge Pages, then memlock must be set to at least 512 * Hugepagesize, which on this system would be 1048576 KB (512*1024*2).* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html)

##### Free the Huge Pages pool
```
echo 0 > /proc/sys/vm/nr_hugepages
```

##### Reload the changes into the running kernel
```
sysctl -p
```
*Note the /proc values will reset after reboot so you may want to set them in an init script (e.g. rc.local or sysctl.conf).*
