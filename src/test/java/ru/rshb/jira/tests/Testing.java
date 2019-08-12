package ru.rshb.jira.tests;

import org.junit.Test;
import ru.rshb.globalCommonClass.GetConfig;

public class Testing {
    private ReportStages reportStages = new ReportStages();

    @Test
    public void TestDate() {
        GetConfig.setNameProperties("jiraglobal");
        System.out.println(GetConfig.getProperty("userFullname"));
//        reportStages.compareDate("fdmkfngjn012i904jgmnm22.11.2018ksdfni3inn", "12132./213/.22.05.05.2016x';cg,.l;bmh");
    }
}
