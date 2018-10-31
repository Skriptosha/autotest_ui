package ru.rshb.jira;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.rshb.globalCommonClass.*;
import ru.rshb.jira.pagesMainJira.AuthorizationPage;
import ru.rshb.jira.pagesMainJira.JiraMainPage;

@Epic("Авторизация Jira")
public class AuthorizationTest extends GlobalDriver {
    private JiraMainPage jiraMainPage;

    /**
     * Тест открытия вкладки Отчеты
     */
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка авторизации в Джире")
    public void reportJira() {
        jiraMainPage.intechReports();
        Avail.availableNewPage(AuthorizationPage.getJiraMainPageWindowHandle(), Avail.NEWPAGE,
                GlobalDriver.getWebDriver());
    }

    @Before
    public void authorization() {
        jiraMainPage = new JiraMainPage(GlobalDriver.getWebDriver());
        GetConfig.setNameProperties("jiraglobal");
        new AuthorizationPage(GlobalDriver.getWebDriver())
                .geturl(GetConfig.getProperty("url"))
                .authorization("testlogin", "testpassword");
    }

    @After
    public void logout() {
//        Avail.availableNewPage(0, GlobalDriver.getWebDriver(),
//                GlobalDriver.getTimeout());
//        jiraMainPage.profileLogout();
    }
}