package tests.playground;

import assertion.PlaygroundPageAsserts;
import assertion.TestCasePageAsserts;
import base.TestBase;
import org.testng.annotations.Test;

import java.util.Random;

public class CreateProject extends TestBase {
    //positive
    @Test(groups = {"smoke", "UI"},
            description = "Verify successful creation of a new project"
    )
    public void testAddNewProject(){
        login();
        navigateToPlaygroundPage();
        Random random = new Random();
        String title = "Project " + random.nextLong(100, 100000);

        playgroundPage.createProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();
        playgroundPageAsserts.assertAddNewProject(playgroundPage, title);
    }

    //positive
    @Test(groups = {"smoke", "UI"},
            description = "Verify successful creation of a new project"
    )
    public void testAddWholeNewProject(){
        login();
        navigateToPlaygroundPage();
        Random random = new Random();
        String title = "Project " + random.nextLong(100, 100000);

        playgroundPage.createWholeProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();
        playgroundPageAsserts.assertAddWholeNewProject(playgroundPage, title);
    }




}
