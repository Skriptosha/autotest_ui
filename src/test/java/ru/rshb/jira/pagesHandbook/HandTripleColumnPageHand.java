package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.old.FindElement;

public class HandTripleColumnPageHand extends HandBasePage {

    public HandTripleColumnPageHand(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Селект Статус БИ
     */
    @FindBy(id = "issueStatusId")
    private WebElement handbookStatusBIStage;

    private String handbookStatusBIStageBy = "//button[@data-id='issueStatusId']";

    /**
     * Поле Название статуса БИ
     */
    @FindBy(id = "issueStatusNameID")
    private WebElement nameBIStatus;

    private String nameBIStatusBy = "issueStatusNameID";

    /**
     * Селект Обьединенный статус БИ
     */
    @FindBy(id = "statusGroupCode")
    private WebElement handbookstatusGroupCode;

    private String handbookstatusGroupCodeBy = "//button[@data-id='statusGroupCode']";

    /**
     * Селект для справочника Управление
     */
    @FindBy(id = "upravlenieId")
    private WebElement handbookStatusManagement;

    private String handbookStatusManagementBy = "//button[@data-id='upravlenieId']";

    /**
     * Статус БИ
     *
     * @param StatusBI   Параметр, который необходимо выбрать в Статус БИ
     * @return ввозвращает последний использованный элемент
     */
    @Step("Выбираем селект Статус БИ")
    public HandTripleColumnPageHand handbookStatusBIStage(String StatusBI) {
        selectCombobox(handbookStatusBIStageBy, StatusBI);
        return this;
    }

    /**
     * Название статуса БИ
     *
     * @param nameBItext название для строки Название статуса БИ
     * @return возвращает последний использованный элемент
     */
    @Step("Вводим название статуса БИ")
    public HandTripleColumnPageHand nameBIStatus(String nameBItext) {
        FindElement.click(By.id(nameBIStatusBy), null, webDriver);
        nameBIStatus.clear();
        nameBIStatus.sendKeys(nameBItext);
        return this;
    }

    /**
     * Обьединенный статус БИ
     *
     * @param GroupCodeBI Параметр, который необходимо выбрать в Обьединенный статус БИ
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем селект Обьединенный статус БИ")
    public HandTripleColumnPageHand handbookstatusGroupCode(String GroupCodeBI) {
        selectCombobox(handbookstatusGroupCodeBy, GroupCodeBI);
        return this;
    }

    /**
     * Управление (Селект для справочника Управление)
     *
     * @param management Управление, которое необходимо выбрать
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем селект Управление")
    public HandTripleColumnPageHand handbookStatusManagement(String management) {
        selectCombobox(handbookStatusManagementBy, management);
        return this;
    }
}