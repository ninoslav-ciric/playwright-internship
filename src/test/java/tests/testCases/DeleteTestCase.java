package tests.testCases;

import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;


public class DeleteTestCase extends TestBase {
    //positive
    @Test(groups = {"smoke", "UI"},
            description = "Verify successful deletion of a test case"
    )
    public void testDeleteTestCase(){
        login();
        navigateToTestCasePage();

        String title = testCasePage.getRandomTestcase().textContent();
        testCasePage.deleteTestCaseExpectSuccess(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();
        testCasePageAsserts.assertDeleteTestCase(testCasePage, title);
    }


}
