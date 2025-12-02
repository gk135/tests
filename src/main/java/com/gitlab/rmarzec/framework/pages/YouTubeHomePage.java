package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YouTubeHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cookieOverlay = By.cssSelector("tp-yt-iron-overlay-backdrop.opened");

    @FindBy(css = "ytd-consent-bump-v2-lightbox ytd-button-renderer:nth-of-type(2) button.yt-spec-button-shape-next--filled")
    private WebElement acceptCookiesButton;

    @FindBy(css = "a[title='Shorts']")
    private WebElement shortsTab;

    @FindBy(css = "a#logo")
    private WebElement homeButton;

    @FindBy(css = "input.yt-searchbox-input")
    private WebElement searchBox;

    @FindBy(css = "button.ytSearchboxComponentSearchButton")
    private WebElement searchButton;


    public YouTubeHomePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.youtube.com/");
    }

    public void acceptCookies() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            acceptButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieOverlay));

        } catch (Exception ignored) {
        }
    }

    public void clickShortsTab() {
        WebElement shorts = wait.until(ExpectedConditions.elementToBeClickable(shortsTab));
        shorts.click();
    }

    public void goToHomePage() {
        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(homeButton));
        home.click();
    }

    public void search(String search) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        searchInput.sendKeys(search);

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();
    }
}
