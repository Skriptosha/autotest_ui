package autotests.ui.globalCommonClass;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.Inet4Address;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс содержит Junit4 @BeforeClass и @AfterClass а так же @Rule TestWatcher
 * При создании класса теста необходимо просто наследовать его от данного класса
 * Для формирования данных для отчета Allure запускать через mvn test
 */

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConf.class})
public class DriverUtils {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Environment environment;

    @Autowired
    private Logger logger;

    /*
     ********************************* Аннотации Allure 2 *********************************
     */

    /**
     * Сделать скриншот
     *
     * @return скриншот в byte[]
     */
    @Attachment(value = "Скриншот страницы", type = "image/png")
    public byte[] takeScreenshot() {
        logger.info("Делаем скриншот");
        if (webDriver == null) {
            logger.severe("При попытке сделать скриншот webDriver == null");
            return null;
        } else return ((TakesScreenshot) ((OverriddenWebDriver) webDriver).getOriginalWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Step("Проверяем страницу")
    public static void pageValidation(String message) {
        Assert.fail(message);
    }

    /**
     * Записывает информацию Environment для отчет Allure 2 через properties
     * Копирует файл categories.json из корня проекта в папку target/allure-results
     *
     * @param driver ВебДрайвер
     */
    public void setEnvironment(WebDriver driver) {
        logger.info("Записываем информацию Environment и Categories для отчета Allure 2");
        WebDriver webDriver = driver;
        if (!driver.getClass().equals(WebDriver.class))
            webDriver = ((OverriddenWebDriver) driver).getOriginalWebDriver();
        logger.info("Успешно получен WebDriver");
        Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
        try {
            Properties properties = new Properties();
            properties.setProperty("Браузер", capabilities.getBrowserName());
            properties.setProperty("Версия Браузера", capabilities.getVersion());
            properties.setProperty("IPv4", Inet4Address.getLocalHost().getHostAddress());
            properties.setProperty("Операционная система", capabilities.getPlatform().name());

            Path path = Paths.get(environment.getProperty("ALLURE_RESULT_PATH")
                    + environment.getProperty("PROPERTY_FILE_PATH"));
            logger.info("path: " + path);

            Files.deleteIfExists(path);

            Files.createDirectories(Paths.get(Objects.requireNonNull(environment.getProperty("ALLURE_RESULT_PATH"))));

            properties.store(Files.newBufferedWriter(path, StandardCharsets.UTF_8)
                    , "Environment для отчета Allure 2");
            logger.fine("Файл environment.properties успешно сохранен");

            Files.copy(Paths.get(Objects.requireNonNull(environment.getProperty("CATEGORY_FILE_PATH")))
                    , Paths.get(environment.getProperty("ALLURE_RESULT_PATH")
                            + environment.getProperty("CATEGORY_FILE_PATH")),
                    StandardCopyOption.REPLACE_EXISTING);
            logger.fine("Файл categories.json успешно скопирован");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка при создании или копировании файла: ", e);
        }
    }


    /*
     ********************************* Скачивание файла *********************************
     */

    /**
     * Проверка скачивания файла, проверяет в директории User home directory/Downloads
     *
     * @param filename  Имя файла с расширением
     * @param webDriver экземпляр ВебДрайвера
     * @param title     Титл страницы, до нажатия кнопки скачивания
     */
    public void isDownload(String filename, WebDriver webDriver, String title) throws IOException {
        int count = 0;
        logger.info("Начало скачивания файла " + filename);

        long startTime = System.currentTimeMillis();

        //Проверяем скачался ли файл
        Path path = Paths.get(System.getProperty("user.home") + "/Downloads/" + filename);

        while (!Files.isReadable(path)) {
            count++;
            Wait.waitPage(0.5);
            if (count == Integer.parseInt(Objects.requireNonNull(environment.getProperty("timeToDownload")))) break;
        }

        if (!Files.isReadable(path)) {
            pageValidation("Ошибка при скачивании файла: Файл " + filename + " не скачался за " + count
                    + " секунд!");
        }

        long estimatedTime = System.currentTimeMillis() - startTime;

        logger.info("Время скачивания: " + (double) estimatedTime / 1000 + " секунд.");

        Files.deleteIfExists(path);
    }

    /*
     ********************************* Вспомогательные методы *********************************
     */

    /**
     * Переход на нужный сайт, модержит javascript для Internet Explorer'a, при ошибке сертификата на странице
     *
     * @param urlKey URL адрес сайта
     */
    @Step("Переходим на сайт")
    public void getURL(String urlKey) {
        logger.info("Переход на сайт: " + urlKey);
        webDriver.navigate().to(urlKey);
        if (webDriver.getTitle().contains(Objects.requireNonNull(environment.getProperty("ieTittle")))) {
            logger.info("Сайт " + urlKey + " содержит ошибку сертификата");
            webDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
        }
    }

    /**
     * Проверка на нужной ли странице находимся
     *
     * @param page Название страницы, которая должна открыться
     */
    public void waitCorrectPage(String page) {
        int count = 0;
        double wait = 0.5;
        logger.info("Проверка адреса сайта");
        while (!webDriver.getCurrentUrl().contains(page) && count <
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("timeout"))) / wait) {
            Wait.waitPage(wait);
            count++;
        }
        if (!webDriver.getCurrentUrl().contains(page)) {
            logger.log(Level.SEVERE, "Некорректная страница! " + webDriver.getCurrentUrl());
            throw new IllegalStateException("Некорректная страница! " + webDriver.getCurrentUrl());
        }
    }

    /**
     * @param message Строка, в которую надо добавить arg
     * @param arg     Строка, аргумент
     * @return Строка с подставленным аргументов в message
     */
    public String formatString(String message, String arg) {
        return message.replace("%d", arg);
    }

    /*
     ********************************* Аннотации JUnit4 *********************************
     */

    @BeforeClass
    public static void begin() {
    }

    @AfterClass
    public static void end() {
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
            logger.info("Junit 4 finished");
            if (webDriver != null) {
                setEnvironment(webDriver);
                webDriver.quit();
            } else logger.severe("webDriver == null!");
        }

        @Override
        protected void failed(Throwable t, Description description) {
            logger.info("Junit 4 failed: " + description.getMethodName());
            takeScreenshot();
        }
    };
}