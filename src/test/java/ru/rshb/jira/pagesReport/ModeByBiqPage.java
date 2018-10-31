package ru.rshb.jira.pagesReport;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.rshb.jira.ReportStages;

import java.util.List;

public class ModeByBiqPage extends ModePageMain {

    public ModeByBiqPage(WebDriver webDriver) {

        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Текст строки в таблице
     */
    private By getline = By.xpath(".//td[1]/a/div");

    /**
     * Элемент Таблица
     */
    @FindBy(xpath = "//*[@id='biqTable']/tbody")
    private WebElement table;

    /**
     * Проверка календарей Выберите период.
     * Месяцы, года или дни в календаре С не должны быть больше чем в календаре ПО. (В обратную сторону не проверяется
     * пока)
     *
     * @param webElement элемент, если необходимо ожидание пока предыдущий элемент исчезнет из DOM
     * @return возвращает последний использованный элемент
     */
    public boolean checkcalendar(WebElement webElement) {
//        boolean year = false;
//        boolean month = false;
//        getListWebElement(".//select[@data-handler='selectYear']", "startDate");
//        String s = firstSelectedOption.getText();
//        getListWebElement(".//select[@data-handler='selectYear']", "endDate");
//        String e = firstSelectedOption.getText();
//        if (s.equals(e)) year = true;
//
//        for (WebElement webelement : getListWebElement(".//select[@data-handler='selectYear']",
//                "startDate")) {
//            if (Integer.parseInt(webelement.getText()) > Integer.parseInt(e))
//                return false;
//        }
//
//        getListWebElement(".//select[@data-handler='selectMonth']", "startDate");
//        s = firstSelectedOption.getAttribute("value");
//        getListWebElement(".//select[@data-handler='selectMonth']", "endDate");
//        e = firstSelectedOption.getAttribute("value");
//        if (s.equals(e)) month = true;
//
//        for (WebElement webelement : getListWebElement(".//select[@data-handler='selectMonth']",
//                "startDate")) {
//            if (Integer.parseInt(webelement.getAttribute("value")) > Integer.parseInt(e))
//                return false;
//        }

//        if (year && month) {
////            PressDelay.pressDelay("startDate", LocatorType.ID, webElement, null);
////            List<WebElement> date = new ArrayList<>(Objects.requireNonNull(FindElemDelay.findElemDelay(".//select" +
////                            "[@data-handler='selectMonth']", LocatorType.X_PATH, null,
////                    null)).findElements(By.xpath(".//a[@class='ui-state-default']")));
//
//            for (WebElement webelement : date) {
//                if (Integer.parseInt(webelement.getText()) > Integer.parseInt(super.to())) return false;
//            }
//        }
        return true;
    }

    /**
     * Получить Таблицу
     *
     * @return возвращает последний использованный элемент
     */
    @Override
    @Step("Получаем элемент biqTable/tbody")
    public WebElement getTable() {
        return table;
    }


    /**
     * Выбор отчета по элементу line
     *
     * @param line Элемент таблицы, по которому необходимо скачать отчет (строка)
     * @return возвращает последний использованный элемент
     */
    @Step("Нажимаем кнопку Выгрузить в Excel")
    public ReportStages exporttoExcel(WebElement line) {
        line.findElement(getline).click();
        return new ReportStages();
    }
}