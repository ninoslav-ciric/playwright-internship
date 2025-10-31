package tests;

import assertion.ErrorProjectPageAsserts;
import assertion.ErrorTestCasePageAsserts;
import assertion.ProjectsPageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectsPage;

import static utils.Allure.logStep;
import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getProjectsUrl;

public class ProjectSecurityTests extends TestBase {


    public void LoginAndTestCreation() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToProjects(getProjectsUrl());
    }

    ErrorProjectPageAsserts projectPageAsserts = new ErrorProjectPageAsserts();

    @Test(groups = {"regression", "UI", "negative"},
            description = "Verify project can not be created with invalid Title."
    )
    public void testCreationProjectFail() {
        LoginAndTestCreation();
        logStep("INFO: Create Project With Same TItle");
        projectPage.createProjectExpectFailure("This String Has More Then 20 Characters");
        logStep("PASS: Project With Same Titled Attempted To Be Created");
        logStep("INFO: Error Project With Same Title");
        projectPageAsserts.validateErrorProject(projectPage);
        logStep("PASS: Error Project With Same Title Verified");
    }


}
