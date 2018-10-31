package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.jira.ReportStages;

public class ModeByIncPriorityPage extends ModePageMain {

    public ModeByIncPriorityPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(id = "btnIncPriority")
    private WebElement exporttoExcel;

    /**
     * Кнопка Выберите год: нажать левую стрелку
     */
    @FindBy(xpath = ".//span[@class='glyphicon glyphicon-chevron-left']")
    private WebElement left;

    /**
     * Кнопка Выберите год: нажать правую стрелку
     */
    @FindBy(xpath = ".//span[@class='glyphicon glyphicon-chevron-right']")
    private WebElement right;

    /**
     * Выберите год: нажать левую стрелку
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем левую стрелку(Выберите год:)")
    public ModeByIncPriorityPage left() {
        left.click();
        return this;
    }

    /**
     * Выберите год: нажать правую стрелку
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем правую стрелку(Выберите год:)")
    public ModeByIncPriorityPage right() {
        right.click();
        return this;
    }

    @Override
    @Step("Нажимаем кнопку Выгрузить в Excel")
    public ReportStages exporttoExcel() {
        try {
            exporttoExcel.click();
        } catch (
                TimeoutException e) {
            Assert.fail("Превышено время ожидания скачивания!");
        }
        return new ReportStages();
    }
}