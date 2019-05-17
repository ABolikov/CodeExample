package testSuites;

import api.GetValueDictionaries;
import api.Send;
import api.SendTranslator;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.HomePage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;
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
    private Send send = new Send();
    private SendTranslator translator = new SendTranslator();
    private GetValueDictionaries getParam = new GetValueDictionaries();

    @BeforeClass
    private void setUpValue() {
        init();
        /*translator.setLex("#" + EMPLOYMENT.getDictionaries(), getParam.getDictionaries(EMPLOYMENT, "Полная занятость"));
        translator.setLex("#" + EXPERIENCE.getDictionaries(), getParam.getDictionaries(EXPERIENCE, "От 1 года до 3 лет"));
        translator.setLex("#" + SCHEDULE.getDictionaries(), getParam.getDictionaries(SCHEDULE, "Полный день"));
        translator.setLex("#specialization", getParam.getSpecializations(IT));
        send.setTranslator(translator);*/
    }

    @Features("Head Hunter API")
    @Stories("Поиск вакансий на сайте")
    @Description("Проверка выполенния поиска по указанным данным")
    @Title("00001")
    @Test(retryAnalyzer = RetryMonitor.class)
    public void jobSearch() {
        log.info("Start test: 00001");
        open(CITE_URL);
        HomePage hp = PageFactory.initElements(getWebDriver(), HomePage.class);
        hp.determineGeolocation("Омск").
                instalSearchSection(JOBS).
                sendKeysSearchVacancies("Ведущий QA инженер").
                clickButtonSearch();
        log.info("Finish test: 00001");
    }
}
