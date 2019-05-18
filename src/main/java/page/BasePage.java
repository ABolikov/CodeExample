package page;

import okhttp3.internal.http2.Settings;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Math.abs;

/**
 * @author abolikov
 * @version 1.0
 */

public class BasePage extends Page {
    BasePage(WebDriver driver) {
        super(driver);
    }
    protected final Logger log = Logger.getLogger(Settings.class.getName());

    //Поле ввода поиска вакансии
    private static final By SEARCH_VACANCIES = By.name("text");
    //Поле раздела поиска
    private static final By SEARCH_SECTION = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Найти'])[1]/preceding::select[1]");
    //Кнопка найти
    private static final By BUTTON_SEARCH = By.cssSelector("span.navi-search-button__text");
    //Ссылка геолакации.
    private static final By LINK_CITY = By.className("HH-Navi-RegionClarification-Container");

    @Step("Ввести название желаемой вакансии")
    public BasePage sendKeysSearchVacancies(String value) {
        $(SEARCH_VACANCIES).click();
        $(SEARCH_VACANCIES).clear();
        $(SEARCH_VACANCIES).sendKeys(value);
        return this;
    }

    //Разделы поиска
    public enum SearchSection {
        JOBS("Вакансии"),
        SUMMARY("Резюме"),
        COMPANIES("Компании");
        private String name;

        SearchSection(String name) {
            this.name = name;
        }

        public String getSearchSection() {
            return name;
        }

    }

    @Step("Установить нужный раздел поиска")
    public BasePage instalSearchSection(SearchSection value) {
        if ($(SEARCH_SECTION).getSelectedOption().text().equals(value.name)) {
            return this;
        } else {
            List<String> names = $(SEARCH_SECTION).getSelectedOptions().texts();
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).equals(value.name)) {
                    $(SEARCH_SECTION).getSelectedOptions().get(i).click();
                    break;
                }
            }
        }
        return this;
    }

    @Step("Нажать кнопку Найти")
    public AnswerSearchPage clickButtonSearch() {
        $(BUTTON_SEARCH).click();
        return PageFactory.initElements(getWebDriver(), AnswerSearchPage.class);
    }

    @Step("Нажать ссылку для выбора региона поиска")
    SearchCityPage clickSearchCites() {
        $(LINK_CITY).click();
        return PageFactory.initElements(getWebDriver(), SearchCityPage.class);
    }

    public String declension(long number, String caseOne, String caseTwo, String caseFive) {
        String str;
        number = abs(number);
        if (number % 10 == 1 && number % 100 != 11) {
            str = caseOne;
        } else if (number % 10 >= 2 && number % 10 <= 4 && (number % 100 < 10 || number % 100 >= 20)) {
            str = caseTwo;
        } else {
            str = caseFive;
        }
        return str;
    }

}
