package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;

import static utils.Timeouts.DEFAULT_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;

public class ProjectsPage extends BasePage {

    //Locators
    private final Locator newProjectButton = page.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("New Project")).
            and(page.locator(".btn.btn-primary"));
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));
    private final Locator toastyAlert = page.getByRole(AriaRole.ALERT)
            .and(page.locator(".Toastify__toast-body"));
    private final Locator projectCard = page.locator(".preview-card-title-value");
    private final Locator technology = page.getByPlaceholder("Technology");
    private final Locator createTechnologyButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Create")).nth(0); //filter was bugging
    private final Locator validationMessage = page.locator("#validation-msg");
    private final Locator removeButton = page.getByRole(AriaRole.BUTTON)
            .and(page.locator(".btn.btn-danger"));
    private final Locator removePopUpButton = page.locator(".confirmation-dialog--buttons--confirm");

    public ProjectsPage(Page page) {
        super(page);
    }

    public void navigateToProjects(String url) {
        try {
            safeNavigate(url);
            newProjectButton.click();
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to open New Project: " + e.getMessage());
        }
    }

    public void navigateToPage(String url) {
        try {
            safeNavigate(url);
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to open New Project: " + e.getMessage());
        }
    }

    public void createProjectExpectSuccess(String title) {
        try {
            fillProjectCreateFormWithLocators(title);
            toastyAlert.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String toastTxt = toastyAlert.innerText();
            System.out.println("This is toast message:"+toastTxt);
            Assert.assertTrue(toastTxt.contains("✔ Project created successfully"), "Toast message not showing");
        } catch (Exception e) {
            Assert.fail("Project creation success flow failed: " + e.getMessage());
        }
    }

    public void createProjectExpectFailure(String title) {
        try {
            fillProjectCreateFormWithLocators(title);
            validationMessage.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            Assert.assertTrue(isValidationMessageVisible(), "Validation message not showing");
        } catch (Exception e) {
            Assert.fail("Project creation success flow failed: " + e.getMessage());
        }
    }

    public void createProjectTechnology(String technologyText){
        try {
            fillTechnolgyFormWithLocators(technologyText);
            toastyAlert.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String toastTxt = toastyAlert.innerText();
            Assert.assertTrue(toastTxt.contains("✔ Technology created successfully"), "Toast message not showing");
        } catch (Exception e) {
            Assert.fail("Technology creation success flow failed: " + e.getMessage());
        }
    }

    public boolean isValidationMessageVisible(){
        try {
            validationMessage.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            return validationMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void removeButtonClick(){
        try {
            page.locator(".preview-card-title-value").first().click();

            removeButton.click();
            removePopUpButton.waitFor(new Locator.WaitForOptions().setTimeout(SHORT_TIMEOUT));
            removePopUpButton.click();

            toastyAlert.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            String toastTxt = toastyAlert.innerText();
            Assert.assertTrue(toastTxt.contains("✔ Project removed successfully"), "Toast message not showing");


        } catch (Exception e){
            Assert.fail("Delete Project form flow failed: " + e.getMessage());
            takeScreenshot("Delete Project form flow failed: " + e.getMessage());
        }
    }

    //Locator approach
    private void fillProjectCreateFormWithLocators(String title) {
        try {
            titleInput.click();
            titleInput.fill(title);
            submitButton.click();
        }
        catch (Exception e) {
            Assert.fail("Create Project form flow failed: " + e.getMessage());
            takeScreenshot("Create project form flow failed: " + e.getMessage());
        }
    }


    private void fillTechnolgyFormWithLocators(String technologyText) {
        try {
            page.locator(".preview-card-title-value").first().waitFor();
            String title = projectCard.first().textContent();
            System.out.println("Title of the Project that we are adding new Technology: " + title);
            projectCard.first().click();
            technology.waitFor(new Locator.WaitForOptions().setTimeout(DEFAULT_TIMEOUT));
            technology.click();
            technology.fill(technologyText);
            createTechnologyButton.click();
        }
        catch (Exception e) {
            Assert.fail("Create Technology flow failed: " + e.getMessage());
            takeScreenshot("Create Technology flow failed: " + e.getMessage());
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