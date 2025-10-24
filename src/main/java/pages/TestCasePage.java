package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;

import static utils.Timeouts.DEFAULT_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;

public class TestCasePage extends BasePage {

    //Locators
    private final Locator newTestCaseButton = page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("New Test Case")).
            and(page.locator(".btn.btn-primary"));
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator ExpectedResultInput =  page.getByPlaceholder("Expected Result");
    private final Locator TestStepsInput =  page.getByPlaceholder("Test step");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));
    private final Locator toastyAlert = page.getByRole(AriaRole.ALERT)
            .and(page.locator(".Toastify__toast-body"));
    private final Locator ValidationMessage = page.locator("#validation-msg");
    private final Locator deleteButton = page.getByRole(AriaRole.BUTTON)
            .and(page.locator(".btn.btn-danger"));
    private final Locator removeButton = page.locator(".confirmation-dialog--buttons--confirm");
    private final Locator titleLocator = page.locator(".preview-card-title-value");

    public TestCasePage(Page page) {
        super(page);
    }

    public void navigateToTestCases(String url) {
        try {
            safeNavigate(url);
            newTestCaseButton.click();
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to open New Test Cases: " + e.getMessage());
        }
    }

    public void navigateToTestCasesForError(String url) {
        try {
            safeNavigate(url);
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to open New Test Cases: " + e.getMessage());
        }
    }

    public void createTestCaseExpectSuccess(String title, String expectedResult, String testSteps) {
        try {
            fillTestCaseFormWithLocators(title, expectedResult, testSteps);
            toastyAlert.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String toastTxt = toastyAlert.innerText();
            Assert.assertTrue(toastTxt.contains("Test case created successfully"), "Toast message not showing");
        } catch (Exception e) {
            Assert.fail("Test case creation success flow failed: " + e.getMessage());
        }
    }


    public void createExistingNameTestCase(String expectedResult, String testSteps) {
        try {
            page.locator(".preview-card-title-value").first().waitFor();
            String title = titleLocator.first().textContent();
            System.out.println("Naslov preview kartice: " + title);
            newTestCaseButton.click();
            fillTestCaseFormWithLocators(title,expectedResult, testSteps);
            toastyAlert.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String toastTxt = toastyAlert.innerText();
            Assert.assertTrue(toastTxt.contains("Test case creating failed"), "Toast message not showing");
        } catch (Exception e) {
            Assert.fail("Test case creation success flow failed: " + e.getMessage());
        }
    }

    //Locator approach
    private void fillTestCaseFormWithLocators(String title, String expectedResult, String testSteps) {
        try {
            titleInput.click();
            titleInput.fill(title);
            ExpectedResultInput.click();
            ExpectedResultInput.fill(expectedResult);
            TestStepsInput.click();
            TestStepsInput.fill(testSteps);
            submitButton.click();
        }
        catch (Exception e) {
            Assert.fail("Create Test Case form flow failed: " + e.getMessage());
            takeScreenshot("Create test case form flow failed: " + e.getMessage());
        }
    }

    public void deleteButtonClick() {
        try {
            page.locator(".portrait-grid a.preview-card:has-text('The one that needs to be deleted')").click();
            deleteButton.click();
            removeButton.waitFor(new Locator.WaitForOptions().setTimeout(SHORT_TIMEOUT));
            removeButton.click();
        }
        catch (Exception e) {
            Assert.fail("Delete Test Case form flow failed: " + e.getMessage());
            takeScreenshot("Delete Test Case form flow failed: " + e.getMessage());
        }
    }

    public boolean isErrorVisible() {
        try {
            return ValidationMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return ValidationMessage.textContent();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean isToastVisiable() {

        return toastyAlert.isVisible();
    }

   public boolean isTestCaseExists() {
        return page.locator(".portrait-grid a.preview-card:has-text('The one that needs to be deleted')").count() > 0;
   }

    public String getCurrentUrl() {
        return page.url();
    }
}