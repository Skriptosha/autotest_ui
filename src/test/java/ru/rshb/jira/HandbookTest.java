package ru.rshb.jira;

import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import ru.rshb.globalCommonClass.*;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;
import ru.rshb.jira.pagesMainJira.ReportsPage;

@Epic("Тест Справочников Jira")
public class HandbookTest extends GlobalDriver {
    private WebDriver webDriver;
    private ReportsPage reportsPage;
    private HandbookStages handbookStages;

    @Before
    public void authorization() {
        webDriver = GlobalDriver.getWebDriver();
        reportsPage = new ReportsPage(webDriver);
        handbookStages = new HandbookStages();

        new HelpClass().authReportsAndHandbooks(webDriver);

        GetConfig.setNameProperties("jirahandbook");
    }

    //    @Before
    public void authorization2() {
        GetConfig.setNameProperties("jiraglobal");
        new AuthorizationPage(webDriver)
                .geturl(GetConfig.getProperty("urlreport"));
        Avail.availablePage(GetConfig.getProperty("urlhost"), webDriver, getTimeout());
        GetConfig.setNameProperties("jirahandbook");
    }


    @Test
    @DisplayName("Справочники - Сервис - Система")
    public void handbookTest1() {
        /**
         * Тест
         * Справочники - Сервис - Система
         */
        reportsPage.handbookServiceSystem();
        handbookStages.stage1ServiceSystem();
        handbookStages.stage3ServiceSystem();
        handbookStages.stage04(GetConfig.getProperty("systemnew"));
    }

    @Test
    @DisplayName("Справочники - ИТ сервис")
    public void handbookTest10() {
        /**
         * Тест
         * Справочники - ИТ сервис
         */
        reportsPage.handbookITservice();
        handbookStages.stage01(GetConfig.getProperty("itService"), "");
        handbookStages.stage03(GetConfig.getProperty("itService"),
                GetConfig.getProperty("itServicenew"), "");
        handbookStages.stage04(GetConfig.getProperty("itServicenew"));
    }

    @Test
    @DisplayName("Справочники - Руководители ЦК")
    public void handbookTest4() {
        /**
         * Тест
         * Справочники - Руководители ЦК
         */
        reportsPage.handbookCCM();
        handbookStages.stage1Leadership();
        handbookStages.stage3Leadership();
        handbookStages.stage04(GetConfig.getProperty("leadershipmannew"));
    }

    @Test
    @DisplayName("Справочники - Сотрудники")
    public void handbookTest3() {
        /**
         * Тест
         * Справочники - Сотрудники
         */
        reportsPage.handbookEmployee();
        handbookStages.stage1Employee();
        handbookStages.stage3Employee();
        handbookStages.stage04(GetConfig.getProperty("fionew"));
    }

    @Test
    @DisplayName("Справочники - Отдел")
    public void handbookTest8() {
        /**
         * Тест
         * Выбор Справочники - Отдел
         */
        reportsPage.handbookDepartment();
        //Запуск этапов
        handbookStages.stage1("", GetConfig.getProperty("managementD"));
        handbookStages.stage3("", GetConfig.getProperty("managementDnew"));
        handbookStages.stage4(GetConfig.getProperty("managementDnew"));
    }

    @Test
    @DisplayName("Справочники - Должности")
    public void handbookTest9() {
        /**
         * Тест
         * Справочники - Должности
         */
        reportsPage.handbookPosition();
        handbookStages.stage01(GetConfig.getProperty("Position"), "");
        handbookStages.stage03(GetConfig.getProperty("Position"),
                GetConfig.getProperty("Positionnew"), "");
        handbookStages.stage04(GetConfig.getProperty("Positionnew"));
    }

    @Test
    @DisplayName("Справочники - Локация")
    public void handbookTest11() {
        /**
         * Тест
         * Справочники - Локация
         */
        reportsPage.handbookLocation();
        handbookStages.stage01(GetConfig.getProperty("Loc"), "");
        handbookStages.stage03(GetConfig.getProperty("Loc"),
                GetConfig.getProperty("Locnew"), "");
        handbookStages.stage04(GetConfig.getProperty("Locnew"));
    }

    @Test
    @DisplayName("Справочники - Управление")
    public void handbookTest12() {
        /**
         * Тест
         * Справочники - Управление
         */
        reportsPage.handbookManagement();
        handbookStages.stage01(GetConfig.getProperty("management"),
                GetConfig.getProperty("managementshort"));
        handbookStages.stage03(GetConfig.getProperty("management"),
                GetConfig.getProperty("managementnew"),
                GetConfig.getProperty("managementnewshort"));
        handbookStages.stage04(GetConfig.getProperty("managementnew"));
    }

    @Test
    @DisplayName("Справочники - Доступ к меню")
    public void handbookTest2() {
        /**
         * Тест
         * Справочники - Доступ к меню
         */
        reportsPage.handbookAccessMenu();
        handbookStages.stage3accessMenu();
    }

    @Test
    @DisplayName("Справочники - Статусы бизнес-инициатив")
    public void handbookTest7() {
        /**
         * Тест
         * Выбор Справочники - Статусы бизнес-инициатив
         */
        reportsPage.handbookStatusBIStage();
        //Запуск этапов
        handbookStages.stage1(GetConfig.getProperty("GroupCode"), "");
        handbookStages.stage2(GetConfig.getProperty("GroupCode"), "");
        handbookStages.stage3(GetConfig.getProperty("GroupCodenew"), "");
        handbookStages.stage4("");
    }

    @Test
    @DisplayName("Справочники - Статусы БИ по жизненному циклу")
    public void handbookTest5() {
        /**
         * Тест
         * Выбор Справочники - Статусы БИ по жизненному циклу
         */
        reportsPage.handbookStatusBILifeCycle();
        //Запуск этапов
        handbookStages.stage1(GetConfig.getProperty("StatusBILifeCycle"), "");
        handbookStages.stage2(GetConfig.getProperty("StatusBILifeCycle"), "");
        handbookStages.stage3(GetConfig.getProperty("StatusBILifeCyclenew"), "");
        handbookStages.stage4("");
    }

    @Test
    @DisplayName("Справочники - Статусы БИ по стадиям реализации")
    public void handbookTest6() {
        /**
         * Тест
         * Выбор Справочники - Статусы БИ по стадиям реализации
         */
        reportsPage.handbookStatusBIPhase();
        //Запуск этапов
        handbookStages.stage1(GetConfig.getProperty("StatusBIPhase"), "");
        handbookStages.stage2(GetConfig.getProperty("StatusBIPhase"), "");
        handbookStages.stage3(GetConfig.getProperty("StatusBIPhasenew"), "");
        handbookStages.stage4("");
    }
}