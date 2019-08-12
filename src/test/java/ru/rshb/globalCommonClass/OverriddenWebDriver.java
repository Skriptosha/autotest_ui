package ru.rshb.globalCommonClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OverriddenWebDriver implements WebDriver {

    private WebDriver webDriver;

    @Autowired
    private WebDriverWait webDriverWait;

    @Autowired
    private OverriddenWebElement overriddenWebElement;

    public OverriddenWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver getOriginalWebDriver() {
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
                        overriddenWebElement.getOverriddenWebElement(webElement))
                .collect(Collectors.toList()));
    }

    @Override
    public WebElement findElement(By by) {
        return overriddenWebElement.getOverriddenWebElement(webDriverWait
                .until(webDriver -> webDriver.findElement(by)));
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
}
