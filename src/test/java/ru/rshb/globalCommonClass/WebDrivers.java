package ru.rshb.globalCommonClass;

public enum WebDrivers {

    opera("opera"),
    ie("ie"),
    firefox("firefox"),
    chrome("chrome");

    private String browser;

    WebDrivers(String browser) {
        this.browser = browser;
    }

    public String getBrowser() {
        return browser;
    }
}
