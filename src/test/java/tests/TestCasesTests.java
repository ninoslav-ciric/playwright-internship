package tests;

import assertion.TestCasesPageAsserts;
import base.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import pages.TestCasesPage;

import static utils.ConfigReader.getBaseUrl;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class TestCasesTests extends TestBase {

    private final TestCasesPageAsserts testCasePageAsserts = new TestCasesPageAsserts();
    protected TestCasesPage testCasesPage;

    @BeforeMethod(alwaysRun = true)
    public void loginAndOpenNewTestCase() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasesPage = new TestCasesPage(page);
        testCasesPage.open(getBaseUrl());
    }

    @Test(description = "Verify fields are visible")
    public void testNewTestCaseFieldsVisible() {
        testCasePageAsserts.validateFieldsVisible(testCasesPage);
    }

    @Test(description = "Fill form and submit successfully")
    public void testCreateNewTestCase() {

        String title = " Login shows dashboard - " + System.currentTimeMillis();
        String description = "User can log in and see the dashboard.";
        String expected = "Dashboard page is visible and avatar is present.";
        String[] steps = {
                "Open the app",
                "Login with valid credentials",
                "Verify Dashboard is displayed"
        };
        boolean automated = true;


        testCasesPage.fillForm(title, description, expected, steps, automated);

        //Will add assert after I make testCaseList page
    }

    @Test(description = "Add steps ")
    public void testAddSteps() {

        testCasesPage.ensureStepCount(5);
        testCasesPage.setStep(3, "Click the Settings icon");
        testCasesPage.setStep(4, "Verify Settings modal is visible");
        testCasePageAsserts.validateStepCount(testCasesPage, 5);

    }

    @Test(description = "Toggle Automated OFF then ON ")
    public void testToggleAutomated() {
        testCasesPage.setAutomated(false);
        testCasePageAsserts.validateAutomatedSwitchOff(testCasesPage);

        testCasesPage.setAutomated(true);

        testCasePageAsserts.validateAutomatedSwitchOn(testCasesPage);
    }

    @Test(description = "Verify error when title is missing")
    public void testCreateNewTestCaseWithoutTitle() {
        String title = "";
        String description = "Attempting to create test case without a title.";
        String expected = "Dashboard page is visible";
        String[] steps = {
                "Open the app",
                "Login with valid credentials",
                "Verify Dashboard is displayed"
        };
        boolean automated = false;

        testCasesPage.fillForm(title, description, expected, steps, automated);

        testCasePageAsserts.validateTitleErrorMessageDisplayed(testCasesPage);
    }

    @Test(description = "Verify error when expected result is missing")
    public void testCreateNewTestCaseWithoutExpected() {
        String title = "Verify User can't create test case without an expected result";
        String description = "Attempting to create test case without an expected result.";
        String expected = "";
        String[] steps = {
                "Open the app",
                "Login with valid credentials",
                "Verify Dashboard is displayed"
        };
        boolean automated = false;

        testCasesPage.fillForm(title, description, expected, steps, automated);

        testCasePageAsserts.validateExpectedErrorMessageDisplayed(testCasesPage);
    }


    @Test(description = "Verify all validation messages appear when submitting an empty form")
    public void testSubmitEmptyFormShowsAllValidationMessages() {

        testCasesPage.submit();
        testCasePageAsserts.validateAllErrorMessagesDisplayed(testCasesPage);
    }


}
