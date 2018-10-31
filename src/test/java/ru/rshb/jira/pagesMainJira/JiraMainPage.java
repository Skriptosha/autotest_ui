package ru.rshb.jira.pagesMainJira;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.*;

public class JiraMainPage {
    private WebDriver webDriver;
    public JiraMainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Меню Интех
     */
    private By intech = By.id("rshbMenu");

    /**
     * Кнопка Меню Интех - Отчеты
     */
    @FindBy(id = "rshbReports")
    private WebElement intechReports;

    /**
     * Кнопка Маркировка БИ к ГП
     */
    @FindBy(id = "markingBiq-link")
    private WebElement intechMarking;

    /**
     * Кнопка Профиль пользователя
     */
    @FindBy(id = "header-details-user-fullname")
    private WebElement profile;

    /**
     * Кнопка Профиль пользователя - выйти
     */
    @FindBy(id = "log_out")
    private WebElement profileLogout;

    /**
     * Меню Интех
     *
     * @return возвращает последний использованный элемент
     */
    void intech() {
        FindElement.findElement(intech, webDriver).click();
    }

    /**
     * Меню Интех - Отчеты
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Заходим в Меню Интех - Отчеты")
    public void intechReports() {
        FindElement.click(intech, null, webDriver).click();
        if (intechReports.isDisplayed()) intechReports.click();
        else {
            FindElement.findElement(intech, webDriver).click();
            intechReports.click();
        }
    }

    /**
     * Меню Интех - Маркировка БИ к ГП
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Заходим в Меню Интех - Маркировка БИ к ГП")
    public void intechMarking() {
        FindElement.click(intech, null, webDriver).click();
        if (intechMarking.isDisplayed()) intechMarking.click();
        else {
            FindElement.findElement(intech, webDriver).click();
            intechMarking.click();
        }
    }

    /**
     * Профиль пользователя
     *
     * @return возвращает последний использованный элемент
     */
    public void profile() {
        profile.click();
    }

    /**
     * Профиль пользователя - выйти
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Делаем Логаут")
    public void profileLogout() {
        profile.click();
        profileLogout.click();
    }
}