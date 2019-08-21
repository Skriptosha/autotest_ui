package ru.rshb.legalEntities.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.rshb.globalCommonClass.DriverUtils;
import ru.rshb.legalEntities.pages.AutPage;
import ru.rshb.legalEntities.pages.MainPage;
import ru.rshb.legalEntities.pages.UsersPage;

import java.util.List;

@Component
public class NewTestEx extends DriverUtils {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private Environment environment;

    @Autowired
    private AutPage autPage;

    @Autowired
    private MainPage mainPage;

    @Autowired
    private UsersPage usersPage;

    @Test
    @DisplayName("Тест входа в приложение")
    public void test(){
        WebElement webElement;
        List<WebElement> webElements;
        webDriver.get(environment.getProperty("urlArmAdmin"));
        autPage.selectAuthentication()
                .authenticationAD()
                .loginField(environment.getProperty("login"))
                .passwordField(environment.getProperty("password"))
                .submit()
                .admin()
                .clients();
        Assert.assertNotNull(webElements = mainPage.allClientsBranch(environment.getProperty("branchName1")));
        webElements.forEach(webElement1 -> System.out.println(webElement1.getText()));
        webElement = mainPage.findClient(environment.getProperty("testClientINN1"));
        mainPage.checkBoxTable(webElement)
                .users();
        Assert.assertTrue("Не тот клиент!",
                usersPage.pageTitle().contains(environment.getProperty("testClientName1")));

    }
}
