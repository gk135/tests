package com.gitlab.rmarzec.framework.base;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.framework.utils.TestListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @BeforeMethod
    public void setUp() {
        DriverFactory driverFactory = new DriverFactory();
        driverFactory.initDriver();
        DriverFactory.getDriver().manage().window().setSize(new Dimension(1920, 1080));
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
