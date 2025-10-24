package assertion;

import org.testng.asserts.SoftAssert;
import pages.EditProjectPage;
import pages.NewProjectPage;

import java.util.UUID;

import static utils.ConfigReader.*;

public class NewProjectPageAsserts {

    private final SoftAssert softAssert;

    public NewProjectPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void validateSuccessfulTitleNewProjectSubmittion(NewProjectPage newProjectPage, EditProjectPage editProjectPage) {

        newProjectPage.navigateToNewProjectPage(getBaseUrl() + "new-project");
        newProjectPage.expectSuccessfullyEnteringTitleAndClickSubmit("DProjekat" + java.util.UUID.randomUUID().toString().substring(0,10));

        softAssert.assertTrue(editProjectPage.getCurrentUrl().contains("/edit-project") );
        softAssert.assertAll();
    }

    public void validateUnsuccessfulSameTitleProject(NewProjectPage newProjectPage, EditProjectPage editProjectPage){
        newProjectPage.navigateToNewProjectPage(getBaseUrl() + "new-project");
        String nazivProjekta = java.util.UUID.randomUUID().toString().substring(0,10);
        newProjectPage.expectSuccessfullyEnteringTitleAndClickSubmit(nazivProjekta);
        softAssert.assertTrue(editProjectPage.getCurrentUrl().contains("/edit-project") );

        newProjectPage.navigateToNewProjectPage(getBaseUrl() + "new-project");
        newProjectPage.expectSuccessfullyEnteringTitleAndClickSubmit(nazivProjekta);
        softAssert.assertFalse(editProjectPage.getCurrentUrl().contains("/edit-project") );
        softAssert.assertEquals(newProjectPage.getValidationMsg(), "Project title already exist");
        softAssert.assertAll();
    }

    public void validateUnsuccessfulTitleForProjectMoreThen20(NewProjectPage newProjectPage, EditProjectPage editProjectPage){
        newProjectPage.navigateToNewProjectPage(getBaseUrl() + "new-project");
        String nazivProjekta = UUID.randomUUID().toString();
        newProjectPage.expectSuccessfullyEnteringTitleAndClickSubmit(nazivProjekta);
        softAssert.assertFalse(editProjectPage.getCurrentUrl().contains("/edit-project"));
        softAssert.assertEquals(newProjectPage.getValidationMsg()
                               , "Title can not have more than 20 character (" + nazivProjekta.length() + ")" );
        softAssert.assertAll();

    }
}
