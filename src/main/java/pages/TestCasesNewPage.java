package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import java.util.List;

public class TestCasesNewPage extends BasePage {

    //Locators
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator descriptionInput =  page.getByPlaceholder("Description");
    private final Locator expectedInput =  page.getByPlaceholder("Expected Result");
    private final Locator testStepInput =  page.getByPlaceholder("Test step");
    private final Locator addStepButton = page.getByText("Add Test Step");
    private final Locator stepInputs = page.locator("input.form-element--input[name^='step-']");
    //private final Locator automatedSwitchButton = page.getByText("Introduction");
    private final Locator submitButton = page.getByText("Submit");
    private final Locator titlesLocator = page.locator(".preview-card-title-value");
    private final Locator errorAlert = page.locator("//*[@role='alert']");
    private final Locator errorMessage = page.locator("#validation-msg");
    private final Locator deleteButton = page.locator("i.far.fa-trash-alt");
    private final Locator confirmButton = page.locator("div.confirmation-dialog--buttons--confirm");

    public TestCasesNewPage(Page page) { super(page); }

    public Locator getStepInputs() {
        return stepInputs;
    }

    private void fillNewCaseFormWithLocators(String title, String description, String expectedResult, String testStep) {
        try {
            titleInput.click();
            titleInput.fill(title);
            descriptionInput.click();
            descriptionInput.fill(description);
            expectedInput.click();
            expectedInput.fill(expectedResult);
            testStepInput.click();
            testStepInput.fill(testStep);

            submitButton.click();
        }
        catch (Exception e) {
            Assert.fail("NewCase form flow failed: " + e.getMessage());
            takeScreenshot("NewCase form flow failed: " + e.getMessage());
        }
    }

    public void newCaseExpectSuccess(String title, String description, String expectedResult, String testStep) {
        try {
            fillNewCaseFormWithLocators(title, description, expectedResult, testStep);
            //page.waitForSelector(DASHBOARD_SELECTOR, new Page.WaitForSelectorOptions().setTimeout(DEFAULT_TIMEOUT));
        } catch (Exception e) {
            Assert.fail("NewCase success flow failed: " + e.getMessage());
        }
    }

    public void newCaseExpectFailure(String title, String description, String expectedResult, String testStep) {
        try {
            fillNewCaseFormWithLocators(title, description, expectedResult, testStep);
        } catch (Exception e) {
            Assert.fail("NewCase failture flow failed: " + e.getMessage());
        }
    }

    public void setStep(int index, String text) {
        stepCount(index + 1);
        stepInputs.nth(index).fill(text);
    }

    public void stepCount(int count) {
        int temp = stepInputs.count();
        while (temp < count) {
            addStepButton.click();
            temp = stepInputs.count();
        }
    }

    public void deleteTestCase(String title) {
        Locator targetTitle = page.locator(".preview-card-title-value:has-text('" + title + "')");
        targetTitle.first().click();

        deleteButton.waitFor();
        deleteButton.click();

        confirmButton.waitFor();
        confirmButton.click();
    }

    public List<String> getAllTitles() {
        //safeNavigate(getTestCasesUrl());
        System.out.println("TitleLista:");
        System.out.println(titlesLocator.allTextContents());
        return titlesLocator.allTextContents();
    }

    public boolean isErrorVisible() {
        return errorMessage.first().isVisible();
    }

    public boolean isError2Visible() {
        return isExpectedResultErrorVisible() || isStepErrorVisible();
    }

    public boolean isExpectedResultErrorVisible() {
        return page.getByText("Expected result is required", new Page.GetByTextOptions().setExact(true)).isVisible();
    }

    public boolean isStepErrorVisible() {
        return page.getByText("There must be at least one test step", new Page.GetByTextOptions().setExact(true)).isVisible();
    }

}
