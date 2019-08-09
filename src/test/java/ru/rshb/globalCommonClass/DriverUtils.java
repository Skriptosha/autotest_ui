package ru.rshb.globalCommonClass;

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
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.rshb.globalCommonClass.old.FindElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Класс GlobalDriver содержит Junit4 @BeforeClass и @AfterClass
 * При создании класса теста необходимо просто наследовать его от GlobalDriver
 * Возможен параллельный запуск тестов, тесты будут выполняться только на одном Браузере.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConf.class})
public class DriverUtils {

    @Autowired
    private GetConfig getConfig;

    @Autowired
    private InitDriver initDriver;

    @Autowired
    private static SpringConf springConf;

    private static String HTTPStatus500;

    private static String SQLServerException;

    private static Integer downloadTime;

    public static Integer getDownloadTime() {
        return downloadTime;
    }

    /**
     * Сделать скриншот
     *
     * @return скриншот в byte[]
     */
    @Attachment(value = "Скриншот страницы", type = "image/png")
    private byte[] takeScreenshot() {
        WebDriver webDriver = initDriver.getWebDriver();
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
    public static void isDownload(String filename, WebDriver webDriver, String title) {
        int count = 0;
        System.out.println("Начало скачивания");
        long startTime = System.currentTimeMillis();

        //Проверяем скачался ли файл
        File file = new File(System.getProperty("user.home") + "/Downloads/"
                + filename);

        while (!file.exists() || !file.isFile()) {
            count++;
            Wait.waitpage(1);
            if (count == 5) {
                if (webDriver.getTitle().contains(HTTPStatus500)) {
                    pageValidation("Ошибка при скачивании файла " + filename + "! Ошибка: " + HTTPStatus500);
                } else if (!title.equals(webDriver.getTitle())) {
                    pageValidation("Ошибка при скачивании файла " + filename + "! Ошибка: " + webDriver.getTitle());
                }
            }
            if (count == getDownloadTime()) break;
        }

        System.out.println("count " + count);

        if (!file.exists() && !file.isFile()) {
            pageValidation("Ошибка при скачивании файла: Файл " + filename + " не скачался за " + count
                    + " секунд!");
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Время скачивания: " + (double) estimatedTime / 1000 + " секунд.");
        file.delete();
    }



    private void closePage() {
        initDriver.getWebDriver().close();
    }

    @BeforeClass
    public static void begin() {
    }

    @AfterClass
    public static void end() {
        InitDriver initDriver = springConf.getBean(InitDriver.class);
        initDriver.closeDriver();
        initDriver.writeEnvironment();
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
            Wait.waitpage(5);
        }
    };
}