package tests;

import assertion.LoginPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;


public class LoginTests extends TestBase {

    @Test(groups = {"smoke", "UI", "positive"},
        description = "Verify successful login with valid credentials"
    )
    public void testValidLogin() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

    }

//    @Test(groups = {"negative"},
//            description = "Verify unsuccessful login with invalid credentials"
//    )
//    public void testInvalidLogin() {
//
//        loginPage.loginExpectSuccess("invalid", "invalid");
//
//        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
//        loginPageAsserts.validateLogin(loginPage);
//
//    }


}