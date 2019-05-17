package page;

import org.openqa.selenium.*;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author abolikov
 * @version 1.0
 */

public class BasePage extends Page {
    BasePage(WebDriver driver) {
        super(driver);
    }

    //Поле ввода поиска вакансии
    private static final By SEARCH_VACANCIES = By.name("text");
    //Поле раздела поиска
    private static final By SEARCH_SECTION = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Найти'])[1]/preceding::select[1]");
    //Кнопка найти
    private static final By BUTTON_SEARCH = By.cssSelector("span.navi-search-button__text");

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
    public BasePage clickButtonSearch() {
        $(BUTTON_SEARCH).click();
        return this;
    }

}
