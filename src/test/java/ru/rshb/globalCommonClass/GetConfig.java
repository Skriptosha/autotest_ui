package ru.rshb.globalCommonClass;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class GetConfig {
    private static ConcurrentHashMap<Long, Properties> thread_Config = new ConcurrentHashMap<>();

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
        FileInputStream fileInputStream = null;
        if (nameProperties != null && !"".equals(nameProperties)) {
            try {
                fileInputStream = new FileInputStream("src/test/java/resources/" + nameProperties +
                        ".properties");
                properties.load(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null)
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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