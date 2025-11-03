package assertion;

import org.testng.asserts.SoftAssert;
import pages.DashboardPage;

public class DashboardAsserts
{
    SoftAssert softAssert;

    public DashboardAsserts()
    {
        this.softAssert = new SoftAssert();
    }

    public void validateAllCardsAreVisible(DashboardPage dashboardPage)
    {
        softAssert.assertTrue(dashboardPage.isCardVisible("HTEC1 Interview"), "Profile card isn't visible");
        softAssert.assertTrue(dashboardPage.isCardVisible("Test Cases"), "Test Cases card isn't visible");
        softAssert.assertTrue(dashboardPage.isCardVisible("Playground"), "Project card isn't visible");
        softAssert.assertTrue(dashboardPage.isCardVisible("Reports"), "Reports card isn't visible");

        softAssert.assertAll();
    }
}
