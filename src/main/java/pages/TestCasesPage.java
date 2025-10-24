package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import utils.Constants;
import utils.Timeouts;

import static utils.ConfigReader.getBaseUrl;

public class TestCasesPage extends BasePage {

    public TestCasesPage(Page page) {
        super(page);
    }

    private final Locator newTestCaseButton = page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("New Test Case"));
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator descriptionInput = page.getByPlaceholder("Description");
    private final Locator expectedResultInput = page.getByPlaceholder("Expected Result");
    private final Locator testStepInput = page.getByPlaceholder("Test step");
    private final Locator addStepButton = page.getByText("Add Test Step");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Submit"));
    private final Locator automatedCheckbox = page.locator("label.switch");
    private final Locator trashButton = page.locator("button:has(i.far.fa-trash-alt)").first();
    private final Locator removeConfirmButton = page.getByText("Remove" , new Page.GetByTextOptions().setExact(true));
    private final Locator titleRequiredError = page.locator("label.form-element--validation").getByText("Title is required");
    private final Locator testCaseExistsError = page.locator("label.form-element--validation").getByText("Test case name already exist");






    public void navigateToNewTestCase(String url) {
        try {

            safeNavigate(url);
            newTestCaseButton.click();
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to navigate to new test case page: " + e.getMessage());
        }
    }

    public void navigateToTestCase(String url) {
        try {

            safeNavigate(url);
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to navigate to new test case page: " + e.getMessage());
        }
    }

    public void fillNewTestForm(String title, String description, String expectedResult, String[] testStep) {
        try {
            titleInput.click();
            titleInput.fill(title);
            descriptionInput.click();
            descriptionInput.fill(description);
            expectedResultInput.click();
            expectedResultInput.fill(expectedResult);
            for(int i=0; i<testStep.length; i++) {
                Locator currentStepInput = testStepInput.nth(i);
                currentStepInput.click();
                currentStepInput.fill(testStep[i]);
                addStepButton.click();
            }
            automatedCheckbox.check();
            submitButton.click();
        } catch (Exception e) {
            Assert.fail("New test form flow failed: " + e.getMessage());
            takeScreenshot("New test form flow failed: " + e.getMessage());
        }
    }

    public void fillNewTestFormExisingTestCase(String title, String description, String expectedResult, String[] testStep) {
        try {
            titleInput.click();
            titleInput.fill(title);
            descriptionInput.click();
            descriptionInput.fill(description);
            expectedResultInput.click();
            expectedResultInput.fill(expectedResult);
            for(int i=0; i<testStep.length; i++) {
                Locator currentStepInput = testStepInput.nth(i);
                currentStepInput.click();
                currentStepInput.fill(testStep[i]);
                addStepButton.click();
            }
            automatedCheckbox.check();
            submitButton.click();
            testCaseExistsError.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(Timeouts.DEFAULT_TIMEOUT));
        } catch (Exception e) {
            Assert.fail("New test form flow failed: " + e.getMessage());
            takeScreenshot("New test form flow failed: " + e.getMessage());
        }
    }

    public void fillNewTestFormWithoutTitle(String description, String expectedResult, String[] testStep) {
        try {
            descriptionInput.click();
            descriptionInput.fill(description);
            expectedResultInput.click();
            expectedResultInput.fill(expectedResult);
            for(int i=0; i<testStep.length; i++) {
                Locator currentStepInput = testStepInput.nth(i);
                currentStepInput.click();
                currentStepInput.fill(testStep[i]);
                addStepButton.click();
            }
            automatedCheckbox.check();
            submitButton.click();
            titleRequiredError.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(Timeouts.DEFAULT_TIMEOUT));
        } catch (Exception e) {
            Assert.fail("New test form flow failed: " + e.getMessage());
            takeScreenshot("New test form flow failed: " + e.getMessage());
        }
    }

    public void newTestCasesExpectSuccess(String title, String description, String expectedResult, String[] testStep) {
        try {
            fillNewTestForm(title, description, expectedResult, testStep);
            page.waitForURL(getBaseUrl() + Constants.TEST_CASES);
        } catch (Exception e) {
            Assert.fail("New test case success flow failed: " + e.getMessage());
        }
    }

    public void newTestCasesExpectFailure(String title, String description, String expectedResult, String[] testStep) {
        try {
            fillNewTestFormExisingTestCase(title, description, expectedResult, testStep);
        } catch (Exception e) {
            Assert.fail("New test case success flow failed: " + e.getMessage());
        }
    }

    public void newTestCasesExpectFailureMissingTitle(String description, String expectedResult, String[] testStep) {
        try {
            fillNewTestFormWithoutTitle(description, expectedResult, testStep);
        } catch (Exception e) {
            Assert.fail("New test case success flow failed: " + e.getMessage());
        }
    }

    public void previewTestCaseExpectSuccess(String title)
    {
        try
        {
            Locator card = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(title));
            card.getByText("Preview").click();
        }
        catch (Exception e)
        {
            Assert.fail("Delete test case success flow failed: " + e.getMessage());
        }
    }

    public void updateTestCaseExpectSuccess(String title, String description, String expectedResult, String[] testStep)
    {
        try
        {
            Locator card = page.locator(".preview-card").getByText(title).first();
            card.click();
            fillNewTestForm(title, description, expectedResult, testStep);
            page.waitForURL(getBaseUrl() + Constants.TEST_CASES);

        }
        catch (Exception e)
        {
            Assert.fail("Delete test case success flow failed: " + e.getMessage());
        }
    }

    public void deleteTestCaseExpectSuccess(String title)
    {
        try
        {
            Locator card = page.locator(".preview-card").getByText(title).first();
            card.click();
            trashButton.click();
            removeConfirmButton.click();
        }
        catch (Exception e)
        {
            Assert.fail("Delete test case success flow failed: " + e.getMessage());
        }
    }

    public String getCurrentUrl(){ return page.url() + Constants.TEST_CASES;}


    public boolean isCardVisible(String title)
    {
        try {
            Locator testCaseCard = page.getByText(title).first();
            testCaseCard.waitFor(new Locator.WaitForOptions().setTimeout(Timeouts.DEFAULT_TIMEOUT));
            return testCaseCard.isVisible();
        }
        catch (Exception e)
        {
            return false;
        }
    }


    public boolean isTitleErrorVisible()
    {
        try
        {
           return titleRequiredError.isVisible();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean isTestCaseExistsErrorVisible()
    {
        try
        {
            return testCaseExistsError.isVisible();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean isModalVisibleAndValid(String title)
    {
        try {

            Locator previewModalTitle = page.locator(".preview-card-modal-title-value").first();
            String titleText = previewModalTitle.textContent().trim();
            return titleText.equals(title);
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
