package autotests.ui.jira.pagesMainJira;

import autotests.ui.globalCommonClass.old.FindElement;
import autotests.ui.jira.pagesHandbook.*;
import autotests.ui.jira.pagesReport.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsPage {
    WebDriver webDriver;

    public ReportsPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    /**
     * Кнопка Отчеты
     */
    @FindBy(xpath = "//*[contains(text(),'Отчеты') and @class='dropdown-toggle']")
    private WebElement reports;

    /**
     * Имя пользователя
     */
//    @FindBy(xpath = "//span[@id='remote-user-fullname']")
    private By remoteUsername = By.xpath("//span[@id='remote-user-fullname']");

    /**
     *
     */
    @FindBy(id = "modeByBiqUnmarkedPS")
    private WebElement reportmodeByBiqUnmarkedPS;

    /**
     *
     */
    @FindBy(id = "modeByBiq")
    private WebElement reportmodeByBiq;

    /**
     *
     */
    @FindBy(id = "modeByIncPriority")
    private WebElement reportmodeByIncPriority;

    /**
     * Отчеты - По статусам
     */
    @FindBy(id = "modeByStatus")
    private WebElement reportmodeByStatus;
    private String reportmodeByStatusBy = "modeByStatus";

    /**
     *
     */
    @FindBy(id = "modeByWorklog")
    private WebElement reportmodeByWorklog;

    /**
     *
     */
    @FindBy(id = "modeByMonthSummary")
    private WebElement reportmodeByMonthSummary;

    /**
     * Отчеты - Статистика по BIQ
     */
    @FindBy(id = "modeByStatisticBIQ")
    private WebElement reportmodeByStatisticBIQ;
    private String reportmodeByStatisticBIQBy = "modeByStatisticBIQ";

    /**
     * Отчеты - Учет рабочего времени
     */
    @FindBy(id = "modeByPs")
    private WebElement reportmodeByPs;
    private String reportmodeByPsBy = "modeByPs";

    /**
     * Справочники
     */
    @FindBy(xpath = "//*[contains(text(),'Справочники') and @class='dropdown-toggle']")
    private WebElement handbook;

    /**
     * Выход
     */
    @FindBy(xpath = "//span[@title='Выход']")
    private WebElement logout;

    /**
     * Имя пользователя который авторизовался
     *
     * @return возвращает Имя пользователя который авторизовался
     */
    public String remoteUsername() {
        return FindElement.findElement(remoteUsername, webDriver).getText();
//        return remoteUsername.getText();
    }

    /**
     * Текст из меню Отчеты
     *
     * @return возвращает Текст меню Отчеты - выбранный пункт
     */
    public String getTextreportMenu() {
        reports.click();
        return reportmodeByMonthSummary.getText();
    }

    /**
     * Отчеты - Отчет по неосмеченным BIQ
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - Отчет по неосмеченным BIQ")
    public ModeByBiqUnmarkedPSPage reportmodeByBiqUnmarkedPS() {
        reports.click();
        reportmodeByBiqUnmarkedPS.click();
        return new ModeByBiqUnmarkedPSPage(webDriver);
    }

    /**
     * Отчеты - По BIQ-ам
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - Отчеты - По BIQ-ам")
    public ModeByBiqPage reportmodeByBiq() {
        reports.click();
        reportmodeByBiq.click();
        return new ModeByBiqPage(webDriver);
    }

    /**
     * Отчеты - По повышениям приоритетов
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - По повышениям приоритетов")
    public ModeByIncPriorityPage reportmodeByIncPriority() {
        reports.click();
        reportmodeByIncPriority.click();
        return new ModeByIncPriorityPage(webDriver);
    }

    /**
     * Отчеты - По статусам
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - По статусам")
    public ModeByStatusPage reportmodeByStatus() {
        reports.click();
        FindElement.click(By.id(reportmodeByStatusBy), null, webDriver);
        return new ModeByStatusPage(webDriver);
    }

    /**
     * Отчеты - По трудозатратам
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - По трудозатратам")
    public ModeByWorklogPage reportmodeByWorklog() {
        reports.click();
        reportmodeByWorklog.click();
        return new ModeByWorklogPage(webDriver);
    }

    /**
     * Отчеты - Сводный по т/з за месяц
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - Сводный по т/з за месяц")
    public ModeByMonthSummaryPage reportmodeByMonthSummary() {
        reports.click();
        reportmodeByMonthSummary.click();
        return new ModeByMonthSummaryPage(webDriver);
    }

    /**
     * Отчеты - Статистика по BIQ
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - Статистика по BIQ")
    public ModeByStatisticBIQPage reportmodeByStatisticBIQ() {
        reports.click();
        FindElement.click(By.id(reportmodeByStatisticBIQBy), null, webDriver);
        return new ModeByStatisticBIQPage(webDriver);
    }

    /**
     * Отчеты - Учет рабочего времени
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Переходим в меню Отчеты - Учет рабочего времени")
    public ModeByPsPage reportmodeByPs() {
        reports.click();
        FindElement.click(By.id(reportmodeByPsBy), null, webDriver);
        return new ModeByPsPage(webDriver);
    }

    @FindBy(id = "modeByDepartment")
    private WebElement handbookDepartment;

    @FindBy(id = "modeByDoljnost")
    private WebElement handbookPosition;

    @FindBy(id = "modeByAccessMenuItems")
    private WebElement handbookAccessMenu;

    @FindBy(id = "modeByDict")
    private WebElement handbookITservice;

    @FindBy(id = "modeByLocation")
    private WebElement handbookLocation;

    @FindBy(id = "modeByCCM")
    private WebElement handbookCCM;

    @FindBy(id = "modeByServisSystem")
    private WebElement handbookServiceSystem;

    @FindBy(id = "modeByEmployee")
    private WebElement handbookEmployee;

    @FindBy(id = "modeByStatusBILifeCycle")
    private WebElement handbookStatusBILifeCycle;

    @FindBy(id = "modeByStatusBIPhase")
    private WebElement handbookStatusBIPhase;

    @FindBy(id = "modeByStatusBIStage")
    private WebElement handbookStatusBIStage;

    @FindBy(id = "modeByUpravlenie")
    private WebElement handbookManagement;

    /**
     * Справочники - Департаменты
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Департаменты")
    public HandTripleColumnPageHand handbookDepartment() {
        handbook.click();
        handbookDepartment.click();
        return new HandTripleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Должности
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Должности")
    public HandSingleColumnPageHand handbookPosition() {
        handbook.click();
        handbookPosition.click();
        return new HandSingleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Доступ к меню
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем правочники - Доступ к меню")
    public HandAccessMenuPageHand handbookAccessMenu() {
        handbook.click();
        handbookAccessMenu.click();
        return new HandAccessMenuPageHand(webDriver);
    }

    /**
     * Справочники - ИТ сервис
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - ИТ сервис")
    public HandSingleColumnPageHand handbookITservice() {
        handbook.click();
        handbookITservice.click();
        return new HandSingleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Локация
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Локация")
    public HandSingleColumnPageHand handbookLocation() {
        handbook.click();
        handbookLocation.click();
        return new HandSingleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Руководители ЦК
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Руководители ЦК")
    public HandLeadershipPageHand handbookCCM() {
        handbook.click();
        handbookCCM.click();
        return new HandLeadershipPageHand(webDriver);
    }

    /**
     * Справочники - Сервис - Система
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Сервис - Система")
    public HandServiceSystemPageHand handbookServiceSystem() {
        handbook.click();
        handbookServiceSystem.click();
        return new HandServiceSystemPageHand(webDriver);
    }

    /**
     * Справочники - Сотрудники
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Сотрудники")
    public HandEmployeePageHand handbookEmployee() {
        handbook.click();
        handbookEmployee.click();
        return new HandEmployeePageHand(webDriver);
    }

    /**
     * Справочники - Статусы БИ по жизненному циклу
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Статусы БИ по жизненному циклу")
    public HandTripleColumnPageHand handbookStatusBILifeCycle() {
        handbook.click();
        handbookStatusBILifeCycle.click();
        return new HandTripleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Статусы БИ по стадиям реализации
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Статусы БИ по стадиям реализации")
    public HandTripleColumnPageHand handbookStatusBIPhase() {
        handbook.click();
        handbookStatusBIPhase.click();
        return new HandTripleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Статусы БИ по этапам
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Статусы БИ по этапам")
    public HandTripleColumnPageHand handbookStatusBIStage() {
        handbook.click();
        handbookStatusBIStage.click();
        return new HandTripleColumnPageHand(webDriver);
    }

    /**
     * Справочники - Управления
     *
     * @return возвращает последний использованный элемент
     */
    @Step("Выбираем Справочники - Управления")
    public HandSingleColumnPageHand handbookManagement() {
        handbook.click();
        handbookManagement.click();
        return new HandSingleColumnPageHand(webDriver);
    }

    /**
     * Выход
     *
     * @param webElement элемент, если необходимо ожидание пока предыдущий элемент исчезнет из DOM
     * @return возвращает последний использованный элемент
     */
    @Step("Выход")
    public void logout(WebElement webElement) {
        logout.click();
    }
}