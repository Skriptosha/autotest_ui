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
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

/**
 * Класс GlobalDriver содержит Junit4 @BeforeClass и @AfterClass
 * При создании класса теста необходимо просто наследовать его от GlobalDriver
 * Возможен параллельный запуск тестов, тесты будут выполняться только на одном Браузере.
 */

@Component
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {SpringConf.class})
public class DriverUtils {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private SpringConf springConf;

    @Autowired
    private Environment environment;

    public String formatString(String message, String arg){
        return message.replace("%d", arg);
    }


    /**
     * Сделать скриншот
     *
     * @return скриншот в byte[]
     */
    @Attachment(value = "Скриншот страницы", type = "image/png")
    private byte[] takeScreenshots() {
        if (webDriver == null) {
            System.out.println("При попытке сделать скриншот webDriver == null");
            return null;
        } else return ((TakesScreenshot) ((OverriddenWebDriver)webDriver).getOriginalWebDriver())
                .getScreenshotAs(OutputType.BYTES);
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
    public void isDownload(String filename, WebDriver webDriver, String title) {
        int count = 0;
        System.out.println("Начало скачивания");
        long startTime = System.currentTimeMillis();

        //Проверяем скачался ли файл
        File file = new File(System.getProperty("user.home") + "/Downloads/"
                + filename);

        while (!file.exists() || !file.isFile()) {
            count++;
            Wait.waitpage(1);
            if (count == Integer.parseInt(Objects.requireNonNull(environment.getProperty("timeToDownload")))) break;
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
        webDriver.close();
    }

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
            closePage();
        }

        @Override
        protected void failed(Throwable t, Description description) {
            takeScreenshots();
            Wait.waitpage(3);
        }
    };
}