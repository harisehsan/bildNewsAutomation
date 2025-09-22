package stepdefinitions;

import Pages.BildLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import util.BaseDriver;
import io.cucumber.java.en.Given;

public class BildLoginSteps {
    private BaseDriver base;

    BildLoginPage bildLoginPage;

    public BildLoginSteps(BaseDriver base)  {
        this.base = base;
        bildLoginPage = new BildLoginPage(BaseDriver.androidDriver);
    }

    @Given("I navigated to the Bild News Home screen")
    public void iNavigatedToTheHomescreen() {
        bildLoginPage.navigateToHomeScreen();
    }

    @And("I goto the Login screen")
    public void iGotoTheLoginPage() {
        bildLoginPage.gotoLoginPage();
    }

    @When("I perform login using correct credentials {string} and {string}")
    public void iPerformLoginUsingCorrectCredentialsAnd(String email, String password) {
        bildLoginPage.performLoginWithCorrectCredentials(email,password);
    }

    @Then("I should be loggedIn as {string}")
    public void iShouldBeLoggedInAs(String email) {
        Assert.assertTrue(bildLoginPage.loggedInScreenElement("Eingeloggt als").isDisplayed(), "Not successfully LoggedIn!");
        Assert.assertTrue(bildLoginPage.loggedInScreenElement(email).isDisplayed(), "LoggedIn email address is not displayed!");
    }


    @When("I perform login using incorrect credentials {string} and {string}")
    public void iPerformLoginUsingIncorrectCredentialsAnd(String email, String password) {
        bildLoginPage.performLoginWithIncorrectCredentials(email,password);
    }

    @Then("I should see login error message")
    public void iShouldSeeLoginErrorMessage() {
      Assert.assertTrue(bildLoginPage.getLoginErrorMessage().getText().contains("Die E-Mail-Adresse oder Passwort wurden nicht korrekt eingegeben."), "Login Error message is not correctly displayed!");
    }
}
