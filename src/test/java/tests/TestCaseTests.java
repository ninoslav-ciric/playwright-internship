package tests;

import assertion.TestCasePageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.Test;
import pages.TestCasePage;

import static utils.Allure.logStep;
import static utils.ConfigReader.*;


public class TestCaseTests extends TestBase {

    public void LoginAndTestCreation(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getTestCaseUrl());
    }

    TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

    @Test(groups = {"regression", "UI", "positive"},
            description = "Verify test case created with correct creds."
    )
    public void testValidTestCaseCreate() {

        LoginAndTestCreation();
        logStep("INFO: Create Test Case");
        testCasePage.createTestCaseExpectSuccess(ValueChoosers.getRandomTitle(), ValueChoosers.getRandomExpected(), "Test is created");
        logStep("PASS: TestCase Created");
        logStep("INFO: Verify Test Case Creation");
        testCasePageAsserts.validateTestCase(testCasePage);
        logStep("PASS: Test Case Creation verified");
    }

    @Test(groups = {"regression", "UI", "positive"},
            description = "Verify that remove test case deletes the correct test case."
    )
    public void testDeleteTestCase() {

        LoginAndTestCreation();
        testCasePage.createTestCaseExpectSuccess("The one that needs to be deleted", ValueChoosers.getRandomExpected(), "Test is created");
        logStep("INFO: Click Delete Button for TestCase");
        testCasePage.deleteButtonClick();
        logStep("PASS: Delete Button Clicked");
        logStep("INFO: Verify Test Case Deleted");
        testCasePageAsserts.validateDelete(testCasePage);
        logStep("PASS: TestCase Deletion verified");
    }


}