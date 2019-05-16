package api;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetValueDictionaries extends AbstractSend {

    public String getAreas(String country, String regionName, String cityName) {
        String id = null;
        List<HashMap> values = new Send().get(API_URL + "/areas").jsonPath().get("");
        for (HashMap value : values) {
            if (value.get("name").equals(country)) {
                ArrayList areas = (ArrayList) value.get("areas");
                for (Object obj : areas) {
                    HashMap region = (HashMap) obj;
                    if (region.get("name").equals(regionName)) {
                        ArrayList cities = (ArrayList) region.get("areas");
                        for (Object city : cities) {
                            HashMap address = (HashMap) city;
                            if (address.get("name").equals(cityName)) {
                                id = address.get("id").toString();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (id == null) {
            Assert.fail("Указанный населенный пункт не найден в справочнике");
        }
        return id;
    }

    public enum Dictionaries {
        EMPLOYMENT("employment"),
        EXPERIENCE("experience"),
        SCHEDULE("schedule");
        private String name;

        Dictionaries(String name) {
            this.name = name;
        }

        public String getDictionaries() {
            return name;
        }

    }

    public String getDictionaries(Dictionaries nameDirectory, String nameParam) {
        String id = null;
        List<HashMap> values = new Send().get(API_URL + "/dictionaries").jsonPath().get(nameDirectory.getDictionaries());
        for (HashMap value : values) {
            if (value.get("name").equals(nameParam)) {
                id = value.get("id").toString();
                break;
            }
        }
        if (id == null) {
            Assert.fail("Указанное значение " + nameParam + " не найдено в справочнике");
        }
        return id;
    }


    public enum Specializations {
        IT("Информационные технологии, интернет, телеком"),
        CONSTRUCTION_REALESTATE("Строительство, недвижимость"),
        SALES("Продажи");
        private String name;

        Specializations(String name) {
            this.name = name;
        }

        public String getSpecializations() {
            return name;
        }

    }

    public String getSpecializations(Specializations nameSpecialty) {
        String id = null;
        List<HashMap> values = new Send().get(API_URL + "/specializations").jsonPath().get("");
        for (HashMap value : values) {
            if (value.get("name").equals(nameSpecialty.getSpecializations())) {
                id = value.get("id").toString();
            }
        }
        if (id == null) {
            Assert.fail("Указанная специализация не найдена");
        }
        return id;
    }
}
