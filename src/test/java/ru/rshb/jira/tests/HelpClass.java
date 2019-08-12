package ru.rshb.jira.tests;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import ru.rshb.globalCommonClass.GetConfig;
import ru.rshb.globalCommonClass.GlobalDriver;
import ru.rshb.globalCommonClass.Wait;
import ru.rshb.globalCommonClass.old.Avail;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;
import ru.rshb.jira.pagesMainJira.JiraMainPage;
import ru.rshb.jira.pagesMainJira.ReportsPage;

public class HelpClass {

    private static int count = 0;

    public void authReportsAndHandbooks(WebDriver webDriver){

        GetConfig.setNameProperties("jiraglobal");
        if (count == 0) {
            System.out.println("AuthorizationPage");
            new AuthorizationPage(webDriver)
                    .getURL(GetConfig.getProperty("url"))
                    .authorization("testlogin", "testpassword");
            count++;
        }

        //Переключаемся на главную страницу
        Avail.availableNewPage(AuthorizationPage.getJiraMainPageWindowHandle(), Avail.OLDPAGE, webDriver);
        Wait.waitpage(2);

        //Открываем вкладку Отчеты
        new JiraMainPage(webDriver).intechReports();

        //Переключение на Отчеты
        Avail.availableNewPage(AuthorizationPage.getJiraMainPageWindowHandle(), Avail.NEWPAGE, webDriver);

        //Проверка сайта
        Avail.availablePage(GetConfig.getProperty("urlhost"), webDriver, GlobalDriver.getTimeout());

        //Проверка имени
        Assert.assertEquals(new ReportsPage(webDriver).remoteUsername(), GetConfig.getProperty("userFullname"));
    }
}