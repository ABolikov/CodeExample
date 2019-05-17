package testSuites;

import api.GetValueDictionaries;
import api.Send;
import api.SendTranslator;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import settings.RetryMonitor;
import settings.Setting;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import static api.GetValueDictionaries.Dictionaries.*;
import static api.GetValueDictionaries.Specializations.IT;

/**
 * @author abolikov
 * @version 1.0
 */

public class HeadHunterAPI extends Setting {
    private Send send = new Send();
    private SendTranslator translator = new SendTranslator();
    private GetValueDictionaries getParam = new GetValueDictionaries();

    @BeforeClass
    private void setUpValue() {
        translator.setLex("#" + EMPLOYMENT.getDictionaries(), getParam.getDictionaries(EMPLOYMENT, "Полная занятость"));
        translator.setLex("#" + EXPERIENCE.getDictionaries(), getParam.getDictionaries(EXPERIENCE, "От 1 года до 3 лет"));
        translator.setLex("#" + SCHEDULE.getDictionaries(), getParam.getDictionaries(SCHEDULE, "Полный день"));
        translator.setLex("#specialization", getParam.getSpecializations(IT));
        send.setTranslator(translator);
    }

    @DataProvider(name = "checkDictionaries")
    private Object[][] checkDictionaries() {
        return new Object[][]{
                {"00001", EMPLOYMENT.getDictionaries(),
                        new String[]{"Полная занятость", "Частичная занятость", "Проектная работа", "Волонтерство", "Стажировка"}},
                {"00002", EXPERIENCE.getDictionaries(),
                        new String[]{"Нет опыта", "От 1 года до 3 лет", "От 3 до 6 лет", "Более 6 лет"}},
                {"00003", SCHEDULE.getDictionaries(),
                        new String[]{"Полный день", "Сменный график", "Гибкий график", "Удаленная работа", "Вахтовый метод"}},
        };
    }

    @Features("Head Hunter API")
    @Stories("Справочники")
    @Description("Проверка содержания справочников.")
    @Test(retryAnalyzer = RetryMonitor.class, dataProvider = "checkDictionaries")
    public void dictionaries(String numCase, String block, String[] values) {
        log.info("Start test: " + numCase);
        int checkValue = 0;
        SoftAssert check = new SoftAssert();
        Response resp = send.get(API_URL + "/dictionaries");
        List<HashMap> hp = resp.jsonPath().get(block);
        for (HashMap test : hp) {
            if (test.containsKey("name")) {
                for (String value : values) {
                    if (test.get("name").toString().equals(value)) {
                        checkValue++;
                        break;
                    }
                }
            } else {
                check.fail("Не во всех элементах присутствует параметр name");
            }
            check.assertTrue(test.containsKey("id"), "В элементе справочника с именем " + test.get("name") + " отствует параметр ID");
            check.assertNotNull(test.get("id"), "В элементе справочника с именем " + test.get("name") + " параметр ID = NULL");
        }
        if (checkValue != values.length) {
            check.fail("Не все значения найдены в справочнике");
        }
        check.assertAll();
        log.info("Finish test: " + numCase);
    }

    @DataProvider(name = "checkSearch")
    private Object[][] checkSearch() {
        String text1 = "Ведущий QA инженер";
        String text2 = "Ведущий инженер";
        String city1 = getParam.getAreas("Россия", "Омская область", "Омск");
        String city2 = getParam.getAreas("Россия", "Новосибирская область", "Новосибирск");
        Consumer<SendTranslator> case1 = (page) -> {
            translator.setLex("#area", city1);
            translator.setLex("#text", text1.replace(" ", "+"));
        };
        Consumer<SendTranslator> case2 = (page) -> {
            translator.setLex("#area", city2);
            translator.setLex("#text", text2.replace(" ", "+"));
        };
        return new Object[][]{
                {"00001", case1, text1, city1},
                {"00002", case2, text2, city2},
        };
    }

    @Features("Head Hunter API")
    @Stories("Поиск вакансий")
    @Description("Проверка выполенния поиска по указанным данным")
    @Test(retryAnalyzer = RetryMonitor.class, dataProvider = "checkSearch")
    public void checkSearchVacancies(String numCase, Consumer<SendTranslator> step, String text, String area)
            throws UnirestException, IOException {
        log.info("Start test: " + numCase);
        SoftAssert check = new SoftAssert();
        boolean flag = false;
        step.accept(translator);
        String[] names = text.split(" ");
        String request = API_URL + iFile.readTest("/testSuites/resources/baseGetResponseSearchVacancies.txt");
        JsonPath resp = send.getUnirest(request);
        List<HashMap> vacancies = resp.getList("items");
        for (HashMap vacancy : vacancies) {
            check.assertEquals(((HashMap) vacancy.get("area")).get("id"), area);
            for (String name : names) {
                if (vacancy.get("name").toString().contains(name)) {
                    flag = true;
                }
            }
            check.assertTrue(flag, "Вакансия с ID = " + vacancy.get("id") + " не содержит ниодной части искомой вакансии");
        }
        check.assertAll();
        log.info("Finish test: " + numCase);
    }
}
