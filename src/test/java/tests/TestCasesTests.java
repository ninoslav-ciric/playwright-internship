package tests;
import assertion.TestCasesPageAsserts;
import base.TestBase;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import providers.TestCasesProviders;
import utils.AllureUtil;
import utils.Constants;

import static utils.ConfigReader.*;


public class TestCasesTests extends TestBase {

    @Test(dataProvider = "NewTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"positiveSmoke", "UI"})
    @Description("Verify successful add tests with valid credentials")
    public void testValidNewTestCase(String title, String description, String expectedResult, String[] testStep) {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToNewTestCase(getBaseUrl() +   Constants.TEST_CASES);
        AllureUtil.logStep("Fill form with valid data");
        testCasesPage.newTestCasesExpectSuccess(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that test case is added");
        testCasesPageAsserts.validateAddedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "ModifyTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"positiveSmoke", "UI"},
             dependsOnMethods = {"testValidNewTestCase" , "testNewTestCaseExisting"}
    )
    @Description("Verify successful modification test with valid credentials")
    public void testValidModificationTestCase(String title, String description, String expectedResult, String[] testStep)
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        AllureUtil.logStep("Fill form with valid data");
        testCasesPage.updateTestCaseExpectSuccess(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that test case is modified");
        testCasesPageAsserts.validateModifiedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "NewTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"negativeSmoke", "UI"},
            dependsOnMethods = {"testValidNewTestCase"}
    )
    @Description("Verify failure adding tests with existing credentials")
    public void testNewTestCaseExisting(String title, String description, String expectedResult, String[] testStep)
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToNewTestCase(getBaseUrl() + Constants.TEST_CASES);
        AllureUtil.logStep("Fill form with existing title");
        testCasesPage.newTestCasesExpectFailure(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that test case is not added");
        testCasesPageAsserts.validateExistingTestCase(testCasesPage);
    }



    @Test(dataProvider = "TestCaseTitle", dataProviderClass = TestCasesProviders.class, groups = {"positiveSmoke", "UI"},
            dependsOnMethods = {"testValidNewTestCase"}
    )
    @Description("Verify successful preview of test case")

    public void testPreviewTestCase(String title)
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        AllureUtil.logStep("Preview test case with desired title");
        testCasesPage.previewTestCaseExpectSuccess(title);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that preview is visible");
        testCasesPageAsserts.validatePreviewTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "TestCaseTitle", dataProviderClass = TestCasesProviders.class, groups = {"positiveSmoke", "UI"},
            dependsOnMethods = {"testValidNewTestCase" , "testPreviewTestCase" , "testNewTestCaseExisting" , "testValidModificationTestCase"},  invocationCount = 2
    )

    @Description("Verify successful deletion of test case")
    public void testDeleteTestCase(String title)
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(),getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        AllureUtil.logStep("Delete test case with desired title");
        testCasesPage.deleteTestCaseExpectSuccess(title);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that test case is deleted");
        testCasesPageAsserts.validateDeletedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "NewTestCaseMissingTitle", dataProviderClass = TestCasesProviders.class, groups = {"negativeSmoke", "UI"})
    @Description("Expecting error for missing title")
    public void testMissingTitle(String description, String expectedResult, String[] testStep)
    {
        AllureUtil.logStep("Login with valid credentials");
        loginPage.loginExpectSuccess(getValidEmail(),getValidPassword());
        AllureUtil.logStep("Navigate to TestCases page");
        testCasesPage.navigateToNewTestCase(getBaseUrl() + Constants.TEST_CASES);
        AllureUtil.logStep("Fill form without title");
        testCasesPage.newTestCasesExpectFailureMissingTitle(description,expectedResult,testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        AllureUtil.logStep("Validate that test case is not added");
        testCasesPageAsserts.validateMissingTitleTestCase(testCasesPage);
    }
}