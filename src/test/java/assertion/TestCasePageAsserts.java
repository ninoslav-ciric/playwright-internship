package assertion;

import org.testng.asserts.SoftAssert;
import pages.TestCasePage;

public class TestCasePageAsserts {
    private final SoftAssert softAssert;
    public TestCasePageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void assertAddNewTestCase(TestCasePage testCasePage, String title){
        softAssert.assertTrue(testCasePage.isTestcasePageVisible(), "Not on testcase page");
        softAssert.assertTrue(testCasePage.isToastifyVisible());
        String toastText = testCasePage.toastContainer.textContent();
        softAssert.assertTrue(toastText.trim().contains("Test case created successfully"), "Toast message does not contain expected text");
        softAssert.assertTrue(testCasePage.isTestcaseExist(title));
        softAssert.assertAll();
    }

    public void assertAddNewTestFailNoExpectedResult(TestCasePage testCasePage, String title){
        softAssert.assertTrue(testCasePage.isNewTestcasePageVisible(), "Not on new-testcase page");
        softAssert.assertEquals(testCasePage.validationMessageNoExpectedResult.textContent(), "Expected result is required");
        softAssert.assertAll();
    }

    public void assertAddNewTestFailExistingTitle(TestCasePage testCasePage, String title){
        softAssert.assertTrue(testCasePage.isNewTestcasePageVisible(), "Not on new-testcase page");
        softAssert.assertEquals(testCasePage.validationMessageExistingTitle.textContent(), "Test case name already exist");
        softAssert.assertTrue(testCasePage.toastContainer.textContent().trim().contains("Test case creating failed"), "Toast message does not contain expected text");

        softAssert.assertAll();
    }

    public void assertDeleteTestCase(TestCasePage testCasePage, String title){
        softAssert.assertTrue(testCasePage.isTestcasePageVisible(), "Not on testcase page");
        softAssert.assertTrue(testCasePage.isToastifyVisible(), "Toastify alert should be visible");
        String toastText = testCasePage.toastContainer.textContent();
        softAssert.assertTrue(toastText.trim().contains("Test case removed successfully"), "Toast message does not contain expected text");
        softAssert.assertFalse(testCasePage.isTestcaseExist(title), "Test case with that title should not exist");
        softAssert.assertAll();
    }
}
