package tests;

import assertion.TestCasesAsserts;
import base.TestBase;
import constants.DataProviderNames;
import constants.SuccessMessages;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TestCasesPage;
import provider.TestCasesProvider;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getTestCasesUrl;

public class TestCasesTests extends TestBase
{
    public TestCasesPage testCasesPage;
    public TestCasesAsserts testCasesAsserts;

    @BeforeMethod
    public void prepareTestCases()
    {
        //setup test cases page and assert
        testCasesPage = new TestCasesPage(page);
        testCasesAsserts = new TestCasesAsserts();

        //login
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        //navigate to test cases page
        testCasesPage.navigateTo(getTestCasesUrl());
    }

    @Test(groups = {"smoke", "UI", "regression"},
            description = "Verify create test cases with valid data",
            dataProvider = DataProviderNames.CREATE_TEST_CASES_WITH_VALID_DATA, dataProviderClass = TestCasesProvider.class)
    public void createValidTestCases(String testName, String title, String description, String expectedResult, String[] steps, boolean autoSwitch,String expectedResponse)
    {
        testCasesPage.createNewTestCase(title,description,expectedResult,steps,autoSwitch);
        testCasesAsserts.validateTestCasesCreated(testCasesPage, expectedResponse);
    }

    @Test(groups = {"UI", "negative", "regression"},
            description = "Verify create test cases with invalid data",
            dataProvider = DataProviderNames.CREATE_TEST_CASES_WITH_INVALID_DATA, dataProviderClass = TestCasesProvider.class)
    public void createInvalidTestCases(String testName, String title, String description, String expectedResult, String[] steps, boolean autoSwitch, String expectedError)
    {
        testCasesPage.createNewTestCase(title,description,expectedResult,steps,autoSwitch);
        testCasesAsserts.validateTestCasesFailedToCreate(testCasesPage, expectedError);

    }

    @Test(groups = {"smoke", "UI", "regression"},
            description = "Verify delete random test case" )
    public void deleteRandomTestCase()
    {
        int countBeforeDelete = testCasesPage.getNumberOfTestCases();
        testCasesPage.deleteRandomTestCase();
        testCasesAsserts.validateDeleteRandomTestCase(testCasesPage, SuccessMessages.DELETE_TEST_CASE, countBeforeDelete);
    }
}
