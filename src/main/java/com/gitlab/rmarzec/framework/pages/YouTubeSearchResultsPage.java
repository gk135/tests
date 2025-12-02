package com.gitlab.rmarzec.framework.pages;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.framework.model.YTTile;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class YouTubeSearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public YouTubeSearchResultsPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    private final By videoRenderers = By.cssSelector("ytd-video-renderer");
    private final By videoTitle = By.cssSelector("a#video-title");
    private final By channelName = By.cssSelector(".long-byline a, #channel-name a");
    private final By badge = By.cssSelector(".yt-badge-shape--live");
    private final By timeElement = By.cssSelector("span#text.ytd-thumbnail-overlay-time-status-renderer, #time-status");

    public List<YTTile> loadVideoElements(int videoCount) {
        Map<String, YTTile> validVideosMap = new LinkedHashMap<>();

        wait.until(ExpectedConditions.presenceOfElementLocated(videoRenderers));

        while (validVideosMap.size() < videoCount) {
            List<WebElement> allRenderers = driver.findElements(videoRenderers);

            for (WebElement video : allRenderers) {
                if (validVideosMap.size() >= videoCount) break;

                try {
                    WebElement titleEl = video.findElement(videoTitle);
                    String href = titleEl.getAttribute("href");

                    if (href == null || !href.contains("/watch?v=")) {
                        continue;
                    }

                    if (validVideosMap.containsKey(href)) {
                        continue;
                    }

                    String title = titleEl.getAttribute("title");
                    String channel = extractChannel(video);
                    String length = extractLength(video);

                    validVideosMap.put(href, new YTTile(title, channel, length));
                } catch (org.openqa.selenium.NoSuchElementException ignored) {
                }
            }

            if (validVideosMap.size() >= videoCount) {
                break;
            }

            int currentRendererCount = allRenderers.size();

            WebElement last = allRenderers.get(allRenderers.size() - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", last);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

            try {
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(videoRenderers, currentRendererCount));
            } catch (TimeoutException e) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

                try {
                    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(videoRenderers, currentRendererCount));
                } catch (TimeoutException ex) {
                    break;
                }
            }
        }

        return new ArrayList<>(validVideosMap.values());
    }

    private String extractChannel(WebElement video) {
        WebElement link = video.findElement(channelName);
        return (link.getAttribute("textContent").trim());
    }

    private String extractLength(WebElement video) {
        if (!video.findElements(badge).isEmpty()) {
            return "LIVE";
        }
        return video.findElement(timeElement).getAttribute("textContent").trim();
    }
}