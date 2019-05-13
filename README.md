# BrowserStack Support Utils


## Details for utilities in this repo:
* `src -> main -> samples -> runCustomMethod.java` - This class is used to run any method defined under WebdriverCommandsTest.java
    Steps to setup this job on Jenkins:
    - Create a maven project in Jenkins
    - Check the box 'This build is paramterised' and declare 2 parameters
        1. Multi-Line Parameter: Capabilities
        2. Choice Paramter: MethodToRun (choices for MethodToRun can be found in MethodToRun.txt file under sampleFiles folder)
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="samples.runCustomMethod" -Dexec.args="\"$Capabilities\" $MethodToRun"`

### Notes: To add a new method, please ensure you make the following changes:
1. Add a new method in src/main/java/utilities/WebdriverCommandsTest.java
2. Add the new method in a switch case in src/main/java/samples/runCustomMethod.java
3. Add the new method name in jenkins under Build with Parameters under Choices

* `src -> main -> samples -> badSSL.java` - This class is used to run acceptSSL capability check
    Steps to setup this job on Jenkins:
    - Create a maven project in Jenkins
    - Check the box 'This build is paramterised' and declare 1 parameter. Note that this job supports passing multiple capabilities in an array format
        1. Multi-Line Parameter: Capabilities
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="samples.badSSL" -Dexec.args="\"$Capabilities\""`

* `src -> main -> utilities -> checkChromeVerMobiles.java` - This class is used to get chrome version on mobile devices
    Steps to setup this job on Jenkins:
    - Create a maven project in Jenkins
    - Check the box 'This build is paramterised' and declare 1 parameter.
        1. String Parameter: ThreadCount (Please specify any integer value between 1-5)
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="utilities.checkChromeVerMobiles" -Dexec.args="$ThreadCount"`

* `src -> main -> utilities -> getLogs.java` - This class is used to parse logs for any automate session
    Steps to setup this job on Jenkins:
    - Create a maven project in Jenkins
    - Check the box 'This build is paramterised' and declare 3 parameters
        1. String Parameter: sessionID (Enter session ID for any automate session)
        2. String Parameter: BS_Username (Any username with super-user access)
        3. String Parameter: BS_Key (Key associated with the username)
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="utilities.getLogs" -Dexec.args="$sessionID $BS_Username $BS_Key"`

* `src -> main -> utilities -> publicURLGenAutomate.java` - This class is used to generate public url for any test session
    Steps to setup this job on Jenkins:
    - Create a maven project in Jenkins
    - Check the box 'This build is paramterised' and declare 4 parameters
        1. String Parameter: sessionID (Enter session ID for any automate session)
        2. String Parameter: BS_Username (Any username with super-user access)
        3. String Parameter: BS_Key (Key associated with the username)
        4. Choice Parameter: sessionType (session type will have choices 'Automate', 'App-Automate' without single quotes and seperated by a line break)
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="utilities.publicURLGenAutomate" -Dexec.args="$sessionID $BS_Username $BS_Key $sessionType"`

* `src -> main -> utilities -> latestBrowsers.java` - This class is used to get latest browser version from BrowserStack's browser API
    - Create a maven project in Jenkins
    - Use git clone for the repo URL
    - Under Goals and Options, specify: `-q clean compile exec:java -Dexec.mainClass="utilities.latestBrowsers"`
