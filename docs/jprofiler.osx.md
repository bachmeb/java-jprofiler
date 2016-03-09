# jprofiler.osx

## References
* http://resources.ej-technologies.com/jprofiler/help/doc/help.pdf
* http://resources.ej-technologies.com/jprofiler/help/doc/
* http://blog.ej-technologies.com/search/label/screencast
* http://blog.ej-technologies.com/2012/01/profiling-jpahibernate.html
* http://blog.ej-technologies.com/2013/07/java-profiling-across-jvm-boundaries.html
* http://blog.ej-technologies.com/2011/09/inspections-in-heap-walker.html
* https://support.apple.com/kb/dl1572?locale=en_US
* https://dimitrisli.wordpress.com/2012/01/20/install-java-on-mac-os-x/

##### Download JProfiler
* https://www.ej-technologies.com/products/jprofiler/overview.html

##### Download Eclipse Java EE IDE for Web Developers
* https://eclipse.org/downloads/  

*OSX, Version 3.7, Indigo*  
* http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/indigo/SR2/eclipse-jee-indigo-SR2-macosx-cocoa-x86_64.tar.gz

##### Create an Apple Developer account
* https://developer.apple.com

##### Login to the Apple Developer portal
* https://developer.apple.com

##### Go to the Apple Developer Downloads page and search for 'java'
* https://developer.apple.com/downloads/

##### Download Java for OS X 2013-005 Developer Package
*This package contains JDK 1.6 update 65*

##### Install Java for OS X 2013-005 Developer Package
*The file name is java_for_os_x_2013005_dp__11m4609.dmg*

##### Open the OSX Terminal application and list the contents of the JavaVirtualMachines directory
*Look for 1.6.0_65-b14-462.jdk*
```
ls /Library/Java/JavaVirtualMachines/
```

##### Open Eclipse Indigo
##### Create a new workspace
##### Create a new project
```
File > New > Project > Java Project
```
* *Create a Java Project*
  * Project name: testapp
  * Use default location: uncheck
  * Location: $DEV/git/java-performance-tuning/projects/testapp
  * JRE: Use a project specific JRE 
  * Click Configure JREs
    * *Installed JREs*
      * Click Add
        * *JRE Type*
          * Select MacOS X VM 
          * Click Next
          * JRE home: /Library/Java/JavaVirtualMachines/1.6.0_65-b14-462.jdk/Contents/Home
          * JRE name: JDK1.6_65
          * Click Finish
      * Click OK
    * *Installed JREs*
      * Select JDK1.6_65
      * Click OK
  * Use a project specific JRE: Select JDK1.6_65
  * Create separate folders for sources and class files: yes
  * Add project to working sets: no
  * Click Next
* Java Settings
  * Default output folder: testapp/bin
  * Click Finish

##### Create a new class
```
File > New > Class
```
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
```
Run > Run
```

*Notice the console output*

##### Open JProfiler
* *Quick Start*
	* Attach to a Locally Running VM 
	* Select Process Name: TestApp
	* Click Start
* *Initial Profiling Settings*
	* Instrumentation
	* Click OK
