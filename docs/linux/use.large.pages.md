# use large pages

## References
* http://www.oracle.com/technetwork/java/javase/tech/largememory-jsp-137182.html
* http://jose-manuel.me/2012/11/how-to-enable-the-jvm-option-uselargepages-in-redhat/
* https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html
* http://aixnote.tistory.com/215
* https://bugs.openjdk.java.net/browse/JDK-8048224
* https://access.redhat.com/solutions/43525

##### What is shared memory?
* *Shared memory allows processes to access common structures and data by placing them in shared memory segments. It is the fastest form of inter-process communication available since no kernel involvement occurs when data is passed between the processes. In fact, data does not need to be copied between the processes.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/chap-Oracle_9i_and_10g_Tuning_Guide-Setting_Shared_Memory.html)

* *The term shared memory is a term used to describe a type of memory management in the Unix kernel.  It is a memory region that can shared between different processes.*
* *This is where semaphores come into play. Basically, flags that are either on or off.*
* *If the memory is being used, the process turns on the flag and other processes have to wait until the semaphore is freed or the flag is turned off.*
* *An application can access shared memory programatically through a set of POSIX C routines, common on most Unix like operating systems including Linux.*
* *The IPC or shared memory API’s have common IPC calls like shmget() and shmat() which allow the programmer to get a shared memory segment, or attach to a segment.*
* *The kernel paramaters SHMMAX and SHMALL need to be defined now.*
* *SHMMAX is really just the maximum size of a single shared memory segment.  It’s size is represented in bytes.* 
* *SHMALL is the sum of all shared memory segments on the whole system.  But it is measured in number of pages.* 
* (http://seriousbirder.com/blogs/linux-understanding-shmmax-and-shmall-settings/)

##### View semaphores
```
ipcs -s
```
```c
/*
------ Semaphore Arrays --------
key        semid      owner      perms      nsems
*/
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

##### See all shared memory settings
```
ipcs -lm
```
```c
/*
------ Shared Memory Limits --------
max number of segments = 4096                     <-- SHMMNI
max seg size (kbytes) = 67108864                  <-- SHMMAX
max total shared memory (kbytes) = 17179869184    <-- SHMALL
min seg size (bytes) = 1
*/
```
* *SHMMAX = 67,108,864 = 64x1024x1024 = 64 million kilobytes = 64 gigabytes* 
* *SHMALL = 17,179,869,184 = 16x1024x1024x1024 = 16 billion kilobytes = 16 terabytes*
 
##### Setting SHMMAX
* *Note if you set SHMMAX to 4294967296 bytes (4294967296=4x1024x1024x1024=4GB) on a 32 bit system, then SHMMAX will essentially bet set to 0 bytes since it wraps around the 4GB value.*
* *This means that SHMMAX should not exceed 4294967295 on a 32 bit system.*
* *On x86-64 platforms, SHMMAX can be much larger than 4GB since the virtual address space is not limited by 32 bits.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/chap-Oracle_9i_and_10g_Tuning_Guide-Setting_Shared_Memory.html)

##### Read the current SHMMAX setting (the maximum size of a shared memory segment)
* If the current SHMMAX setting is greater than the amount of physical memory, the SHMMAX setting does not need to be increased.
```
cat /proc/sys/kernel/shmmax
```
```c
/*
68719476736
*/
```
* The SHMMAX value must be larger than the Java heap size
* 68,719,476,736 = 1024x1024x1024x64 = 64 gigabytes
* In the case of a machine with 8GB RAM, the SHMMAX value does not need to be increased, because 64GB is greater than 8GB, and the heap size will be no greater than 8GB

##### Read the Kernel sysctl configuration file for Red Hat Linux
* *This file is used during the boot process. The Huge Pages pool is usually guaranteed if requested at boot time*
```
sudo cat /etc/sysctl.conf | grep shmmax
```

##### Increase SHMMAX value
*On a system with 4 GB of physical RAM (or less) the following will make all the memory sharable. (4,294,967,295 = (4x1024x1024x1024)-1))*
```
sudo /sbin/sysctl -w kernel.shmmax=4294967295
```
*On a system with 8 GB of physical RAM (or less) the following will make all the memory sharable. (8,589,934,591 = (8x1024x1024x1024)-1))*
```
sudo /sbin/sysctl -w kernel.shmmax=8589934591
```
* 64 GB
```
sudo /sbin/sysctl -w kernel.shmmax=68719476736
```

##### To make the shmmax setting permanent, edit sysctl.conf
```
sudo nano /etc/sysctl.conf
```
```c
/*
# Controls the maximum shared segment size, in bytes
kernel.shmmax = 8589934591
*/
```
##### Setting SHMALL
* *Since SHMALL is the sum of all the shared memory segments on your system, you had better make it smaller than your total system memory.* (http://seriousbirder.com/blogs/linux-understanding-shmmax-and-shmall-settings/)

##### Get the current page size
```
getconf PAGE_SIZE
```
```c
// 4096
```
##### Read the current SHMALL setting (the sum of all the shared memory segments)
* SHMALL is measured in memory pages not bytes. (http://seriousbirder.com/blogs/linux-understanding-shmmax-and-shmall-settings/)
```
cat /proc/sys/kernel/shmall
```
```c
/*
2097152
*/
```
* 2097152 * 4096 = 8589934592 = 8x1024x1024x1024 = 8 GB
* The SHMALL value must be less than than your total system memory

##### Read the SHMALL setting in the kernel sysctl configuration file
```
sudo cat /etc/sysctl.conf | grep shmall
```
```c
/*
kernel.shmall = 4294967296
*/
```

##### Convert the SHMALL value to a memory value in bytes
```
bc
4294967296*4096
17592186044416
4294967296*4096/1024
17179869184
4294967296*4096/1024/1024
16777216
4294967296*4096/1024/1024/1024
16384
4294967296*4096/1024/1024/1024/1024
16
```
* The memory value of 4294967296 shmall is 16 terabytes

##### Calculate a SHMALL value for 6 GB of memory with a 4096 page size
```
bc
6*1024*1024*1024
6442450944
6*1024*1024*1024/4096
1572864
```

##### Set the SHMALL value
*To set the SHMALL value to 6 GB where the page is is 4k*
```
sudo /sbin/sysctl -w kernel.shmall=1572864
```

##### To make the SHMALL setting permanent, edit sysctl.conf
```
sudo nano /etc/sysctl.conf
```
```c
/*
# Controls the maximum number of shared memory segments, in pages
kernel.shmall = 1572864
*/
```

##### Obtain the size of Huge Pages
* *To calculate the number of Huge Pages you first need to know the Huge Page size.*
```
grep Hugepagesize /proc/meminfo
```
```c
/*
Hugepagesize:     2048 kB
*/
```
* *The output shows that the size of a Huge Page on this system is 2MB. This means if a 1GB Huge Pages pool should be allocated, then 512 Huge Pages need to be allocated.* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html)

##### Calculate the number of huge pages to allocate
* In the following example we want to reserve 3 GB of a 4 GB system for large pages 
  * Huge page size: 2048k
  * Space to be reserved in megabytes: 3g = 3 x 1024m = 3072m
  * Space to be reserved in kilobytes: 3072m = 3072 * 1024k = 3145728k
  * Huge pages required: 3145728k (space for huge pages) / 2048k (huge page size) = 1536
* In the following example we want to reserve 6 GB of a 8 GB system for large pages 
  * Huge page size: 2048k
  * Space to be reserved in megabytes: 6g = 6 x 1024m = 6144m
  * Space to ber reserved in kilobytes: 6144m = 6144 * 1024k = 6291456k
  * Huge pages required: 6291456k (space for huge pages) / 2048k (huge page size) = 3072

##### To allocate 3072 Huge Pages
```
sudo su
echo 3072 > /proc/sys/vm/nr_hugepages 
exit
```

##### To allocate 1536 Huge Pages
```
sudo su
echo 1536 > /proc/sys/vm/nr_hugepages 
exit
```

##### To allocate 512 Huge Pages
```
sudo su
echo 512 > /proc/sys/vm/nr_hugepages
exit
```

##### Alternatively, you can use sysctl(8) to change it:
```
sysctl -w vm.nr_hugepages=512
```

##### Verify whether the kernel was able to allocate the requested number of Huge Pages
```
grep HugePages_Total /proc/meminfo
```
```
HugePages_Total:  1137
```
* *If HugePages_Total is lower than what was requested with nr_hugepages, then the system does either not have enough memory or there are not enough physically contiguous free pages. In the latter case the system needs to be rebooted which should give you a better chance of getting the memory.*

##### Read the Kernel sysctl configuration file for Red Hat Linux
* *This file is used during the boot process. The Huge Pages pool is usually guaranteed if requested at boot time*
```
sudo cat /etc/sysctl.conf
```
```bash
# Kernel sysctl configuration file for Red Hat Linux
#
# For binary values, 0 is disabled, 1 is enabled.  See sysctl(8) and
# sysctl.conf(5) for more details.

# Controls IP packet forwarding
net.ipv4.ip_forward = 0

# Controls source route verification
net.ipv4.conf.default.rp_filter = 1

# Do not accept source routing
net.ipv4.conf.default.accept_source_route = 0

# Controls the System Request debugging functionality of the kernel
kernel.sysrq = 0

# Controls whether core dumps will append the PID to the core filename
# Useful for debugging multi-threaded applications
kernel.core_uses_pid = 1

# Controls the use of TCP syncookies
net.ipv4.tcp_syncookies = 1

# Controls the default maxmimum size of a mesage queue
kernel.msgmnb = 65536

# Controls the maximum size of a message, in bytes
kernel.msgmax = 65536

# Controls the maximum shared segment size, in bytes
kernel.shmmax = 68719476736

# Controls the maximum number of shared memory segments, in pages
kernel.shmall = 4294967296
```
* 68,719,476,736 = 1024x1024x1024x64 = 64 gigabytes
* 4,294,967,296 = 1024x1024x1024x4 = 4 billion pages

##### To make the allocation of Huge Pages permanent, add vm.nr_hugepages=x to the file /etc/sysctl.conf
```
sudo su
echo "vm.nr_hugepages=3072" >> /etc/sysctl.conf
exit
sudo cat /etc/sysctl.conf
```

##### Reboot
```
sudo reboot
```

##### Verify whether the kernel was able to allocate the requested number of Huge Pages
```
grep HugePages_Total /proc/meminfo
```
```
HugePages_Total:  3072
```

##### Get the number of free Huge Pages on the system
```
grep HugePages_Free /proc/meminfo
```
```
3072
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
* *The memlock setting is specified in kilobytes and must match the memory size of the number of Huge Pages that Java should be able to allocate. So if the Java application should be able to use 512 Huge Pages, then memlock must be set to at least 512 x Hugepagesize, which on this system would be 1048576 KB (512x1024x2).* (https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/5/html/Tuning_and_Optimizing_Red_Hat_Enterprise_Linux_for_Oracle_9i_and_10g_Databases/sect-Oracle_9i_and_10g_Tuning_Guide-Large_Memory_Optimization_Big_Pages_and_Huge_Pages-Configuring_Huge_Pages_in_Red_Hat_Enterprise_Linux_4_or_5.html)

##### Free the Huge Pages pool
```
echo 0 > /proc/sys/vm/nr_hugepages
```

##### Run the Java application with XX:+UseLargePages
```
XX:+UseLargePages
```

##### Get the number of free Huge Pages on the system
```
grep HugePages_Free /proc/meminfo
```
```

```
* *Free system memory will automatically be decreased by the size of the Huge Pages pool allocation regardless whether the pool is being used.*

##### Check the free system memory
```
grep MemFree /proc/meminfo
```
```
```
