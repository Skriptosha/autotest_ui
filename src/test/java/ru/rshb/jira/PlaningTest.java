package ru.rshb.jira;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.rshb.globalCommonClass.*;
import ru.rshb.jira.pagePlaning543.MarkingBiqActionPage;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;
import ru.rshb.jira.pagesMainJira.JiraMainPage;

import java.util.List;

@Epic("ФТ 5.4.3 Планирование")
public class PlaningTest extends GlobalDriver {
    private WebDriver webDriver;
    private JiraMainPage jiraMainPage;
    private String[] strings = new String[]{"Код", "Тема", "Статус", "Исполнитель", "Дата создания",
            "Группа приоритизации (год и квартал)"};

    @Before
    public void authorization() {
//        setWebDriver();
        GetConfig.setNameProperties("jiraglobal");
        webDriver = GlobalDriver.getWebDriver();
        jiraMainPage = new JiraMainPage(webDriver);
        // Авторизация на сайте
        new AuthorizationPage(webDriver)
                .geturl(GetConfig.getProperty("url"))
                .authorization("testlogin", "testpassword");
    }

    /**
     * Тест по пункту ФТ 5.4.3 Планирование
     * Тест не пройдет только на IE. Скорее всего проходить и не будет.
     */
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест по пункту ФТ 5.4.3 Планирование")
    public void planing543() {
        //Вход на страницу Маркировка БИ к ГП
        jiraMainPage.intechMarking();

        MarkingBiqActionPage markingBiqActionPage = new MarkingBiqActionPage(webDriver);
        //Проверяем, что по умолчанию открыта вкладка Маркировка
        Assert.assertEquals(markingBiqActionPage.markingisactive(), "true");
        //Выбираем фильтр
        markingBiqActionPage.filter(GetConfig.getProperty("filter"));

        //Сравниваем поля JQL-запрос: и параметр data-jql в выбранного значения в фильтре
        Assert.assertEquals(markingBiqActionPage.requestJQL(),
                markingBiqActionPage.filtergetdatajql(GetConfig.getProperty("filter")));

        List<WebElement> tablelist = markingBiqActionPage.tableHead();

        //Проверяем колличиство столбцов
        Assert.assertEquals(tablelist.size(), Integer.parseInt(GetConfig.getProperty("tableColumn")));

        //Берем названия столбцов
        StringBuilder tablename = new StringBuilder();
        for (WebElement anArrayList : tablelist) {
            tablename.append(anArrayList.getText());
        }
        System.out.println(tablename);
        //Проверяем названия столбцов
        for (String string : strings) {
            Assert.assertTrue(tablename.toString().contains(string));
        }
        //Проверяем колличество столбцов по записи(должно совпрадать с оглавлением таблицы
        tablelist = markingBiqActionPage.tableColumn();
        System.out.println(tablelist.size());
        Assert.assertEquals(tablelist.size(), Integer.parseInt(GetConfig.getProperty("tableColumn")));

        //Запоминаем колличество столбцов
        tablelist = markingBiqActionPage.tableHead();
//        WebElement webElement = tablelist.get(1);
        int size = tablelist.size() - 1;

        //Заполняем метку ГП
        markingBiqActionPage.markGPtext(GetConfig.getProperty("markGPnew"));
        markingBiqActionPage.markGPadd();

        //Проверяем предупреждающее сообщение
        Assert.assertTrue(markingBiqActionPage.warnmessage().contains(GetConfig.getProperty("filterDraftwarning2")) );

        //Получаем список столбцов
        Wait.waitpage(7);
        tablelist = markingBiqActionPage.tableHead();
        //Сравниваем с тем что было
        Assert.assertEquals(size, tablelist.size() - 1);

        tablelist.remove(0);
        System.out.println("tablelist " + tablelist.size());
        //Проверяем добавилась ли новая ГП в записи.
        for (WebElement tableElem : tablelist) {
            Assert.assertTrue(markingBiqActionPage.tablelinegp(tableElem).contains(GetConfig.getProperty("markGPnew")));
        }
//        getWebDriver().navigate().refresh();
//        getWebDriver().switchTo().alert().accept();
        String title = webDriver.getTitle();
        //Переходим в списки
        markingBiqActionPage.lists()
                .prioritizationList(GetConfig.getProperty("filter"))
                .listGP();

        //Проверяем скачался ли файл
        isdownload(GetConfig.getProperty("fileNameGPList"), webDriver, title);
        //Проверяем возможно ли выбрать неправильные периоды.
//        markingBiqActionPage.biListfrom(GetConfig.getProperty("markGP1wrong"), null);
//        markingBiqActionPage.biListto(GetConfig.getProperty("markGP2wrong"), null);
//        markingBiqActionPage.listnewGP(null);
        // Доработка: ограничить возможность выбора даты 2 не раньше даты 1.

        //Выбираем необходимые значения для пункта "БИ, поступившие между"
        markingBiqActionPage.biListfrom(GetConfig.getProperty("markGP1"))
                .biListto(GetConfig.getProperty("markGP2"))
                .listnewGP();

        //Проверяем сообщение о датах
        String text = markingBiqActionPage.infomessage();

        //Проверка даты от
        String datetext = markingBiqActionPage.filtercyclefrom(GetConfig.getProperty("markGP1"));
        System.out.println(datetext);
        Assert.assertTrue(text.contains(datetext.substring(0, 10)));

        //Проверка даты до
        datetext = markingBiqActionPage.filtercycleto(GetConfig.getProperty("markGP2"));
        System.out.println(datetext);
        Assert.assertTrue(text.contains(datetext.substring(0, 10)));

        //Проверяем скачался ли файл
        isdownload(GetConfig.getProperty("fileNameGPListnew"), webDriver, title);
    }
}


