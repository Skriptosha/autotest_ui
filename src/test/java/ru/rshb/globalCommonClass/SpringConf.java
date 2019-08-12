package ru.rshb.globalCommonClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackageClasses = {GetConfig.class},
basePackages = {"ru.rshb.globalCommonClass", "ru.rshb.jira.tests"}, lazyInit = true)
@PropertySource({"file:D:\\Проекты Java\\autotest_ui\\src\\test\\java\\resources\\webdriver.properties",
        "file:D:\\Проекты Java\\autotest_ui\\src\\test\\java\\resources\\jiraglobal.properties"})
public class SpringConf {

    @Autowired
    Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    public <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass.getSimpleName().substring(0, 1)
                .toLowerCase().concat(tClass.getSimpleName().substring(1)), tClass);
    }
}
