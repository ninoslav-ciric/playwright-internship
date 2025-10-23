package tests;

import assertion.DashboardPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import pages.DashboardPage;

import static utils.ConfigReader.getBaseUrl;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class DashboardTests extends TestBase {

    private final DashboardPageAsserts dashboardAsserts = new DashboardPageAsserts();

    @Test(description = "Verify dashboard loads and key UI is visible (title, logout, avatar)")
    public void testDashboardLoadsAndAvatarVisible() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        DashboardPage dashboardPage = new DashboardPage(page);
        dashboardPage.open(getBaseUrl());

        dashboardAsserts.validateDashboardLoaded(dashboardPage);
    }

    @Test(description = "Verify side menu navigation links work from Dashboard")
    public void testSideMenuNavigation() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        DashboardPage dashboardPage = new DashboardPage(page);
        dashboardPage.open(getBaseUrl());

        dashboardPage.goToProfile();
        dashboardAsserts.validateNavigationToProfile(dashboardPage);

        dashboardPage.goToDashboard();
        dashboardAsserts.validateDashboardLoaded(dashboardPage);

        dashboardPage.goToTestCases();
        dashboardAsserts.validateNavigationToTestCases(dashboardPage);

        dashboardPage.goToDashboard();
        dashboardAsserts.validateDashboardLoaded(dashboardPage);

        dashboardPage.goToPlayground();
        dashboardAsserts.validateNavigationToPlayground(dashboardPage);

        dashboardPage.goToDashboard();
        dashboardAsserts.validateDashboardLoaded(dashboardPage);

        dashboardPage.goToReports();
        dashboardAsserts.validateNavigationToReports(dashboardPage);
    }

    @Test(description = "Verify that all main dashboard navigation cards (Profile, Test Cases, Playground, Reports) are visible")
    public void testDashboardCardsAreVisible() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        DashboardPage dashboardPage = new DashboardPage(page);
        dashboardPage.open(getBaseUrl());

        dashboardPage.waitForCardsToBeVisible();

        dashboardAsserts.validateDashboardCardsVisible(dashboardPage);


    }

}
