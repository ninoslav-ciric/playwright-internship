package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;

public class DashboardPage extends BasePage{

    private final String DASHBOARD_SELECTOR = "button:has-text('Dashboard')";
    private final String TESTCASES_SELECTOR = "button:has-text('Test Cases')";
    private final String PLAYGROUND_SELECTOR = "button:has-text('Playground')";
    private final String REPORTS_BUTTON_SELECTOR = "button:has-text('Reports')";
    private final String ERROR_MESSAGE_SELECTOR = "label#validation-msg";
    private final String INTRODUCTION_SELECTOR = "button:has-text('Introduction')";

    //Locators
    private final Locator dashboardButton = page.getByText("Dashboard");
    private final Locator testCasesButton =  page.getByText("Test Cases");
    private final Locator playgroundButton =  page.getByText("Playground");
    private final Locator reportsButton =  page.getByText("Reports");
    private final Locator introductionButton = page.getByText("Introduction");;

    public DashboardPage(Page page) { super(page); }

    public void goToTestCases() {
        try {
            testCasesButton.click();
        }
        catch (Exception e) {
            Assert.fail("goToTestCases form flow failed: " + e.getMessage());
            takeScreenshot("goToTestCases form flow failed: " + e.getMessage());
        }
    }

    public void navigateToDashboard(String url) {
        try {
            safeNavigate(url);
            dashboardButton.click();
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to navigate to dashboard page: " + e.getMessage());
        }
    }
}
