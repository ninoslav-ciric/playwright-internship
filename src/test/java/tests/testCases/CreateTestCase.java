package tests.testCases;

import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import java.util.Random;

public class CreateTestCase extends TestBase {
    //positive
    @Test(groups = {"smoke", "UI"},
            description = "Verify successful creation of a new test case"
    )
    public void testAddNewTestCase(){
        Random random = new Random();
        login();
        navigateToTestCasePage();
        String title = "Test case " + random.nextLong(100, 100000);

        testCasePage.createTestCaseExpectSuccess(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();
        testCasePageAsserts.assertAddNewTestCase(testCasePage, title);
    }

    //negative
    @Test(groups = {"smoke", "UI"},
            description = "Verify unsuccessful creation of a new test case"
    )
    public void testInvalidAddNewTestCase(){
        login();
        navigateToTestCasePage();
        String title = "Invalid case title";
        testCasePage.createTestCaseExpectFailureNoExpectedResult(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();
        testCasePageAsserts.assertAddNewTestFailNoExpectedResult(testCasePage, title);
    }

    //negative
    @Test(groups = {"smoke", "UI"},
            description = "Verify unsuccessful creation of a new test case with title that already exists"
    )
    public void testAddNewTestCaseExistingTitle(){
        login();
        navigateToTestCasePage();

        String title = testCasePage.getRandomTestcase().textContent();
        testCasePage.createTestCaseExpectFailureExistingTitle(title);

        TestCasePageAsserts testCasePageAsserts = new TestCasePageAsserts();
        testCasePageAsserts.assertAddNewTestFailExistingTitle(testCasePage, title);
    }
}
