package autotests.ui.globalCommonClass.old;

import autotests.ui.globalCommonClass.Wait;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Возможен параллельный запуск тестов, тесты будут выполняться только на одном Браузере.
 */
public class GlobalDriver {

    private static final String PROPERTY_FILE = "environment.properties";
    private static final String DRIVERS_PATH = "./drivers/";

    private static GetConfig getConfig;

    private static String HTTPStatus500;

    private static String SQLServerException;

    /**
     * Геттер для получения экземпляра ВебДрайвера
     *
     * @return экземпляр Вебдрайвера
     */
    public static WebDriver getWebDriver() {
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

    public static void setWebDriver() {
        GetConfig.setNameProperties("webdriver");
        //Титл страницы при проверке страницы
        HTTPStatus500 = GetConfig.getProperty("HTTPStatus500");
        //Ошибка на странице HTTPStatus500
        SQLServerException = GetConfig.getProperty("SQLServerException");
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
                        GetConfig.getProperty("opera.path") + "/opera.exe");
                webDriver = new OperaDriver(operaOptions);
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
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
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
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
                thread_webDriver.put(Thread.currentThread().getId(), webDriver);
                setEnvironment(webDriver);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + GetConfig.getProperty("chrome.driver"));
                ChromeOptions chromeOptions = new ChromeOptions();
                webDriver = new ChromeDriver(chromeOptions);
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
     * Сделать скриншот
     *
     * @return скриншот в byte[]
     */
    @Attachment(value = "Скриншот страницы", type = "image/png")
    private static byte[] takeScreenshot() {
        WebDriver webDriver = getWebDriver();
        if (webDriver == null) {
            System.out.println("При попытке сделать скриншот webDriver == null");
            return null;
        }

        if (webDriver.getTitle().contains(HTTPStatus500)) {
            WebElement webElement = FindElement.findElement(By.xpath("/html/body/pre[3]"), webDriver);
            if (webElement.getText().contains(SQLServerException)) {
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
            }
        }
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Записывает информацию только при первом вызове
     *
     * @param webDriver ВебДрайвер
     */
    private static void setEnvironment(WebDriver webDriver) {
        if (thread_webDriver.mappingCount() == 1) {
            Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
            Browser = capabilities.getBrowserName();
            BrowserVersion = capabilities.getVersion();
            OS = capabilities.getPlatform().getPartOfOsName()[0];
        }
    }

    private static void writeEnvironment() {
        Properties properties = new Properties();
        properties.setProperty("Браузер", Browser);
        properties.setProperty("Версия Браузера", BrowserVersion);
        properties.setProperty("IPv4", Server);
        properties.setProperty("Операционная система", OS);
        File file = new File(System.getProperty("user.dir") + "/target/allure-results/"
                + PROPERTY_FILE);
        FileOutputStream fileOutputStream = null;
        if (file.exists() && file.isFile()) file.delete();
        try {
            fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "Создание нового файла");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Необходимо доработать, категории не отображаются
     */
    private static void copyCategories() {
        File src = new File(System.getProperty("user.dir") + "/categories.json");
        File dst = new File(System.getProperty("user.dir") + "/target/allure-results/categories.json");
        File dst2 = new File(System.getProperty("user.dir") + "/allure-results/categories.json");
        try {
            Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(src.toPath(), dst2.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Проверяем страницу")
    public static void pageValidation(String message) {
        Assert.fail(message);
    }

    /**
     * Проверка скачивания файла, проверяет в директории User home directory/Downloads
     *
     * @param filename  Имя файла с расширением
     * @param webDriver экземпляр ВебДрайвера
     * @param title     Титл страницы, до нажатия кнопки скачивания
     */
    public static void isdownload(String filename, WebDriver webDriver, String title) {
        int count = 0;
        System.out.println("Начало скачивания");
        long starttime = System.currentTimeMillis();

        //Проверяем скачался ли файл
        File file = new File(System.getProperty("user.home") + "/Downloads/"
                + filename);

        while (!file.exists() || !file.isFile()) {
            count++;
            Wait.waitPage(1);
            if (count == 5) {
                if (webDriver.getTitle().contains(HTTPStatus500)) {
                    pageValidation("Ошибка при скачивании файла " + filename + "! Ошибка: " + HTTPStatus500);
                } else if (!title.equals(webDriver.getTitle())) {
                    pageValidation("Ошибка при скачивании файла " + filename + "! Ошибка: " + webDriver.getTitle());
                }
            }
        }

        System.out.println("count " + count);

        if (!file.exists() && !file.isFile()) {
            pageValidation("Ошибка при скачивании файла: Файл " + filename + " не скачался за " + count
                    + " секунд!");
        }
        long estimatedTime = System.currentTimeMillis() - starttime;
        System.out.println("Время скачивания: " + (double) estimatedTime / 1000 + " секунд.");
        file.delete();
    }

    private static void closeDriver() {
        WebDriver webDriver = thread_webDriver.get(Thread.currentThread().getId());
        if (webDriver != null)
            webDriver.quit();
        thread_webDriver.remove(Thread.currentThread().getId());
    }

    private void closePage() {
       getWebDriver().close();
    }

    @BeforeClass
    public static void begin() {
        setWebDriver();
    }

    @AfterClass
    public static void end() {
        closeDriver();
        writeEnvironment();
        copyCategories();
    }

    @Rule
    public TestRule testWatcher = new TestWatcher() {

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void starting(Description description) {
        }

        @Override
        protected void finished(Description description) {
            closePage();
        }

        @Override
        protected void failed(Throwable t, Description description) {
            takeScreenshot();
            Wait.waitPage(5);
        }
    };
}