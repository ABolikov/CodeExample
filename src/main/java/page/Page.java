package page;

import org.openqa.selenium.WebDriver;

/**
 * @author abolikov
 * @version 1.0
 */

public abstract class Page {

    protected WebDriver driver;

    Page(WebDriver driver) {
        this.driver = driver;
    }

    public final WebDriver getWebDriver() {
        return driver;
    }
}