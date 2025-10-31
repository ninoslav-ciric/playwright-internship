package assertion;


import org.testng.asserts.SoftAssert;
import pages.TestCasePage;

public class ErrorTestCasePageAsserts {
    private final SoftAssert softAssert;

    public ErrorTestCasePageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void softAsserting(TestCasePage testCasePage){

        softAssert.assertTrue(testCasePage.getCurrentUrl().contains("/testcases"), "URL should contain test cases");
        softAssert.assertAll();
    }

    public void validateErrorTestCase(TestCasePage testCasePage){

        softAssert.assertTrue(testCasePage.isToastVisiable(), "Toast message is showcasing");
        softAssert.assertAll();
    }

}