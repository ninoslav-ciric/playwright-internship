package assertion;

import org.testng.asserts.SoftAssert;
import pages.TestCasesNewPage;

import java.util.List;

public class TestCaseNewAsserts {

    private final SoftAssert softAssert;

    public TestCaseNewAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateNewCase(TestCasesNewPage testCasesNewPage) {

        softAssert.assertFalse(testCasesNewPage.isErrorVisible(), "NewCase creating failed");
        softAssert.assertAll();
    }

    public void invalidateNewCase(TestCasesNewPage testCasesNewPage) {

        softAssert.assertFalse(testCasesNewPage.isErrorVisible(), "NewCase creating failed");
        softAssert.assertAll();
    }

    public void invalidateNewCaseData(TestCasesNewPage testCasesNewPage) {

        softAssert.assertTrue(testCasesNewPage.isError2Visible(), "NewCase creating failed");
        softAssert.assertAll();
    }

    public void deleteCase(String titleToDelete, List<String> titleLista) {
        softAssert.assertFalse(titleLista.contains(titleToDelete), "Deleted test case should no longer exist");
        softAssert.assertAll();
    }

    public void validateStepCount(TestCasesNewPage testCasesNewPage, int len) {
        int actualCount = testCasesNewPage.getStepInputs().count();

        softAssert.assertEquals(actualCount, len,"Number of step inputs invalid");
    }
}
