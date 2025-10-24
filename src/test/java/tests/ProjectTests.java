package tests;

import assertion.ProjectsPageAsserts;
import base.TestBase;
import common.ValueChoosers;
import org.testng.annotations.Test;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getProjectsUrl;

public class ProjectTests extends TestBase {
    public void LoginAndTestCreation(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToProjects(getProjectsUrl());
    }

    ProjectsPageAsserts  projectPageAsserts=new ProjectsPageAsserts();

    @Test(groups = {"regression", "UI"},
            description = "Verify project was created with correct creds."
    )
    public void testValidProjectCreate() {

        LoginAndTestCreation();
        projectPage.createProjectExpectSuccess(ValueChoosers.getRandomProjectTitle());

        projectPageAsserts.validateToastShowing(projectPage);

    }

    @Test(groups = {"regression", "UI"},
            description = "Verify technology was created."
    )
    public void testValidTechnologyCreate() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToPage(getProjectsUrl());
        projectPage.createProjectTechnology(ValueChoosers.getRandomTechnology());

        projectPageAsserts.validateToastShowing(projectPage);

    }
    @Test(groups = {"regression", "UI"},
            description = "Verify that remove projects deletes the correct test case."
    )
    public void testDeleteTestCase() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToPage(getProjectsUrl());

        projectPage.removeButtonClick();

        projectPageAsserts.validateToastShowing(projectPage);

    }
}

