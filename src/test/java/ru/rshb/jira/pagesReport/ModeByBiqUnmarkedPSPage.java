package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.jira.tests.ReportStages;

import java.util.List;

public class ModeByBiqUnmarkedPSPage extends ModePageMain {
    private List<WebElement> list;

    public ModeByBiqUnmarkedPSPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Гр. приоритизации
     */
    private String groupPrior = "//select[@id='grPrior']";

    /**
     * Статус
     */
    private String issueStatus = "//select[@id='issueStatusId']";

    /**
     *
     */
    @FindBy(id = "issueStatusId")
    private WebElement StatusId;
    private String StatusIdBy = "issueStatusId";
    /**
     *
     */
    @FindBy(id = "grPrior")
    private WebElement grPrior;
    private String grPriorBy = "grPrior";

    /**
     * Кнопка Выгрузить в Excel
     */
    @FindBy(id = "btnBiqUnmarkedPS")
    private WebElement exporttoExcel;

    /**
     * Гр. приоритизации: выбирает рандомно значение из селекта
     *
     * @return возвращает страницу ModeByBiqUnmarkedPSPage
     */
    @Step("Гр. приоритизации: выбираем рандомно значение из селекта")
    public ModeByBiqUnmarkedPSPage groupPrior() {
        list = getListWebElement(groupPrior);
        getSelect(grPrior).selectByVisibleText(list.get(random.nextInt(list.size())).getText());
        allurePriorandStatus(getSelect(grPrior).getFirstSelectedOption().getText());
        return this;
    }

    @Step("Значение: ")
    private void allurePriorandStatus(String option){
        System.out.println(option);
    }

    /**
     * Статус: выбирает рандомно значение из селекта
     *
     * @return возвращает страницу ModeByBiqUnmarkedPSPage
     */
    @Step("Статус: выбираем рандомно значение из селекта")
    public ModeByBiqUnmarkedPSPage issueStatusId() {
        list = getListWebElement(issueStatus);
        getSelect(StatusId).selectByVisibleText(list.get(random.nextInt(list.size())).getText());
        allurePriorandStatus(getSelect(StatusId).getFirstSelectedOption().getText());
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