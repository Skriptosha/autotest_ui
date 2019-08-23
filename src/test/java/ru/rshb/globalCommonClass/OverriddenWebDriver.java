package ru.rshb.globalCommonClass;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OverriddenWebDriver implements WebDriver {

    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

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
        return webDriverWait.until(webDriver -> webDriver.findElements(by)
                .stream().map(webElement ->
                        new OverriddenWebElement(webElement, webDriverWait))
                .collect(Collectors.toList()));
    }

    @Override
    public WebElement findElement(By by) {
        return new OverriddenWebElement(webDriverWait
                .until(webDriver -> webDriver.findElement(by)), webDriverWait);
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

    private static final String PROPERTY_FILE = "environment.properties";

    @Autowired
    private Environment environment;

    @Value("${driver}")
    private WebDrivers type;

    @PostConstruct
    public void init() {
        switch (type) {
            case opera:
                System.setProperty("webdriver.opera.driver", DRIVERS_PATH + environment.getProperty("opera.driver"));
                OperaOptions operaOptions = new OperaOptions();
                System.out.println(environment.getProperty("opera.path"));
                operaOptions.setBinary(Objects.requireNonNull(environment.getProperty("opera.path")));
                operaOptions.setCapability("browserName", "chrome");
                operaOptions.setCapability("browserVersion", "63.0");
                webDriver = new OperaDriver(operaOptions);
                break;
            case ie:
                System.setProperty("webdriver.ie.driver", DRIVERS_PATH + environment.getProperty("ie.driver"));

                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.setCapability(InternetExplorerDriver
                        .INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
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
            default:
                System.out.println("Данный [" + type + "] драйвер не установлен! Доступны opera, ie, firefox, chrome");
                System.exit(0);
                break;
        }
        webDriver.manage().window().maximize();
        setEnvironment(webDriver);
        webDriverWait = new WebDriverWait(webDriver,
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("timeout"))));
    }

    /**
     * Записывает информацию только при первом вызове
     *
     * @param webDriver ВебДрайвер
     */
    private static void setEnvironment(WebDriver webDriver) {
        Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
        try {
            Properties properties = new Properties();
            properties.setProperty("Браузер", capabilities.getBrowserName());
            properties.setProperty("Версия Браузера", capabilities.getVersion());
            properties.setProperty("IPv4", Inet4Address.getLocalHost().getHostAddress());
            properties.setProperty("Операционная система", capabilities.getPlatform().getPartOfOsName()[0]);
            File file = new File(System.getProperty("user.dir") + "/target/allure-results/"
                    + PROPERTY_FILE);
            if (file.exists() && file.isFile()) file.delete();
            properties.store(new FileOutputStream(file), "Создание нового файла");
        } catch (IOException e) {
            // ничего
        }
    }
}