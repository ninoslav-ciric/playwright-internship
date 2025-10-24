package assertion;

import org.testng.asserts.SoftAssert;
import pages.LoginPage;

public class LoginPageAsserts extends AssertsBase {

    public void validateLogin(LoginPage loginPage) {

        softAssert.assertTrue(loginPage.isDashboardVisible(), "Dashboard should be visible after login");
        softAssert.assertTrue(loginPage.getCurrentUrl().contains("/dashboard"), "URL should contain dashboard");
        softAssert.assertAll();
    }

    public void invalidateLogin(LoginPage loginPage) {

        softAssert.assertNotNull(loginPage.getErrorMessage());
        softAssert.assertAll();
    }


}
