package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikipediaPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "p-lang-btn")
    private WebElement languageButton;

    @FindBy(css = ".interlanguage-link-target")
    private List<WebElement> languageLinks;

    public WikipediaPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://pl.wikipedia.org/wiki/Wiki");
    }

    public void clickLanguageButton() {
        wait.until(ExpectedConditions.elementToBeClickable(languageButton));
        languageButton.click();
    }

    public List<WebElement> getLanguageLinks() {
        wait.until(driver -> !languageLinks.isEmpty());
        return languageLinks;
    }

    public String getLanguageName(WebElement languageElement) {
        return languageElement.getAttribute("data-language-autonym");
    }

    public String getLanguageUrl(WebElement languageElement) {
        return languageElement.getAttribute("href");
    }
}