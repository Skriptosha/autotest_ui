package ru.rshb.jira.pagesHandbook;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HandLeadershipPageHand extends HandBasePage {
    public HandLeadershipPageHand(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Селект Система
     */
    @FindBy(id = "systemId")
    private WebElement systemSelect;

    private String systemSelectBy = "//button[@data-id='systemId']";

    /**
     * Селект ЦК:
     */
    @FindBy(id = "competenceCenterId")
    private WebElement competenceCenterSelect;

    private String competenceCenterSelectBy = "//button[@data-id='competenceCenterId']";

    /**
     * Селект Руководитель ЦК:
     */
    @FindBy(id = "managerId")
    private WebElement managerSelect;

    private String managerSelectBy = "//button[@data-id='managerId']";

    /**
     * Селект Проект:
     */
    @FindBy(id = "projectId")
    private WebElement projectSelect;

    private String projectSelectBy = "//button[@data-id='projectId']";

    /**
     * Селект Отдел:
     */
    @FindBy(id = "departmentId")
    private WebElement departmentSelect;

    private String departmentSelectBy = "//button[@data-id='departmentId']";

    /**
     * Селект система
     *
     * @param system     Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Селект система")
    public HandLeadershipPageHand systemSelect(String system) {
        selectCombobox(systemSelectBy, system);
        return this;
    }

    /**
     * Селект ЦК:
     *
     * @param competenceCenter Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Селект ЦК")
    public HandLeadershipPageHand competenceCenterSelect(String competenceCenter) {
        selectCombobox(competenceCenterSelectBy, competenceCenter);
        return this;
    }

    /**
     * Селект Руководитель ЦК:
     *
     * @param manager    Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Селект Руководитель ЦК")
    public HandLeadershipPageHand managerSelect(String manager) {
        selectCombobox(managerSelectBy, manager);
        return this;
    }

    /**
     * Селект Проект:
     *
     * @param project    Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Селект Проект")
    public HandLeadershipPageHand projectSelect(String project) {
        selectCombobox(projectSelectBy, project);
        return this;
    }

    /**
     * Селект Отдел:
     *
     * @param department Значение поля из списка
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Селект Отдел")
    public HandLeadershipPageHand departmentSelect(String department) {
        selectCombobox(departmentSelectBy, department);
        return this;
    }
}