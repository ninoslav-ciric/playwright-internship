package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.Timeouts;

import java.util.List;
import java.util.Random;


public class TestCasesPage extends BasePage
{
    private final Locator newTestCaseButton     = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("New Test Case"));

    private final Locator titleInput            = page.getByPlaceholder("Title");
    private final Locator descriptionInput      = page.getByPlaceholder("Description");
    private final Locator expectedResultInput   = page.getByPlaceholder("Expected Result");
    private final Locator testStepInput         = page.getByPlaceholder("Test step");
    private final Locator addTestStepButton     = page.locator("div.full-width-btn");
    private final Locator autoSwitch            = page.locator("div.react-switch-bg");
    private final Locator submitButton          = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));

    private final Locator portraitGrid          = page.locator("div.portrait-grid");
    private final Locator deleteButton          = page.locator("button.btn.btn-danger");
    private final Locator testCaseCards          = portraitGrid.locator("div.preview-card");
    private final Locator confirmDeleteButton    = page.locator("div.confirmation-dialog--buttons--confirm", new Page.LocatorOptions().setHasText("Remove"));

    private final Locator allErrors             = page.locator("label.form-element--validation");
    private final Locator titleError            = page.getByText("Title is required");
    private final Locator expectedResultError   = page.getByText("Expected result is required");
    private final Locator stepsError            = page.getByText("There must be at least one test step");

    private final Locator successResponse       = page.locator("div.Toastify__toast.Toastify__toast--default.SUCCESS_TOAST");


    public TestCasesPage(Page page)
    {
        super(page);
    }


    @Override
    public void navigateTo(String url)
    {
        safeNavigate(url);
        waitForVisible(newTestCaseButton, Timeouts.SHORT_TIMEOUT);
    }


    public void createNewTestCase(String title, String description, String expectedResult, String[] testSteps, boolean onOffSwitch)
    {
        clickNewTestCaseButton();
        fillNewTestCaseForm(title, description, expectedResult, testSteps, onOffSwitch);
    }


    public void deleteRandomTestCase()
    {
        waitForVisible(portraitGrid, Timeouts.DEFAULT_TIMEOUT);

        if (testCaseCards.count() == 0)
            return;

        int randomIndex = new Random().nextInt(testCaseCards.count());
        Locator randomTestCase = testCaseCards.nth(randomIndex);

        randomTestCase.scrollIntoViewIfNeeded();
        safeLocatorClick(randomTestCase);

        waitForVisible(deleteButton, Timeouts.DEFAULT_TIMEOUT);
        safeLocatorClick(deleteButton);

        waitForVisible(confirmDeleteButton, Timeouts.DEFAULT_TIMEOUT);
        safeLocatorClick(confirmDeleteButton);
    }


    public String getSuccessResponseMessage()
    {
        getSuccessResponse().waitFor(); // waits for the toast to appear
        return getSuccessResponse().innerText().trim();
    }


    public List<String> getVisibleErrorMessages()
    {
        allErrors.waitFor();
        return allErrors.allInnerTexts().stream().map(String::trim).toList();
    }


    public int getNumberOfTestCases()
    {
        return testCaseCards.count();
    }

    public Locator getSuccessResponse()     { return successResponse.first(); }
    public Locator getTitleError()          { return titleError; }
    public Locator getExpectedResultError() { return expectedResultError; }
    public Locator getStepsError()          { return stepsError; }



    private void setTitle(String title)
    {
        safeLocatorFill(titleInput, title);
    }

    private void setDescription(String description)
    {
        safeLocatorFill(descriptionInput, description);
    }

    private void setExpectedResult(String result)
    {
        safeLocatorFill(expectedResultInput, result);
    }

    private void setTestSteps(String[] steps)
    {
        for (int i = 0; i < steps.length; i++)
        {
            safeLocatorFill(testStepInput.nth(i), steps[i]);
        }
    }

    private void setAutoSwitch(boolean OnOff)
    {
        boolean current = autoSwitch.isChecked();
        if(current != OnOff)
        {
            safeLocatorClick(autoSwitch);
        }
    }

    private void clickAddStep()
    {
        safeLocatorClick(addTestStepButton);
    }

    private void clickSubmitButton()
    {
        safeLocatorClick(submitButton);
    }

    private void clickNewTestCaseButton()
    {
        safeLocatorClick(newTestCaseButton);
    }

    private void fillNewTestCaseForm(String title, String description, String expectedResult, String[] testSteps, boolean onOffSwitch)
    {
        setTitle(title);
        setDescription(description);
        setExpectedResult(expectedResult);
        fillAndAddSteps(testSteps);
        setAutoSwitch(onOffSwitch);
        clickSubmitButton();
    }

    private void fillAndAddSteps(String[] steps)
    {
        if (steps != null && steps.length > 0)
        {
            int currentSteps = testStepInput.count();
            while(currentSteps < steps.length)
            {
                clickAddStep();
                currentSteps = testStepInput.count();
            }

            setTestSteps(steps);
        }
    }
}
