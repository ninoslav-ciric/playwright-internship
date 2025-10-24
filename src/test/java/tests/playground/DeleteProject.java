package tests.playground;

import assertion.PlaygroundPageAsserts;
import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;

public class DeleteProject extends TestBase {
    //positive
    @Test(groups = {"smoke", "UI"},
            description = "Verify successful deletion of a project"
    )
    public void testDeleteTestCase(){
        login();
        navigateToPlaygroundPage();

        String title = playgroundPage.getRandomProject().textContent();
        playgroundPage.deleteProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();
        playgroundPageAsserts.assertDeleteProject(playgroundPage, title);
    }
}
