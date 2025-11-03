package assertion;

import org.testng.asserts.SoftAssert;
import pages.TestCasesPage;

import java.util.List;

public class TestCasesAsserts
{
    SoftAssert softAssert;

    public TestCasesAsserts()
    {
        this.softAssert = new SoftAssert();
    }

    public void validateTestCasesCreated(TestCasesPage testCasesPage, String expectedResponse)
    {
        softAssert.assertTrue(testCasesPage.getSuccessResponseMessage().contains(expectedResponse), "Response message didn't match. Failed to create test case");
        softAssert.assertAll();
    }

    public void validateTestCasesFailedToCreate(TestCasesPage testCasesPage, String expectedError)
    {
        List<String> actualErrors = testCasesPage.getVisibleErrorMessages();
        softAssert.assertTrue(actualErrors.stream().anyMatch(message -> message.contains(expectedError)), "Error message didn't match");
        softAssert.assertTrue(testCasesPage.getSuccessResponse().isHidden(), "Success message is visible");
        softAssert.assertAll();
    }

    public void validateDeleteRandomTestCase(TestCasesPage testCasesPage, String expectedResponse, int countBeforeDelete)
    {
        softAssert.assertTrue(testCasesPage.getSuccessResponseMessage().contains(expectedResponse), "Response message didn't match. Failed to delete test case");
        softAssert.assertTrue(testCasesPage.getNumberOfTestCases() < countBeforeDelete, "Number of test cases after delete isn't lower then before delete");
        softAssert.assertAll();
    }
}
