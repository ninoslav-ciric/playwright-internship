package tests;

import assertion.LoginPageAsserts;
import assertion.PlaygroundPageAsserts;
import assertion.TestCaseNewAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import pages.PlaygroundNewProjectPage;

import java.util.List;
import java.util.Random;

import static utils.ConfigReader.*;

public class PlaygroundTests extends TestBase {

    Random random = new Random();

    public void login() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);
    }

    @Test(description = "Create Project with valid title")
    public void createProjectValidTitle() { // positive

        login();

        loginPage.navigateToTestCases(getPyalgroundUrl());
        List<String> titleLista = playgroundNewProjectPage.getAllTitles();

        loginPage.navigateToTestCases(getNewProjectPlaygroundUrl()); // moze preko btn-a ali akcenat na validnost Title-a

        String validTitle = "AK_Project_";
        while (titleLista.contains(validTitle))
        {
            validTitle = "AK_Project_" + random.nextInt(1000);
        }

        playgroundNewProjectPage.newProjectExpectSaccess(validTitle);
        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();
        playgroundPageAsserts.invalidateNewProject(playgroundNewProjectPage);

    }

    @Test(description = "Delete Project")
    public void deleteProject() { // positive

        login();

        loginPage.navigateToTestCases(getPyalgroundUrl());
        List<String> titleLista = playgroundNewProjectPage.getAllTitles();

        if (titleLista.isEmpty()) { return; }

        String titleToDelete = titleLista.getFirst();
        playgroundNewProjectPage.deleteProject(titleToDelete);

        List<String> updatedTitles = playgroundNewProjectPage.getAllTitles();

        PlaygroundPageAsserts playgroundPageAsserts = new PlaygroundPageAsserts();
        playgroundPageAsserts.deleteProject(titleToDelete, updatedTitles);

    }
}
