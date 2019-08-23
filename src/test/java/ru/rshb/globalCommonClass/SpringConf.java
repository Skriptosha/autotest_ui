package ru.rshb.globalCommonClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.rshb.globalCommonClass.old.GetConfig;

@Configuration
@ComponentScan(basePackageClasses = {GetConfig.class},
basePackages = {"ru.rshb.globalCommonClass", "ru.rshb.jira.tests", "ru.rshb.legalEntities.pages",
"ru.rshb.legalEntities.tests"}, lazyInit = true)
@PropertySource({"classpath:webdriver.properties",
        "classpath:jiraglobal.properties",
"classpath:legalEntites.properties"})
public class SpringConf {

    @Autowired
    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass.getSimpleName().substring(0, 1)
                .toLowerCase().concat(tClass.getSimpleName().substring(1)), tClass);
    }
}
