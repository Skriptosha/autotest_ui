package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.old.FindElement;
import ru.rshb.jira.tests.ReportStages;

public class ModeByStatisticBIQPage extends ModePageMain {

    public ModeByStatisticBIQPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Радиобаттон Портфель активных БИ по статусам
     */
    @FindBy(xpath = ".//input[@value='stage']")
    private WebElement stages;

    /**
     * Радиобаттон Движение БИ по жизненному циклу
     */
    @FindBy(xpath = ".//input[@value='life_cycle']")
    private WebElement lifecycle;

    /**
     * Радиобаттон Портфель активных БИ по ОЦК
     */
    @FindBy(xpath = ".//input[@value='phase_ock']")
    private WebElement phaseock;

    /**
     *  Радиобаттон Портфель активных БИ по подразделениям
     */
    @FindBy(xpath = ".//input[@value='phase_dept']")
    private WebElement phasedept;

    /**
     * Радиобаттон Динамика реализации БИ
     */
    @FindBy(xpath = ".//input[@value='dynamic_impl']")
    private WebElement dynamicimpl;

    /**
     * Радиобаттон Динамика реализации БИ по ОЦК
     */
    @FindBy(xpath = ".//input[@value='dynamic_impl_ock']")
    private WebElement dynamicimplock;

    /**
     * Радиобаттон Движение БИ по ЖЦ (только поступившие)
     */
    @FindBy(xpath = ".//input[@value='life_cycle_only_from_new']")
    private WebElement lifecycleonlyfromnew;

    /**
     * Список при выборе радиобаттонов: Портфель активных БИ по ОЦК; Портфель активных БИ по подразделениям
     */
    @FindBy(id = "statusGroupCode")
    private WebElement statusGroupCode;

    /**
     * Радиобаттон Динамика реализации БИ (только поступившие)
     */
    @FindBy(xpath = ".//input[@value='dynamic_impl_only_from_new']")
    private WebElement dynamicimplonlyfromnew;

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(id = "btnBiqStatistic")
    private WebElement exporttoExcel;
    private String exporttoExcelBy = "btnBiqStatistic";

    /**
     * Радиобаттон Просроченные оценки
     */
    @FindBy(xpath = ".//input[@value='overdue_tasks']")
    private WebElement overduetasks;

    /**
     * Радиобаттон Менеджеры задач БИ
     */
    @FindBy(xpath = ".//input[@value='biqsByManager']")
    private WebElement biqsByManager;

    /**
     * Портфель активных БИ по статусам + пусто
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Портфель активных БИ по статусам")
    public ModeByStatisticBIQPage stages() {
        stages.click();
        return this;
    }

    /**
     * Движение БИ по жизненному циклу + fromSelect - toSelect
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Движение БИ по жизненному циклу")
    public ModeByStatisticBIQPage lifecycle() {
        lifecycle.click();
        return this;
    }

    /**
     * Портфель активных БИ по ОЦК + statusGroupCode
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Портфель активных БИ по ОЦК")
    public ModeByStatisticBIQPage phaseock() {
        phaseock.click();
        return this;
    }

    /**
     * Портфель активных БИ по подразделениям + statusGroupCode
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Портфель активных БИ по подразделениям")
    public ModeByStatisticBIQPage phasedept() {
        phasedept.click();
        return this;
    }

    /**
     * Список при выборе радиобаттонов: Портфель активных БИ по ОЦК; Портфель активных БИ по подразделениям
     *
     * @param value Строка, значение, которое необходимо выбрать в списке. Возможно либо 1 либо 2
     */
    @Step("Выбираем В стадии реализации(1) или До стадии реализации(2)")
    public void statusGroupCode(String value) {
        getSelect(statusGroupCode).selectByValue(value);
    }

    /**
     * Динамика реализации БИ + fromSelect - toSelect
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Динамика реализации БИ")
    public ModeByStatisticBIQPage dynamicimpl() {
        dynamicimpl.click();
        return this;
    }

    /**
     * Динамика реализации БИ по ОЦК + fromSelect - toSelect
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Динамика реализации БИ по ОЦК")
    public ModeByStatisticBIQPage dynamicimplock() {
        dynamicimplock.click();
        return this;
    }

    /**
     * Движение БИ по ЖЦ (только поступившие) + fromSelect - toSelect
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Движение БИ по ЖЦ (только поступившие)")
    public ModeByStatisticBIQPage lifecycleonlyfromnew() {
        lifecycleonlyfromnew.click();
        return this;
    }

    /**
     * Динамика реализации БИ (только поступившие) + fromSelect - toSelect
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Динамика реализации БИ (только поступившие)")
    public ModeByStatisticBIQPage dynamicimplonlyfromnew() {
        dynamicimplonlyfromnew.click();
        return this;
    }

    @Override
    @Step("Нажимаем кнопку Выгрузить в Excel")
    public ReportStages exporttoExcel() {
        try {
            FindElement.click(By.xpath(exporttoExcelBy), null, webDriver);
        } catch (TimeoutException e){
            Assert.fail("Превышено время ожидания скачивания!");
        }
        return new ReportStages();
    }

    /**
     * Просроченные оценки
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Просроченные оценки")
    public ModeByStatisticBIQPage overduetasks() {
        overduetasks.click();
        return this;
    }

    /**
     * Менеджеры задач БИ
     *
     * @return возвращает страницу ModeByStatisticBIQPage
     */
    @Step("Выбираем Менеджеры задач БИ")
    public ModeByStatisticBIQPage biqsByManager() {
        biqsByManager.click();
        return this;
    }
}