package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.*;

import java.util.List;

public class ModeByMonthSummaryPage extends ModePageMain {


    public ModeByMonthSummaryPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Кнопка Выберите период: налево
     */
    @FindBy(xpath = ".//a[@onclick='yearOperation(-1)']/span")
    private WebElement periodleft;

    /**
     * Кнопка Выберите период: направо
     */
    @FindBy(xpath = ".//a[@onclick='yearOperation(1)']/span")
    private WebElement periodright;

    /**
     * Кнопка Полный вид
     */
    @FindBy(xpath = ".//input[@value='full']")
    private WebElement fullview;

    /**
     * Кнопка Свернутый вид
     */
    @FindBy(xpath = ".//input[@value='socr']")
    private WebElement foldedview;

    /**
     * Кнопка Сводный отчет
     */
    @FindBy(xpath = ".//input[@value='svod']")
    private WebElement summaryreport;

    /**
     * Кнопка По план-сметам и BIQ
     */
    @FindBy(xpath = ".//input[@value='allocRes']")
    private WebElement BIQreport;

    /**
     * Кнопка Полный вид(пропорционально)
     */
    @FindBy(xpath = ".//input[@value='fullParent']")
    private WebElement fullviewproportional;

    /**
     * Кнопка Свернутый вид(пропорционально)
     */
    @FindBy(xpath = ".//input[@value='socrParent']")
    private WebElement foldedviewproportional;

    /**
     * Кнопка По план-сметам и BIQ (пропорционально)
     */
    @FindBy(xpath = ".//input[@value='parent']")
    private WebElement BIQreportproportional;

    /**
     * Кнопка Полный вид+ (по проектам)
     */
    @FindBy(xpath = ".//input[@value='temp']")
    private WebElement fullviewproject;

    /**
     * Кнопка По план-сметам и BIQ с выводом закрытых План-смет (пропорционально)
     */
    @FindBy(xpath = ".//input[@value='parentViewClosePs']")
    private WebElement parentViewClosePs;

    /**
     * Кнопка Отчет рентабельности
     */
    @FindBy(xpath = ".//input[@value='parentRent']")
    private WebElement parentRent;

    /**
     * Кнопка Контроль списания
     */
    @FindBy(xpath = ".//input[@value='timesheetControl']")
    private WebElement timesheetControl;

    /**
     * Селект Управление
     */
    @FindBy(id = "upravlenieId")
    private WebElement upravlenie;
    private String upravlenieBy = "//select[@id='upravlenieId']";

    /**
     * Селект Департамент
     */
    @FindBy(id = "departmentId")
    private WebElement department;
    private String departmentBy = "//select[@id='departmentId']";

    /**
     * Выбор для селекта - Выберите значение -
     */
    private String allValues = "- Выберите значение -";

    /**
     * Выберите период: налево
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Нажимаем стрелку налево в выборе периода")
    public ModeByMonthSummaryPage periodleft() {
         periodleft.click();
         return this;
    }

    /**
     * Выберите период: направо
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Нажимаем стрелку направо в выборе периода")
    public ModeByMonthSummaryPage periodright() {
        periodright.click();
        return this;
    }

    /**
     * Месяц
     *
     * @param monthnumber номер месяца (Например Янв - 1)
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем месяц")
    public ModeByMonthSummaryPage month(String monthnumber) {
        monthnumber = String.valueOf((Integer.parseInt(monthnumber)-1));
        FindElement.findElement(By.xpath(".//*[@data-id='" + monthnumber +"']"), webDriver).click();
        return this;
    }

    /**
     * Полный вид
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид")
    public ModeByMonthSummaryPage fullview() {
        fullview.click();
        return this;
    }

    /**
     * Свернутый вид
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Свернутый вид")
    public ModeByMonthSummaryPage foldedview() {
        foldedview.click();
        return this;
    }

    /**
     * Сводный отчет
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Сводный отчет")
    public ModeByMonthSummaryPage summaryreport() {
        summaryreport.click();
        return this;
    }

    /**
     * По план-сметам и BIQ
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет По план-сметам и BIQ")
    public ModeByMonthSummaryPage BIQreport() {
        BIQreport.click();
        return this;
    }

    /**
     * Полный вид(пропорционально)
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид(пропорционально)")
    public ModeByMonthSummaryPage fullviewproportional() {
        fullviewproportional.click();
        return this;
    }

    /**
     * Свернутый вид(пропорционально)
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Свернутый вид(пропорционально)")
    public ModeByMonthSummaryPage foldedviewproportional() {
        foldedviewproportional.click();
        return this;
    }

    /**
     * По план-сметам и BIQ (пропорционально)
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет По план-сметам и BIQ (пропорционально)")
    public ModeByMonthSummaryPage BIQreportproportional() {
        BIQreportproportional.click();
        return this;
    }

    /**
     * Полный вид+ (по проектам)
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид+ (по проектам)")
    public ModeByMonthSummaryPage fullviewproject() {
        fullviewproject.click();
        return this;
    }

    /**
     * По план-сметам и BIQ с выводом закрытых План-смет (пропорционально)
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид+ (по проектам)")
    public ModeByMonthSummaryPage parentViewClosePs() {
        parentViewClosePs.click();
        return this;
    }

    /**
     * Отчет рентабельности
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид+ (по проектам)")
    public ModeByMonthSummaryPage parentRent() {
        parentRent.click();
        return this;
    }

    /**
     * Контроль списания
     *
     * @return возвращает страницу ModeByMonthSummaryPage
     */
    @Step("Выбираем отчет Полный вид+ (по проектам)")
    public ModeByMonthSummaryPage timesheetControl() {
        timesheetControl.click();
        return this;
    }

    /**
     * Управление: выбирает рандомно значение из селекта
     *
     * @return возвращает страницу ModeByBiqUnmarkedPSPage
     */
    @Step("Управление: выбирает рандомно значение из селекта")
    public ModeByMonthSummaryPage upravlenie() {
        List<WebElement> list = getListWebElement(upravlenieBy);
        if (random.nextInt(2) == 1) getSelect(upravlenie).selectByVisibleText(allValues);
        else getSelect(upravlenie).selectByVisibleText(list.get(random.nextInt(list.size())).getText());
        allurePriorandStatus(getSelect(upravlenie).getFirstSelectedOption().getText());
        return this;
    }

    @Step("Значение: ")
    private void allurePriorandStatus(String option){
        System.out.println(option);
    }

    /**
     * Департамент: выбирает рандомно значение из селекта
     *
     * @return возвращает страницу ModeByBiqUnmarkedPSPage
     */
    @Step("Департамент: выбирает рандомно значение из селекта")
    public ModeByMonthSummaryPage department() {
        List<WebElement> list = getListWebElement(departmentBy);
        if (random.nextInt(2) == 1) getSelect(department).selectByVisibleText(allValues);
        else getSelect(department).selectByVisibleText(list.get(random.nextInt(list.size())).getText());
        allurePriorandStatus(getSelect(department).getFirstSelectedOption().getText());
        return this;
    }
}