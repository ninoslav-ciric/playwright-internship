package tests;

import assertion.DashboardAsserts;
import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;


import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getDashboardUrl;


public class DashboardTests extends TestBase
{
    public DashboardPage dashboardPage;
    public DashboardAsserts dashboardAsserts;


    @BeforeMethod
    public void prepareDashboard()
    {
        //setup page and asserts
        dashboardPage = new DashboardPage(page);
        dashboardAsserts = new DashboardAsserts();

        //login
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        //navigate to dashboard page
        dashboardPage.navigateTo(getDashboardUrl());
    }


    @Test(groups = {"smoke", "UI"},
            description = "Verify all dashboard cards are visible")
    public void allCardsAreVisible()
    {
        //assert
        dashboardAsserts.validateAllCardsAreVisible(dashboardPage);
    }


    @Test(groups = {"regression", "UI"},
            description = "Verify navigation to Test Cases through card")
    public void navigateToTestCases()
    {
        //click on test cases card
        dashboardPage.openCard("Test Cases");

        //assert
        Assert.assertTrue(dashboardPage.getUrl().contains("/testcases"), "Didn't navigate to Test Cases page");
    }


    @Test(groups = {"regression", "UI"},
            description = "Verify navigation for all dashboard cards")
    public void navigateToAllDashboardCards()
    {
        var allCards = dashboardPage.getAllCardsInMap();

        for(var card : allCards.entrySet())
        {
            //get name and locator
            String cardName = card.getKey();
            String locator = card.getValue();

            //open card
            dashboardPage.openCard(cardName);

            //card assert
            Assert.assertTrue(dashboardPage.getUrl().contains(locator), "Navigation failed for card: " + cardName);

            //return to dashboard
            dashboardPage.navigateTo(getDashboardUrl());
        }
    }
}
