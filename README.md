# SwingSet2.Tests [![Build Status](https://travis-ci.org/UnknitSplash/SwingSet2.Tests.svg?branch=master)](https://travis-ci.org/UnknitSplash/SwingSet2.Tests)
Integration Tests for SwingSet2 

## Prerequisites 
* Install [jdk8u151](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Download [Demos and Samples](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) for that jdk. We''ll need a **_SwingSet2.jar_** which is located at **_jdk1.8.0_151/demo/jfc/SwingSet2_**

## Build (_build task_)
1. Open a Shell and navigate to the project root (e.g. _SwingSet2.Tests_)
2. run command:    
   ```Shell
   - Mac:     ./gradlew build
   ```
   ```Shell
   - Windows: gradlew.bat build
   ```
## Running Integration Tests (_integrationTest task_)
You can just run this task, because it will trigger the "build" task in the process
1. Put an app jar (SwingSet2.jar) into the **"sut"** directory in the project root(e.g. _SwingSet2.Tests/sut_). If you use Mac, make sure that you allow SwingSet2.jar to be launched! (Try to load it. If you recieve a message from the system, prohibiting you to launch it, go to System Preferences -> Security and Privacy -> Click to open anyway)
2. Open a Shell and navigate to the project root (e.g. _SwingSet2.Tests_)
3. Run command:    
   ```Shell
   - Mac:     ./gradlew integrationTest
   ```
   ```Shell
   - Windows: gradlew.bat integrationTest
   ```
 4. Don't touch the mouse and the keyboard for the duration of a test run.

## Configuring
There are two configuration points in the project: config and specifications.
### Config
Responsible for defining our test process. It allows us to change different timeouts(for finding elements, executing tests, disposing the app), delay between the events, whether to use system look & feel as default or use cross-platform one instead and etc. We can define different values for the platforms. If you have a slow machine, try tweaking timeout values in config.
```Java
mac.sutStartClass=SwingSet2Applet
mac.elementTimeout=1000
mac.testTimeout=15000
mac.disposeTimeout=3000
mac.specifications=mac:java:motif:nimbus
mac.delayBetweenEvents=10
mac.systemLook=true
mac.configuration=Basic

windows.sutStartClass=SwingSet2Applet
windows.elementTimeout=3000
windows.testTimeout=25000
windows.disposeTimeout=3000
windows.specifications=windows:java:motif:nimbus
windows.delayBetweenEvents=200
windows.systemLook=true
windows.configuration=Basic
```
Config is defined in _SwingSet2.Tests/src/main/resources/config.properties_
### Specifications
Responsible for defining the test behaviour. Here I store all the relative values for tests, so they can be easily interchanged. We must always have a default specification. If we define a custom one, and a test is set to use it, first it's going to look for a desired property in custom specification. In case the test couldn't find property in custom specification, it will look for it in the default one. Custom specifications are useful for describing values that may vary depending on an app's state (e.g. Look & Feel of SwingSet2). Each property name must start with a prefix, the specification name. For example, property in default specification must start with **_"default"_**, separated from the property name with a **_dot(.)_**.
```Java
default.tableDemo.imageColumn=Favorite Food
default.tableDemo.firstImage=images/ImageClub/food/apple.jpg
default.tableDemo.lastImage=images/ImageClub/food/watermelon.jpg

default.tableDemo.inputDouble=0x1.fffffffffffffP+1042
default.tableDemo.inputDoubleExpected=Infinity

default.tableDemo.inputColor=Black
default.tableDemo.inputColorExpected=0:0:0

default.tableDemo.initialReordering=true
default.tableDemo.initialHorizLines=true
default.tableDemo.initialVertLines=true
default.tableDemo.initialRowSelection=true
default.tableDemo.initialColumnSelection=false
default.tableDemo.initialIntercellSpacing=1
default.tableDemo.initialRowHeight=33
default.tableDemo.initialSelectMode=Multiple ranges
default.tableDemo.initialResizeMode=Subsequent columns

default.menu.lookAndFeelMenu=Look & Feel

mac.menu.lookAndFeel=Mac OS X Look & Feel
mac.tableDemo.selectionColor=9:80:208
mac.tableDemo.gridColor=255:255:255
mac.tableDemo.showGrid=true

windows.menu.lookAndFeel=Windows Style Look & Feel
windows.tableDemo.selectionColor=0:120:215
windows.tableDemo.gridColor=128:128:128
windows.tableDemo.showGrid=true

java.menu.lookAndFeel=Java Look & Feel
java.tableDemo.selectionColor=184:207:229
java.tableDemo.gridColor=122:138:153
java.tableDemo.showGrid=true
```
Specifications are defined in _SwingSet2.Tests/src/main/resources/specifications.properties_
## IntelliJ IDEA
.idea folder is in repository, so you can just open the project.
**_But_**, after you open the project and it loads fully, you need to **discard any changes** IDEA made to the project in **_git_**. This is necessary because IDEA overrides project files and removes module-library dependency on SUT directory. After discarding, IDEA will recognize the directory and you will be able to use the junit runner in the idea to run tests. If you don't reset changes, you'll have to add app dependancy on **_sut directory_** in **_Project Settings_** manually in order to run tests with the embedded runner.
