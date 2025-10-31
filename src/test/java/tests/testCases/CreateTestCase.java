package tests.testCases;

import assertion.LoginPageAsserts;
import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import utils.AllureSteps;


import java.util.Random;

public class CreateTestCase extends TestBase {
    AllureSteps steps = new AllureSteps();
    //positive
    @Test(groups = {"positive"},
            description = "Verify successful creation of a new test case"
    )
    public void testAddNewTestCase(){
        Random random = new Random();
        login();
        navigateToTestCasePage();
        String title = "Test case " + random.nextLong(100, 100000);

        steps.logStep("Creating new test case with title: " + title);
        testCasePage.createTestCaseExpectSuccess(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

        steps.logStep("Verify the new test case is visible");
        testCasePageAsserts.assertAddNewTestCase(testCasePage, title);
    }

    //negative
    @Test(groups = {"negative"},
            description = "Verify unsuccessful creation of a new test case"
    )
    public void testInvalidAddNewTestCase(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Playground page");
        navigateToTestCasePage();
        String title = "Invalid case title";

        steps.logStep("Creating new test case with title: " + title);
        testCasePage.createTestCaseExpectFailureNoExpectedResult(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

        steps.logStep("Verify the new test case is visible");
        testCasePageAsserts.assertAddNewTestFailNoExpectedResult(testCasePage, title);
    }

    //negative
    @Test(groups = {"negative"},
            description = "Verify unsuccessful creation of a new test case with title that already exists"
    )
    public void testAddNewTestCaseExistingTitle(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Playground page");
        navigateToTestCasePage();

        String title = testCasePage.getRandomTestcase().textContent();

        steps.logStep("Creating new test case with title: " + title);
        testCasePage.createTestCaseExpectFailureExistingTitle(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

        steps.logStep("Verify the new test case is not visible");
        testCasePageAsserts.assertAddNewTestFailExistingTitle(testCasePage, title);
    }

    @Test(groups = {"negative"},
            description = "Verify unsuccessful login with invalid credentials"
    )
    public void testInvalidLogin() {
        steps.logStep("Login to the application");
        loginPage.loginExpectSuccess("invalid", "invalid");

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();

        steps.logStep("Verify login to the application");
        loginPageAsserts.validateLogin(loginPage);

    }

    @Test(groups = {"skipped"},
            description = "Verify successful creation of a new test case",
            dependsOnMethods = {"testInvalidLogin"}
    )
    public void testSkippedAddNewTestCase(){
        Random random = new Random();
        login();
        navigateToTestCasePage();
        String title = "Test case " + random.nextLong(100, 100000);

        steps.logStep("Creating new test case with title: " + title);
        testCasePage.createTestCaseExpectSuccess(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

        steps.logStep("Verify the new test case is visible");
        testCasePageAsserts.assertAddNewTestCase(testCasePage, title);
    }
}
