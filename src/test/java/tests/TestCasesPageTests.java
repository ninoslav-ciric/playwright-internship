package tests;

import assertion.TestCasesAsserts;
import base.TestBase;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;
import pages.TestCasesPage;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestCasesPageTests extends TestBase {

    @Test(groups = {"UI"},
            description = "Verify successful deletion of test cases"
    )
    public void testDeleteAllTestCases() {
        currentPage = new TestCasesPage(page);
        TestCasesPage testCasesPage = (TestCasesPage) currentPage;
        TestCasesAsserts testCasesAsserts = new TestCasesAsserts();
        testCasesPage.goTo();

        while (true) {
            List<Locator> allTestCases = testCasesPage.getAllTestCases();

            if (allTestCases.isEmpty()) break;

            Locator testCase = allTestCases.getFirst();

            try {
                testCase.scrollIntoViewIfNeeded();
                if (testCase.isVisible()) {
                    testCase.click();
                    String caseID = testCasesPage.deleteCase();
                    testCasesAsserts.assertToastMessage(testCasesPage.getSuccessToast(),"Successful toast must be shown after deletion of test case");
                }
            } catch (Exception e) {
                testCasesAsserts.assertFailedDeletion(e);
            }
        }


        testCasesAsserts.assertAllTestCasesDeleted(testCasesPage);
    }

    @Test(groups = {"UI"},
    description = "Verify creation of new test case works")
    public void testCreateNewTestCase() {
        currentPage = new TestCasesPage(page);
        TestCasesPage testCasesPage = (TestCasesPage) currentPage;
        TestCasesAsserts testCasesAsserts = new TestCasesAsserts();
        testCasesPage.goTo();

        String testCaseTitle = "Test by Bogdan " + ThreadLocalRandom.current().nextInt(1,1_000_000);
        testCasesPage.createNewTestCase(testCaseTitle, "This is test case created by Bogdan Stevanovic", "123412345");

        testCasesAsserts.assertToastMessage(testCasesPage.getSuccessToast(), "Successful toast must be present after adding a test case");
    }

    @Test(
            groups = {"UI"},
            description = "Verify failing creation of duplicate test case"
    )
    public void testCreatingExistingTextCase(){
        currentPage = new TestCasesPage(page);
        TestCasesPage testCasesPage = (TestCasesPage) currentPage;
        TestCasesAsserts testCasesAsserts = new TestCasesAsserts();
        testCasesPage.goTo();

        String testCaseTitle = "Test by Bogdan " + ThreadLocalRandom.current().nextInt(1,1_000_000);
        testCasesPage.createNewTestCase(testCaseTitle, "This is test case created by Bogdan Stevanovic", "123412345");

        testCasesAsserts.assertToastMessage(testCasesPage.getSuccessToast(),"Success message must be shown after creating test case");

        testCasesPage.createNewTestCase(testCaseTitle, "This is test case created by Bogdan Stevanovic", "123412345");
        Locator error_loc = testCasesPage.getCreationError();
        error_loc.scrollIntoViewIfNeeded();
        testCasesAsserts.assertVisibleLocator(error_loc);
    }
}
