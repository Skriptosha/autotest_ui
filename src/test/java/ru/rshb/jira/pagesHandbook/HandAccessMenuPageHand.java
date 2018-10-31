package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.*;

public class HandAccessMenuPageHand extends HandBasePage {

    public HandAccessMenuPageHand(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Селект Пункт меню
     */
    @FindBy(id = "menuId")
    private WebElement menuOptionSelect;

    /**
     * Кнопка Пункт меню
     */
    private String menuOptionSelectBy = "//button[@data-id='menuId']";

    /**
     * Поле Подпункт меню
     */
    @FindBy(id = "name")
    private WebElement menuitemField;

    /**
     * Поле Подпункт меню
     */
    private String menuitemFieldBy = "//input[@id='name']";

    /**
     * Селект Уровень доступа
     */
    @FindBy(id = "accessLevelId")
    private WebElement accessLevelSelect;

    /**
     * Кнопка Уровень доступа
     */
    private String accessLevelSelectBy = "//button[@data-id='accessLevelId']";

    /**
     * Чекбокс Видимость
     */
    @FindBy(xpath = "//*[@id='visible']/..")
    private WebElement visibility;

    /**
     * Пункт меню
     *
     * @param menuOption Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Пункт меню")
    HandAccessMenuPageHand menuOptionSelect(String menuOption) {
        selectCombobox(menuOptionSelectBy, menuOption);
        return this;
    }

    /**
     * Подпункт меню
     *
     * @param menuitem   текст для поля Подпункт меню
     * @return возвращает последний использованный элемент
     */
    @Step("Вводим Подпункт меню")
    public HandAccessMenuPageHand menuitemField(String menuitem) {
        FindElement.click(By.xpath(menuitemFieldBy), null, webDriver);
        menuitemField.clear();
        menuitemField.sendKeys(menuitem);
        return this;
    }

    /**
     * Уровень доступа
     *
     * @param accessLevel Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Уровень доступа")
    HandAccessMenuPageHand accessLevelSelect(String accessLevel) {
        selectCombobox(accessLevelSelectBy, accessLevel);
        return this;
    }

    /**
     * Видимость
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем на переключатель Видимость")
    HandAccessMenuPageHand visibility() {
        visibility.click();
        return this;
    }
}