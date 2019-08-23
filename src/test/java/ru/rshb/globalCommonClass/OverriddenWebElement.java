package ru.rshb.globalCommonClass;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class OverriddenWebElement implements WebElement {

    private WebElement webElement;

    private WebDriverWait webDriverWait;

    OverriddenWebElement(WebElement webElement, WebDriverWait webDriverWait) {
        this.webElement = webElement;
        this.webDriverWait = webDriverWait;
    }

    @Override
    public void click() {
        webDriverWait.until(webDriver -> {webElement.click(); return true;});
    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        webDriverWait.until(webDriver -> {webElement.sendKeys(charSequences); return true;});
    }

    @Override
    public void clear() {
        webDriverWait.until(webDriver -> {webElement.clear(); return true;});
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return webElement.getAttribute(s);
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webDriverWait.until(webDriver -> webElement.findElements(by)).stream().map(webElement ->
                new OverriddenWebElement(webElement, webDriverWait))
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        return new OverriddenWebElement(webDriverWait.until(webDriver -> webElement.findElement(by)), webDriverWait);
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return webElement.getRect();
    }

    @Override
    public String getCssValue(String s) {
        return webElement.getCssValue(s);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return webElement.getScreenshotAs(outputType);
    }
}
