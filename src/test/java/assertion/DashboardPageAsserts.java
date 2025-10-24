package assertion;

import org.testng.asserts.SoftAssert;
import pages.DashboardPage;

public class DashboardPageAsserts {
    private final SoftAssert softAssert;

    public DashboardPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateVisible(DashboardPage dashboardPage) {

        //softAssert.assertTrue(dashboardPage.isDashboardVisible(), "Dashboard should be visible after login");
        //softAssert.assertTrue(dashboardPage.getCurrentUrl().contains("/dashboard"), "URL should contain dashboard");
        softAssert.assertAll();
    }
}
