package tests;

import assertion.EditProjectAsserts;
import assertion.LoginPageAsserts;
import base.TestBase;
import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EditProjectPage;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class EditProjectTests extends TestBase {
    @BeforeMethod
    public void methodSetUp(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
    }

    @Test
    (description = "User can successfully select people which are on project")
    public void testPeopleSelection(){

        EditProjectAsserts editProjectAsserts = new EditProjectAsserts();
        editProjectAsserts.validateSuccessfulPeopleSelection(new EditProjectPage(page));

    }
    @Test
    (description = "User can add new technology in technologies")
    public void testSuccessfulTechnologyCreation(){

        EditProjectAsserts editProjectAsserts = new EditProjectAsserts();
        editProjectAsserts.validateSuccessfulTechnologyCreation(editProjectPage);
    }
}
