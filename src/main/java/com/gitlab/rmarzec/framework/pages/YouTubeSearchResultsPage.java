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

    public List<YTTile> getFirst12Videos() {
        List<YTTile> ytTileList = new ArrayList<>();
        Set<String> addedHrefs = new HashSet<>();

        wait.until(ExpectedConditions.presenceOfElementLocated(videoRenderers));

        loadEnoughVideos(30, 10);

        List<WebElement> videos = driver.findElements(videoRenderers);

        for (WebElement video : videos) {
            if (ytTileList.size() >= 12) break;
            WebElement titleEl = video.findElement(videoTitle);
            String href = titleEl.getAttribute("href");
            String title = titleEl.getAttribute("title");

            if (href == null || !href.contains("/watch?v=") || addedHrefs.contains(href))
                continue;

            String channel = extractChannel(video);
            String length = extractLength(video);

            ytTileList.add(new YTTile(title, channel, length));
            addedHrefs.add(href);
        }

        return ytTileList;
    }


    private void loadEnoughVideos(int minCount, int maxScrolls) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        for (int attempt = 0; attempt < maxScrolls; attempt++) {

            List<WebElement> videos = driver.findElements(videoRenderers);

            if (videos.size() >= minCount) {
                return;
            }

            WebElement last = videos.get(videos.size() - 1);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", last);
            } catch (Exception ignored) {
            }

            try {
                shortWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(videoRenderers, videos.size()));
            } catch (Exception ignored) {
            }
        }

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