package tests;

import assertion.TestCaseAsserts;
import base.TestBase;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.TestCasePage;

import static utils.ConfigReader.*;

public class TestCaseTests extends TestBase {

    private TestCasePage testCasePage;

    //Positive
    @Test
    @Description("Verify successful creation of new test case with valid data")
    public void testValidNewTestCase() {
        String title = "DusanTest";
        String desc = "This is description";
        String expResult = "Success";
        String stepOne = "Click button";
        testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");
        testCasePage.createNewTestCaseExpectSuccess(title, desc, expResult, stepOne);

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateCreationNewTestCase(testCasePage, title, desc, expResult);
    }

    @Test
    @Description("Verify successful deletion of an already existing test case")
    public void testValidDeleteTestCase() {
        String title = "DusanTest";
        testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");
        testCasePage.deleteTestCaseExpectSuccess(title);

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateDeletionTestCase(testCasePage, title);
    }

    @Test
    @Description("Verify successful update of a test case with valid data")
    public void testValidUpdateTestCase() {
        String oldTitle = "DusanTest";
        String newTitle = "DusanNewTest";
        String newDesc = "Updated description by Dusan Petrovic";
        String newResult = "Updated result";
        String newTestStep1 = "New test step1";
        String newTestStep2 = "New test step2";
        boolean isAutomated = true;

        testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");
        testCasePage.updateTestCaseExpectSuccess(
                oldTitle, newTitle, newDesc, newResult,
                newTestStep1, newTestStep2, isAutomated);

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateUpdateTestCaseValidData(testCasePage, newTitle, newDesc, newResult);
    }

    @Test
    @Description("Verify that preview is correctly shown")
    public void testValidPreviewShow() {
        String title = "DusanTest";
        testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");

        testCasePage.showPreviewExpectedSuccess(title);

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateShowTestCasePreview(testCasePage, title);
    }

    @Test
    @Description("Verify that clicking the back arrow on the Test Cases page correctly navigates to the Dashboard.")
    public void testNavigateBackButton() {
        TestCasePage testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");

        testCasePage.clickNavigateBackButton();

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateClickBackButton(testCasePage);
    }

    //Negative
    @Test
    @Description("Verify that updating a test case with an already existing title shows validation error and fails to save.")
    public void testInvalidUpdateWithDuplicateTitle() {
        testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");

        testCasePage.updateTestCaseExpectSuccess(
                "DusanTest",
                "DusanExistingTest",
                "Updated description by Dusan Petrovic",
                "Updated result",
                "New test step1",
                "New test step2",
                true
        );

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateUpdateTestCaseInvalidData(testCasePage);
    }

    @Test
    @Description("Verify that creating a test case without entering Expected Result shows validation error and fails to submit.")
    public void testCreateTestCaseWithoutExpectedResult() {
        TestCasePage testCasePage = new TestCasePage(page);

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getBaseUrl() + "testcases");

        // Try to create a test case without expected result
        testCasePage.createTestCaseWithoutExpectedResult(
                "Missing Expected Result Test",
                "This description is fine but expected result is missing",
                "Click button"
        );

        TestCaseAsserts testCaseAsserts = new TestCaseAsserts();
        testCaseAsserts.validateCreateWithoutExpectedResult(testCasePage);
    }
}
