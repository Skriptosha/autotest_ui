package ru.rshb.legalEntities.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutPage {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private MainPage mainPage;

    private String selectAuthentication = "selectorForMethod";

    private String authenticationAD = "//option[@title='Аутентификация через ActiveDirectory']";

    private String authenticationLP = "//option[@title='Аутентификация по имени и паролю']";

    private String loginField = "textfield";

    private String passwordField = "passwordfield";

    private String submit = "//span[contains(text(), 'Войти')]";


    @Step("Выбираем Алгоритм")
    public AutPage selectAuthentication(){
        webDriver.findElement(By.id(selectAuthentication)).click();
        return this;
    }

    @Step("Выбираем ActiveDirectory")
    public AutPage authenticationAD(){
        webDriver.findElement(By.xpath(authenticationAD)).click();
        return this;
    }

    @Step("Выбираем по имени и паролю")
    public AutPage authenticationLP(){
        webDriver.findElement(By.xpath(authenticationLP)).click();
        return this;
    }

    @Step("Заполняем поле логин")
    public AutPage loginField(String login){
        webDriver.findElement(By.id(loginField)).sendKeys(login);
        return this;
    }

    @Step("Заполняем поле пароль")
    public AutPage passwordField(String password){
        webDriver.findElement(By.id(passwordField)).sendKeys(password);
        return this;
    }

    @Step("Нажимаем Войти")
    public MainPage submit(){
        webDriver.findElement(By.xpath(submit)).click();
        return mainPage;
    }
}
