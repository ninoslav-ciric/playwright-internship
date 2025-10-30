package assertion;

import org.testng.asserts.SoftAssert;
import pages.DashboardPage;

public class DashboardPageAsserts {

    private final SoftAssert softAssert;

    public DashboardPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateDashboardLoaded(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.isLoaded(), "Dashboard should be visible and loaded correctly");
        softAssert.assertTrue(dashboardPage.isAvatarVisible(), "Avatar should be visible on the dashboard");
        softAssert.assertTrue(dashboardPage.url().contains("/dashboard"), "URL should contain '/dashboard'");
        softAssert.assertAll();
    }

    public void validateNavigationToProfile(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.url().contains("/profile"), "URL should contain '/profile' after navigation");
        softAssert.assertAll();
    }

    public void validateNavigationToTestCases(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.url().contains("/testcases"), "URL should contain '/testcases' after navigation");
        softAssert.assertAll();
    }

    public void validateNavigationToPlayground(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.url().contains("/projects"), "URL should contain '/projects' after navigation");
        softAssert.assertAll();
    }

    public void validateNavigationToReports(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.url().contains("/reports"), "URL should contain '/reports' after navigation");
        softAssert.assertAll();
    }

    public void validateDashboardCardsVisible(DashboardPage dashboardPage) {
        softAssert.assertTrue(dashboardPage.getCardProfile().isVisible(), "Profile card should be visible on the dashboard");
        softAssert.assertTrue(dashboardPage.getCardTestCases().isVisible(), "Test Cases card should be visible on the dashboard");
        softAssert.assertTrue(dashboardPage.getCardPlayground().isVisible(), "Playground card should be visible on the dashboard");
        softAssert.assertTrue(dashboardPage.getCardReports().isVisible(), "Reports card should be visible on the dashboard");
        softAssert.assertAll();
    }

}
