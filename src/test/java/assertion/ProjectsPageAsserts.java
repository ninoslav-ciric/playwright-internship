package assertion;

import org.testng.asserts.SoftAssert;
import pages.ProjectsPage;

public class ProjectsPageAsserts {
    private final SoftAssert softAssert;
    public ProjectsPageAsserts(){
        this.softAssert = new SoftAssert();
    }

    public void softAsserting(ProjectsPage projectPage){
        //softAssert.assertTrue(projectPage.getCurrentUrl().contains("/projects"), "URL should projects");
        softAssert.assertAll();
    }

    public void validateToastShowing(ProjectsPage projectPage){

        softAssert.assertTrue(projectPage.isToastVisiable(), "Toast message is showcasing");
        softAsserting(projectPage);
    }

}
