package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.jira.tests.ReportStages;

public class ModeByWorklogPage extends ModePageMain {

    public ModeByWorklogPage(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(xpath = "//input[@name='btnExcelWorklog']")
    private WebElement exporttoExcel;

    @Override
    @Step("Нажимаем кнопку Выгрузить в Excel")
    public ReportStages exporttoExcel() {
        try {
        exporttoExcel.click();
        } catch (TimeoutException e){
            Assert.fail("Превышено время ожидания скачивания!");
        }
        return new ReportStages();
    }
}