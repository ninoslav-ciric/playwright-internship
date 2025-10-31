package tests.playground;

import assertion.PlaygroundPageAsserts;
import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import utils.AllureSteps;

public class DeleteProject extends TestBase {
    AllureSteps steps = new AllureSteps();
    //positive
    @Test(groups = {"positive"},
            description = "Verify successful deletion of a project"
    )
    public void testDeleteTestCase(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Playground page");
        navigateToPlaygroundPage();

        String title = playgroundPage.getRandomProject().textContent();

        steps.logStep("Deleting project with title: " + title);
        playgroundPage.deleteProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();

        steps.logStep("Verify the deleted project is not visible");
        playgroundPageAsserts.assertDeleteProject(playgroundPage, title);
    }
}
