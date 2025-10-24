package tests;

import assertion.LoginPageAsserts;
import assertion.TestCaseNewAsserts;
import base.TestBase;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static utils.ConfigReader.*;

public class TestCasesTests extends TestBase {

    Random random = new Random();

    public void login() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);
    }

    @Test(description = "Create Test with valid data")
    public void createTestValidData() { //positive

        login();

        loginPage.navigateToTestCases(getTestCasesUrl());
        List<String> titleLista = testCasesNewPage.getAllTitles();

        loginPage.navigateToTestCases(getNewTestCasesUrl());

        String validTitle = "AK_TestCase_1";
        while (titleLista.contains(validTitle))
        {
            validTitle = "AK_TestCase_" + random.nextInt(1000);
        }

        String validDescription = "Description " + random.nextInt(1000);
        String validExpectedResult = "ExpectedResult " + random.nextInt(1000);
        String validStep = "Step " + random.nextInt(1000);

        testCasesNewPage.newCaseExpectSuccess(validTitle, validDescription, validExpectedResult, validStep);
        TestCaseNewAsserts testCaseNewAsserts = new TestCaseNewAsserts();
        testCaseNewAsserts.validateNewCase(testCasesNewPage);
    }


    @Test(description = "Create Test with invalid title")
    public void createTestInvalidTitle() { // negative

        login();

        loginPage.navigateToTestCases(getTestCasesUrl());
        List<String> titleLista = testCasesNewPage.getAllTitles();

        loginPage.navigateToTestCases(getNewTestCasesUrl());


        String invalidTitle = titleLista.isEmpty() ? "" : titleLista.getFirst();
        String validDescription = "Description " + random.nextInt(1000);
        String validExpectedResult = "ExpectedResult " + random.nextInt(1000);
        String validStep = "Step " + random.nextInt(1000);

        testCasesNewPage.newCaseExpectFailure(invalidTitle, validDescription, validExpectedResult, validStep);
        TestCaseNewAsserts testCaseNewAsserts = new TestCaseNewAsserts();
        testCaseNewAsserts.invalidateNewCase(testCasesNewPage);
    }

    @Test(description = "Create Test with invalid data")
    public void createTestInvalidData() { // negative

        login();

        loginPage.navigateToTestCases(getTestCasesUrl());
        List<String> titleLista = testCasesNewPage.getAllTitles();

        loginPage.navigateToTestCases(getNewTestCasesUrl());


        String validTitle = "AK_TestCase_1";
        while (titleLista.contains(validTitle))
        {
            validTitle = "AK_TestCase_" + random.nextInt(1000);
        }
        String invalidDescription = "";
        String invalidExpectedResult = "";
        String invalidStep = "";

        testCasesNewPage.newCaseExpectFailure(validTitle, invalidDescription, invalidExpectedResult, invalidStep);
        TestCaseNewAsserts testCaseNewAsserts = new TestCaseNewAsserts();
        testCaseNewAsserts.invalidateNewCaseData(testCasesNewPage);
    }

    @Test(description = "Delete TestCase")
    public void deleteTestCase() { // positive

        login();

        loginPage.navigateToTestCases(getTestCasesUrl());
        List<String> titleLista = testCasesNewPage.getAllTitles();

        if (titleLista.isEmpty()) { return; }

        String titleToDelete = titleLista.getFirst();
        testCasesNewPage.deleteTestCase(titleToDelete);

        List<String> updatedTitles = testCasesNewPage.getAllTitles();

        TestCaseNewAsserts testCaseNewAsserts = new TestCaseNewAsserts();
        testCaseNewAsserts.deleteCase(titleToDelete, updatedTitles);

    }

    @Test(description = "Add steps to set")
    public void testAddSteps() { // positive

        login();

        loginPage.navigateToTestCases(getNewTestCasesUrl());

        testCasesNewPage.stepCount(5);
        testCasesNewPage.setStep(0, "initial step");
        testCasesNewPage.setStep(1, "first step");

        TestCaseNewAsserts testCaseNewAsserts = new TestCaseNewAsserts();
        testCaseNewAsserts.validateStepCount(testCasesNewPage, 5);

    }

}
