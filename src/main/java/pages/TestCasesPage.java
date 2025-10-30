package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TestCasesPage extends BasePage {

    private final Locator navTitle = page.getByText("New Test Case").first();

    private final Locator titleInput        = page.getByPlaceholder("Title");
    private final Locator descriptionText   = page.getByPlaceholder("Description");
    private final Locator expectedResultInp = page.getByPlaceholder("Expected Result");

    private final Locator stepInputs = page.locator("input.form-element--input[name^='step-']");
    private final Locator addStepBtn = page.getByText("Add Test Step").first();
    private final Locator automatedSwitchInput = page.locator("div.react-switch-bg");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));


    //Error messages
    private final Locator titleErrorMessage = page.getByText("Title is required");
    private final Locator expectedResultErrorMessage = page.getByText("Expected result is required");
    private final Locator stepsErrorMessage = page.getByText("There must be at least one test step");


    public TestCasesPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        safeNavigate(baseUrl + "new-testcase");
        navTitle.waitFor();
    }

    public void setTitle(String title) {
        titleInput.fill(title);
    }

    public void setDescription(String description) {
        descriptionText.fill(description);
    }

    public void setExpectedResult(String expected) {
        expectedResultInp.fill(expected);
    }

    public void submit() {
        submitButton.click();
    }

    public void addStep() {
        addStepBtn.click();
    }

    public void setStep(int index, String text) {
        ensureStepCount(index + 1);
        stepInputs.nth(index).fill(text);
    }

    public void ensureStepCount(int count) {
        int current = stepInputs.count();
        while (current < count) {
            addStep();
            current = stepInputs.count();
        }
    }

    public void setAutomated(boolean value) {
        boolean current = automatedSwitchInput.isChecked();
        if (current != value) {
            automatedSwitchInput.click();
        }
    }

    public boolean isAutomatedChecked() {
        return automatedSwitchInput.isChecked();
    }


    public Locator getTitleInput()        { return titleInput; }
    public Locator getDescriptionText()   { return descriptionText; }
    public Locator getExpectedResultInp() { return expectedResultInp; }
    public Locator getStepInputs()        { return stepInputs; }
    public Locator getAddStepBtn()        { return addStepBtn; }
    public Locator getSubmitButton()      { return submitButton; }
    public Locator getAutomatedSwitchInput(){ return automatedSwitchInput; }
    public Locator getNavTitle()          { return navTitle; }
    public Locator getTitleErrorMessage() {return titleErrorMessage;}
    public Locator getStepsErrorMessage() { return stepsErrorMessage; }
    public Locator getExpectedResultErrorMessage() { return expectedResultErrorMessage; }

    public void fillForm(String title, String description, String expectedResult, String[] steps, boolean automated) {
        setTitle(title);
        setDescription(description);
        setExpectedResult(expectedResult);
        if (steps != null && steps.length > 0) {
            ensureStepCount(steps.length);
            for (int i = 0; i < steps.length; i++) {
                stepInputs.nth(i).fill(steps[i]);
            }
        }

        setAutomated(automated);

        submit();
    }


}
