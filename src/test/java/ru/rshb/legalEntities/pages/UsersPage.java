package ru.rshb.legalEntities.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersPage {

    @Autowired
    private WebDriver webDriver;

    private String pageTitle = "//td[@class = 'ib-datagrid-title-middle']/span";


    @Step("Получаем текст со страницы \"Пользователи клиента\"")
    public String pageTitle(){
       return webDriver.findElement(By.xpath(pageTitle)).getText();
    }
}
