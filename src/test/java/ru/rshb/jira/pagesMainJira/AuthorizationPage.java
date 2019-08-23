package ru.rshb.jira.pagesMainJira;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.globalCommonClass.old.Avail;
import ru.rshb.globalCommonClass.old.GetConfig;
import ru.rshb.globalCommonClass.old.GlobalDriver;

public class AuthorizationPage {
    private WebDriver webDriver;
    private static final ThreadLocal<String> jiraMainPageWindowHandle = new ThreadLocal<>();

    /**
     * Получить Хандл окна авторизации.
     *
     * @return Стринг, хандл главного окна Jira
     */
    public final static String getJiraMainPageWindowHandle() {
        if (jiraMainPageWindowHandle.get() != null)
            return jiraMainPageWindowHandle.get();
        else throw new IllegalStateException("jiraMainPageWindowHandle = null");
    }

    private final static void setJiraMainPageWindowHandle(String jiraMainPageWindowHandle) {
        AuthorizationPage.jiraMainPageWindowHandle.set(jiraMainPageWindowHandle);
    }


    public AuthorizationPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    private String urlContains = "secure/Dashboard";

    /**
     * Поле Ввода логина
     */
    @FindBy(id = "login-form-username")
    private WebElement login;

    /**
     * Поле Ввода пароля
     */
    @FindBy(id = "login-form-password")
    private WebElement password;

    /**
     * Кнопка Войти
     */
    @FindBy(id = "login")
    private WebElement enter;

    /**
     * @param urlKey URL адрес сайта
     * @return возвращает страницу AuthorizationPage
     */
    @Step("Переходим на сайт")
    public AuthorizationPage getURL(String urlKey) {
        webDriver.navigate().to(urlKey);
        if (webDriver.getTitle().contains("Certificate"))
            webDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
        return this;
    }

    /**
     * Авторизация на сайте
     *
     * @param loginKey    Ключ Логина для файла properties
     * @param passwordKey Ключ Пароля для файла properties
     * @return возвращает страницу AuthorizationPage
     */
    @Step("Вводим логин и пароль")
    public JiraMainPage authorization(String loginKey, String passwordKey) {

        Avail.availablePage(urlContains, webDriver, GlobalDriver.getTimeout());
        login.sendKeys(GetConfig.getProperty(loginKey));
        password.sendKeys(GetConfig.getProperty(passwordKey));
        enter.click();
        setJiraMainPageWindowHandle(webDriver.getWindowHandle());
        return new JiraMainPage(webDriver);
    }
}