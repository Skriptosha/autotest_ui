package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.rshb.globalCommonClass.old.FindElement;
import ru.rshb.globalCommonClass.old.GlobalDriver;

import java.util.List;

public class HandBasePage extends GlobalDriver {
    WebDriver webDriver;

    public HandBasePage(WebDriver webDriver) {
        if (!webDriver.getCurrentUrl().contains("reports")) {
            GlobalDriver.pageValidation("Это не страница Справочников");
        }
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Элемент таблица
     */
    @FindBy(id = "myTable")
    private WebElement table;

    /**
     * Окно Удалить или Добавить (myModal)
     */
    @FindBy(id = "myModal")
    private WebElement modal;

    /**
     * Строка поиска
     */
    @FindBy(id = "myInput")
    private WebElement search;

    private String searchBy = "myInput";

    /**
     * Кнопка Добавить
     */
    @FindBy(xpath = "//a[@class='btn btn-default btn-smallk glyphicon glyphicon-plus' and @data-toggle='modal']")
    private WebElement add;

    /**
     * Кнопка редактировать
     */
    private String edit = ".//a[@class='btn btn-primary btn-small glyphicon glyphicon-pencil'"
            + " and @data-toggle='modal']";

    /**
     * Кнопка удалить
     */
    private String delete = ".//a[@class='btn btn-danger btn-small glyphicon glyphicon-remove'" +
            " and @data-toggle='modal']";

    /**
     * Строка поиска в dropdown-menu open
     */
    private String searchBox = "(//input[@aria-label='Search'])[last()]";

    /**
     * Кнопка сохранить
     */
    @FindBy(id = "saveDict")
    private WebElement save;

    private String saveBy = "saveDict";

    /**
     * Получаем все стоки в таблице
     */
    private String allLines = ".//tbody/tr";

    /**
     * Сохранить изменения
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем Сохранить изменения")
    public void save() {
        save.click();
    }

    /**
     * Нажать кнопку Добавить
     *
     * @return данная страница
     */
    @Step("Нажимаем кнопку Добавить")
    public void add() {
        add.click();
    }

    /**
     * Получить Вебэлемент(строку) в таблице по названию
     *
     * @param nameLine Строка по которой необходимо найти элемент
     * @return возвращает Вебэлемент
     */
    @Step("Ищем Вебэлемент(строку) в таблице по названию")
    public WebElement getLine(String nameLine) {
        try {
            return FindElement.findElement(By.xpath("//div[contains(text(),'"
                    + nameLine + "')]/.."), webDriver);
        } catch (TimeoutException exception) {
            Assert.fail("Строка " + nameLine + " не найдена!");
            throw new IllegalStateException("Строка " + nameLine + " не найдена!");
        }
    }

    /**
     * Получить все Вебэлементы(строки) в таблице по названию
     *
     * @param nameLine Строка по которой необходимо найти элемент
     * @return возвращает Вебэлемент
     */
    @Step("Ищем Вебэлементы(строки) в таблице по названию")
    public List<WebElement> getLines(String nameLine) {
        return table.findElements(By.xpath("//div[contains(text(),'"
                + nameLine + "')]/.."));
    }

    /**
     * Получить текст секции в строке
     *
     * @param line    Вебэлемент (строка)
     * @param section int секции, откуда необходимо вернуть текст
     * @return возвращает текст секции
     */
    public String getTextfromLine(WebElement line, int section) {
        return line.findElement(By.xpath(".//div[" + String.valueOf(section) + "]")).getText();
    }

    /**
     * Редактирование элемента
     *
     * @param nameLine Строка по котрой необходимо найти элемент для редактирования
     * @return возвращает страницу NewRecordPageHand()
     */
    @Step("Нажимаем значок Редактирование элемента")
    public void editLine(String nameLine) {
        WebElement webElement = getLine(nameLine);
        new Actions(webDriver).moveToElement(webElement).perform();
        FindElement.click(By.xpath(edit), webElement, webDriver);
    }

    /**
     * Удаление элемента
     *
     * @param nameLine Строка по которой необходимо найти элемент для удаления
     */
    @Step("Нажимаем значок Удаление элемента")
    public void deleteLine(String nameLine) {
        WebElement webElement = getLine(nameLine);
        new Actions(webDriver).moveToElement(webElement).perform();
        FindElement.click(By.xpath(delete), webElement, webDriver);
        FindElement.click(By.id(saveBy), modal, webDriver);
        System.out.println("modal.isDisplayed() " + modal.isDisplayed());
        while (modal.isDisplayed()) {
            System.out.println("deleteLine цикл");
            FindElement.click(By.id(saveBy), modal, webDriver);
        }
    }

    /**
     * Получить объект селект по ВебЭлементу
     *
     * @param webElement элемент, если необходимо ожидание пока предыдущий элемент исчезнет из DOM
     * @return возвращает последний использованный элемент
     */
    Select getSelect(WebElement webElement) {
        Select select = new Select(webElement);
        return select;
    }

    /**
     * Выбор элемента в окне Добавить
     *
     * @param selectBy Строка - Выберите значение - (xPath)
     * @param value    Значение, которое необходимо выбрать в этой строке (VisibleText)
     */
    void selectCombobox(String selectBy, String value) {
        FindElement.click(By.xpath(selectBy), modal, webDriver);
        FindElement.SendKeys(By.xpath(searchBox), value, modal, webDriver);
        FindElement.click(By.xpath("(//span[contains(text(), '" + value + "')])[last()]"), modal, webDriver);
    }

    /**
     * Получить Таблицу
     *
     * @return возвращает последний использованный элемент
     */
    public WebElement getTable() {
        return table;
    }

    /**
     * Получить Таблицу
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Получаем все элементы таблицы")
    public List<WebElement> getAllLinesTable() {
        return table.findElements(By.xpath(allLines));
    }

    /**
     * Поиск
     *
     * @param stringtosearch Строка для поиска в таблице
     * @return возвращает последний использованный элемент
     */
    @Step("Поиск")
    public HandBasePage search(String stringtosearch) {
        FindElement.click(By.id(searchBy), null, webDriver);
        FindElement.findElement(By.id(searchBy), webDriver).clear();
        FindElement.findElement(By.id(searchBy), webDriver).sendKeys(stringtosearch);
        return this;
    }
}