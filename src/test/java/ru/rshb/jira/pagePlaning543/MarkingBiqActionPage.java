package ru.rshb.jira.pagePlaning543;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.rshb.globalCommonClass.old.FindElement;
import ru.rshb.globalCommonClass.old.GlobalDriver;

import java.util.ArrayList;
import java.util.List;

public class MarkingBiqActionPage {
    private WebDriver webDriver;

    public MarkingBiqActionPage(WebDriver webDriver) {
        if (!webDriver.getCurrentUrl().contains("MarkingBiqAction")) {
            GlobalDriver.pageValidation("Это не страница Маркировка БИ к ГП");
        }
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Вкладка Маркировка
     */
    @FindBy(id = "aui-uid-0")
    private WebElement marking;

    /**
     * Метка ГП
     */
    @FindBy(id = "markingBiq-label")
    private WebElement markGPtext;

    /**
     * Кнопка Промаркировать БИ из списка!
     */
    @FindBy(id = "markingBiq-label-add")
    private WebElement markGPadd;

    /**
     * JQL-запрос:
     */
    @FindBy(id = "markingBiq-filter-jql")
    private WebElement requestJQL;

    /**
     * Кнопка JQL-запрос: Поиск
     */
    @FindBy(id = "markingBiq-jql-requery")
    private WebElement requestJQLbutton;

    /**
     * Элемент Таблица
     */
    @FindBy(id = "markingBiq-table")
    private WebElement getTable;

    /**
     * Вкладка Списки
     */
    @FindBy(id = "aui-uid-1")
    private WebElement lists;

    /**
     * Селект Цикл приоритизации:
     */
    @FindBy(id = "select-cycle")
    private WebElement prioritizationList;

    /**
     * Список на ГП
     */
    @FindBy(id = "biq-list-for-cycle")
    private WebElement listGP;

    /**
     * Список новых БИ
     */
    @FindBy(id = "biq-list-for-cycle2")
    private WebElement listnewGP;

    /**
     * Предупреждающее сообщение
     */
    @FindBy(xpath = "//div[@id='info-message-bar']/div/p[2]")
    private WebElement warnmessage;

    /**
     * Сообщение Выбраны даты:
     */
    @FindBy(xpath = "//div[@id='info-message-bar2']/div/p")
    private WebElement infomessage;

    /**
     * Колличество столбцов с оглавлением таблицы
     */
    private By tableHead = By.xpath("//tbody/tr[1]/th");

    /**
     * Колличество столбцов
     */
    private By tableColumn = By.xpath("//tbody/tr[2]/td");

    /**
     * Колличество столбцов
     */
    private By tablelinegp = By.xpath("//td[6]");

    /**
     * Кнопка Фильтр:
     */
    private By markingBiqfilter = By.id("markingBiq-filter");

    /**
     * БИ, поступившие между: список 1 (от)
     */
    private By biListfrom = By.id("select-cycle-from");

    /**
     * БИ, поступившие между: список 2 (до)
     */
    private By biListto = By.id("select-cycle-to");

    /**
     * Вкладка Маркировка
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Открываем вкладку Маркировка")
    public MarkingBiqActionPage marking() {
        marking.click();
        return this;
    }

    /**
     * Если вкладка Маркировка открыта в данный момент, вернет true
     *
     * @return Возврашает true или false
     */
    public String markingisactive() {
        return marking.getAttribute("aria-selected");
    }

    /**
     * @return
     */
    public List<WebElement> tableHead() {
        return new ArrayList<>(getTable.findElements(tableHead));
    }

    /**
     * @return
     */
    public List<WebElement> tableColumn() {
        return new ArrayList<>(getTable.findElements(tableColumn));
    }

    /**
     * Фильтр:
     *
     * @param text Значение, выбирается по selectByVisibleText
     * @return
     */
    @Step("Выбираем значение в списке Фильтр:")
    public MarkingBiqActionPage filter(String text) {
        getSelect(FindElement.findElement(markingBiqfilter, webDriver)).selectByVisibleText(text);
        return this;
    }

    /**
     * Выбраны даты:
     *
     * @return Текст сообщения
     */
    @Step
    public String infomessage() {
        return infomessage.getText();
    }

    /**
     * @param text Значение селекта по selectByVisibleText
     * @return строка, параметр атрибута data-jql
     */
    public String getfromselect(String text, By idofElement, String attribute) {
        List<WebElement> list = getSelect(FindElement.findElement(idofElement, webDriver)).getOptions();
        for (WebElement webElement : list) {
            if (webElement.getText().contains(text)) return webElement.getAttribute(attribute);
        }
        return null;
    }

    /**
     * @param text Значение селекта по selectByVisibleText
     * @return строка, параметр атрибута data-jql
     */
    @Step("Получаем атрибут data-jql из фильтра Фильтр:")
    @Attachment(value = "атрибут data-jql")
    public String filtergetdatajql(String text) {
        return getfromselect(text, markingBiqfilter, "data-jql");
    }

    /**
     * @param text Значение селекта БИ, поступившие между: список 1 (от) (по selectByVisibleText)
     * @return строка, параметр атрибута data-datefrom
     */
    @Step("Получаем аттрибут data-datefrom из фильтра БИ, поступившие между: от")
    @Attachment(value = "атрибут data-datefrom")
    public String filtercyclefrom(String text) {
        return getfromselect(text, biListfrom, "data-datefrom");
    }

    /**
     * @param text Значение селекта по БИ, поступившие между: список 2 (до) (по selectByVisibleText)
     * @return строка, параметр атрибута data-datefrom
     */
    @Step("Получаем аттрибут data-dateto из фильтра БИ, поступившие между: до")
    @Attachment(value = "атрибут data-dateto")
    public String filtercycleto(String text) {
        return getfromselect(text, biListto, "data-dateto");
    }


    @Step("Получаем предупреждающее сообщение")
    @Attachment(value = "info-message-bar")
    public String warnmessage() {
        return warnmessage.getText();
    }

    /**
     *
     * @param webElement
     * @return
     */
    public String tablelinegp(WebElement webElement) {
        System.out.println("tablelinegp " + webElement.findElement(tablelinegp).getText());
        return webElement.findElement(tablelinegp).getText();
    }

    /**
     * Поле Метка ГП
     *
     * @param text Текст для заполнения поля Метка ГП:
     * @return возвращает последний использованный элемент
     */
    @Step("Заполняем поле Метка ГП")
    public MarkingBiqActionPage markGPtext(String text) {
        markGPtext.sendKeys(text);
        return this;
    }

    /**
     * Промаркировать БИ из списка!
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем Промаркировать БИ из списка!")
    public MarkingBiqActionPage markGPadd() {
        markGPadd.click();
        return this;
    }

    /**
     * JQL-запрос:
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Получаем JQL-запрос:")
    @Attachment(value = "JQL-запрос:")
    public String requestJQL() {
        return requestJQL.getText();
    }

    /**
     * JQL-запрос: Поиск
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем JQL-запрос: Поиск")
    public MarkingBiqActionPage requestJQLbutton() {
        requestJQLbutton.click();
        return this;
    }

    /**
     * Таблица: возвращаем вебэлемент таблицы
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Получаем Таблицу")
    public WebElement getTable() {
        return getTable;
    }

    /**
     * Вкладка Списки
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим на вкладка Списки")
    public MarkingBiqActionPage lists() {
        lists.click();
        return this;
    }

    /**
     * Цикл приоритизации:
     *
     * @param text Строка, которую необходимо выбрать в списке Цикл приоритизации
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим на вкладку Списки")
    public MarkingBiqActionPage prioritizationList(String text) {
        getSelect(prioritizationList).selectByVisibleText(text);
        return this;
    }

    /**
     * Список на ГП
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем на кнопку Список на ГП")
    public MarkingBiqActionPage listGP() {
        listGP.click();
        return this;
    }

    /**
     * БИ, поступившие между: список 1 (от)
     *
     * @param text Строка, которую необходимо выбрать в списке БИ, поступившие между: от
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем текст в списке БИ, поступившие между: список 1 (от)")
    public MarkingBiqActionPage biListfrom(String text) {
        getSelect(FindElement.findElement(biListfrom, webDriver)).selectByVisibleText(text);
        return this;
    }

    /**
     * БИ, поступившие между: список 2 (до)
     *
     * @param text Строка, которую необходимо выбрать в списке БИ, поступившие между: до
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем текст в списке БИ, поступившие между: список 2 (до)")
    public MarkingBiqActionPage biListto(String text) {
        getSelect(FindElement.findElement(biListto, webDriver)).selectByVisibleText(text);
        return this;
    }

    /**
     * Список новых БИ
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем кнопку Список новых БИ")
    public MarkingBiqActionPage listnewGP() {
        listnewGP.click();
        return this;
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
}