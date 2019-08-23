package ru.rshb.jira.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rshb.globalCommonClass.DriverUtils;
import ru.rshb.globalCommonClass.old.Avail;
import ru.rshb.globalCommonClass.old.GetConfig;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;
import ru.rshb.jira.pagesMainJira.JiraMainPage;

@Epic("Авторизация Jira")
public class AuthorizationTest extends DriverUtils {
    private JiraMainPage jiraMainPage;

    @Autowired
    private WebDriver webDriver;

    /**
     * Тест открытия вкладки Отчеты
     */
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка авторизации в Джире")
    public void reportJira() {
        jiraMainPage.intechReports();
        Avail.availableNewPage(AuthorizationPage.getJiraMainPageWindowHandle(), Avail.NEWPAGE,
                webDriver);
    }

    @Before
    public void authorization() {
        jiraMainPage = new JiraMainPage(webDriver);
        GetConfig.setNameProperties("jiraglobal");
        new AuthorizationPage(webDriver)
                .getURL(GetConfig.getProperty("url"))
                .authorization("testlogin", "testpassword");
    }

    @After
    public void logout() {
//        Avail.availableNewPage(0, GlobalDriver.getWebDriver(),
//                GlobalDriver.getTimeout());
//        jiraMainPage.profileLogout();
    }
}