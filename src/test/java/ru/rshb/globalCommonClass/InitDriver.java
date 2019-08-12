package ru.rshb.globalCommonClass;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InitDriver {

    private static final String DRIVERS_PATH = "./drivers/";

    private static final String PROPERTY_FILE = "GetConfig.properties";


    /**
     * Геттер для получения экземпляра WebDriver
     *
     * @return экземпляр WebDriver
     */
    @Bean
    public WebDriver getWebDriver() {
        long id = Thread.currentThread().getId();
        if (threadWebDriver.get(id) != null)
            return threadWebDriver.get(id);
        else
            throw new IllegalStateException("По данному threadID " + id + " не найден экземпляр WebDriver");
    }

    /**
     * Геттер для получения экземпляра WebDriverWait
     *
     * @return экземпляр WebDriverWait
     */
    @Bean
    public WebDriverWait getWebDriverWait() {
        long id = Thread.currentThread().getId();
        if (threadWebDriverWait.get(id) != null)
            return threadWebDriverWait.get(id);
        else
            throw new IllegalStateException("По данному threadID " + id + " не найден экземпляр WebDriverWait");
    }

    private static ConcurrentHashMap<Long, WebDriver> threadWebDriver = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<Long, WebDriverWait> threadWebDriverWait = new ConcurrentHashMap<>();

    public static int getTimeout() {
        return timeout;
    }

    private static int timeout;

    private static String Browser;
    private static String BrowserVersion;
    private static String Server;
    private static String OS;

    public static void setWebDriver() {
        GetConfig.setNameProperties("webdriver");
        try {
            Server = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        timeout = Integer.parseInt(GetConfig.getProperty("timeout"));
        String type = GetConfig.getProperty("driver");
        WebDriver webDriver = null;
        switch (type) {
            case "opera":
                System.setProperty("webdriver.opera.driver", DRIVERS_PATH + GetConfig.getProperty("opera.driver"));
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.setBinary("C:/Program Files/Opera/" +
                        GetConfig.getProperty("opera.version") + "/opera.exe");
                webDriver = new OperaDriver(operaOptions);
                threadWebDriver.put(Thread.currentThread().getId(), new OverriddenWebDriver(webDriver));
                threadWebDriverWait.put(Thread.currentThread().getId(), new WebDriverWait(webDriver, timeout));
                setEnvironment(webDriver);
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", DRIVERS_PATH + GetConfig.getProperty("ie.driver"));
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.setCapability(InternetExplorerDriver
                        .INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                webDriver = new InternetExplorerDriver(internetExplorerOptions);
                threadWebDriver.put(Thread.currentThread().getId(), new OverriddenWebDriver(webDriver));
                threadWebDriverWait.put(Thread.currentThread().getId(), new WebDriverWait(webDriver, timeout));
                setEnvironment(webDriver);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + GetConfig.getProperty("firefox.driver"));
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
                threadWebDriver.put(Thread.currentThread().getId(), new OverriddenWebDriver(webDriver));
                threadWebDriverWait.put(Thread.currentThread().getId(), new WebDriverWait(webDriver, timeout));
                setEnvironment(webDriver);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + GetConfig.getProperty("chrome.driver"));
                ChromeOptions chromeOptions = new ChromeOptions();
                webDriver = new ChromeDriver(chromeOptions);
                threadWebDriver.put(Thread.currentThread().getId(), new OverriddenWebDriver(webDriver));
                threadWebDriverWait.put(Thread.currentThread().getId(), new WebDriverWait(webDriver, timeout));
                setEnvironment(webDriver);
                break;
            default:
                System.out.println("Данный [" + type + "] драйвер не установлен! Доступны opera, ie, firefox, chrome");
                System.exit(0);
                break;
        }
        webDriver.manage().window().maximize();
    }

    /**
     * Записывает информацию только при первом вызове
     *
     * @param webDriver ВебДрайвер
     */
    private static void setEnvironment(WebDriver webDriver) {
        if (threadWebDriver.mappingCount() == 1) {
            Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
            Browser = capabilities.getBrowserName();
            BrowserVersion = capabilities.getVersion();
            OS = capabilities.getPlatform().getPartOfOsName()[0];
        }
    }

    static void writeEnvironment() {
        Properties properties = new Properties();
        properties.setProperty("Браузер", Browser);
        properties.setProperty("Версия Браузера", BrowserVersion);
        properties.setProperty("IPv4", Server);
        properties.setProperty("Операционная система", OS);
        File file = new File(System.getProperty("user.dir") + "/target/allure-results/"
                + PROPERTY_FILE);
        if (file.exists() && file.isFile()) file.delete();
        try {
            properties.store(new FileOutputStream(file), "Создание нового файла");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    static void closeDriver() {
        WebDriver webDriver = threadWebDriver.get(Thread.currentThread().getId());
        if (webDriver != null)
            webDriver.quit();
        threadWebDriver.remove(Thread.currentThread().getId());
    }
}
