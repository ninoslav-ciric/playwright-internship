package assertion;


import org.testng.asserts.SoftAssert;
import pages.ProjectsPage;

public class ErrorProjectPageAsserts {
    private final SoftAssert softAssert;

    public ErrorProjectPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void softAsserting(ProjectsPage projectPage){

        softAssert.assertTrue(projectPage.getCurrentUrl().contains("/new-project"), "URL should contain test cases");
        softAssert.assertAll();
    }

    public void validateErrorProject(ProjectsPage projectPage){

        softAssert.assertTrue(projectPage.isValidationMessageVisible(), "Error message is showcasing");
        softAssert.assertAll();
    }

}