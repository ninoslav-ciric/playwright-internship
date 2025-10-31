package assertion;

import utils.Constants;
import org.testng.asserts.SoftAssert;
import pages.TestCasesPage;

public class TestCasesPageAsserts {

    private final SoftAssert softAssert;

    public TestCasesPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateAddedTestCase(TestCasesPage testCasesPage , String title) {
        softAssert.assertTrue(testCasesPage.getCurrentUrl().contains(Constants.TEST_CASES), "URL should contain testcases");
        softAssert.assertTrue(testCasesPage.isCardVisible(title));
        softAssert.assertAll();
    }
    public void validateExistingTestCase(TestCasesPage testCasesPage)
    {
        softAssert.assertTrue(testCasesPage.getCurrentUrl().contains(Constants.TEST_CASES), "URL should contain testcases");
        softAssert.assertTrue(testCasesPage.isTestCaseExistsErrorVisible());
        softAssert.assertAll();
    }

    public void validateModifiedTestCase(TestCasesPage testCasesPage , String title)
    {
        softAssert.assertTrue(testCasesPage.getCurrentUrl().contains(Constants.TEST_CASES), "URL should contain testcases");
        softAssert.assertTrue(testCasesPage.isCardVisible(title));
        //treba provera samih elemenata description, expected itd
        softAssert.assertAll();
    }

    public void validatePreviewTestCase(TestCasesPage testCasesPage, String title)
    {
        softAssert.assertTrue(testCasesPage.isModalVisibleAndValid(title));
        softAssert.assertAll();
    }
    public void validateDeletedTestCase(TestCasesPage testCasesPage, String title)
    {
        softAssert.assertTrue(testCasesPage.getCurrentUrl().contains(Constants.TEST_CASES), "URL should contain testcases");
        softAssert.assertFalse(testCasesPage.isCardVisible(title));
        softAssert.assertAll();
    }

    public void validateMissingTitleTestCase(TestCasesPage testCasesPage)
    {
        softAssert.assertTrue(testCasesPage.getCurrentUrl().contains(Constants.TEST_CASES), "URL should contain testcases");
        softAssert.assertTrue(testCasesPage.isTitleErrorVisible());
        softAssert.assertAll();
    }
}
