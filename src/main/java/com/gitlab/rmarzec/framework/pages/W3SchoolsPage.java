package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class W3SchoolsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cookieAcceptButton = By.cssSelector(".fast-cmp-home-accept button");

    @FindBy(id = "fast-cmp-iframe")
    private WebElement cookieFrame;

    @FindBy(css = "a.w3-btn[href*='tryit']")
    private WebElement tryItButton;

    @FindBy(id = "cars")
    private WebElement carsDropdown;

    @FindBy(css = "h1")
    private WebElement selectElementHeader;

    public W3SchoolsPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.w3schools.com/tags/tag_select.asp");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cookieFrame));
            driver.switchTo().frame(cookieFrame);

            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();

            driver.switchTo().defaultContent();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }
    }

    public void printCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Actual address URL: " + currentUrl);
    }

    public void clickTryItButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tryItButton));
        tryItButton.click();
    }

    public void switchToTryItWindow() {
        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("iframeResult"));
    }

    public String getSelectElementHeader() {
        wait.until(ExpectedConditions.visibilityOf(selectElementHeader));
        return selectElementHeader.getText();
    }

    public void selectCarByVisibleText(String carName) {
        wait.until(ExpectedConditions.elementToBeClickable(carsDropdown));
        Select select = new Select(carsDropdown);
        select.selectByVisibleText(carName);
    }

    public Map<String, String> getSelectedOption() {
        Select select = new Select(carsDropdown);
        WebElement selectedOption = select.getFirstSelectedOption();

        String selectedText = selectedOption.getText();
        String selectedValue = selectedOption.getAttribute("value");

        Map<String, String> result = new HashMap<>();
        result.put("text", selectedText);
        result.put("value", selectedValue);

        return result;
    }
}