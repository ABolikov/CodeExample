package testSuites;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.AnswerSearchPage;
import page.HomePage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import settings.RetryMonitor;
import settings.Setting;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static page.BasePage.SearchSection.JOBS;

/**
 * @author abolikov
 * @version 1.0
 */

public class HeadHunterSite extends Setting {

    @BeforeClass
    private void setUpValue() {
        init();
    }

    @DataProvider(name = "JobSearch")
    private Object[][] jobSearch() {
        return new Object[][]{
                {"00001", "Омск", "Ведущий QA инженер", true},
                {"00002", "Чита", "АЗС", true},
                {"00003", "Новый Уренгой", "sgfdsgfdhg", false},
        };
    }

    @Features("Head Hunter API")
    @Stories("Поиск вакансий на сайте")
    @Description("Проверка выполенния поиска по указанным данным")
    @Test(retryAnalyzer = RetryMonitor.class, dataProvider = "JobSearch")
    public void checkJobSearch(String numCase, String city, String vacancy, Boolean visibleVacancy) {
        log.info("Start test: " + numCase);
        open(CITE_URL);
        HomePage hp = PageFactory.initElements(getWebDriver(), HomePage.class);
        hp.determineGeolocation(city).
                instalSearchSection(JOBS).
                sendKeysSearchVacancies(vacancy).
                clickButtonSearch().
                checkHeaderAnswerSearchPage(vacancy, visibleVacancy);
        if (visibleVacancy) {
            AnswerSearchPage aSP = PageFactory.initElements(getWebDriver(), AnswerSearchPage.class);
            aSP.checkNemaVacancy(vacancy, city);
        }
        log.info("Finish test: " + numCase);
    }
}
