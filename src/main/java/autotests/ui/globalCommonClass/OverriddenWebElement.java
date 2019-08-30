package autotests.ui.globalCommonClass;

import org.openqa.selenium.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Реализация для интерфейса WebElement. Используется для внедрения WebDriverWait в стандартные методы WebElement"а
 * Используется в классе OverriddenWebDriver, в методах findElements и findElement. Текущий обьект не меняется, а
 * всегда возращается новый.
 *
 */
public class OverriddenWebElement implements WebElement  {

    private WebElement webElement;

    private Exception exception;

    private Logger logger = Logger.getLogger("autotests");

    OverriddenWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public void click() {
        try {
            logger.info("Нажимаем на элемент ");
            OverriddenWebDriver.getWebDriverWait().until(webDriver -> {
                try {
                    webElement.click();
                } catch (Exception e) {
                    exception = e;
                    return false;
                }
                return true;
            });
        } catch (Exception e){
            try {
                throw exception;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Ошибка при нажатии на элемент: ", ex);
            }
        }
    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        logger.info("Печатаем текст " + Arrays.toString(charSequences));
        try {
            OverriddenWebDriver.getWebDriverWait().until(webDriver -> {
                try {
                    webElement.sendKeys(charSequences);
                } catch (Exception e) {
                    return false;
                }
                return true;
            });
        } catch (Exception e){
            try {
                throw exception;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Ошибка при вводе текста: ", ex);
            }
        }
    }

    @Override
    public void clear() {
        try {
            logger.info( "Очищаем форму");
            OverriddenWebDriver.getWebDriverWait().until(webDriver -> {
                try {
                    webElement.clear();
                } catch (Exception e) {
                    return false;
                }
                return true;
            });
        } catch (Exception e){
            try {
                throw exception;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Ошибка при очистке формы: ", ex);
            }
        }
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
        logger.info("Ищем элементы: " + by.toString());
        return OverriddenWebDriver.getWebDriverWait().until(webDriver -> webElement.findElements(by)).stream()
                .map(OverriddenWebElement::new)
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        logger.info("Ищем элемент: " + by.toString());
        return new OverriddenWebElement(OverriddenWebDriver.getWebDriverWait()
                .until(webDriver -> webElement.findElement(by)));
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
