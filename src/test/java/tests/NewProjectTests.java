package tests;

import assertion.NewProjectPageAsserts;
import base.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.NewProjectPage;

import static utils.ConfigReader.*;

public class NewProjectTests extends TestBase {
    @BeforeMethod
    public void methodSetUp(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
    }

    @Test
    public void testValidNewProjectTitleSubmit() {

        NewProjectPageAsserts  newProjectPageAsserts = new NewProjectPageAsserts();
        newProjectPageAsserts.validateSuccessfulTitleNewProjectSubmittion(newProjectPage, editProjectPage);
    }

    @Test
    (description = "Validate unsuccessful new project title submitting for titles which is the same as some other project's title")
    public void testInvalidNewProjectSameTitleSubmit() {

        NewProjectPageAsserts  newProjectPageAsserts = new NewProjectPageAsserts();
        newProjectPageAsserts.validateUnsuccessfulSameTitleProject(newProjectPage, editProjectPage);
    }

    @Test
    (description = "Validate unsuccessful new project title submitting for titles which length is more then 20 characters.")
    public void testInvalidNewProjectTitleMoreThen20Submit() {

        NewProjectPageAsserts  newProjectPageAsserts = new NewProjectPageAsserts();
        newProjectPageAsserts.validateUnsuccessfulTitleForProjectMoreThen20(newProjectPage, editProjectPage);
    }

}
