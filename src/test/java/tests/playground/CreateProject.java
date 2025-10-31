package tests.playground;

//import assertion.PlaygroundPageAsserts;
import assertion.PlaygroundPageAsserts;
import base.TestBase;
//import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import utils.AllureSteps;


import java.util.Random;



public class CreateProject extends TestBase {
    AllureSteps steps = new AllureSteps();

    //positive
    @Test(groups = {"positive"},
            description = "Verify successful creation of a new project"
    )
    public void testAddNewProject(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Playground page");
        navigateToPlaygroundPage();
        Random random = new Random();
        String title = "Project " + random.nextLong(100, 100000);

        steps.logStep("Creating new project with title: " + title);
        playgroundPage.createProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();

        steps.logStep("Verify the new project is visible");
        playgroundPageAsserts.assertAddNewProject(playgroundPage, title);
    }

    //positive
    @Test(groups = {"positive"},
            description = "Verify successful creation of a new project"
    )
    public void testAddWholeNewProject(){
        steps.logStep("Login to the application");
        login();

        steps.logStep("Navigate to Playground page");
        navigateToPlaygroundPage();
        Random random = new Random();
        String title = "Project " + random.nextLong(100, 100000);

        steps.logStep("Creating new project with title: " + title);
        playgroundPage.createWholeProjectExpectSuccess(title);

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();

        steps.logStep("Verify the new project is visible");
        playgroundPageAsserts.assertAddWholeNewProject(playgroundPage, title);
    }




}
