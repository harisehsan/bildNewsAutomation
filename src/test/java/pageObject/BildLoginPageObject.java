package pageObject;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static util.BaseDriver.androidDriver;

public class BildLoginPageObject {

    public WebElement myAccountLbl()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Mein Konto\")"));
    }

    public WebElement loginBtn()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"LOGIN\")"));
    }

    public WebElement emailtxtBox()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().resourceId(\"identifier\")"));
    }

    public WebElement passwordtxtBox()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().resourceId(\"password\")"));
    }

    public WebElement registerBtn()
    {
        return androidDriver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"JETZT ANMELDEN\")"));
    }

    public WebElement cannotLogintxtView()
    {
        return androidDriver.findElement(new By.ByXPath("//*[contains(@text, 'Konto anlegen')]"));
    }

    public WebElement loggedInScreenElement(String locator) {
        return androidDriver.findElement(
                new AppiumBy.ByAndroidUIAutomator(
                        "new UiSelector().text(\"" + locator + "\")"
                )
        );
    }
}
