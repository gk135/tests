package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.base.BaseTest;
import com.gitlab.rmarzec.framework.pages.WikipediaPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class Task2Test extends BaseTest {
    @Test
    public void Task2Test() {
        WikipediaPage wikipediaPage = new WikipediaPage();

        wikipediaPage.open();
        wikipediaPage.clickLanguageButton();
        List<WebElement> languages = wikipediaPage.getLanguageLinks();

        Assert.assertFalse(languages.isEmpty(), "Language list should not be empty\n");

        System.out.println("List of available languages:");

        for (WebElement language : languages) {
            String name = wikipediaPage.getLanguageName(language);

            if ("English".equals(name)) {
                System.out.println(name + " - URL: " + wikipediaPage.getLanguageUrl(language));
            } else {
                System.out.println(name);
            }
        }
    }
}
