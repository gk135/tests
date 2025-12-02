package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.base.BaseTest;
import com.gitlab.rmarzec.framework.pages.GooglePage;
import com.gitlab.rmarzec.framework.pages.W3SchoolsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Task3Test extends BaseTest {

    @Test
    public void Task3Test() {
        GooglePage googlePage = new GooglePage();
        googlePage.open();
        googlePage.acceptCookies();
        googlePage.search("HTML select tag - W3Schools");
        googlePage.clickLuckyButton();

        W3SchoolsPage w3SchoolsPage = new W3SchoolsPage();
        String expectedUrl = "https://www.w3schools.com/tags/tag_select.asp";
        String currentUrl = w3SchoolsPage.getCurrentUrl();

        if (!currentUrl.equals(expectedUrl)) {
            w3SchoolsPage.open();
        }

        w3SchoolsPage.acceptCookies();
        w3SchoolsPage.clickTryItButton();
        w3SchoolsPage.switchToTryItWindow();

        System.out.println("Header: " + w3SchoolsPage.getSelectElementHeader());

        String modelInput = "Opel";
        w3SchoolsPage.selectCarByVisibleText(modelInput);
        w3SchoolsPage.clickSubmit();
        String selectedValue = w3SchoolsPage.getSelectedOptionValue();
        System.out.println(modelInput + "," + selectedValue);
        Assert.assertEquals(selectedValue, "opel", "The selected value should be ‘opel’");
    }
}
