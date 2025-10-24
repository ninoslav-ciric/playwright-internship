package tests;

import assertion.DashboardPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class DashboardTests extends TestBase {

    @Test(groups = {"smoke", "UI"},
            description = "Verify successful that cards are visible"
    )
    public void testValidCards()
    {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        DashboardPageAsserts dashboardPageAsserts = new DashboardPageAsserts();
        dashboardPageAsserts.validateVisible(dashboardPage);
    }
}
