package com.gitlab.rmarzec.framework.utils;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DriverFactory {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public WebDriver initDriver() {
        configureLogging();

        WebDriverManager.getInstance(FirefoxDriver.class)
                .driverVersion("0.30.0")
                .setup();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages", "pl");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.addPreference("devtools.console.stdout.content", false);
        options.addPreference("browser.dom.window.dump.enabled", false);
        options.setLogLevel(org.openqa.selenium.firefox.FirefoxDriverLogLevel.FATAL);
        options.addArguments("--log-level=3");


        WebDriver webDriver = new FirefoxDriver(options);
        tlDriver.set(webDriver);
        return getDriver();
    }
    private void configureLogging() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.edge.silentOutput", "true");
        System.setProperty("webdriver.firefox.logfile", "/dev/null");
        System.setProperty("webdriver.gecko.driver.silent", "true");

        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.remote").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.remote.ProtocolHandshake").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("io.netty").setLevel(java.util.logging.Level.OFF);

    }

}
