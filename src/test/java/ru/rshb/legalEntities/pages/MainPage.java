package ru.rshb.legalEntities.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rshb.globalCommonClass.DriverUtils;

import java.util.List;

@Component
public class MainPage {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private UsersPage usersPage;

    @Autowired
    private DriverUtils driverUtils;


    private String admin = "nonsubmitImpl";

    private String clients = "nonsubmitImpl_5";

    private String table = "//table[@class='ib-overflow ib-datagrid-rows']";

    private String checkBoxTable = ".//input[@type = 'checkbox']";

    private String users = "//img[@title= 'Пользователи']";

    private String tableINN = "//td[@title='%d']/..";

    private String allClientsBranch = ".//input[@value = '%d']/../..";

    @Step("Нажимаем кнопку \"Администрирование\"")
    public MainPage admin(){
        webDriver.findElement(By.id(admin)).click();
        return this;
    }

    @Step("Нажимаем кнопку \"Клиенты\"")
    public MainPage clients(){
        webDriver.findElement(By.id(clients)).click();
        return this;
    }

    @Step("Находим клиента в таблице по ИНН")
    public WebElement findClient(String INN){
        return webDriver.findElement(By.xpath(table))
                .findElement(By.xpath(driverUtils.formatString(tableINN, INN)));
    }

    @Step("Нажимаем чек-бокс на клиенте")
    public MainPage checkBoxTable(WebElement line){
        line.findElement(By.xpath(checkBoxTable)).click();
        return this;
    }

    @Step("Нажимаем кнопку \"Пользователи\"")
    public UsersPage users(){
        webDriver.findElement(By.xpath(users)).click();
        return usersPage;
    }

    @Step("Находим всех клиентов филиала")
    public List<WebElement> allClientsBranch(String branch){
        return webDriver.findElement(By.xpath(table))
                .findElements(By.xpath(driverUtils.formatString(allClientsBranch, branch)));
    }
}
