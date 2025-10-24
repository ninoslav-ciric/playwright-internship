package tests;

import assertion.LoginPageAsserts;
import base.TestBase;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static utils.ConfigReader.getValidEmail;
import static utils.ConfigReader.getValidPassword;

public class TestCasesTest extends TestBase {
    private final String TESTCASE_BUTTON_SELECTOR = "a[href='/testcase']";
    private final SoftAssert softAssert = new SoftAssert();
    @Test
    public void testTestCaseValid(){

        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        //Title
        Locator titleInput = page.getByPlaceholder("Title");
        titleInput.fill("Vanja test");

        //Description
        Locator descriptionInput = page.getByPlaceholder("Description");
        descriptionInput.fill("Opis problema jedan dva tri");

        //Expected Result
        Locator expectedResultInput = page.getByPlaceholder("Expected Result");
        expectedResultInput.fill("Nesto se desava ovde");

        //Test step
        Locator testStepInput = page.getByPlaceholder("Test step");
        testStepInput.fill("Korak broj jedan");


        //Button Submit
        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();


        page.waitForSelector("div:has-text('Vanja test')", new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));


        softAssert.assertTrue(divElement.isVisible(), "Test case should be visible");
        softAssert.assertAll();
    }

    @Test
    private void testTestCaseInvalid(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();
        page.locator("//*[@id=\"root\"]/div/div[3]/div[2]/div/div[1]/div/div/div/span/a").click();

        Locator submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();

        page.waitForSelector("label:has-text('Title is required')", new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator divElement = page.getByText("Title is required");

        softAssert.assertTrue(divElement.isVisible(), "Invalid send is not working");
        softAssert.assertAll();

    }

    @Test
    private void deleteTestCaseTest(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());

        LoginPageAsserts loginPageAsserts = new LoginPageAsserts();
        loginPageAsserts.validateLogin(loginPage);

        page.locator("//*[@id=\"root\"]/div/div[3]/div[1]/div/div/a[3]").click();

        Locator divElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));
        divElement.click();

        Locator deleteButton = page.locator("button:has(i.far.fa-trash-alt)").first();
        deleteButton.click();

        Locator removeElement = page.locator("#react-confirm-alert").getByText("Remove", new Locator.GetByTextOptions().setExact(true));
        removeElement.click();

        Locator removedElement = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Vanja test Description: Opis"));

        softAssert.assertFalse(removedElement.isVisible(), "Report should be deleted");
        softAssert.assertAll();
    }
}
