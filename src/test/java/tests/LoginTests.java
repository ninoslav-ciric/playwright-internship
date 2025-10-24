package tests;

import assertion.LoginPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.TokenHandler;

import static utils.ConfigReader.*;

public class LoginTests extends TestBase {

    @Test(groups = {"smoke", "UI"},
            description = "Verify successful login with valid credentials"
    )
    public void testValidLogin() {
        currentPage = new LoginPage(page);
        LoginPage loginPage = (LoginPage) currentPage;
        loginPage.navigateToLogin(getBaseUrl());
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);
        loginPageAsserts.validateLoginToken(loginPage);

        TokenHandler.saveAuthToken(loginPage);
    }
}