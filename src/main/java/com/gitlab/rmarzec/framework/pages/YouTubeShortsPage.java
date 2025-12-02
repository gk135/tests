package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YouTubeShortsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "span.ytReelChannelBarViewModelChannelName a.yt-core-attributed-string__link")
    private WebElement channelName;

    public YouTubeShortsPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getChannelName() {
        wait.until(ExpectedConditions.visibilityOf(channelName));
        return channelName.getText().trim();
    }

}
