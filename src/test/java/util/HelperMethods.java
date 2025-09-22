package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class HelperMethods extends BaseDriver{

    public static void clickUntilEnabled(
                                      WebElement element,
                                      int pollingSec,
                                      int totalTimeSec) {

        new FluentWait<>(androidDriver)
                .withTimeout(Duration.ofSeconds(totalTimeSec))
                .pollingEvery(Duration.ofSeconds(pollingSec))
                .until(d -> {
                    try {
                        element.click();
                        element.isEnabled();
                        return false;
                    } catch (StaleElementReferenceException | NoSuchElementException e) {
                        return true;
                    }
                });
    }

    public static void clickUntilSecondElementEmpty(
            WebElement elementToClick,
            WebElement secondElement,
            int pollingSec,
            int totalTimeSec) {

        new FluentWait<>(androidDriver)
                .withTimeout(Duration.ofSeconds(totalTimeSec))
                .pollingEvery(Duration.ofSeconds(pollingSec))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> {
                    try {
                        elementToClick.click();
                        String text = secondElement.getText();
                        if (text == null || text.trim().isEmpty()) {
                            return true;
                        }
                    } catch (Exception ignored) {

                    }
                    return false;
                });
    }




}
