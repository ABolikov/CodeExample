package page;

import org.openqa.selenium.WebDriver;

/**
 * @author abolikov
 * @version 1.0
 */

public abstract class Page {

    private WebDriver driver;

    Page(WebDriver driver) {
        this.driver = driver;
    }

    final WebDriver getWebDriver() {
        return driver;
    }

    public abstract String declension(long number, String caseOne, String caseTwo, String caseFive);
}