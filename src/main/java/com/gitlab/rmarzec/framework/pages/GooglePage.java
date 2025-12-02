package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GooglePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "img[alt='Google']")
    private WebElement googleLogoElement;

    @FindBy(css = "button[id*='accept'], button[id*='L2AGLb']")
    private WebElement acceptCookiesButton;

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(name = "btnI")
    private WebElement luckyButton;

    public GooglePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.google.com/");
    }

    public void acceptCookies() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(acceptCookiesButton),
                ExpectedConditions.visibilityOf(googleLogoElement)
        ));

        if (acceptCookiesButton.isDisplayed() && acceptCookiesButton.isEnabled()) {
            acceptCookiesButton.click();
        }
    }

    public void search(String searchText) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.sendKeys(searchText);
    }

    public void clickLuckyButton() {
        wait.until(ExpectedConditions.elementToBeClickable(luckyButton));
        luckyButton.click();
    }
}
