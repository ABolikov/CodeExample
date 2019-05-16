package settings;

import okhttp3.internal.http2.Settings;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.codeborne.selenide.Configuration;

import java.util.logging.Logger;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Setting {
    protected final Logger log = Logger.getLogger(Settings.class.getName());
    protected static String CITE_URL = "https://wwww.hh.ru/";
    protected static String API_URL = "https://api.hh.ru";

    /**
     * Базовая Предустановка для всех тестов
     */
   // @BeforeClass
    public void init() {
        Configuration.browser = "chrome";
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        String chromeBinary = "src/main/resources/drivers/chrome/chromedriver" + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);
        Configuration.timeout = 25000;
        Configuration.startMaximized = true;

    }

    /**
     * Выполнить после всех наборов, закрытие Браузера.
     */
  //  @AfterClass(alwaysRun = true)
    public void tearDown() {
        getWebDriver().close();
    }
}
