package page;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

class SearchCityPage extends BasePage {
    SearchCityPage(WebDriver driver) {
        super(driver);
        WebDriverRunner.setWebDriver(driver);
    }

    //Поле ввода города для поиска
    private static final By SEARCH_CITY = By.id("area-search-input");

    //Выпадающий списко найденных сопдадений
    private static final By SELECT_SEARCH_CITY = By.className("g-anim-fade_in");


    @Step("Выбрать город")
    AnswerSearchPage sendKeysSearchCity(String city) {
        $(SEARCH_CITY).click();
        $(SEARCH_CITY).clear();
        $(SEARCH_CITY).sendKeys(city);
        List<WebElement> cites = $(SELECT_SEARCH_CITY).findElementsByClassName("Bloko-Suggest-Item");
        for (WebElement element : cites) {
            if ($(element).getText().contains(city)) {
                $(element).click();
            } else {
                Assert.fail("Указанный город " + city + " не найден");
            }
        }
        return PageFactory.initElements(getWebDriver(), AnswerSearchPage.class);
    }

}
