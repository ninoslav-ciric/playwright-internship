package tests;

import assertion.LoginPageAsserts;
import base.TestBase;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.AllureClass;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class TestCasesTest extends TestBase {
    private final String TESTCASE_BUTTON_SELECTOR = "a[href='/testcase']";
    private final SoftAssert softAssert = new SoftAssert();
    @Test(groups = {"positive"},
            description = "Verify successful creating of new test case"
    )
    @Description("Verify successful creating of new test case")
    public void testTestCaseValid(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to test case.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        //Title
        AllureClass.logStep("Fill input for title.");
        Locator titleInput = page.getByPlaceholder("Title");
        titleInput.fill("Vanja test");

        //Description
        AllureClass.logStep("Fill input for description.");
        Locator descriptionInput = page.getByPlaceholder("Description");
        descriptionInput.fill("Opis problema jedan dva tri");

        //Expected Result
        AllureClass.logStep("Fill input for expected result.");
        Locator expectedResultInput = page.getByPlaceholder("Expected Result");
        expectedResultInput.fill("Nesto se desava ovde");

        //Test step
        AllureClass.logStep("Fill input for test step.");
        Locator testStepInput = page.getByPlaceholder("Test step");
        testStepInput.fill("Korak broj jedan");


        //Button Submit
        AllureClass.logStep("Click submit button.");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();


        page.waitForSelector("div:has-text('Vanja test')", new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));


        softAssert.assertTrue(divElement.isVisible(), "Test case should be visible");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testTestCaseValid"}, groups = {"negative"},
            description = "Verify invalid creating of new test case"
    )
    @Description("Verify invalid creating of new test case")
    private void testTestCaseInvalid(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        AllureClass.logStep("Navigate to test case.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        AllureClass.logStep("Click submit button.");
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();

        page.waitForSelector("label:has-text('Title is required')", new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator divElement = page.getByText("Title is required");

        softAssert.assertTrue(divElement.isVisible(), "Invalid send is not working");
        softAssert.assertAll();

    }

    @Test(dependsOnMethods = {"testTestCaseValid", "testTestCaseInvalid"}, groups = {"positive"},
            description = "Verify successful deleting of test case"
    )
    @Description("Verify successful deleting of test case")
    private void deleteTestCaseTest(){
        AllureClass.logStep("Login to page.");
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);
        AllureClass.logStep("Navigate to test case.");
        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();

        AllureClass.logStep("Navigate to specific test case that needs to be deleted.");
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));
        divElement.click();

        AllureClass.logStep("Click the delete button");
        Locator deleteButton = page.locator("button:has(i.far.fa-trash-alt)").first();
        deleteButton.click();
        AllureClass.logStep("Click the remove button");
        Locator removeElement = page.locator("#react-confirm-alert").getByText("Remove", new Locator.GetByTextOptions().setExact(true));
        removeElement.click();

        Locator removedElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));

        softAssert.assertFalse(removedElement.isVisible(), "Report should be deleted");
        softAssert.assertAll();
    }
}
