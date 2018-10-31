package ru.rshb.jira;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import ru.rshb.globalCommonClass.*;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;

@Epic("Тест Отчетов Jira")
public class ReportTest extends GlobalDriver {
    private WebDriver webDriver;
    private ReportStages reportStages;

    @Before
    public void authorization() {
        webDriver = GlobalDriver.getWebDriver();
        reportStages = new ReportStages();

        new HelpClass().authReportsAndHandbooks(webDriver);

        GetConfig.setNameProperties("jirareport");
    }

    //    @Before
    public void authorization2() {
        GetConfig.setNameProperties("jiraglobal");
        new AuthorizationPage(webDriver)
                .geturl(GetConfig.getProperty("urlreport"));
        Avail.availablePage(GetConfig.getProperty("urlhost"), webDriver, getTimeout());
        GetConfig.setNameProperties("jirareport");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - По BIQ-ам")
    public void reportTest2() {
        /**
         * Тест Отчеты - По BIQ-ам
         */
        reportStages.stagereportmodeByBiq();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - По статусам")
    public void reportTest3() {
        /**
         * Отчеты - По статусам
         */
        reportStages.stagereportmodeByStatus();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - По трудозатратам")
    public void reportTest4() {
        /**
         * Отчеты - По трудозатратам
         */
        reportStages.stagemodeByWorklog();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - Учет рабочего времени")
    public void reportTest6() {
        /**
         * Отчеты - Учет рабочего времени
         */
        reportStages.stagemodeByPsPage();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Сводный по т/з за месяц")
    public void reportTest1() {
        /**
         * Тест Сводный по т/з за месяц
         */
        reportStages.stagereportmodeByMonthSummary();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - Статистика по BIQ")
    public void reportTest5() {
        /**
         * Отчеты - Статистика по BIQ
         */
        reportStages.stagemodeByStatisticBIQ();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчеты - По повышениям приоритетов")
    public void reportTest7() {

        /**
         * Отчеты - По повышениям приоритетов
         */
        reportStages.stagemodeByIncPriority();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест отчета Отчет по неосмеченным BIQ")
    public void reportTest8() {
        /**
         * Отчет по неосмеченным BIQ
         */
        reportStages.stagebiqUnmarkedPS();
    }
}