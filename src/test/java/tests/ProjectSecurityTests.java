package tests;

import assertion.ErrorProjectPageAsserts;
import assertion.ErrorTestCasePageAsserts;
import assertion.ProjectsPageAsserts;
import base.TestBase;
import com.github.javafaker.Faker;
import common.ValueChoosers;
import org.testng.annotations.Test;
import pages.ProjectsPage;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;
import static utils.ConfigReader.getProjectsUrl;

public class ProjectSecurityTests extends TestBase {

    public void LoginAndTestCreation() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        projectPage.navigateToProjects(getProjectsUrl());
    }

    ErrorProjectPageAsserts projectPageAsserts = new ErrorProjectPageAsserts();

    @Test(groups = {"regression", "UI"},
            description = "Verify project cna't be created with invalid Title."
    )
    public void testCreationProjectFail() {
        LoginAndTestCreation();

        projectPage.createProjectExpectFailure("This String Has More Then 20 Characters");

        projectPageAsserts.validateErrorProject(projectPage);
    }


}
