package tests;

import assertion.TestCasesPageAsserts;
import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TestCasesPage;

import static utils.AllureLogger.logStep;
import static utils.ConfigReader.getBaseUrl;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class TestCasesTests extends TestBase {

    private final TestCasesPageAsserts testCasePageAsserts = new TestCasesPageAsserts();
    protected TestCasesPage testCasesPage;

    @BeforeMethod(alwaysRun = true)
    @Step("Login and open Test Cases page before each test")
    public void loginAndOpenNewTestCase() {
        logStep("Logging in with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        logStep("Opening Test Cases page");
        testCasesPage = new TestCasesPage(page);
        testCasesPage.open(getBaseUrl());
    }

    @Test(
            description = "Verify fields are visible",
            groups = { "UI", "smoke"}
    )
    @Description("Ensures the New Test Case form shows all required fields (title, description, expected, steps, automated).")
    public void testNewTestCaseFieldsVisible() {
        logStep("Validating that all fields are visible on the New Test Case form");
        testCasePageAsserts.validateFieldsVisible(testCasesPage);
    }

    @Test(
            description = "Fill form and submit successfully",
            groups = { "UI"}
    )
    @Description("Fills out the New Test Case form with valid data and submits successfully.")
    public void testCreateNewTestCase() {
        String title = "Login shows dashboard - " + System.currentTimeMillis();
        String description = "User can log in and see the dashboard.";
        String expected = "Dashboard page is visible and avatar is present.";
        String[] steps = {
                "Open the app",
                "Login with valid credentials",
                "Verify Dashboard is displayed"
        };
        boolean automated = true;

        fillFormStep(title, description, expected, steps, automated);
        logStep("Form submitted — add assertions once Test Case List page is implemented");
        //  Add assert after TestCaseList page exists
    }

    @Test(
            description = "Add steps",
            groups = {"UI"}
    )
    @Description("Ensures user can add/modify steps in the form and the step count is correct.")
    public void testAddSteps() {
        ensureStepCountStep(5);
        setStepStep(3, "Click the Settings icon");
        setStepStep(4, "Verify Settings modal is visible");
        logStep("Validating the step count should be 5");
        testCasePageAsserts.validateStepCount(testCasesPage, 5);
    }

    @Test(
            description = "Toggle Automated OFF then ON",
            groups = {"UI"}
    )
    @Description("Verifies the Automated switch toggles OFF and ON and reflects correctly.")
    public void testToggleAutomated() {
        toggleAutomatedStep(false);
        logStep("Validating Automated switch is OFF");
        testCasePageAsserts.validateAutomatedSwitchOff(testCasesPage);

        toggleAutomatedStep(true);
        logStep("Validating Automated switch is ON");
        testCasePageAsserts.validateAutomatedSwitchOn(testCasesPage);
    }

    @Test(
            description = "Verify error when title is missing",
            groups = {"UI"}
    )
    @Description("Attempts to create a test case without a title and expects a validation error.")
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

        fillFormStep(title, description, expected, steps, automated);
        logStep("Validating title error message is displayed");
        testCasePageAsserts.validateTitleErrorMessageDisplayed(testCasesPage);
    }

    @Test(
            description = "Verify error when expected result is missing",
            groups = {"UI"}
    )
    @Description("Attempts to create a test case without an expected result and expects a validation error.")
    public void testCreateNewTestCaseWithoutExpected() {
        String title = "Verify user can't create test case without an expected result";
        String description = "Attempting to create test case without an expected result.";
        String expected = "";
        String[] steps = {
                "Open the app",
                "Login with valid credentials",
                "Verify Dashboard is displayed"
        };
        boolean automated = false;

        fillFormStep(title, description, expected, steps, automated);
        logStep("Validating expected result error message is displayed");
        testCasePageAsserts.validateExpectedErrorMessageDisplayed(testCasesPage);
    }

    @Test(
            description = "Verify all validation messages appear when submitting an empty form",
            groups = {"regression", "UI"}
    )
    @Description("Submits an empty form and verifies all relevant validation messages are displayed.")
    public void testSubmitEmptyFormShowsAllValidationMessages() {
        submitFormStep();
        logStep("Validating that all error messages are displayed for empty form submission");
        testCasePageAsserts.validateAllErrorMessagesDisplayed(testCasesPage);
    }


    @Step("Fill New Test Case form and submit")
    private void fillFormStep(String title, String description, String expected, String[] steps, boolean automated) {
        logStep(String.format("Filling form: title='%s', automated=%s", title, automated));
        testCasesPage.fillForm(title, description, expected, steps, automated);
    }

    @Step("Ensure step count is {count}")
    private void ensureStepCountStep(int count) {
        logStep("Ensuring step count is " + count);
        testCasesPage.ensureStepCount(count);
    }

    @Step("Set step #{index} to: {text}")
    private void setStepStep(int index, String text) {
        logStep(String.format("Setting step %d to '%s'", index, text));
        testCasesPage.setStep(index, text);
    }

    @Step("Toggle Automated to {state}")
    private void toggleAutomatedStep(boolean state) {
        logStep("Toggling Automated to " + (state ? "ON" : "OFF"));
        testCasesPage.setAutomated(state);
    }

    @Step("Submit form")
    private void submitFormStep() {
        logStep("Submitting New Test Case form");
        testCasesPage.submit();
    }
}
