package tests;

import assertion.LoginPageAsserts;
import base.TestBase;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utils.AllureUtil;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;


public class LoginTests extends TestBase {

    @Test(groups = {"positiveSmoke", "UI"})
    @Description("Verify successful login with valid credentials")
    public void testValidLogin() {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        AllureUtil.logStep("Redirect to dashboards page after login");
        loginPageAsserts.validateLogin(loginPage);

    }
}