package autotests.ui.globalCommonClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Реализация для интерфейса WebDriver. Используется для внедрения WebDriverWait в стандартные методы WebDriver"а
 * Инициализация самого WebDriver происходит в методе init(); После Инициализации может быть получен
 * через Спринг с помощью @Autowired. Список доступных реализаций WebDriver"а находиться в enum"е WebDrivers.
 * Получить оригинальный WebDriver можно через геттер getOriginalWebDriver(), а инициализированный WebDriverWait
 * (является статик полем) через getWebDriverWait(). Не расчитан на использование в параллельных тестах в одной JVM.
 */
@Component
public class OverriddenWebDriver implements WebDriver {

    @Autowired
    private Logger logger;

    private WebDriver webDriver;

    private static WebDriverWait webDriverWait;

    static WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    WebDriver getOriginalWebDriver() {
        return webDriver;
    }

    @Override
    public void get(String s) {
        webDriver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return webDriverWait.until(WebDriver::getCurrentUrl);
    }

    @Override
    public String getTitle() {
        return webDriverWait.until(WebDriver::getTitle);
    }

    @Override
    public List<WebElement> findElements(By by) {
        logger.info("Ищем элементы: " + by.toString());
        return webDriverWait.until(webDriver -> webDriver.findElements(by)
                .stream().map(OverriddenWebElement::new)
                .collect(Collectors.toList()));
    }

    @Override
    public WebElement findElement(By by) {
        logger.info("Ищем элемент: " + by.toString());
        return new OverriddenWebElement(webDriverWait.until(webDriver -> webDriver.findElement(by)));
    }

    @Override
    public String getPageSource() {
        return webDriver.getPageSource();
    }

    @Override
    public void close() {
        webDriver.close();
    }

    @Override
    public void quit() {
        webDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return webDriverWait.until(WebDriver::getWindowHandles);
    }

    @Override
    public String getWindowHandle() {
        return webDriver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return webDriver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return webDriver.navigate();
    }

    @Override
    public Options manage() {
        return webDriver.manage();
    }

    private static final String DRIVERS_PATH = "./drivers/";

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        WebDrivers type = WebDrivers.valueOf(environment.getProperty("driver"));
        logger.info("type: " + type.toString());
        switch (type) {
            case opera:
                System.setProperty("webdriver.opera.driver", DRIVERS_PATH + environment.getProperty("opera.driver"));
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.setBinary(Objects.requireNonNull(environment.getProperty("opera.path")));
                operaOptions.setCapability("browserName", "chrome");
                operaOptions.setCapability("browserVersion", "63.0");
                webDriver = new OperaDriver(operaOptions);
                break;
            case ie:
                System.setProperty("webdriver.ie.driver", DRIVERS_PATH + environment.getProperty("ie.driver"));
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                webDriver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            case firefox:
                System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + environment.getProperty("firefox.driver"));
                System.setProperty("webdriver.firefox.bin", "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("browser.download.folderList", 2);
                firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, text/plain," +
                                " application/pdf, text/csv," +
                                " application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
                firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
                firefoxOptions.addPreference("browser.download.manager.closeWhenDone", true);
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case chrome:
                System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + environment.getProperty("chrome.driver"));
                ChromeOptions chromeOptions = new ChromeOptions();
                webDriver = new ChromeDriver(chromeOptions);
                break;
        }
        logger.fine("Экземпляр webDriver успешно создан");
        webDriver.manage().window().maximize();
        webDriverWait = new WebDriverWait(webDriver,
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("timeout"))));
        logger.fine("Экземпляр webDriverWait успешно создан");
    }
}