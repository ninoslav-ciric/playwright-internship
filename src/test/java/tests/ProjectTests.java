package tests;

import assertion.ProjectsPageAsserts;
import base.TestBase;
import common.ValueChoosers;
import org.testng.annotations.Test;

import static utils.Allure.logStep;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getProjectsUrl;

public class ProjectTests extends TestBase {
    public void LoginAndTestCreation(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToProjects(getProjectsUrl());
    }

    ProjectsPageAsserts  projectPageAsserts=new ProjectsPageAsserts();

    @Test(groups = {"regression", "UI", "positive"},
            description = "Verify project was created with correct creds."
    )
    public void testValidProjectCreate() {

        LoginAndTestCreation();
        logStep("INFO: CreateProject");
        projectPage.createProjectExpectSuccess(ValueChoosers.getRandomProjectTitle());
        logStep("PASS: Project Created");
        logStep("INFO: Verify Project Created");
        projectPageAsserts.validateToastShowing(projectPage);
        logStep("PASS: Project Creation verified");
    }

    @Test(groups = {"regression", "UI", "positive"},
            description = "Verify technology tab was created."
    )
    public void testValidTechnologyCreate() {

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToPage(getProjectsUrl());
        logStep("INFO: Creating technology");
        projectPage.createProjectTechnology(ValueChoosers.getRandomTechnology());
        logStep("PASS: Technology created");
        logStep("INFO: Verify Technology created");
        projectPageAsserts.validateToastShowing(projectPage);
        logStep("PASS: Technology Creation verified");
    }
    @Test(groups = {"regression", "UI", "positive"},
            description = "Verify that remove projects deletes the correct test case."
    )
    public void testDeleteTestCase() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToPage(getProjectsUrl());
        logStep("INFO: Click Remove Button");
        projectPage.removeButtonClick();
        logStep("PASS: Remove Button Clicked");
        logStep("INFO: Verify TestCase Deleted ");
        projectPageAsserts.validateToastShowing(projectPage);
        logStep("PASS: TestCase Deletion verified ");
    }
}

