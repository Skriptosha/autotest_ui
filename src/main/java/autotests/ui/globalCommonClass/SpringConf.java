package autotests.ui.globalCommonClass;

import autotests.ui.globalCommonClass.old.GetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackageClasses = {GetConfig.class},
basePackages = {"autotests.ui.globalCommonClass", "tests.jira.tests", "autotests.ui.legalEntities.pages",
"tests.legalEntities.tests"}, lazyInit = true)
@PropertySource({"classpath:webdriver.properties",
        "classpath:jira/jiraglobal.properties",
"classpath:legalEntites/legalEntites.properties"})
public class SpringConf {

    @Autowired
    private Environment environment;

    @Bean
    public Logger logger(){
        try {
            LogManager.getLogManager()
                    .readConfiguration(Files.newInputStream(Paths.get(Objects.requireNonNull
                            (environment.getProperty("logger.path")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Logger.getLogger("autotests");
    }
}
