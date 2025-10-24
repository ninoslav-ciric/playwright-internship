package assertion;

import org.testng.asserts.SoftAssert;
import pages.TestCasesPage;

public class TestCasesPageAsserts {

    private final SoftAssert softAssert;

    public TestCasesPageAsserts() {
        this.softAssert = new SoftAssert();
    }


    public void validateFieldsVisible(TestCasesPage pageObj) {
        softAssert.assertTrue(pageObj.getNavTitle().isVisible(), "New Test Case title should be visible.");
        softAssert.assertTrue(pageObj.getTitleInput().isVisible(), "Title input should be visible.");
        softAssert.assertTrue(pageObj.getDescriptionText().isVisible(), "Description textarea should be visible.");
        softAssert.assertTrue(pageObj.getExpectedResultInp().isVisible(), "Expected Result input should be visible.");
        softAssert.assertTrue(pageObj.getAddStepBtn().isVisible(), "Add Test Step button should be visible.");
        softAssert.assertTrue(pageObj.getAutomatedSwitchInput().isVisible(), "Automated Switch should be visible.");

        softAssert.assertTrue(pageObj.getSubmitButton().isVisible(), "Submit button should be visible.");

        softAssert.assertAll();
    }

    public void validateAutomatedSwitchOn(TestCasesPage pageObj) {
        softAssert.assertTrue(
                pageObj.isAutomatedChecked(),
                "Automated switch should be checked."
        );

        softAssert.assertAll();
    }

    public void validateAutomatedSwitchOff(TestCasesPage pageObj) {
        softAssert.assertFalse(
                pageObj.isAutomatedChecked(),
                "Automated switch should not be checked."
        );

        softAssert.assertAll();
    }

    public void validateStepCount(TestCasesPage pageObj, int length) {
        int actualCount = pageObj.getStepInputs().count();

        softAssert.assertEquals(
                actualCount,
                length,
                "Number of step inputs should match the provided steps length."
        );
    }

    public void validateTitleErrorMessageDisplayed(TestCasesPage pageObj) {
        softAssert.assertTrue(pageObj.getTitleErrorMessage().innerText().contains("Title is required"));
        softAssert.assertAll();
    }
    public void validateExpectedErrorMessageDisplayed(TestCasesPage pageObj) {
        softAssert.assertTrue(pageObj.getExpectedResultErrorMessage().isVisible());
        softAssert.assertAll();
    }


    public void validateAllErrorMessagesDisplayed(TestCasesPage pageObj) {
        softAssert.assertTrue(pageObj.getTitleErrorMessage().isVisible(), "Title error message should be visible.");
        softAssert.assertTrue(pageObj.getExpectedResultErrorMessage().isVisible(), "Expected result error message should be visible.");
        softAssert.assertTrue(pageObj.getStepsErrorMessage().isVisible(), "Steps error message should be visible.");
        softAssert.assertAll();
    }



    //Add when TestCaseList is created
//    public void validateSubmittedSuccessfully(){
//
//    }


}
