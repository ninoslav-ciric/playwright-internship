package tests;

import assertion.ProjectsPageAssertions;
import base.TestBase;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProjectsPage;

import java.util.List;

public class ProjectsPageTests extends TestBase {
    @Test(
            groups = {"UI", "Negative"},
            description = "Validates that creating a new project with an already existing person triggers the appropriate error message."
    )
    public void testCreateNewProjectExistingPerson(){
        currentPage = new ProjectsPage(page);
        ProjectsPage projectsPage = (ProjectsPage) currentPage;
        ProjectsPageAssertions projectsPageAssertions = new ProjectsPageAssertions();
        projectsPage.goTo();

        projectsPage.createProjectExistingPerson();
        Locator err_msg = projectsPage.getPersonErrorMessage();
        projectsPageAssertions.assertFailedPersonCreation(err_msg);
    }

    @Test(
            groups = {"UI", "Functional", "Positive"},
            description = "Verify that all existing persons are correctly added to the first project in the list."
    )
    public void testAddAllPersonsIntoFirstProject(){
        currentPage = new ProjectsPage(page);
        ProjectsPage projectsPage = (ProjectsPage) currentPage;
        ProjectsPageAssertions projectsPageAssertions = new ProjectsPageAssertions();
        projectsPage.goTo();

        Locator project = projectsPage.createProjectTitleOnly();

        if(project == null)
            Assert.fail("No project found");

        List<String> selectedNames = projectsPage.addPeopleOnProject(project);
        project.scrollIntoViewIfNeeded();
        projectsPage.getPreviewButton(project).click();
        List<String> actualNames = projectsPage.getPersonNamesInPreview();
        projectsPageAssertions.assertCorrectPersons(actualNames,selectedNames);
    }
}
