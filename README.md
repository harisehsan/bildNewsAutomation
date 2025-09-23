### Testcases Overview

As per the given requirements in the Mobile Test challenge document. I have developed the automation tests for 2 scenarios:

The first scenario verifies that the login functionality works correctly and that the user can log in when using the correct credentials.  The automation covers navigating to the login screen, entering the correct credentials, and clicking the login button. The test verifies that the text on the logged-in screen `Eingeloggt als <User email address>` is displayed to make the test scenario pass; if not, then fail it. The steps for this scenario are:

 Given I navigated to the Bild News Home screen
 And I go to the Login screen
 When I perform a login using the correct credentials `<Email>` and `<Password>`
 Then I should be logged as `<Email>`

The second scenario verifies that a login error should be displayed when the user performs a login with incorrect credentials.  The automation covers navigating to the login screen, entering the wrong credentials, and clicking the login button. The test verifies that the text on the screen `Die E-Mail-Adresse oder Passwort wurden nicht korrekt eingegeben.` is displayed to make the test scenario pass; if not, then fail it. The steps for this scenario are:

Given I navigated to the Bild News Home screen
And I goto the Login screen
When I perform login using incorrect credentials `<Email>` and `<Wrong password>`
 Then I should see login error message

### Framework Architecture

I developed the test framework from scratch using the following framework/tools:

1. Cucumber - Behaviour Driven Development (BDD).
2. Appium - Test automation tool for mobile apps
3. TestNG - A library in Java to perform test assertions checks.
4. Java - for test automation programming.  
6. Allure - A Reporting tool that provides test output.
7. Maven - A Build system for Java and for external dependencies management. 
8. Page Object Model design pattern.

– Note: I have made this project to be able to run on any Android device on which the Bild News app is installed. Unfortunately, I won’t be able to do this for iOS as I don’t have a MacBook. Additionally, the apps from the App Store won’t be able to run directly on cloud-based simulators like Sauce Labs without reassigning with the developer certificate.  
 
### Project Structure

1. The file `BildNews.feature`, which is the cucumber feature file in the directory `src/test/java/features/`, defines the Gherkin steps (in English) for the tests.
2. The file `BildLoginSteps.java` is the step definition file in the directory `src/test/java/stepdefinitions/`. It is used to link the cucumber feature file with the Java page classes to define the Programming for the Gherkin steps. Moreover, the test assertions checks are defined in that file.
3. The file  `BildLoginPage.java`, which is the Page file in the directory `src/test/java/Pages/`. This page file contains the actual programming-based methods that perform the test automation tasks, such as clicks/taps and sendKeys, etc., on page locators. Additionally, the dynamic waits, such as implicit and fluent waits, are also called in that class. 
4. The files `BildHomePageObject.java` and `BildLoginPageObject.java` in the directory `src/test/java/pageObject`. These Java classes are used to contain the Page locators that are used in the page class file to perform UI automation tasks. These class files are important to manage the page object model (POM) design pattern.
5. The file `Hooks.java` is in the directory `src/test/java/stepdefinitions/`. This contains `Before` and `After` blocks. 
 - The block before contains the desired capabilities execution to launch the Bild news app on the connected Android device. The device detection will be done automatically. But the Bild News app must be installed on the app. It also starts the video recording for the automated test execution before the test execution.
 - The After block is used to stop video recording and attach it to the allure report. For failed scenarios only, it takes the screenshot and attaches it to the allure report. Finally, it quit the running Android driver after the test execution.
6 . The file `BaseDriver.java` in the directory `src/test/java/util/` is used to define the Android driver. The file `HelperMethods.java` in the same directory as mentioned above is used to implement methods such as fluent waits that are Page class file, but it can be reused in the same or other page classes as the project grows.
7. The file `RunTest.java` in the `src/test/java/runConfig/` is used to define the execution configurations for the Cucumber-based framework.
8. The file `pom.xml` in the root directory contains external dependencies for managing the project.
9. The directories, such as `target` for the report, `screenshots` for the screenshot files, and `videos` for the execution video files, are created dynamically when the tests are executed locally.  


### Pre-requisites for the execution locally

Install JDK (Java Development Kit) latest version. For an OS like Windows, the environment variable must be set after installation. Then run the following command in the CMD/Terminal to ensure that the Java version is showing correctly:
`java -version`

Install Apache Maven latest version. For an OS like Windows, the environment variable must be set after installation. Then run the following command in CMD/Terminal to ensure that the Maven version is showing correctly:
`mvn -v`

Install Node.js. Then run the following command to check the version.
`node -v`
`npm -v`

Install Appium server 2 by using the command `npm i -g appium`. Then run the following command to check that the Appium version is showing correctly:
`appium -v`

Install the UI Automator 2 by using the following command:
`appium driver install uiautomator2`

Install Android SDK Manager and in the SDK Manager install:
Android SDK Platform (for the API level you’ll test)
Android SDK Platform-Tools (gives you adb)

Set the environment variables like ANDROID_HOME (or ANDROID_SDK_ROOT) to your SDK path and add these to your PATH:
`$ANDROID_HOME/platform-tools`
`$ANDROID_HOME/tools and $ANDROID_HOME/tools/bin`
Install the Bild News app from playstore in the Android Mobile.
Connect that Android phone to the Laptop, Computer, or MacBook via cable.
Run the command in CMD/Terminal `adb devices`. Make sure that the connected device UDID is shown.
Clone the project from my GitHub repo using the following command:
`git clone https://github.com/harisehsan/bildNewsAutomation.git`

– Note: In the case of not getting the version by running the command. Need to troubleshoot the problem and reinstall them properly. However, you can skip the step(s) if installed or configured already. 

 
### Execution Steps

Open a terminal/CMD and run the following command to start the Appium server:
`appium`
Open a new terminal tab, Go to the cloned project repository in the terminal/CMD, and run the following command: 
`dir` (in Windows CMD) 
`ls -a` (in the terminal of Mac/Linux)
By running the above command, if you get the file `pom.xml`, it shows that you are in the right directory. Otherwise, use the `cd` command to go to the clone repository.  
To run the tests, execute the following command:
`mvn clean test -Dcucumber.filter.tags="@bild_news_login" allure:serve`

### Execution in GitHub Actions CI
I have configured it to run in GitHub Actions using a self-hosted runner, because my Android device is connected to my local machine. 

Connect the Android phone to a local PC/MacBook.
Run the Appium server.
Configure the self-hosted runner. Please go to the following page to get more information on how to configure it with respect to the OS:
`https://docs.github.com/en/actions/how-tos/manage-runners/self-hosted-runners/add-runners`
After configuring the self-hosted runner. Run the command `./run` to execute the self-hosted runner.
Go to the link: `https://github.com/harisehsan/bildNewsAutomation/actions/workflows/bildNewsAutomation.yml`  
Click on the `Run Workflow` button to start the test execution in CI pipelines. 

### Flakiness mitigation

I faced the issue that when I entered the credentials for login in the text box, the login button enabling takes a little bit so I used fluent wait of Appium to handle it perfectly. The fluent wait looks for the element, like the login button become enabled, then it performs the clicking.


### I developed the automation tests using

PC: Windows 11
Mobile Device: Samsung A12 / OS: 13 (Android)


