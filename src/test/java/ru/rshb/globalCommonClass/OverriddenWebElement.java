package ru.rshb.globalCommonClass;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OverriddenWebElement implements WebElement {

    private WebElement webElement;

    @Autowired
    private WebDriverWait webDriverWait;

    @Autowired
    private OverriddenWebElement overriddenWebElement;

    public WebElement getOverriddenWebElement(WebElement webElement){
        this.webElement = webElement;
        return this;
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
                overriddenWebElement.getOverriddenWebElement(webElement))
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        return this.getOverriddenWebElement(webDriverWait.until(webDriver -> webElement.findElement(by)));
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
