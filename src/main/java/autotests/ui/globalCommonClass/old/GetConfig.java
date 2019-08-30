package autotests.ui.globalCommonClass.old;


import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GetConfig {
    private static ConcurrentHashMap<Long, Properties> thread_Config = new ConcurrentHashMap<>();

    private static final String pack = "src/test/resources/";

    /**
     * Файл properties должен находиться в src/test/resources/. Для загрузки необходимого файла properties,
     * необходимо вызвать setNameProperties с именем файла.
     * Далее для получения значения вызывается getProperty(key)
     */
    public static void setNameProperties(String nameProperties) {
        readFile(nameProperties);
    }

    private static void readFile(String nameProperties) {
        Properties properties = new Properties();
        if (nameProperties != null && !"".equals(nameProperties)) {
            try {
                properties.load(new FileInputStream(pack + nameProperties +
                        ".properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            thread_Config.put(Thread.currentThread().getId(), properties);
        } else {
            throw new IllegalStateException("Необходимо вызвать setNameProperties!");
        }
    }

    public static String getProperty(String key) {
        String encodeString = thread_Config.get(Thread.currentThread().getId()).getProperty(key);
        if (encodeString == null) {
            throw new IllegalStateException("Не найдено значение по ключу " + key + "! Файл properties: "
                    + thread_Config.get(Thread.currentThread().getId()));
        }
        return encodeString;
    }
}