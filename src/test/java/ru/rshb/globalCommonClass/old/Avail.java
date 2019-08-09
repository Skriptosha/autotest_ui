package ru.rshb.globalCommonClass.old;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class Avail {

    public static final int OLDPAGE = 0;
    public static final int NEWPAGE = 1;

    /**
     * Ожидание изчезновения элемента
     *
     * @param webElement   Элемент, если необходимо ожидание пока предыдущий элемент исчезнет из DOM
     * @param webDriver    Вебдрайвер
     * @param timeoutInSec Задержка в секундах для ожидания появления элемента
     */
    public static void stalenessElement(WebElement webElement, WebDriver webDriver, int timeoutInSec) {
        new WebDriverWait(webDriver, timeoutInSec).
                until(ExpectedConditions.stalenessOf(webElement));
    }

    /**
     * Расчитана на переключение между двумя окнами.
     *
     * @param baseWindowHandle Хандл базовой страницы
     * @param WhatPage         Текущую или новую страницу (OLDPAGE или NEWPAGE)
     * @param webDriver        Вебдрайвер
     */
    public static void availableNewPage(String baseWindowHandle, int WhatPage, WebDriver webDriver) {
        List<String> pageList = new ArrayList<>(webDriver.getWindowHandles());
        for (String handle : pageList) {
            if (!handle.equals(baseWindowHandle) && WhatPage == 1) webDriver.switchTo().window(handle);
        }
        if (WhatPage == 0) webDriver.switchTo().window(baseWindowHandle);
    }

    public static void availablePage(String url, WebDriver webDriver, int timeoutInSec) {
        new WebDriverWait(webDriver, timeoutInSec).until(ExpectedConditions.urlContains(url));
    }
}