package testSuites;

import api.GetValueDictionaries;
import api.Send;
import api.SendTranslator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import settings.Setting;
import api.GetValueDictionaries.Dictionaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static api.GetValueDictionaries.Dictionaries.*;
import static api.GetValueDictionaries.Specializations.IT;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author abolikov
 * @version 1.0
 */

public class HeadHunterSite extends Setting {
    private SendTranslator translator = new SendTranslator();

    @BeforeClass
    private void setUpValue() {
        GetValueDictionaries getParam = new GetValueDictionaries();
        translator.setLex("#area",getParam.getAreas("Россия", "Омская область", "Омск"));
        translator.setLex("#"+EMPLOYMENT.getDictionaries(),getParam.getDictionaries(EMPLOYMENT, "Полная занятость"));
        translator.setLex("#"+EXPERIENCE.getDictionaries(),getParam.getDictionaries(EXPERIENCE, "От 1 года до 3 лет"));
        translator.setLex("#"+SCHEDULE.getDictionaries(),getParam.getDictionaries(SCHEDULE, "Полный день"));
        translator.setLex("#specialization",getParam.getSpecializations(IT));
    }

    @Test
    public void dictionaries() {
        String request = API_URL+"/vacancies?area=#area&employment=#employment&experience=#experience&schedule=#schedule&specialization=#specialization";
     /* String request = API_URL+"/vacancies?area=68&experience=between1And3";*/
        new Send().setTranslator(translator).get(request);

    }

    @Test
    public void jobSearch() {
        open(CITE_URL);

    }
}
