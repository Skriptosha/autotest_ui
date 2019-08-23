package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.rshb.globalCommonClass.old.FindElement;
import ru.rshb.globalCommonClass.old.GlobalDriver;
import ru.rshb.jira.tests.ReportStages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModePageMain {

    WebDriver webDriver;
    WebElement firstSelectedOption = null;
    Random random = new Random();

    public ModePageMain(WebDriver webDriver) {
        if (!webDriver.getCurrentUrl().contains("reports/reports")) {
            GlobalDriver.pageValidation("Это не страница отчетов");
        }
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Предупреждающее сообщение
     */
    @FindBy(xpath = ".//div[@class='alert alert-block']")
    private WebElement alert;

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(id = "btnMonthSummary")
    private WebElement exporttoExcel;

    /**
     * Кнопка Выберите период: С
     */
    @FindBy(id = "startDate")
    private WebElement from;

    /**
     * нопка Выберите период: по
     */
    @FindBy(id = "endDate")
    private WebElement to;

    /**
     * Кнопка Выберите период: Выбор года
     */
    @FindBy(xpath = "//select[@data-handler='selectYear']")
    private WebElement year;
    private String yearBy = "//select[@data-handler='selectYear']";

    /**
     * Кнопка Выберите период: Выбор месяца
     */
    @FindBy(xpath = "//select[@data-handler='selectMonth']")
    private WebElement month;
    private String monthBy = "//select[@data-handler='selectMonth']";

    public String getMonth() {
        return monthBy;
    }

    /**
     * Элемент календарь
     */
    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']")
    private WebElement calendar;

    /**
     * Элемент Таблица
     */
    @FindBy(xpath = "//*[@id='myTable']/tbody")
    private WebElement table;

    /**
     * Элемент квадрат для галочки на строке в таблице
     */
    private String check = "//td[1]/div";

    /**
     * Текст строки в таблице
     */
    private String text = "//td[2]";

    /**
     * Кнопка Загрузить за все время
     */
    @FindBy(xpath = ".//button[@title='Загрузить за все время']")
    private WebElement alltime;

    /**
     * Все дочерние элементы tr
     */
    private String tableElement = "//following-sibling::tr";

    @FindBy(id = "ui-datepicker-div")
    private WebElement widgetContent;

    /**
     * Выгрузить в Excel
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем кнопку Выгрузить в Excel")
    public ReportStages exporttoExcel() {
        try {
            exporttoExcel.click();
        } catch (TimeoutException e) {
            Assert.fail("Превышено время ожидания скачивания!");
        }
        return new ReportStages();
    }

    /**
     * Предупреждающее сообщение
     *
     * @return Предупреждающее сообщение
     */
    @Step("Возвращаем Предупреждающее сообщение")
    public String getAlert() {
        return alert.getText();
    }

    /**
     * Выберите период: С (дата)
     */
    @Step("Выбираем период С в календаре")
    public void fromSelect(String number, int month, String year) {
        clickFrom();
        widgetSelect(number, month, year);
    }

    /**
     * Выберите период: По (дата)
     */
    @Step("Выбираем период По в календаре")
    public void toSelect(String number, int month, String year) {
        clickTo();
        widgetSelect(number, month, year);
    }

    public void clickFrom() {
            from.click();
    }

    public void clickTo() {
            to.click();
    }

    void widgetSelect(String number, int month, String year){
        if (year.length() == 4) selectByValue(this.yearBy, year);
        if (month < 3) selectByValue(this.monthBy, String.valueOf(month - 1));
        System.out.println("number " + number);
        if (number.length() < 3) calendar.findElement(By.xpath(".//a[contains(text(),'" + number + "')]")).click();
    }

    void selectByValue(String select, String value){
        FindElement.click(By.xpath(select), null, webDriver);
        FindElement.click(By.xpath("//option[@value='" + value + "']"),
                FindElement.findElement(By.xpath(select), webDriver)
                , webDriver);
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
     * Получить Таблицу
     *
     * @return возвращает Таблицу
     */
    @Step("Получаем элемент myTable/tbody")
    public WebElement getTable() {
        return table;
    }

    /**
     * Получить tr элементы таблицы table
     *
     * @return возвращает List<WebElement>
     */
    @Step("Получаем список элементов в таблице(myTable или biqTable)/tbody")
    public List<WebElement> getTableElement() {
        return getTable().findElements(By.xpath(tableElement));
    }

    /**
     * Выделить элемент
     */
    public void markline(WebElement element) {
        element.click();
        getnameline(element);
    }

    /**
     * Получить название строки элемента
     *
     * @param element элемент в таблице
     */
    public void getnameline(WebElement element) {
        allurenameline(element.getText());
    }

    /**
     * Добавить в отчет название строки элемента
     */
    @Step("Название строки выбранного элемента")
    public void allurenameline(String text) {
        System.out.println(text);
    }

    /**
     * Убрать выделение элемента. Вызывается после markline и после скачивания файла.
     */
    public void unmarkline(WebElement element) {
        element.click();
    }

    /**
     * С (дата)
     *
     * @return возвращает последний использованный элемент
     */
    public String from() {
        return from.getAttribute("value");
    }

    /**
     * По (дата)
     *
     * @return возвращает последний использованный элемент
     */
    public String to() {
        return to.getAttribute("value");
    }

    /**
     * Загрузить за все время
     */
    @Step("Нажимаем кнопку Загрузить за все время")
    public void alltime() {
        alltime.click();
    }

    /**
     * Получение List<WebElement> и WebElement firstSelectedOption
     *
     * @param xpath Строка X_PATH Селекта, по которому надо получить Лист элементов
     * @return возвращает последний использованный элемент
     */
    public List<WebElement> getListWebElement(String xpath) {
        Select select = getSelect(FindElement.findElement(By.xpath(xpath), webDriver));
        firstSelectedOption = select.getFirstSelectedOption();
        return new ArrayList<>(select.getOptions());
    }
}