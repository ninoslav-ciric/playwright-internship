package tests;

import assertion.ManageProjectsAsserts;
import base.TestBase;
import com.microsoft.playwright.Locator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class ManageProjectsTests extends TestBase {

    private final ManageProjectsAsserts asserts = new ManageProjectsAsserts();
    private int projectId = 0;

    private String getProjectEditUrl() {
        return "https://qa-sandbox.ni.htec.rs/edit-project/" + projectId;
    }

    @BeforeMethod(groups = "requires login")
    public void login() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
    }

    @Test(groups = "requires login")
    public void addProjectTest() {
        page.navigate("https://qa-sandbox.ni.htec.rs/projects");
        page.getByText("New Project").click();
        var titleField = page.getByPlaceholder("Title");
        String generatedName = generateBase64();
        titleField.fill(generatedName);


        page.getByText("Submit").click();

        page.waitForURL(Pattern.compile("https://qa-sandbox.ni.htec.rs/edit-project/*"));

        asserts.projectCreated(page);

        projectId = Integer.parseInt(page.url().replace("https://qa-sandbox.ni.htec.rs/edit-project/", ""));

    }

    @Test(dependsOnMethods = "addProjectTest", groups = "requires login")
    public void addPerson() {
        page.navigate(getProjectEditUrl());
        var picky = page.locator("#picky").getByText("PeopleSelect all");
        picky.click();

        List<Integer> options = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        List<String> names = new ArrayList<String>();
        Random random = new Random();
        int selectCount = random.nextInt(1, 6);
        for (int i = 0; i < selectCount; i++) {
            int index = random.nextInt(0, options.size());
            var option = page.locator("#picky-option-" + options.remove(index));
            option.click();
            names.add(option.innerText());
        }

        asserts.addedPeople(page, names);

        page.getByText("Submit").first().click();

    }

    @Test(dependsOnMethods = "addProjectTest", groups = "requires login")
    public void createPerson() {
        page.navigate(getProjectEditUrl());

        String team = generateBase64();

        var teamInput = page.locator("input[name=\"team\"]");
        teamInput.scrollIntoViewIfNeeded();
        teamInput.fill(team);

        var createButton = page.locator("button").getByText("Create").last();
        createButton.click();

        var nameInput = page.locator("input[name=\"person\"]");
        nameInput.scrollIntoViewIfNeeded();

        String name = generateBase64();
        nameInput.fill(name);

        var picky = page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^TeamsTeam$"))).locator("#picky");
        picky.scrollIntoViewIfNeeded();
        picky.click();

        var option = page.locator("#picky-option-0");
        option.scrollIntoViewIfNeeded();
        option.click();

        var submitButton = page.getByText("Submit").last();
        submitButton.scrollIntoViewIfNeeded();
        submitButton.click();

        asserts.isPersonInTeam(page, name, team);
    }

    @Test(dependsOnMethods = "addProjectTest", groups = "requires login")
    public void deleteProjectTest() {
        page.navigate(getProjectEditUrl());
        String title = page.getByPlaceholder("Title").first().innerText();

        var removeButton = page.getByText("Remove").first();
        removeButton.scrollIntoViewIfNeeded();
        removeButton.click();
        page.locator("div.confirmation-dialog--buttons--confirm").getByText("Remove").click();

        asserts.projectNotExists(page, title);

    }


}
