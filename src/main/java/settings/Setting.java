package settings;

import okhttp3.internal.http2.Settings;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Setting {
    private boolean start = false;
    protected static iFileReader iFile = new iFileReader();
    protected final Logger log = Logger.getLogger(Settings.class.getName());
    protected static String CITE_URL = "https://wwww.hh.ru/";
    protected static String API_URL = "https://api.hh.ru";

    public void init() {
        Configuration.browser = "chrome";
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        String chromeBinary = "src/main/resources/drivers/chrome/chromedriver" + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);
        Configuration.timeout = 25000;
        Configuration.startMaximized = true;
        start = true;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (start) {
            getWebDriver().close();
        }
    }

    /**
     * Очистить Куки после каждого теста
     */
    @AfterMethod
    public void clearCookies() {
        if (start) {
            alertAccept();
            open(CITE_URL);
            alertAccept();
            getWebDriver().manage().deleteAllCookies();
        }
    }

    private void alertAccept() {
        if (isAlertPresent()) {
            Alert alert = (new WebDriverWait(getWebDriver(), 10)).until(ExpectedConditions.alertIsPresent());
            alert.accept();
            alertAccept();
        }
    }

    private boolean isAlertPresent() {
        try {
            getWebDriver().switchTo().alert();
            return true;

        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    @Attachment(value = "Запрос", type = "text/plain")
    private static String requestAttach(String request) {
        return request;
    }

    @Attachment(value = "Ответ", type = "text/plain")
    private static String answerAttach(String answer) {
        return answer;
    }

    @Step("Сервер запроса : {0}")
    private static void server(String requestServer) {
    }

    public static void allureOutputInfo(String requestServer, String request, String answer) {
        if (requestServer != null) {
            server(requestServer);
        }
        requestAttach(request);
        answerAttach(answer);
    }
}
