package tests;

import assertion.TestCasePageAsserts;
import assertion.ErrorTestCasePageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.Test;
import pages.TestCasePage;

import static utils.Allure.logStep;
import static utils.ConfigReader.*;

public class TestCaseSecurityTests extends TestBase {

    public void LoginAndTestCreation() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getTestCaseUrl());
    }

    ErrorTestCasePageAsserts testCasePageAsserts = new ErrorTestCasePageAsserts();

    @Test(groups = {"regression", "UI", "negative"},
            description = "Verify test case can't be created with same title."
    )
    public void testCreationTestCaseFail() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCasesForError(getTestCaseUrl());
        logStep("INFO: Create Test Casse With Existing Title");
        testCasePage.createExistingNameTestCase("Existing", "Existing");
        logStep("PASS: TestCase With Existing Title created");
        logStep("INFO: Verify Error for Existing Title");
        testCasePageAsserts.validateErrorTestCase(testCasePage);
        logStep("PASS: Error for Existing Title Verified");
    }
}