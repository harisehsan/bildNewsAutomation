package pageObject;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static util.BaseDriver.androidDriver;


public class BildHomePageObject {
    public WebElement acceptCondition()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Alle akzeptieren\")"));
    }
    public WebElement furtherBtn() {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"WEITER\")"));
    }

    public WebElement skipBtn() {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().resourceId(\"js-submit-button\")"));
    }

    public WebElement skipBtn2() {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().textContains(\"BERSPRINGEN\")"));
    }
    public WebElement noThanksBtn() {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"NEIN, DANKE\")"));
    }

    public WebElement moreLbl()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Mehr\")"));
    }
}
