package tests;

import assertion.DashboardPageAsserts;
import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;

import static utils.AllureLogger.logStep;
import static utils.ConfigReader.getBaseUrl;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class DashboardTests extends TestBase {

    private final DashboardPageAsserts dashboardAsserts = new DashboardPageAsserts();
    protected DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    @Step("Login and open dashboard before each test")
    public void loginAndOpenDashboard() {
        logStep("Logging in with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        logStep("Opening Dashboard page");
        dashboardPage = new DashboardPage(page);
        dashboardPage.open(getBaseUrl());
    }

    @Test(
            description = "Verify dashboard loads and key UI is visible (title, logout, avatar)",
            groups = {"regression", "smoke", "UI"}
    )
    @Description("Checks that dashboard loads correctly and key elements (title, logout button, and avatar) are visible.")
    public void testDashboardLoadsAndAvatarVisible() {
        logStep("Validating that dashboard page loaded successfully with avatar visible");
        dashboardAsserts.validateDashboardLoaded(dashboardPage);
    }

    @Test(
            description = "Verify side menu navigation links work from Dashboard",
            groups = {"regression", "UI"}
    )
    @Description("Verifies that all side menu navigation links (Profile, Test Cases, Playground, Reports) work properly from Dashboard.")
    public void testSideMenuNavigation() {
        navigateAndValidateProfile();
        navigateAndValidateDashboard();
        navigateAndValidateTestCases();
        navigateAndValidateDashboard();
        navigateAndValidatePlayground();
        navigateAndValidateDashboard();
        navigateAndValidateReports();
    }

    @Test(
            description = "Verify that all main dashboard navigation cards are visible",
            groups = {"regression", "UI"}
    )
    @Description("Ensures all main dashboard cards (Profile, Test Cases, Playground, Reports) are visible.")
    public void testDashboardCardsAreVisible() {
        logStep("Validating visibility of all dashboard cards");
        dashboardAsserts.validateDashboardCardsVisible(dashboardPage);
    }


    @Step("Navigate to Profile and validate")
    private void navigateAndValidateProfile() {
        logStep("Navigating to Profile page");
        dashboardPage.goToProfile();
        dashboardAsserts.validateNavigationToProfile(dashboardPage);
    }

    @Step("Navigate to Dashboard and validate")
    private void navigateAndValidateDashboard() {
        logStep("Navigating to Dashboard page");
        dashboardPage.goToDashboard();
        dashboardAsserts.validateDashboardLoaded(dashboardPage);
    }

    @Step("Navigate to Test Cases and validate")
    private void navigateAndValidateTestCases() {
        logStep("Navigating to Test Cases page");
        dashboardPage.goToTestCases();
        dashboardAsserts.validateNavigationToTestCases(dashboardPage);
    }

    @Step("Navigate to Playground and validate")
    private void navigateAndValidatePlayground() {
        logStep("Navigating to Playground page");
        dashboardPage.goToPlayground();
        dashboardAsserts.validateNavigationToPlayground(dashboardPage);
    }

    @Step("Navigate to Reports and validate")
    private void navigateAndValidateReports() {
        logStep("Navigating to Reports page");
        dashboardPage.goToReports();
        dashboardAsserts.validateNavigationToReports(dashboardPage);
    }
}
