package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HandServiceSystemPageHand extends HandBasePage {
    public HandServiceSystemPageHand(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Система
     */
    private String systemSelectBy = "//button[@data-id='systemId']";

    /**
     * Кнопка Cервис
     */
    private String serviceSelectBy = "//button[@data-id='serviceId']";

    /**
     * Селект Cервис
     */
    @FindBy(id = "serviceId")
    private WebElement serviceSelect;

    /**
     * Селект система
     *
     * @param system     Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем cелект Cистема")
    public HandServiceSystemPageHand systemSelect(String system) {
        selectCombobox(systemSelectBy, system);
        return this;
    }

    /**
     * Выбери сервис
     *
     * @param service    Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем cелект Cервис")
    public HandServiceSystemPageHand serviceSelect(String service) {
        service = serviceSelect.findElement(By.xpath("//option[contains(text(),'" + service + "')]")).getText();
        selectCombobox(serviceSelectBy, service);
        return this;
    }
}