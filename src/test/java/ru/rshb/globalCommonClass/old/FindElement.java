package ru.rshb.globalCommonClass.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rshb.globalCommonClass.DriverUtils;
import ru.rshb.globalCommonClass.InitDriver;

import javax.annotation.ParametersAreNonnullByDefault;

public class FindElement extends DriverUtils {
    public static WebElement findElement(By by, WebDriver webDriver) {

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, InitDriver.getTimeout());
        final WebElement[] webElement = new WebElement[1];

//        availableJS(webDriver, getTimeout());

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));

        webDriverWait.until(new ExpectedCondition<Boolean>() {
            @ParametersAreNonnullByDefault
            public Boolean apply(WebDriver webDriver) {
                try {
                    webElement[0] = webDriver.findElement(by);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });
        return webElement[0];
    }

    /**
     * Аналог webElement.click();
     *
     * @param by               Идентификатор типа By
     * @param nestedSearch Элемент, в котором необходимо осуществить поиск, если нет такой необходимости
     *                         то nestedSearch должен быть равен null
     * @param webDriver Экземпляр ВебДрайвера
     * @return возвращает последний использованный элемент
     */
    public static WebElement click(final By by, final WebElement nestedSearch, WebDriver webDriver) {
        final WebElement[] webElement = new WebElement[1];
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, InitDriver.getTimeout());

        webDriverWait.until(new ExpectedCondition<Boolean>() {
            @ParametersAreNonnullByDefault
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (nestedSearch != null) {
                        webElement[0] = nestedSearch.findElement(by);
                        webElement[0].click();
                    } else {
                        webElement[0] = webDriver.findElement(by);
                        webElement[0].click();
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });

        return webElement[0];
    }

    /**
     * Аналог webElement.sendKeys(keys);
     *
     * @param by               Идентификатор типа By
     * @param keys             строка которую необходимо передать
     * @param nestedSearch Элемент, в котором необходимо осуществить поиск, если нет такой необходимости
     *                         то nestedSearch должен быть равен null
     * @return возвращает последний использованный элемент
     */
    public static WebElement SendKeys(final By by, final String keys, final WebElement nestedSearch,
                                      WebDriver webDriver) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, InitDriver.getTimeout());

        final WebElement[] webElement = new WebElement[1];

        webDriverWait.until(new ExpectedCondition<Boolean>() {
            @ParametersAreNonnullByDefault
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (nestedSearch != null) {
                        webElement[0] = nestedSearch.findElement(by);
                        webElement[0].sendKeys(keys);
                    } else {
                        webElement[0] = webDriver.findElement(by);
                        webElement[0].sendKeys(keys);
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        });
        return webElement[0];
    }

    /**
     * Ожидание выполняния jQuery на странице
     *
     * @param webDriver    Вебдрайвер
     * @param timeoutInSec задержка в секундах для ожидания появления элемента
     * @return возвращает true
     */
    static Boolean availableJS(WebDriver webDriver, int timeoutInSec) {
        final JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) webDriver);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeoutInSec);

        Boolean wait;
        wait = webDriverWait.until(new ExpectedCondition<Boolean>() {
            @ParametersAreNonnullByDefault
            public Boolean apply(WebDriver webDriver) {
                try {
                    return ((Long) javascriptExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    System.out.println("no jQuery present");
                    return true;
                }
            }
        });
        return wait;
    }
}