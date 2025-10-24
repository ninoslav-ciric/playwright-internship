package assertion;

import org.testng.asserts.SoftAssert;
import pages.DashboardPage;


public class DashboardPageAsserts {
    private final SoftAssert softAssert;
    public DashboardPageAsserts(){softAssert = new SoftAssert();}

    public void validateVisible(DashboardPage dashboardPage)
    {
        softAssert.assertTrue(dashboardPage.isInterviewCardVisible());
        softAssert.assertTrue(dashboardPage.isPlaygroundCardVisible());
        softAssert.assertTrue(dashboardPage.isReportsCardVisible());
        softAssert.assertTrue(dashboardPage.isTestCasesCardVisible());
        softAssert.assertAll();
    }
}
