package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.*;

public class HandEmployeePageHand extends HandBasePage {
    public HandEmployeePageHand(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Поле Табельный №:
     */
    @FindBy(id = "tabn")
    private WebElement pernumberField;

    private String pernumberFieldBy = "tabn";

    /**
     * Поле ФИО:
     */
    @FindBy(id = "fio")
    private WebElement fioField;

    private String fioFieldBy = "fio";

    /**
     * Селект Должность:
     */
    @FindBy(id = "doljnostId")
    private WebElement positionSelect;

    private String positionSelectBy = "//button[@data-id='doljnostId']";

    /**
     * Селект Локация:
     */
    @FindBy(id = "locationId")
    private WebElement locationSelect;

    private String locationSelectBy = "//button[@data-id='locationId']";

    /**
     * Селект Управление:
     */
    @FindBy(id = "upravlenieId")
    private WebElement managementSelect;

    private String managementSelectBy = "//button[@data-id='upravlenieId']";

    /**
     * Селект Отдел:
     */
    @FindBy(id = "departmentId")
    private WebElement departmentSelect;

    private String departmentSelectBy = "//button[@data-id='departmentId']";

    /**
     * Селект Пользователь JIRA:
     */
    @FindBy(id = "managerId")
    private WebElement managerSelect;

    private String managerSelectBy = "//button[@data-id='managerId']";

    /**
     * Вывод в отчете
     */
    @FindBy(xpath = "//*[@id='isShowReport']//..")
    private WebElement reportOutput;

    /**
     *
     * @param attribute
     * @return
     */
    public String pernumberFieldreadonly(String attribute){
        return FindElement.findElement(By.id(pernumberFieldBy), webDriver).getAttribute(attribute);
    }
    /**
     * Табельный №:
     *
     * @param nametext   текст для поля Табельный №
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем табельный №:")
    public HandEmployeePageHand pernumberField(String nametext) {
        FindElement.click(By.id(pernumberFieldBy), null, webDriver);
        pernumberField.clear();
        pernumberField.sendKeys(nametext);
        return this;
    }

    /**
     * ФИО:
     *
     * @param nametext   текст для поля ФИО
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем ФИО:")
    public HandEmployeePageHand fioField(String nametext) {
        FindElement.click(By.id(fioFieldBy), null, webDriver);
        fioField.clear();
        fioField.sendKeys(nametext);
        return this;
    }

    /**
     * Должность:
     *
     * @param position   Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Должность:")
    public HandEmployeePageHand positionSelect(String position) {
        selectCombobox(positionSelectBy, position);
        return this;
    }

    /**
     * Локация:
     *
     * @param location   Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Локация:")
    public HandEmployeePageHand locationSelect(String location) {
        selectCombobox(locationSelectBy, location);
        return this;
    }

    /**
     * Управление:
     *
     * @param management Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Управление:")
    public HandEmployeePageHand managementSelect(String management) {
        selectCombobox(managementSelectBy, management);
        return this;
    }

    /**
     * Отдел:
     *
     * @param department Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Отдел:")
    public HandEmployeePageHand departmentSelect(String department) {
        selectCombobox(departmentSelectBy, department);
        return this;
    }

    /**
     * Пользователь JIRA:
     *
     * @param manager    Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Пользователь JIRA:")
    public HandEmployeePageHand managerSelect(String manager) {
        selectCombobox(managerSelectBy, manager);
        return this;
    }

    /**
     * Вывод в отчете
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Вывод в отчете")
    public HandEmployeePageHand reportOutput() {
        reportOutput.click();
        return this;
    }
}