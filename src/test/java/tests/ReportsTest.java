package tests;

import base.TestBase;
import assertion.LoginPageAsserts;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import org.testng.asserts.SoftAssert;
import utils.AllureClass;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class ReportsTest extends TestBase {
    private final SoftAssert softAssert = new SoftAssert();

    @Test(groups = {"positive"},
            description = "Verify successful creating of new report"
    )
    @Description("Verify successful creating of new report")
    private void testReportsValid() throws AWTException {
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to reports.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[5]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        //Summary
        AllureClass.logStep("Fill input for summary.");
        Locator summaryInput = page.getByPlaceholder("Summary");
        summaryInput.fill("Vanja test 123");

        AllureClass.logStep("Select option Task.");
        Locator dropdownTask = page.locator("select[name='type']");
        dropdownTask.selectOption("Task");

        AllureClass.logStep("Select option Medium.");
        Locator dropdownSeverity = page.locator("select[name='severity']");
        dropdownSeverity.selectOption("Medium");

        AllureClass.logStep("Select option Minor.");
        Locator dropdownPriority = page.locator("select[name='priority']");
        dropdownPriority.selectOption("Minor");

        AllureClass.logStep("Fill input for description.");
        Locator descriptionInput = page.getByPlaceholder("Description");
        descriptionInput.click();
        descriptionInput.fill("Opis Vanja jedan dva tri");

        AllureClass.logStep("Fill input for reproduction step.");
        Locator reproductionStepInput = page.getByPlaceholder("Reproduction step");
        reproductionStepInput.click();
        reproductionStepInput.fill("Korak jedan");

        AllureClass.logStep("Fill input for expected result.");
        Locator resultInput = page.getByPlaceholder("Expected result");
        resultInput.click();
        resultInput.fill("Rezultat jedan dva tri");

        //page.locator("input[name='image']").setInputFiles(Paths.get("C:\\Users\\VanjaStankovic\\Desktop\\Vanja Stankovic Playwright\\src\\test\\screenshot\\Screenshot 2025-10-24 105836.png"));
        //page.locator("input[name='image']").click();
        AllureClass.logStep("Click button to add image.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div/div[2]/div[8]/div/div[2]/div[2]/label").click();

        Robot robot = new Robot();
        robot.delay(1000);

        StringSelection filePath = new StringSelection("C:\\Users\\VanjaStankovic\\Desktop\\Vanja Stankovic Playwright\\src\\test\\screenshot\\Screenshot 2025-10-24 105836.png");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);

        // Press CTRL+V to paste the file path
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter to confirm
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(1000);

        AllureClass.logStep("Click button submit.");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();

        page.waitForSelector("div:has-text('Vanja test 123')", new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type: Task"));

        softAssert.assertTrue(divElement.isVisible(), "Report should be visible");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testReportsValid"}, groups = {"negative"},
            description = "Verify invalid creating of new report"
    )
    @Description("Verify invalid creating of new report")
    private void reportTestCaseInvalid(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to reports.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[5]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        AllureClass.logStep("Click button submit.");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();

        page.waitForSelector("label:has-text('Summary is required')", new Page.WaitForSelectorOptions().setTimeout(4000));

        Locator summaryElement = page.getByText("Summary is required");
        Locator issueElement = page.getByText("Issue type is required");
        Locator severityElement = page.getByText("Severity is required");
        Locator priorityElement = page.getByText("Priority is required");
        Locator stepsElement = page.getByText("There must be at least one reproduction step");

        softAssert.assertTrue(summaryElement.isVisible(), "Invalid send for Summary, is not working");
        softAssert.assertTrue(issueElement.isVisible(), "Invalid send for Issue type, is not working");
        softAssert.assertTrue(severityElement.isVisible(), "Invalid send for Severity, is not working");
        softAssert.assertTrue(priorityElement.isVisible(), "Invalid send for Priority, is not working");
        softAssert.assertTrue(stepsElement.isVisible(), "There must be at least one reproduction step");
        softAssert.assertAll();

    }

    @Test(dependsOnMethods = {"testReportsValid", "reportTestCaseInvalid"}, groups = {"positive"},
            description = "Verify data in preview section"
    )
    @Description("Verify data in preview section")
    private void previewReportTest(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to reports.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[5]").click();

        AllureClass.logStep("Navigate to preview of report.");
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type:"));
        divElement.getByText("Preview").click();

        page.waitForSelector("div:has-text('Report Preview')", new Page.WaitForSelectorOptions().setTimeout(4000));

        Locator previewReportTitle = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type:"));
        String title = previewReportTitle.textContent().trim();

        softAssert.assertTrue(title.contains("Vanja test 123"), "Content needs to match in preview");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testReportsValid", "reportTestCaseInvalid", "previewReportTest"}, groups = {"positive"},
            description = "Verify successful deleting of report"
    )
    @Description("Verify successful deleting of report")
    private void deleteReportTest(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to reports.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[5]").click();

        AllureClass.logStep("Navigate to specific report.");
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type:"));
        divElement.click();

        AllureClass.logStep("Click button Remove");
        Locator deleteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove"));
        deleteButton.click();

        AllureClass.logStep("Click button trash");
        Locator removeElement = page.locator("#react-confirm-alert").getByText("Remove", new Locator.GetByTextOptions().setExact(true));
        removeElement.click();

        Locator removedElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type: Task"));

        softAssert.assertFalse(removedElement.isVisible(), "Report should be deleted");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testReportsValid", "reportTestCaseInvalid", "previewReportTest", "deleteReportTest"}, groups = {"positive"},
            description = "Verify successful deleting of report"
    )
    @Description("Verify successful deleting of report")
    private void deleteReportTestFail(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to reports.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[5]").click();

        AllureClass.logStep("Navigate to specific report.");
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Nesto 123:"));
        divElement.click();

        AllureClass.logStep("Click button Remove");
        Locator deleteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove"));
        deleteButton.click();

        AllureClass.logStep("Click button trash");
        Locator removeElement = page.locator("#react-confirm-alert").getByText("Remove", new Locator.GetByTextOptions().setExact(true));
        removeElement.click();

        Locator removedElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test 123 Issue type: Task"));

        softAssert.assertFalse(removedElement.isVisible(), "Report should be deleted");
        softAssert.assertAll();
    }
}
