package settings;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryMonitor implements IRetryAnalyzer {
    /**
     * Начальное количество попыток.
     */
    private int retryCount = 0;

    /**
     * Метод для повторения проваленных тестов, указать maxRetryCount.
     */
    public final boolean retry(final ITestResult result) {
        int maxRetryCount = 1;
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}
