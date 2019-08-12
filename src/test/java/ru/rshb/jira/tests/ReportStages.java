package ru.rshb.jira.tests;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.rshb.globalCommonClass.GetConfig;
import ru.rshb.globalCommonClass.GlobalDriver;
import ru.rshb.jira.pagesMainJira.ReportsPage;
import ru.rshb.jira.pagesReport.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportStages extends GlobalDriver {
    private WebDriver webDriver = GlobalDriver.getWebDriver();
    private Random random = new Random();
    private ArrayList<WebElement> list;
    private ModeByBiqPage modeByBiqPage;
    //Время в секундах, при превышении которого при скачивании, тест не пройден.
    private int time = 230;

    /**
     * Тест Сводный по т/з за месяц
     */
    void stagereportmodeByMonthSummary() {
        //Отчеты - Сводный по т/з за месяц
        new ReportsPage(webDriver).reportmodeByMonthSummary()
                //Попытка скачать без выбора месяца
                .foldedview()
                .exporttoExcel();
        ModeByMonthSummaryPage modeByMonthSummaryPage = new ModeByMonthSummaryPage(webDriver);
        //Предупреждающее сообщение
        Assert.assertTrue(modeByMonthSummaryPage.getAlert().contains(GetConfig.getProperty("Alert1")));
        //Выбираем месяц и проверяем диапазон дат
        modeByMonthSummaryPage.month(GetConfig.getProperty("month1"));
        Assert.assertEquals(modeByMonthSummaryPage.from(), GetConfig.getProperty("datefrom1"));
        Assert.assertEquals(modeByMonthSummaryPage.to(), GetConfig.getProperty("dateto1"));
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета - Полный вид
        modeByMonthSummaryPage.fullview();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefullview"), modeByMonthSummaryPage);
        //Выбираем другой месяц
        modeByMonthSummaryPage.month(GetConfig.getProperty("month2"));
        Assert.assertEquals(modeByMonthSummaryPage.from(), GetConfig.getProperty("datefrom2"));
        Assert.assertEquals(modeByMonthSummaryPage.to(), GetConfig.getProperty("dateto2"));
        //Выбираем управление
        modeByMonthSummaryPage.upravlenie();
        //Выбираем тп отчета - Свернутый вид
        modeByMonthSummaryPage.foldedview();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefoldedview"), modeByMonthSummaryPage);
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета - Сводный отчет
        modeByMonthSummaryPage.summaryreport();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filesummaryreport"), modeByMonthSummaryPage);
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета - По план-сметам и BIQ
        modeByMonthSummaryPage.BIQreport();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("fileBIQreport"), modeByMonthSummaryPage);
        //Выбираем другой год
        modeByMonthSummaryPage
                .periodleft()
                //Выбираем другой месяц
                .month(GetConfig.getProperty("month3"));
        Assert.assertEquals(modeByMonthSummaryPage.from(), GetConfig.getProperty("datefrom3"));
        Assert.assertEquals(modeByMonthSummaryPage.to(), GetConfig.getProperty("dateto3"));
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета - Полный вид(пропорционально)
        modeByMonthSummaryPage.fullviewproportional();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefullview"), modeByMonthSummaryPage);
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета - Свернутый вид(пропорционально)
        modeByMonthSummaryPage
                //Выбираем месяц и проверяем диапазон дат
                .month(GetConfig.getProperty("month1"))
                .foldedviewproportional();
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefoldedview"), modeByMonthSummaryPage);
        //Выбираем управление
        modeByMonthSummaryPage.upravlenie();
        //Выбираем тип отчета - По план-сметам и BIQ (пропорционально)
        modeByMonthSummaryPage.BIQreportproportional();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("fileBIQreport"), modeByMonthSummaryPage);
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        //Выбираем тип отчета По план-сметам и BIQ с выводом закрытых План-смет (пропорционально)
        modeByMonthSummaryPage.parentViewClosePs();
        download(GetConfig.getProperty("fileBIQreport"), modeByMonthSummaryPage);
        //Выбираем управление
        modeByMonthSummaryPage.upravlenie();
        //Выбираем тип отчета - Полный вид+ (по проектам)
        modeByMonthSummaryPage.fullviewproject();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefullview"), modeByMonthSummaryPage);
        //Выбираем управление и департамент
        modeByMonthSummaryPage.upravlenie()
                .department();
        // Добавить Отчет рентабельности
        modeByMonthSummaryPage.parentRent();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("filefullview"), modeByMonthSummaryPage);
        // Добавить Контроль списания
        modeByMonthSummaryPage.timesheetControl();
        //Проверяем скачался ли файл
        download(GetConfig.getProperty("fileTimesheet"), modeByMonthSummaryPage);
    }

    /**
     * Тест Отчеты - По BIQ-ам
     */
    void stagereportmodeByBiq() {

        new ReportsPage(webDriver).reportmodeByBiq()
                .alltime();
        modeByBiqPage = new ModeByBiqPage(webDriver);
        list = new ArrayList<>(modeByBiqPage.getTableElement());
        System.out.println("list.size() " + list.size());
        //Отчеты проверяются в цикле
        for (int i = 0; i < 10; i++) {
            //Проверка скачивания
            downloadbyBIQ(GetConfig.getProperty("fileBIQ"), list.get(new Random().nextInt(list.size())));
        }
    }

    /**
     * Отчеты - По статусам
     */
    void stagereportmodeByStatus() {

        new ReportsPage(webDriver).reportmodeByStatus();
        ModeByStatusPage modeByStatusPage = new ModeByStatusPage(webDriver);
        list = new ArrayList<>(modeByStatusPage.getTableElement());
        System.out.println(list.size());
        //Проходимся по всем отчетам
        for (int i = 0; i < 10; i++) {
            //Отмечаем элемент в таблице
            int a = new Random().nextInt(list.size());
            modeByStatusPage.markline(list.get(a));
            //Проверка скачивания
            download(GetConfig.getProperty("fileStatus"), modeByStatusPage);
            //Убираем отметку
            modeByStatusPage.unmarkline(list.get(a));
        }
    }

    /**
     * Отчеты - По трудозатратам
     */
    void stagemodeByWorklog() {

        new ReportsPage(webDriver).reportmodeByWorklog();

        for (int i = 0; i < 2; i++) {
            //Выбрать дату на календарях
            fromto();
            //Проверка скачивания
            download(GetConfig.getProperty("fileWorklog"), new ModeByWorklogPage(webDriver));
        }
    }

    /**
     * Отчеты - Статистика по BIQ
     */
    void stagemodeByStatisticBIQ() {

        new ReportsPage(webDriver).reportmodeByStatisticBIQ()
                //Портфель активных БИ по статусам
                .stages();
        ModeByStatisticBIQPage modeByStatisticBIQPage = new ModeByStatisticBIQPage(webDriver);
        //Проверка скачивания
        download(GetConfig.getProperty("fileStage"), modeByStatisticBIQPage);
        //Движение БИ по жизненному циклу
        modeByStatisticBIQPage.lifecycle();
        //Выбрать дату на календарях
        fromto();
        //Проверка скачивания
        download(GetConfig.getProperty("fileMoveBIQ"), modeByStatisticBIQPage);
        //Портфель активных БИ по ОЦК
        modeByStatisticBIQPage.phaseock()
                //Выбор значения из правого списка
                .statusGroupCode("1");
        //Проверка скачивания
        download(GetConfig.getProperty("fileBiqByOCK"), modeByStatisticBIQPage);
        //Портфель активных БИ по подразделениям
        modeByStatisticBIQPage.phasedept()
                //Выбор значения из правого списка
                .statusGroupCode("2");
        //Проверка скачивания
        download(GetConfig.getProperty("fileBiqByDepartment"), modeByStatisticBIQPage);
        //Динамика реализации БИ
        modeByStatisticBIQPage.dynamicimpl();
        //Выбрать дату на календарях
        fromto();
        //Проверка скачивания
        download(GetConfig.getProperty("fileDynamicImplementation"), modeByStatisticBIQPage);
        //Динамика реализации БИ по ОЦК
        modeByStatisticBIQPage.dynamicimplock();
        //Выбрать дату на календарях
        fromto();
        //Проверка скачивания
        download(GetConfig.getProperty("fileDynamicImplementationOCK"), modeByStatisticBIQPage);
        //Движение БИ по ЖЦ
        modeByStatisticBIQPage.lifecycleonlyfromnew();
        //Выбрать дату на календарях
        fromto();
        //Проверка скачивания
        download(GetConfig.getProperty("fileStatusMoveBIQ"), modeByStatisticBIQPage);
        //Динамика реализации БИ
        modeByStatisticBIQPage.dynamicimplonlyfromnew();
        //Выбрать дату на календарях
        fromto();
        //Проверка скачивания
        download(GetConfig.getProperty("fileDynamicImplementation"), modeByStatisticBIQPage);
        //Просроченные оценки
        modeByStatisticBIQPage.overduetasks();
        //Проверка скачивания
        download(GetConfig.getProperty("fileReportOverDueTasks"), modeByStatisticBIQPage);
        //Менеджеры задач БИ
        modeByStatisticBIQPage.biqsByManager();
        //Проверка скачивания
        download(GetConfig.getProperty("fileBIQsByManager"), modeByStatisticBIQPage);
    }

    /**
     * Отчеты - Учет рабочего времени
     */
    void stagemodeByPsPage() {

        new ReportsPage(webDriver).reportmodeByPs()
                //За все время
                .alltime();
        System.out.println(webDriver.getTitle());
        ModeByPsPage modeByPsPage = new ModeByPsPage(webDriver);
        list = new ArrayList<>(modeByPsPage.getTableElement());
        System.out.println(list.size());
        //Проходимся по всем элементам
        for (int i = 0; i < 10; i++) {
            //Отмечаем элемент в таблице
            int a = new Random().nextInt(list.size());
            modeByPsPage.markline(list.get(a));
            //Проверка скачивания
            download(GetConfig.getProperty("fileByPS"), modeByPsPage);
            //Убрать выделение элемента
            modeByPsPage.unmarkline(list.get(a));
        }
    }

    /**
     * Отчеты - По повышениям приоритетов
     */
    void stagemodeByIncPriority() {
        ModeByIncPriorityPage modeByIncPriorityPage = new ReportsPage(webDriver).reportmodeByIncPriority();

        download(GetConfig.getProperty("fileIncPriority"), modeByIncPriorityPage);

        modeByIncPriorityPage.left();

        download(GetConfig.getProperty("fileIncPriority"), modeByIncPriorityPage);
    }

    /**
     * Отчет по неосмеченным BIQ
     */
    void stagebiqUnmarkedPS() {
        ModeByBiqUnmarkedPSPage modeByBiqUnmarkedPSPage = new ReportsPage(webDriver).reportmodeByBiqUnmarkedPS();

        for (int i = 0; i < random.nextInt(10) + 10; i++) {

            modeByBiqUnmarkedPSPage.groupPrior();

            modeByBiqUnmarkedPSPage.issueStatusId();

            download(GetConfig.getProperty("fileUnmarkedPS"), modeByBiqUnmarkedPSPage);
        }
    }

    /**
     * Скачивание файла и проверка что он скачался
     *
     * @param filename имя файла который должен скачаться с расширением.
     * @param Page     Экземпляр страницы, где должен быть реализован метод exporttoExcel + страница обязательно
     *                 должна наследоваться от страницы .
     */
    void download(String filename, ModePageMain Page) {
        String title = webDriver.getTitle();
        Page.exporttoExcel();
        //Проверяем скачался ли файл
        GlobalDriver.isdownload(filename, webDriver, title);
    }

    /**
     * Скачивание файла и проверка что он скачался для Отчеты - По BIQ-ам
     *
     * @param filename имя файла который должен скачаться с расширением.
     * @param line     ВебЭлемент (строка из таблицы) по которому необходимо скачать файл
     */
    void downloadbyBIQ(String filename, WebElement line) {
        modeByBiqPage.getnameline(line);
        String title = webDriver.getTitle();
        modeByBiqPage.exporttoExcel(line);
        //Проверяем скачался ли файл
        GlobalDriver.isdownload(filename, webDriver, title);
    }

    /**
     * Использование методов fromSelect и toSelect (описаны в классе ModePageMain)
     * + Дата toSelect выбирается текущая (кроме года)
     * Дата fromSelect выбирается случайная, до текущей (кроме года)
     */
    void fromto() {
        ModePageMain modePageMain = new ModePageMain(webDriver);
        int temp;
        int month2;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM");
        String date = format.format(new Date());
        String val1 = date.substring(0, 2);
        String val2 = date.substring(3);
        if (val1.startsWith("0"))
            val1 = val1.substring(1);
        if (val2.startsWith("0"))
            val2 = val2.substring(1);
        month2 = random.nextInt(Integer.parseInt(val2) - 1) + 1;

        System.out.println("month1 " + val2);
        System.out.println("month2 " + month2);

        temp = Integer.parseInt(val1);
        modePageMain.toSelect(val1, Integer.parseInt(val2), GetConfig.getProperty("year_2"));
        modePageMain.fromSelect(String.valueOf(random.nextInt(temp - 1) + 1), month2
                , GetConfig.getProperty("year_2"));
    }

    /**
     * Сравнение Дат
     *
     * @param datetoCompare1 Строка с датой, для сравнения, дата должна быть в формате dd.MM.yyyy
     * @param datetoCompare2 Строка с датой, для сравнения, дата должна быть в формате dd.MM.yyyy
     * @return Возвращает -1 в случае ошибки, 1 - если datetoCompare2 больше datetoCompare1, 2 - если меньше
     * и 0 - если они одинаковы.
     */
    int compareDate(String datetoCompare1, String datetoCompare2) {
        Date date1 = null;
        Date date2 = null;

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        String regex = "\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(datetoCompare1);
        Matcher matcher2 = pattern.matcher(datetoCompare2);

        try {
            if (matcher1.find()) {
                System.out.println("matcher1 " + matcher1.group());
                date1 = format.parse(matcher1.group());
            } else {
                System.out.println("Дата в формате dd.MM.yyyy не найдена в искомой строке datetoCompare1.");
                return -1;
            }

            if (matcher2.find()) {
                System.out.println("matcher2 " + matcher2.group());
                date2 = format.parse(matcher2.group());
            } else {
                System.out.println("Дата в формате dd.MM.yyyy не найдена в искомой строке datetoCompare2.");
                return -1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (Objects.requireNonNull(date2).after(Objects.requireNonNull(date1))) {
            System.out.println(format.format(date2) + " is after then " + format.format(date1));
            return 1;
        }

        if (Objects.requireNonNull(date2).before(Objects.requireNonNull(date1))) {
            System.out.println(format.format(date2) + " is before then " + format.format(date1));
            return 2;
        }

        if (Objects.requireNonNull(date2).equals(Objects.requireNonNull(date1))) {
            System.out.println(format.format(date2) + " is equivalent to" + format.format(date1));
            return 0;
        }
        return -1;
    }
}