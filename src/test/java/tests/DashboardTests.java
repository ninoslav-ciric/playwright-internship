package tests;

import assertion.DashboardPageAsserts;
import base.TestBase;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utils.AllureUtil;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class DashboardTests extends TestBase {

    @Test(groups = {"positiveSmoke", "UI"})
    @Description("Verify successful that cards are visible")
    public void testValidCards()
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        DashboardPageAsserts dashboardPageAsserts = new DashboardPageAsserts();
        AllureUtil.logStep("Validate that dashboard page is visible");
        dashboardPageAsserts.validateVisible(dashboardPage);
    }
}
