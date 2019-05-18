package page;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class AnswerSearchPage extends BasePage {
    public AnswerSearchPage(WebDriver driver) {
        super(driver);
        WebDriverRunner.setWebDriver(driver);
    }

    //Локатор всей страницы ответа на выполенный поиск
    private static final By SEARCH_ANSWER = By.className("row-content");
    //Локатор списка результатов ответа на выполенный поиск
    private static final By JOB_LIST = By.className("vacancy-serp");

    @Step("Получение списка вакансий c вложенными параметрами")
    private List<WebElement> getJobLis() {
              return $(JOB_LIST).findElementsByClassName("vacancy-serp-item ");
    }

    @Step("Проверка заголовка страницы ответа")
    public AnswerSearchPage checkHeaderAnswerSearchPage(String respNemaVacancy, Boolean notNull) {
        String value = $(SEARCH_ANSWER).findElementByClassName("header").getText();
        int sizeList = getJobLis().size();
        String str = declension(sizeList, "вакансия", "вакансии", "вакансий");
        if (notNull) {
            if (value.contains("ничего не найдено")) {
                Assert.fail("По указанному запросу вакансий не найдено");
            } else {
                Assert.assertEquals(value, sizeList + " " + str + " «" + respNemaVacancy + "»");
            }
        } else {
            Assert.assertEquals(value, "По запросу «" + respNemaVacancy + "» ничего не найдено");
        }
        return this;
    }

    @Step("Поверка результатов выполенного выбора по наименованию и городу")
    public AnswerSearchPage checkNemaVacancy(String respNemaVacancy, String city) {
        SoftAssert check = new SoftAssert();
        boolean flag = false;
        List<WebElement> jobLis = getJobLis();
        String[] elementsName = respNemaVacancy.toLowerCase().split(" ");
        for (WebElement elem : jobLis) {
            String name = $(elem).findElementByClassName("resume-search-item__name").getText().toLowerCase();
            for (String elementName : elementsName) {
                if (name.contains(elementName)) {
                    flag = true;
                    break;
                }
            }
            check.assertTrue(flag,
                    "В списке вакансий выведена информация о вакансии не содержащей части имени запроса пиоиска: " + "«" + name + "»");
            check.assertEquals($(elem).findElementsByClassName("vacancy-serp-item__meta-info").get(1).getText(), city,
                    "Найденная вакансия не соотвтеут выбранному городу: " + "«" + name + "»");
        }
        check.assertAll();
        return this;
    }
}
