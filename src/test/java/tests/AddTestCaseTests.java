package tests;

import assertion.AddTestCaseAsserts;
import base.TestBase;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class AddTestCaseTests extends TestBase {

    AddTestCaseAsserts asserts = new AddTestCaseAsserts();

    String title = "";
    String description = "Test description";
    String expectedResult = "Test success";
    String[] steps = {"Step 1", "Step 2", "Step 3", "Step 4"};

    @BeforeMethod(groups = "requires login")
    public void login() {
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
    }

    @Test(groups = "requires login")
    public void addTestCaseTest() {
        page.navigate("https://qa-sandbox.ni.htec.rs/testcases");
        page.getByText("New Test Case").click();
        var formElementItemLocator = page.locator(".form-element-item");
        title = generateBase64();
        formElementItemLocator.getByPlaceholder("Title").fill(title);
        formElementItemLocator.getByPlaceholder("Description").fill(description);
        formElementItemLocator.getByPlaceholder("Expected Result").fill(expectedResult);

        for (int i = 0; i < steps.length - 1; i++) {
            page.locator("input[name=\"step-" + i + "\"]").fill(steps[i]);
            page.getByText("Add Test Step").click();

        }

        page.locator("input[name=\"step-" + (steps.length - 1) + "\"]").fill(steps[steps.length - 1]);

        page.getByText("Submit").click();

        var previewTitle = page.locator(".preview-card-title-value").getByText(title);
        previewTitle.scrollIntoViewIfNeeded();
        PlaywrightAssertions.assertThat(previewTitle).isAttached();
        var card = previewTitle.locator("../..");
        card.locator(".preview-card-body--items-single-preview-title").getByText("Preview").click();

        var fieldsBody = page
                .locator(".preview-card-modal-body")
                .first()
                .locator(".preview-card-modal-body--items")
                .first();
        var fields = fieldsBody
                .locator(".preview-card-modal-body--items-single")
                .all();

        PlaywrightAssertions.assertThat(fields.get(0).locator(".preview-card-modal-body--items-single-value")).containsText(description);

        PlaywrightAssertions.assertThat(fields.get(1).locator(".preview-card-modal-body--items-single-value")).containsText(expectedResult);

        PlaywrightAssertions.assertThat(fields.get(2).locator(".preview-card-modal-body--items-single-value")).containsText("No");

        var previewSteps = fieldsBody.locator(".preview-card-modal-body--items").first();
        var previewStepsListDiv = previewSteps.locator(".preview-card-modal-body--items-single-short-value").first();
        var previewStepsList = previewStepsListDiv.locator(".preview-card-modal-body--items-single-short-value").all();
        for (int i = 0; i < steps.length; i++) {
            PlaywrightAssertions.assertThat(previewStepsList.get(i)).containsText((i + 1) + ". " + steps[i]);
        }

    }

    @Test(dependsOnMethods = "addTestCaseTest", groups = "requires login")
    public void addExistingTest() {
        page.navigate("https://qa-sandbox.ni.htec.rs/testcases");
        page.getByText("New Test Case").click();
        var formElementItemLocator = page.locator(".form-element-item");
        formElementItemLocator.getByPlaceholder("Title").fill(title);
        formElementItemLocator.getByPlaceholder("Description").fill(description);
        formElementItemLocator.getByPlaceholder("Expected Result").fill(expectedResult);

        for (int i = 0; i < steps.length - 1; i++) {
            page.locator("input[name=\"step-" + i + "\"]").fill(steps[i]);
            page.getByText("Add Test Step").click();

        }

        page.locator("input[name=\"step-" + (steps.length - 1) + "\"]").fill(steps[steps.length - 1]);

        page.getByText("Submit").click();

        asserts.testCaseNotCreated(page);
    }
}
