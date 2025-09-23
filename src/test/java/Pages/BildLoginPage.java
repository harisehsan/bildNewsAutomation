package Pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObject.BildHomePageObject;
import pageObject.BildLoginPageObject;
import util.HelperMethods;

import java.time.Duration;


public class BildLoginPage extends HelperMethods {
    AndroidDriver androidDriver;
    BildLoginPageObject bildLoginPageObject = new BildLoginPageObject();
    BildHomePageObject bildHomePageObject = new BildHomePageObject();

    public BildLoginPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, bildLoginPageObject);
        PageFactory.initElements(androidDriver, bildHomePageObject);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void navigateToHomeScreen() {
        bildHomePageObject.acceptCondition().click();
        bildHomePageObject.furtherBtn().click();
        bildHomePageObject.skipBtn().click();
        bildHomePageObject.skipBtn2().click();
        bildHomePageObject.noThanksBtn().click();
    }

    public void gotoLoginPage()
    {
        bildHomePageObject.moreLbl().click();
        bildLoginPageObject.myAccountLbl().click();
        bildLoginPageObject.loginBtn().click();
    }

    public void performLoginWithCorrectCredentials(String email, String password)
    {
        bildLoginPageObject.emailtxtBox().sendKeys(email);
        bildLoginPageObject.passwordtxtBox().sendKeys(password);
        clickUntilEnabled(bildLoginPageObject.registerBtn(), 3, 9);
    }

    public WebElement loggedInScreenElement(String locator)
    {
        return bildLoginPageObject.loggedInScreenElement(locator);
    }

    public void performLoginWithIncorrectCredentials(String email, String password)
    {
        bildLoginPageObject.emailtxtBox().sendKeys(email);
        bildLoginPageObject.passwordtxtBox().sendKeys(password);
        clickUntilSecondElementEmpty(bildLoginPageObject.registerBtn(), bildLoginPageObject.passwordtxtBox(), 3, 9);
    }

    public WebElement getLoginErrorMessage()
    {
        return bildLoginPageObject.cannotLogintxtView();
    }
}
