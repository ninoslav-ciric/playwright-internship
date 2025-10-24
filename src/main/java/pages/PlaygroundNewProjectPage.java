package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;

import java.util.List;

public class PlaygroundNewProjectPage extends BasePage{

    //Locators
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator submitButton = page.getByText("Submit");
    private final Locator titlesLocator = page.locator(".preview-card-title-value");
    private final Locator errorMessage = page.locator("#validation-msg").first();
    private final Locator deleteButton = page.locator("button.btn.btn-danger");
    private final Locator confirmButton = page.locator("div.confirmation-dialog--buttons--confirm");


    public PlaygroundNewProjectPage(Page page) { super(page); }

    private void fillNewCaseFormWithLocators(String title) {
        try {
            titleInput.click();
            titleInput.fill(title);

            submitButton.click();
        }
        catch (Exception e) {
            Assert.fail("NewCase form flow failed: " + e.getMessage());
            takeScreenshot("NewCase form flow failed: " + e.getMessage());
        }
    }

    public List<String> getAllTitles() {
        //safeNavigate(getTestCasesUrl());
        System.out.println("TitleLista:");
        System.out.println(titlesLocator.allTextContents());
        return titlesLocator.allTextContents();
    }

    public void newProjectExpectSaccess(String title) {
        try {
            fillNewCaseFormWithLocators(title);
        } catch (Exception e) {
            Assert.fail("New Project success flow failed: " + e.getMessage());
        }
    }

    public void deleteProject(String title) {
        Locator targetTitle = page.locator(".preview-card-title-value:has-text('" + title + "')");
        targetTitle.first().click();

        deleteButton.waitFor();
        deleteButton.click();

        confirmButton.waitFor();
        confirmButton.click();
    }

    public boolean isErrorVisible() {
        return errorMessage.isVisible();
    }
}
