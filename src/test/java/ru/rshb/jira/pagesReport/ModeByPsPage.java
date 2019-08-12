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

public class ModeByPsPage extends ModePageMain {

    public ModeByPsPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(id = "btnPlanSmetaExcel")
    private WebElement exporttoExcel;

    /**
     * Элемент Таблица
     */
    private By table = By.xpath("//*[@id='biqTable']/tbody");

    /**
     * Получить Таблицу
     *
     * @return возвращает последний использованный элемент
     */
    @Override
    @Step("Получаем элемент myTable/tbody")
    public WebElement getTable() {
        return FindElement.findElement(table, webDriver);
    }

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