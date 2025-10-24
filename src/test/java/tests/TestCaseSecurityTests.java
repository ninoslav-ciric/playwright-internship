package tests;

import assertion.TestCasePageAsserts;
import assertion.ErrorTestCasePageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.Test;
import pages.TestCasePage;

import static utils.ConfigReader.*;

public class TestCaseSecurityTests extends TestBase {

    public void LoginAndTestCreation() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getTestCaseUrl());
    }

    ErrorTestCasePageAsserts testCasePageAsserts = new ErrorTestCasePageAsserts();

    @Test(groups = {"regression", "UI"},
            description = "Verify test case can't be created with same title."
    )
    public void testCreationTestCaseFail() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCasesForError(getTestCaseUrl());

        testCasePage.createExistingNameTestCase("Existing", "Existing");

        testCasePageAsserts.validateErrorTestCase(testCasePage);
    }
}