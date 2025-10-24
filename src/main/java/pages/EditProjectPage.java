package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;

import static utils.ConfigReader.getBaseUrl;

public class EditProjectPage extends BasePage{
    private NewProjectPage newProjectPage;

    private final Locator titleInput =  page.getByPlaceholder("Title");
    private final Locator peoplePlaceholder = page.getByText("People");

    private final Locator technologyInput =  page.getByPlaceholder("Technology");
    private final Locator createButton = page.locator("div.project-bottom-technologies:has-text('Here you can create technologies') >> button:has-text('Create')");



    public EditProjectPage(Page page) {
        super(page);
        newProjectPage = new NewProjectPage(page);
    }

    public void navigateToEditProjectPage() {
        try {

            newProjectPage.navigateToNewProjectPage(getBaseUrl() + "new-project");
            newProjectPage.expectSuccessfullyEnteringTitleAndClickSubmit("DProjekat" + java.util.UUID.randomUUID().toString().substring(0,10));

        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to navigate to login page: " + e.getMessage());
        }
    }

    public void selectPeopleOptions(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("People")).click();

        page.locator("[data-testid='option']")
                .filter(new Locator.FilterOptions().setHasText("Aida Hadzibegovic"))
                .click();
    }


    public boolean isOptionSelected(String optionText) {
        Locator selectedOption = page.locator("div.option.selected").filter(new Locator.FilterOptions().setHasText(optionText));
        return selectedOption.isVisible();
    }


    public String getCurrentUrl() {
        return page.url();
    }

    public void inputTechnologyName(){
        technologyInput.fill("Cypress");
    }
    public void clickOnCreateButton()
    {
        createButton.click();
    }
    public Locator getFirstTechnology(){
        return page.locator(
                "div.project-bottom-technologies:has-text('Here you can create technologies') >> div.settings >> div.settings-list"
        ).first();
    }
}
