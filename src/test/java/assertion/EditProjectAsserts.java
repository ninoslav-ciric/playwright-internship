package assertion;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EditProjectPage;
import pages.NewProjectPage;

import static utils.ConfigReader.*;

public class EditProjectAsserts {

    private final SoftAssert softAssert;

    public EditProjectAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateSuccessfulPeopleSelection(EditProjectPage editProjectPage){
        editProjectPage.navigateToEditProjectPage();

        editProjectPage.selectPeopleOptions();
        softAssert.assertTrue(editProjectPage.isOptionSelected("Aida Hadzibegovic"));
        softAssert.assertAll();
    }
    public void validateSuccessfulTechnologyCreation(EditProjectPage editProjectPage){
        editProjectPage.navigateToEditProjectPage();

        editProjectPage.inputTechnologyName();
        editProjectPage.clickOnCreateButton();

        String firstTechnologyName = editProjectPage.getFirstTechnology().textContent();
        softAssert.assertEquals(firstTechnologyName, "Cypress");
        softAssert.assertAll();
    }

}
