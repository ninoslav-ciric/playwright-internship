package tests;

import assertion.TestCasePageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.Test;
import pages.TestCasePage;

import static utils.ConfigReader.*;


public class TestCaseTests extends TestBase {

    public void LoginAndTestCreation(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        testCasePage.navigateToTestCases(getTestCaseUrl());
    }

    TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

    @Test(groups = {"regression", "UI"},
            description = "Verify test case created with correct creds."
    )
    public void testValidTestCaseCreate() {

        LoginAndTestCreation();
        testCasePage.createTestCaseExpectSuccess(ValueChoosers.getRandomTitle(), ValueChoosers.getRandomExpected(), "Test is created");

        testCasePageAsserts.validateTestCase(testCasePage);

    }

    @Test(groups = {"regression", "UI"},
            description = "Verify that remove test case deletes the correct test case."
    )
    public void testDeleteTestCase() {

        LoginAndTestCreation();
        testCasePage.createTestCaseExpectSuccess("The one that needs to be deleted", ValueChoosers.getRandomExpected(), "Test is created");
        testCasePage.deleteButtonClick();

        testCasePageAsserts.validateDelete(testCasePage);

    }


}