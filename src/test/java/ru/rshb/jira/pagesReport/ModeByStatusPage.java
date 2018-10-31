package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.jira.ReportStages;

public class ModeByStatusPage extends ModePageMain {

    public ModeByStatusPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Элемент Строка для поиска
     */
    @FindBy(id = "myInput")
    private WebElement myinput;

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(xpath = "//input[@name='btnExcelStatus']")
    private WebElement exporttoExcel;

    /**
     * Поиск
     *
     * @param stringtosearch Строка для поиска в таблице
     * @return возвращает последний использованный элемент
     */
    @Step("Ищем строку stringtosearch")
    public ModeByStatusPage search(String stringtosearch) {
        myinput.clear();
        myinput.sendKeys(stringtosearch);
        return this;
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