package page;

import com.codeborne.selenide.WebDriverRunner;
import jdk.nashorn.internal.ir.IfNode;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * @author abolikov
 * @version 1.0
 */

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
        WebDriverRunner.setWebDriver(driver);
    }

    //Поле ввода поиска вакансии
    private static final By GEOLOCATION = By.className("bloko-notification");

    @Step("Определить геолокацию")
    public HomePage determineGeolocation(String city) {
        if ($(GEOLOCATION).isDisplayed()) {
            if ($(GEOLOCATION).findElementByClassName("bloko-form-row").getText().contains(city)) {
                $(GEOLOCATION).findElementByClassName("navi-region-clarification-text").click();
            } else {
                $(GEOLOCATION).findElementByClassName("bloko-button_primary-minor").click();
            }

        }
        return PageFactory.initElements(getWebDriver(), HomePage.class);
    }

}
