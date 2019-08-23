package ru.rshb.jira.tests;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.rshb.globalCommonClass.old.GetConfig;
import ru.rshb.globalCommonClass.old.GlobalDriver;
import ru.rshb.jira.pagesHandbook.*;
import ru.rshb.jira.pagesMainJira.ReportsPage;

import java.util.ArrayList;
import java.util.List;

public class HandbookStages extends GlobalDriver {
    private WebDriver webDriver = GlobalDriver.getWebDriver();
    private ReportsPage reportsPage = new ReportsPage(webDriver);
    private WebElement webElement;
    private List<WebElement> tablelist;

    /**
     * Тест для 4 справочников: Статусы БИ по жизненному циклу, Статусы БИ по стадиям реализации,
     * Статусы БИ по этапам и справочника Управление
     * Первый этап - добавляем новую запись
     *
     * @param management Параметр поля управление для справочника Управление,
     *                   если это другой справочник, то параметр должен быть равен ""
     * @param column3    Параметр 3 столбца, который отличается у данных справочников
     */
    @Step("Первый этап - добавляем новую запись")
    void stage1(String column3, String management) {
        HandTripleColumnPageHand handTripleColumnPage = new HandTripleColumnPageHand(webDriver);
        HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
        //Создаем новую запись
        handTripleColumnPage.add();
        if (management.equals("")) {
            //Выбор статуса БИ
            handTripleColumnPage.handbookStatusBIStage(GetConfig.getProperty("statusBI"))
                    //Заполняем Название статуса БИ
                    .nameBIStatus(GetConfig.getProperty("nameBI"))
                    //Выбираем Обьединенный статус БИ:
                    .handbookstatusGroupCode(column3);
        } else {
            //заполняем Департамент
            handSingleColumnPage.nameFieldtoAdd(GetConfig.getProperty("department"))
                    //Заполняем Короткое наименование
                    .shortNameFieldtoAdd(GetConfig.getProperty("departmentshort"));
            //Заполняем Управление
            handTripleColumnPage.handbookStatusManagement(management);
        }
        //Сораняем
        handTripleColumnPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handTripleColumnPage.search("".equals(management) ? GetConfig.getProperty("nameBI") :
                GetConfig.getProperty("departmentshort"));
        //Корректируем поля для проверки в зависимости от справочника
        String field1;
        String field2;
        String field3;
        if (management.equals("")) {
            field1 = GetConfig.getProperty("nameBI");
            field2 = GetConfig.getProperty("statusBI");
            field3 = column3;
        } else {
            field1 = GetConfig.getProperty("departmentshort");
            field2 = GetConfig.getProperty("department");
            field3 = management;
        }
        //Ищем название
        webElement = handTripleColumnPage.getLine(field1);
        //Проверка выбранных позиций
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 1), field2);
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 3), field3);
    }

    /**
     * Тест для 4 справочников: Статусы БИ по жизненному циклу, Статусы БИ по стадиям реализации,
     * Статусы БИ по этапам и справочника Управление
     *
     * Второй этап - добавляем ту же самую запись
     *
     * @param management Параметр поля управление для справочника Управление,
     *                   если это другой справочник, то параметр должен быть равен ""
     * @param column3    Параметр 3 столбца, который отличается у данных справочников
     */
    @Step("Второй этап - добавляем ту же самую запись")
    void stage2(String column3, String management) {
        HandTripleColumnPageHand handTripleColumnPage = new HandTripleColumnPageHand(webDriver);
        HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
        //Создаем новую запись
        handTripleColumnPage.add();
        if (management.equals("")) {
            //Выбор статуса БИ
            handTripleColumnPage.handbookStatusBIStage(GetConfig.getProperty("statusBI"))
                    //Заполняем Название статуса БИ
                    .nameBIStatus(GetConfig.getProperty("nameBI"))
                    //Выбираем Обьединенный статус БИ:
                    .handbookstatusGroupCode(column3);
        } else {
            //заполняем Департамент
            handSingleColumnPage.nameFieldtoAdd(GetConfig.getProperty("department"))
                    //Заполняем Короткое наименование
                    .shortNameFieldtoAdd(GetConfig.getProperty("departmentshort"));
            //Заполняем Управление
            handTripleColumnPage.handbookStatusManagement(management);
        }
        //Сораняем
        handTripleColumnPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handSingleColumnPage.search("".equals(management) ? GetConfig.getProperty("nameBI") :
                GetConfig.getProperty("departmentshort"));
        //Проверяем колличество записей
        tablelist = new ArrayList<>(handTripleColumnPage.getLines(GetConfig.getProperty("nameBI")));
        System.out.println(tablelist.size());
        Assert.assertEquals(1, tablelist.size());
        //Корректируем поля для проверки в зависимости от справочника
        String field1;
        String field2;
        String field3;
        if (management.equals("")) {
            field1 = GetConfig.getProperty("nameBI");
            field2 = GetConfig.getProperty("statusBI");
            field3 = column3;
        } else {
            field1 = GetConfig.getProperty("departmentshort");
            field2 = GetConfig.getProperty("department");
            field3 = management;
        }
        //Ищем название
        webElement = handTripleColumnPage.getLine(field1);
        //Проверка выбранных позиций
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 1), field2);
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 3), field3);
    }

    /**
     * Тест для 4 справочников: Статусы БИ по жизненному циклу, Статусы БИ по стадиям реализации,
     * Статусы БИ по этапам и справочника Управление
     * Третий этап - редактруем ранее созданную запись
     *
     * @param managementnew Параметр поля управление для справочника Управление отличный от management,
     *                      если это другой справочник, то параметр должен быть равен ""
     * @param column3new    Параметр 3 столбца, отличный от column3 предыдущий этапов
     */
    @Step("Третий этап - редактруем ранее созданную запись")
    void stage3(String column3new, String managementnew) {
        HandTripleColumnPageHand handTripleColumnPage = new HandTripleColumnPageHand(webDriver);
        HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
        if (managementnew.equals("")) {
            //Редактировать
            handTripleColumnPage.search(GetConfig.getProperty("nameBI"))
                    .editLine(GetConfig.getProperty("nameBI"));
            //Заполняем Название статуса БИ
            handTripleColumnPage.nameBIStatus(GetConfig.getProperty("nameBInew"))
                    //Выбор статуса БИ
                    .handbookStatusBIStage(GetConfig.getProperty("statusBInew"))
                    //Выбираем Обьединенный статус БИ:
                    .handbookstatusGroupCode(column3new);
        } else {
            //Редактировать
            handTripleColumnPage.search(GetConfig.getProperty("department"))
                    .editLine(GetConfig.getProperty("department"));
            //заполняем Департамент
            handSingleColumnPage.nameFieldtoAdd(GetConfig.getProperty("departmentnew"))
                    //Заполняем Короткое наименование
                    .shortNameFieldtoAdd(GetConfig.getProperty("departmentshortnew"));
            //Заполняем Управление
            handTripleColumnPage.handbookStatusManagement(managementnew);
        }
        //Сораняем
        handTripleColumnPage.save();
        //Обновляем страницу
        refreshPage();
        //Корректируем поля для проверки в зависимости от справочника
        String field1;
        String field2;
        String field3;
        if (managementnew.equals("")) {
            field1 = GetConfig.getProperty("nameBInew");
            field2 = GetConfig.getProperty("statusBInew");
            field3 = column3new;
        } else {
            field1 = GetConfig.getProperty("departmentshortnew");
            field2 = GetConfig.getProperty("departmentnew");
            field3 = managementnew;
        }
        //Ищем название
        webElement = handTripleColumnPage.getLine(field1);
        //Проверка выбранных позиций
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 1), field2);
        Assert.assertEquals(handTripleColumnPage.getTextfromLine(webElement, 3), field3);
    }

    /**
     * Четвертый этап - удаляем ранее созданную запись
     *
     * @param managementnew Параметр поля управление для справочника Управление отличный от management,
     *                      если это другой справочник, то параметр должен быть равен ""
     */
    @Step("Четвертый этап - удаляем ранее созданную запись")
    void stage4(String managementnew) {
        HandTripleColumnPageHand handTripleColumnPage = new HandTripleColumnPageHand(webDriver);
        String field;
        if (managementnew.equals("")) field = GetConfig.getProperty("nameBInew");
        else field = GetConfig.getProperty("departmentnew");
        //Удаляем проверенный элемент
        handTripleColumnPage.search(field)
                .deleteLine(field);
        //Делаем поиск и проверяем отображение элементов
        handTripleColumnPage.search(field);
        tablelist = new ArrayList<>(handTripleColumnPage.getAllLinesTable());
        System.out.println(tablelist.size());
        isVisible(field);
    }

    /**
     * Тест для справочников с одним столбцом:
     * Первый этап - добавляем новую запись
     *
     * @param name      строка названия для справочников с одним столбцом
     * @param shortname Поле Короткое наименование для Справочника Управления
     *                  в остальных случаях должна быть пустая строка
     */
    @Step("Первый этап - добавляем новую запись")
    void stage01(String name, String shortname) {

            HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
            //Создаем новую запись
            handSingleColumnPage.add();
            //Ввод необходимо значения справочника
            handSingleColumnPage.nameFieldtoAdd(name);
            //Для справочника Управления вводим короткое наименование
            if (!shortname.equals(""))
                handSingleColumnPage.shortNameFieldtoAdd(shortname);
            //Сораняем
            handSingleColumnPage.save();
            //Обновляем страницу
            refreshPage();
            //Поиск
            handSingleColumnPage.search(name);
            //Ищем название
            webElement = handSingleColumnPage.getLine(name);
            //Проверка выбранных позиций
            if (!"".equals(shortname)) {
                Assert.assertEquals(handSingleColumnPage.getTextfromLine(webElement, 2), shortname);
           }
    }

    /**
     * Тест для справочников с одним столбцом:
     * Третий этап - редактруем ранее созданную запись
     *
     * @param name         строка названия для справочников с одним столбцом
     * @param namenew      новая строка названия, отличная от предыдущей
     * @param shortnamenew Поле Короткое наименование для Справочника Управления
     *                     в остальных случаях должна быть пустая строка
     */
    @Step("Третий этап - редактруем ранее созданную запись")
    void stage03(String name, String namenew, String shortnamenew) {
            HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
            //Редактируем текущую запись
            handSingleColumnPage.search(name)
                    .editLine(name);
            //Ввод необходимо значения справочника
            handSingleColumnPage.nameFieldtoAdd(namenew);
            //Для справочника Управления вводим короткое наименование
            if (!shortnamenew.equals(""))
                handSingleColumnPage.shortNameFieldtoAdd(shortnamenew);
            //Сораняем
            handSingleColumnPage.save();
            //Обновляем страницу
            refreshPage();
        //Поиск
        handSingleColumnPage.search(namenew);
            //Ищем название
            webElement = handSingleColumnPage.getLine(namenew);
            //Проверка выбранных позиций
            if (!"".equals(shortnamenew)) {
                Assert.assertEquals(handSingleColumnPage.getTextfromLine(webElement, 2), shortnamenew);
            }
    }

    /**
     * Четвертый этап - удаляем ранее созданную запись
     *
     * @param namenew строка, добавленая в 3 этапе
     */
    @Step("Четвертый этап - удаляем ранее созданную запись")
    void stage04(String namenew) {
        HandSingleColumnPageHand handSingleColumnPage = new HandSingleColumnPageHand(webDriver);
        //Удаляем проверенный элемент
        handSingleColumnPage.search(namenew)
                .deleteLine(namenew);
        //Делаем поиск и проверяем отображение элементов
        handSingleColumnPage.search(namenew);
        tablelist = new ArrayList<>(handSingleColumnPage.getAllLinesTable());
        System.out.println(tablelist.size());
        isVisible(namenew);
    }

    /**
     * Тест для справочника Руководители ЦК
     * Первый этап - добавляем новую запись
     */
    @Step("Первый этап - добавляем новую запись")
    void stage1Leadership() {
        HandLeadershipPageHand leadershipPage = new HandLeadershipPageHand(webDriver);
        //Создаем новую запись
        leadershipPage.add();
        //Ввод необходимых значений справочников
        leadershipPage.systemSelect(GetConfig.getProperty("leadershipsystem"))
                .competenceCenterSelect(GetConfig.getProperty("leadershipcc"))
                .managerSelect(GetConfig.getProperty("leadershipman"))
                .projectSelect(GetConfig.getProperty("leadershipproject"))
                .departmentSelect(GetConfig.getProperty("leadershipdepartment"));
        //Сораняем
        leadershipPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        leadershipPage.search(GetConfig.getProperty("leadershipman"));
        //Ищем название
        webElement = leadershipPage.getLine(GetConfig.getProperty("leadershipman"));
        //Проверка выбранных позиций
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 1),
                GetConfig.getProperty("leadershipsystem"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 2),
                GetConfig.getProperty("leadershipcc"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 4),
                GetConfig.getProperty("leadershipproject"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 5),
                GetConfig.getProperty("leadershipdepartment"));
    }

    /**
     * Тест для справочника Руководители ЦК
     * Третий этап - редактруем ранее созданную запись
     */
    @Step("Третий этап - редактруем ранее созданную запись")
    void stage3Leadership() {
        HandLeadershipPageHand leadershipPage = new HandLeadershipPageHand(webDriver);
        //Редактируем текущую запись
        leadershipPage.search(GetConfig.getProperty("leadershipman"))
                .editLine(GetConfig.getProperty("leadershipman"));
        //Ввод необходимых значений справочников
        leadershipPage.systemSelect(GetConfig.getProperty("leadershipsystemnew"))
                .competenceCenterSelect(GetConfig.getProperty("leadershipccnew"))
                .managerSelect(GetConfig.getProperty("leadershipmannew"))
                .projectSelect(GetConfig.getProperty("leadershipprojectnew"))
                .departmentSelect(GetConfig.getProperty("leadershipdepartmentnew"));
        //Сораняем
        leadershipPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        leadershipPage.search(GetConfig.getProperty("leadershipmannew"));
        ///Ищем название
        webElement = leadershipPage.getLine(GetConfig.getProperty("leadershipmannew"));
        //Проверка выбранных позиций
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 1),
                GetConfig.getProperty("leadershipsystemnew"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 2),
                GetConfig.getProperty("leadershipccnew"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 4),
                GetConfig.getProperty("leadershipprojectnew"));
        Assert.assertEquals(leadershipPage.getTextfromLine(webElement, 5),
                GetConfig.getProperty("leadershipdepartmentnew"));
    }

    /**
     * Тест для справочника Сотрудники
     * Первый этап - добавляем новую запись
     */
    @Step("Первый этап - добавляем новую запись")
    void stage1Employee() {
        HandEmployeePageHand handEmployeePage = new HandEmployeePageHand(webDriver);
        //Создаем новую запись
        handEmployeePage.add();
        //Ввод необходимых значений справочников
        handEmployeePage.pernumberField("Тест12345")
                .fioField(GetConfig.getProperty("fio"))
                .positionSelect(GetConfig.getProperty("employeeposition"))
                .locationSelect(GetConfig.getProperty("employeelocation"))
                .managementSelect(GetConfig.getProperty("employeemanagement"))
                .departmentSelect(GetConfig.getProperty("employeedepartment"))
                .managerSelect(GetConfig.getProperty("employeemanager"));
        //Переключатель
        handEmployeePage.reportOutput();
        //Сораняем
        handEmployeePage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handEmployeePage.search(GetConfig.getProperty("fio"));
        //Ищем название
        webElement = handEmployeePage.getLine(GetConfig.getProperty("fio"));
        //Проверка выбранных позиций
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 1),
                "Тест12345");
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 3),
                GetConfig.getProperty("employeeposition"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 4),
                GetConfig.getProperty("employeemanagement"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 5),
                GetConfig.getProperty("employeedepartment"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 6),
                GetConfig.getProperty("employeemanager"));
    }

    /**
     * Тест для справочника Сотрудники
     * Третий этап - редактруем ранее созданную запись
     */
    @Step("Третий этап - редактруем ранее созданную запись")
    void stage3Employee() {
        HandEmployeePageHand handEmployeePage = new HandEmployeePageHand(webDriver);
        //Редактируем текущую запись
        handEmployeePage.search(GetConfig.getProperty("fio"))
                .editLine(GetConfig.getProperty("fio"));
        //Ввод необходимых значений справочников
        Assert.assertEquals(handEmployeePage.pernumberFieldreadonly("readonly"), "true");
        handEmployeePage.fioField(GetConfig.getProperty("fionew"))
                .positionSelect(GetConfig.getProperty("employeepositionnew"))
                .locationSelect(GetConfig.getProperty("employeelocationnew"))
                .managementSelect(GetConfig.getProperty("employeemanagementnew"))
                .departmentSelect(GetConfig.getProperty("employeedepartmentnew"))
                .managerSelect(GetConfig.getProperty("employeemanagernew"));
        //Переключатель
        handEmployeePage.reportOutput();
        //Сораняем
        handEmployeePage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handEmployeePage.search(GetConfig.getProperty("fionew"));
        //Ищем название
        webElement = handEmployeePage.getLine(GetConfig.getProperty("fionew"));
        //Проверка выбранных позиций
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 1),
                "Тест12345");
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 3),
                GetConfig.getProperty("employeepositionnew"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 4),
                GetConfig.getProperty("employeemanagementnew"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 5),
                GetConfig.getProperty("employeedepartmentnew"));
        Assert.assertEquals(handEmployeePage.getTextfromLine(webElement, 6),
                GetConfig.getProperty("employeemanagernew"));
//        Assert.assertTrue(FindElemDelay.findElemDelay(".//div[7]/div/label/span/span[2]", LocatorType.X_PATH,
//                null, webElement).isDisplayed());
    }

    /**
     * Тест для справочника Доступ к пунктам меню
     * Третий этап - редактируем уже существующую запись
     */
    @Step("Третий этап - редактируем уже существующую запись")
    void stage3accessMenu() {
        HandAccessMenuPageHand accessMenuPage = new HandAccessMenuPageHand(webDriver);
        //Редактируем текущую запись
        accessMenuPage.search(GetConfig.getProperty("accessmenuitem"))
                .editLine(GetConfig.getProperty("accessmenuitem"));
        //Редактирование поля Подпункт меню
        accessMenuPage.menuitemField(GetConfig.getProperty("accessmenuitemnew"));
        //Сораняем
        accessMenuPage.save();
        //Обновляем страницу
        refreshPage();
        //Ищем название
        Assert.assertNotNull(accessMenuPage.getLine(GetConfig.getProperty("accessmenuitemnew")));
        //Проверка выбранных позиций
//        PressDelay.pressDelay(".//*[contains(text(),'Отчеты ') and @class='dropdown-toggle']",
//                LocatorType.X_PATH, null, null);
        Assert.assertEquals(reportsPage.getTextreportMenu(), GetConfig.getProperty("accessmenuitemnew"));
        //Возвращаемся в меню
        reportsPage.handbookAccessMenu();
        //Возвращаем начальное значение
        accessMenuPage.editLine(GetConfig.getProperty("accessmenuitemnew"));
        accessMenuPage.menuitemField(GetConfig.getProperty("accessmenuitem"));
        //Сораняем
        accessMenuPage.save();
        //Обновляем страницу
        refreshPage();
        //Проверка выбранных позиций
//        PressDelay.pressDelay(".//*[contains(text(),'Отчеты ') and @class='dropdown-toggle']",
//                LocatorType.X_PATH, null, null);
        Assert.assertEquals(reportsPage.getTextreportMenu(), GetConfig.getProperty("accessmenuitem"));
    }

    /**
     * Тест для справочника Сервис - Система
     * Первый этап - добавляем новую запись
     */
    @Step("Первый этап - добавляем новую запись")
    void stage1ServiceSystem() {
        HandServiceSystemPageHand handServiceSystemPage = new HandServiceSystemPageHand(webDriver);
        //Создаем новую запись
        handServiceSystemPage.add();
        //Ввод необходимо значения справочника
        handServiceSystemPage.systemSelect(GetConfig.getProperty("system"))
                .serviceSelect(GetConfig.getProperty("service"));
        //Сораняем
        handServiceSystemPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handServiceSystemPage.search(GetConfig.getProperty("system"));
        //Ищем название
        webElement = handServiceSystemPage.getLine(GetConfig.getProperty("system"));
        //Проверка выбранных позиций
        Assert.assertTrue(handServiceSystemPage.getTextfromLine(webElement, 2)
                .contains(GetConfig.getProperty("service")));
    }

    /**
     * Тест для справочника Сервис - Система
     * Третий этап - редактруем уже существующую запись
     */
    @Step("Третий этап - редактруем уже существующую запись")
    void stage3ServiceSystem() {
        HandServiceSystemPageHand handServiceSystemPage = new HandServiceSystemPageHand(webDriver);
        //Редактируем текущую запись
        handServiceSystemPage.search(GetConfig.getProperty("system"))
                .editLine(GetConfig.getProperty("system"));
        //Ввод необходимого значения справочника
        handServiceSystemPage.systemSelect(GetConfig.getProperty("systemnew"))
                .serviceSelect(GetConfig.getProperty("servicenew"));
        //Сораняем
        handServiceSystemPage.save();
        //Обновляем страницу
        refreshPage();
        //Поиск
        handServiceSystemPage.search(GetConfig.getProperty("systemnew"));
        //Ищем название
        webElement = handServiceSystemPage.getLine(GetConfig.getProperty("systemnew"));
        //Проверка выбранных позиций
        Assert.assertTrue(handServiceSystemPage.getTextfromLine(webElement, 2)
                .contains(GetConfig.getProperty("servicenew")));
    }

    void refreshPage(){
        webDriver.navigate().refresh();
    }

    @Step("Проверяем видимость элементов в таблице")
    void isVisible(String fieldtoSearch){
        for (WebElement tableElem : tablelist) {
            Assert.assertTrue("Элемент " + fieldtoSearch + " не был удален, либо задублирован"
                    ,tableElem.getAttribute("style").contains("display: none"));
        }
    }
}