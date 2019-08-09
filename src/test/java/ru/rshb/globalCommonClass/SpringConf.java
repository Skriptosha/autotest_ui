package ru.rshb.globalCommonClass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {GetConfig.class},
basePackages = {"ru.rshb.globalCommonClass", "ru.rshb.jira.tests"}, lazyInit = true)
public class SpringConf {

    private ApplicationContext applicationContext;

    SpringConf(){
        applicationContext = new AnnotationConfigApplicationContext(SpringConf.class);
    }

    @Bean
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass.getSimpleName().substring(0, 1)
                .toLowerCase().concat(tClass.getSimpleName().substring(1)), tClass);
    }
}
