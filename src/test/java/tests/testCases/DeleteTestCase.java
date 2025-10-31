package tests.testCases;

import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import utils.AllureSteps;


public class DeleteTestCase extends TestBase {
    AllureSteps steps = new AllureSteps();
    //positive
    @Test(groups = {"positive"},
            description = "Verify successful deletion of a test case"
    )
    public void testDeleteTestCase(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Test case page");
        navigateToTestCasePage();

        String title = testCasePage.getRandomTestcase().textContent();

        steps.logStep("Deleting test case with title: " + title);
        testCasePage.deleteTestCaseExpectSuccess(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();

        steps.logStep("Verify the deleted test case is not visible");
        testCasePageAsserts.assertDeleteTestCase(testCasePage, title);
    }


}
