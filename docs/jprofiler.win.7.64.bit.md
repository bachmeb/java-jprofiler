# jprofiler.win.7.64.bit

Install and run JProfiler on a 64-bit Win 7 machine

## References
* http://resources.ej-technologies.com/jprofiler/help/doc/help.pdf
* http://resources.ej-technologies.com/jprofiler/help/doc/
* http://blog.ej-technologies.com/search/label/screencast
* http://blog.ej-technologies.com/2012/01/profiling-jpahibernate.html
* http://blog.ej-technologies.com/2013/07/java-profiling-across-jvm-boundaries.html
* http://blog.ej-technologies.com/2011/09/inspections-in-heap-walker.html
* https://support.apple.com/kb/dl1572?locale=en_US
* https://dimitrisli.wordpress.com/2012/01/20/install-java-on-mac-os-x/

In this tutorial $DEV refers to C:\DEV\ on my system. For consistency, you can choose any folder you like to be your DEV folder. 

##### Download JDK 1.6 update 45, 64-bit
* http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase6-419409.html#jdk-6u45-oth-JPR

##### Install JDK 1.6 update 45
* The filename is jdk-6u45-windows-x64.exe
* *Custom Setup*
	* Install to: $DEV\java\6\64\jdk1.6.0_45\

##### Add bin folder to Windows PATH environment variable
* Start > Control Panel > System > Advanced System Settings > Environment Variables > System Variables > Path > Edit
* Append the following: $DEV\java\6\64\jdk1.6.0_45\bin;
* Start > cmd
```
java -version
```

##### Download Eclipse Java EE IDE for Web Developers
* https://eclipse.org/downloads/  

*Windows, 64-bit, Version 3.7, Indigo*  
* http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/indigo/SR2/eclipse-jee-indigo-SR2-win32-x86_64.zip

##### Install Eclipse
* Unzip the package
* Rename the eclipse folder to 64
* Move the indigo folder to $DEV/eclipse/indigo/64

##### Download JProfiler for Windows 64-bit
* https://www.ej-technologies.com/products/jprofiler/overview.html

##### Install JProfiler 
* Location: $DEV\jprofiler\9
* *License Information*
	* Yes, I would like to evaluate or enter my license information
	* Next
	* Evaluate for 10 days
	* Next
* *IDE Integration* 
	* Eclipse 3.7 (Indigo)
	* Click Integrate
	* Choose Installation Folder
		* $DEV\eclipse\indigo\64
	* Click Next
* *Completing Setup*
	* Click Finish

##### Open Eclipse Indigo
* Start > Eclipse Indigo

##### Create a new workspace
* $DEV/eclipse/workspace/java-performance-tuning

##### Create a new project
* File > New > Project > Java Project
	* *Create a Java Project*
	  * Project name: testapp
	  * Use default location: uncheck
	  * Location: $DEV\git\java-performance-tuning\projects\testapp
	  * JRE: Use a project specific JRE 
	  * Click Configure JREs
	    * *Installed JREs*
	      * Click Add
	        * *JRE Type*
	          * Select Standard VM 
	          * Click Next
	          * JRE home: C:\DEV\java\6\64\jdk1.6.0_45
	          * JRE name: JDK1.6_45-64
	          * Click Finish
	    * *Installed JREs*
	      * Select JDK1.6_45-64
	      * Click OK
	  * Use a project specific JRE: Select JDK1.6_45-64
	  * Create separate folders for sources and class files: yes
	  * Add project to working sets: unchecked
	  * Click Next
	* Java Settings
	  * Default output folder: testapp/bin
	  * Click Finish

##### Create a new class
*File > New > Class
	* *Java Class*
		* Source folder: testapp/src
		* Package: (default)
		* Name: TestApp
		* Modifiers: Public
		* Superclass: java.lang.Object
		* Interfaces: (none)
		* Which methods and stubs would you like to create?
			* public static void main(String args[])
		* Click Finish

##### Code the class
```java
public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestApp me = new TestApp();
		me.go();
	}
	
	public void go(){
		System.out.println("Hello");
		
		for(int i=0; i<987654321;i++){
			System.out.println("Number: "+i);	
		}
	}

}
```
##### Run the app
* Run > Run > Run As > Java Application

*Notice the console output*

##### Open JProfiler
* Start > Programs > JProfiler
* *Quick Start*
	* Attach to a Locally Running VM 
	* Select Process Name: TestApp
	* Click Start
* *Initial Profiling Settings*
	* Instrumentation
	* Click OK
* *Local attach*
	* Select Telemetries > Overview
	* Select Telemetries > GC Activity
	* Select Telemetries > Classes
	* Select Telemetries > Threads
	* Select Telemetries > CPU Load
	* Select Live Memory > All Objects

##### Terminate TestApp in Eclipse
* Console > Terminate

##### Update the TestApp class
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        TestApp me = new TestApp();
        me.go();
    }

    public void go(){
        System.out.println("How many strings go into the box?");

        //for(int i=0; i<987654321;i++){
        //  System.out.println("Number: "+i);   
        //}

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = bufferedReader.readLine();
            System.out.println(line);
            int num = Integer.parseInt(line);
            Box box = new Box(num);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    class Box {
        ArrayList<String> strings;

        Box(){
            strings = new ArrayList<String>();
        }
        Box(int number){
            strings = new ArrayList<String>();
            for(int i=0;i<number;i++){
                strings.add(String.valueOf(i));
                System.out.println("There are "+ i +" strings in the box");
            }
        }
    }
}
```

