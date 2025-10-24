package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.util.Random;

public class TestCasePage extends BasePage{

    private final Locator newTestCaseButton = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("New Test Case"));
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator descriptionInput = page.getByPlaceholder("Description");
    private final Locator expectedResultInput = page.getByPlaceholder("Expected Result");
    private final Locator testStep0Input = page.locator("#step-0");//page.getByPlaceholder("Test step");
    private final Locator addTestStepButton = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Add Test Step"));
    private final Locator automatedSwitchButton = page.locator("div.react-switch-bg");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));
    private final Locator confirmAlert = page.locator(".react-confirm-alert");
    private final Locator deleteTestCaseButton = page.locator("button.btn.btn-danger");
    public final Locator validationMessageNoExpectedResult = page.locator("text=Expected result is required");
    public final Locator validationMessageExistingTitle = page.locator("text=Test case name already exist");
    public final Locator toastContainer = page.locator("div.Toastify__toast-container.Toastify__toast-container--bottom-right");
    private static final String TESTCASE_URL = "/testcases";
    private static final String NEW_TESTCASE_URL = "/new-testcase";
    private final Locator confirmRemoveButton = page.locator("div.confirmation-dialog--buttons--confirm").filter(new Locator.FilterOptions().setHasText("Remove"));
    public TestCasePage(Page page) { super(page); }

    public void createTestCaseExpectSuccess(String title){
        try {
            newTestCaseButton.click();
            Assert.assertTrue(isNewTestcasePageVisible(), "New test case page should be visible");

            fillValidNewTestCaseForm(title);

        }
        catch(Exception e){
            Assert.fail("Create new test case failed: " + e.getMessage());
        }
    }
    public void createTestCaseExpectFailureNoExpectedResult(String title){
        try {
            newTestCaseButton.click();
            Assert.assertTrue(isNewTestcasePageVisible(), "New test case page should be visible");

            fillInvalidNewTestCaseFormWithoutExpectedResult(title);


        }
        catch(Exception e){
            Assert.fail("Create new test case failed: " + e.getMessage());
        }
    }
    public void createTestCaseExpectFailureExistingTitle(String title){
        try {
            newTestCaseButton.click();
            Assert.assertTrue(isNewTestcasePageVisible(), "New test case page should be visible");

            fillValidNewTestCaseForm(title);
        }
        catch(Exception e){
            Assert.fail("Create new test case failed: " + e.getMessage());
        }
    }
    private void fillValidNewTestCaseForm(String title){
        titleInput.click();
        titleInput.fill(title);
        descriptionInput.click();
        descriptionInput.fill("Opis test case-a");
        expectedResultInput.click();
        expectedResultInput.fill("pass");
        testStep0Input.click();
        testStep0Input.fill("Do smth");
        automatedSwitchButton.click();
        submitButton.click();
    }

    private void fillInvalidNewTestCaseFormWithoutExpectedResult(String title){//Expected result is required
        titleInput.click();
        titleInput.fill(title);
        testStep0Input.click();
        testStep0Input.fill("Do smth");
        submitButton.click();
    }

    public boolean isTestcasePageVisible(){
        page.waitForURL(url -> url.contains(TESTCASE_URL));
        return page.url().contains(TESTCASE_URL);
    }

    public boolean isNewTestcasePageVisible(){
        //page.waitForURL(NEW_TESTCASE_URL);
        return page.url().contains(NEW_TESTCASE_URL);
    }

    private Locator getTestcaseByTitle(String title){
        return page.locator(".preview-card-title-value:text-is('" + title + "')");
    }

    public Locator getRandomTestcase(){
        if(!isTestcasePageVisible()){
            safeNavigate(TESTCASE_URL);
        }
        Locator allTestCases = page.locator(".preview-card-title-value");
        page.locator(".preview-card-title-value").first().waitFor();

        int count = allTestCases.count();
        if(count == 0){
            return null;
        }
        Random random = new Random();
        int ran = random.nextInt(count);
        return allTestCases.nth(ran);

    }

    public boolean isTestcaseExist(String title){
        return getTestcaseByTitle(title).isVisible();
    }

    public boolean isToastifyVisible(){
        toastContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return toastContainer.isVisible();

    }

    public void deleteTestCaseExpectSuccess(String title){
        try {
            getTestcaseByTitle(title).click();
            deleteTestCaseButton.click();
            Assert.assertTrue(isConfirmationDialogVisible(), "Confirmation dialog should be visible");
            confirmRemoveButton.click();
        }
        catch(Exception e){
            Assert.fail("Delete test case failed: " + e.getMessage());
        }
    }

    public boolean isConfirmationDialogVisible(){
        confirmAlert.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return confirmAlert.isVisible();
    }

}
