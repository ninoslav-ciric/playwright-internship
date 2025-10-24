package tests;
import assertion.TestCasesPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import providers.TestCasesProviders;
import utils.Constants;

import static utils.ConfigReader.*;


public class TestCasesTests extends TestBase {

    @Test(dataProvider = "NewTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Verify successful add tests with valid credentials"
    )
    public void testValidNewTestCase(String title, String description, String expectedResult, String[] testStep) {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasesPage.navigateToNewTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.newTestCasesExpectSuccess(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validateAddedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "ModifyTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Verify successful modification test with valid credentials" , dependsOnMethods = {"testValidNewTestCase" , "testNewTestCaseExisting"}
    )
    public void testValidModificationTestCase(String title, String description, String expectedResult, String[] testStep)
    {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.updateTestCaseExpectSuccess(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validateModifiedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "NewTestCaseSuccess", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Verify failure adding tests with existing credentials" , dependsOnMethods = {"testValidNewTestCase"}
    )
    public void testNewTestCaseExisting(String title, String description, String expectedResult, String[] testStep)
    {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasesPage.navigateToNewTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.newTestCasesExpectFailure(title, description, expectedResult, testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validateExistingTestCase(testCasesPage);
    }



    @Test(dataProvider = "TestCaseTitle", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Verify successful preview of test case", dependsOnMethods = {"testValidNewTestCase"}
    )

    public void testPreviewTestCase(String title)
    {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.previewTestCaseExpectSuccess(title);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validatePreviewTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "TestCaseTitle", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Verify successful deletion of test case", dependsOnMethods = {"testValidNewTestCase" , "testPreviewTestCase" , "testNewTestCaseExisting" , "testValidModificationTestCase"}
    )
    public void testDeleteTestCase(String title)
    {
        loginPage.loginExpectSuccess(getValidEmail(),getValidPassword());
        testCasesPage.navigateToTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.deleteTestCaseExpectSuccess(title);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validateDeletedTestCase(testCasesPage, title);
    }


    @Test(dataProvider = "NewTestCaseMissingTitle", dataProviderClass = TestCasesProviders.class, groups = {"smoke", "UI"},
            description = "Expecting error for missing title"
    )
    public void testMissingTitle(String description, String expectedResult, String[] testStep)
    {
        loginPage.loginExpectSuccess(getValidEmail(),getValidPassword());
        testCasesPage.navigateToNewTestCase(getBaseUrl() + Constants.TEST_CASES);
        testCasesPage.newTestCasesExpectFailureMissingTitle(description,expectedResult,testStep);
        TestCasesPageAsserts testCasesPageAsserts = new TestCasesPageAsserts();
        testCasesPageAsserts.validateMissingTitleTestCase(testCasesPage);
    }
}