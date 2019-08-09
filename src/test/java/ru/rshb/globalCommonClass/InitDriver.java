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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private static final String PROPERTY_FILE = "environment.properties";

    @Autowired
    private GetConfig getConfig;

    /**
     * Геттер для получения экземпляра ВебДрайвера
     *
     * @return экземпляр Вебдрайвера
     */
    @Bean
    public WebDriver getWebDriver() {
        long id = Thread.currentThread().getId();
        if (thread_webDriver.get(id) != null)
            return thread_webDriver.get(id);
        else
            throw new IllegalStateException("По данному threadID " + id + " не найден экземпляр ВебДрайвера");
    }

    private static ConcurrentHashMap<Long, WebDriver> thread_webDriver = new ConcurrentHashMap<>();

    public static int getTimeout() {
        return timeout;
    }

    private static int timeout;

    private static String Browser;
    private static String BrowserVersion;
    private static String Server;
    private static String OS;

    @PostConstruct
    public void setWebDriver() {
        getConfig.setNameProperties("webdriver");
        try {
            Server = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        timeout = Integer.parseInt(getConfig.getProperty("timeout"));
        String type = getConfig.getProperty("driver");
        WebDriver webDriver = null;
        switch (type) {
            case "opera":
                System.setProperty("webdriver.opera.driver", DRIVERS_PATH + getConfig.getProperty("opera.driver"));
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.setBinary("C:/Program Files/Opera/" +
                        getConfig.getProperty("opera.version") + "/opera.exe");
                webDriver = new OverriddenWebDriver(new OperaDriver(operaOptions));
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
                setEnvironment(webDriver);
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", DRIVERS_PATH + getConfig.getProperty("ie.driver"));
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.setCapability(InternetExplorerDriver
                        .INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
                internetExplorerOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                webDriver = new OverriddenWebDriver(new InternetExplorerDriver(internetExplorerOptions));
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
                setEnvironment(webDriver);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + getConfig.getProperty("firefox.driver"));
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("browser.download.folderList", 2);
                firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, text/plain," +
                                " application/pdf, text/csv," +
                                " application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
                firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
                firefoxOptions.addPreference("browser.download.manager.closeWhenDone", true);
                webDriver = new OverriddenWebDriver(new FirefoxDriver(firefoxOptions));
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
                setEnvironment(webDriver);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + getConfig.getProperty("chrome.driver"));
                ChromeOptions chromeOptions = new ChromeOptions();
                webDriver = new OverriddenWebDriver(new ChromeDriver(chromeOptions));
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
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
    private void setEnvironment(WebDriver webDriver) {
        if (thread_webDriver.mappingCount() == 1) {
            Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
            Browser = capabilities.getBrowserName();
            BrowserVersion = capabilities.getVersion();
            OS = capabilities.getPlatform().getPartOfOsName()[0];
        }
    }

    void writeEnvironment() {
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

    void closeDriver() {
        WebDriver webDriver = thread_webDriver.get(Thread.currentThread().getId());
        if (webDriver != null)
            webDriver.quit();
        thread_webDriver.remove(Thread.currentThread().getId());
    }
}
