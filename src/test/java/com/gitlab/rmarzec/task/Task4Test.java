package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.base.BaseTest;
import com.gitlab.rmarzec.framework.pages.YouTubeHomePage;
import com.gitlab.rmarzec.framework.pages.YouTubeSearchResultsPage;
import com.gitlab.rmarzec.framework.pages.YouTubeShortsPage;
import com.gitlab.rmarzec.framework.model.YTTile;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class Task4Test extends BaseTest {

    @Test
    public void Task4Test() {

        YouTubeHomePage youTubeHomePage = new YouTubeHomePage();
        youTubeHomePage.open();
        youTubeHomePage.acceptCookies();
        youTubeHomePage.clickShortsTab();

        YouTubeShortsPage shortsPage = new YouTubeShortsPage();
        String channelName = shortsPage.getChannelName();
        System.out.println("channel name: " + channelName);
        Assert.assertNotNull(channelName, "Channel name should not be null");

        youTubeHomePage.goToHomePage();
        youTubeHomePage.search("Live");

        YouTubeSearchResultsPage searchResultsPage = new YouTubeSearchResultsPage();
        List<YTTile> ytTileList = searchResultsPage.getFirst12Videos();

        Assert.assertFalse(ytTileList.isEmpty(), "The list of movies should not be empty");
        Assert.assertTrue(ytTileList.size() <= 12, "There should be a maximum of 12 films.");

        System.out.println("Clips:");
        for (YTTile tile : ytTileList) {
            System.out.println(tile.toString());
        }
    }
}
