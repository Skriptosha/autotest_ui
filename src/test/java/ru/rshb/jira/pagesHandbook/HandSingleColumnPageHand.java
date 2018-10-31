package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.*;

public class HandSingleColumnPageHand extends HandBasePage {

    public HandSingleColumnPageHand(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Селект Система
     */
    @FindBy(id = "name")
    private WebElement nameFieldtoAdd;
    private String nameFieldtoAddBy = "//input[@id='name']";

    /**
     * Селект Система
     */
    @FindBy(id = "short_name")
    private WebElement shortNameFieldtoAdd;

    /**
     * Название поля в зависимости от Справочника
     *
     * @param nametext   название для строки в зависимости от Справочника
     * @return возвращает последний использованный элемент
     */
    @Step("Заполняем поле name")
    public HandSingleColumnPageHand nameFieldtoAdd(String nametext) {
        FindElement.click(By.xpath(nameFieldtoAddBy), null, webDriver);
        nameFieldtoAdd.clear();
        nameFieldtoAdd.sendKeys(nametext);
        return this;
    }

    /**
     * Поле Короткое наименование для Справочника Управления
     *
     * @param shortnametext название для строки в зависимости от Справочника
     * @return возвращает последний использованный элемент
     */
    @Step("Заполняем поле Короткое наименование")
    public HandSingleColumnPageHand shortNameFieldtoAdd(String shortnametext) {
        shortNameFieldtoAdd.clear();
        shortNameFieldtoAdd.sendKeys(shortnametext);
        return this;
    }
}