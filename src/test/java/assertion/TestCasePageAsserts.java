package assertion;


import org.testng.asserts.SoftAssert;
import pages.TestCasePage;

public class TestCasePageAsserts {
    private final SoftAssert softAssert;
    public TestCasePageAsserts(){
        this.softAssert = new SoftAssert();
    }

    public void softAsserting(TestCasePage testCasePage){
        softAssert.assertTrue(testCasePage.getCurrentUrl().contains("/testcases"), "URL should contain test cases");
        softAssert.assertAll();
    }

    public void validateTestCase(TestCasePage testCasePage){

        softAssert.assertTrue(testCasePage.isToastVisiable(), "Toast message is showcasing");
        softAsserting(testCasePage);
    }

    public void validateDelete(TestCasePage testCasePage){
        boolean test = testCasePage.isTestCaseExists();
        softAssert.assertFalse(test, "should be deleted but is still visable");
        softAssert.assertAll();
    }


}
