package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.util.Random;


public class PlaygroundPage extends BasePage{
    private final Locator newProjectButton = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("New Project"));
    private final Locator titleInput = page.getByPlaceholder("Title");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));
    public final Locator toastContainer = page.locator("div.Toastify__toast-container.Toastify__toast-container--bottom-right");
    private final Locator pickPeopleDroplist = page.locator(".project-top-people")
            .getByTestId("picky-input");
    private final Locator selectAllPeopleDroplist = page.locator(".project-top-people")
            .getByTestId("selectall");
    private final Locator submitButtonPeople = page.locator(".project-top-people .submit-button .btn.btn-primary");
    private final Locator submitButtonProject = page.locator(".project-bottom-technologies .submit-button .btn.btn-primary");
    public final Locator backButton = page.locator("a.navigate-left");
    private final Locator technologyInput = page.getByPlaceholder("Technology");
    private final Locator seniorityInput = page.getByPlaceholder("Seniority");
    private final Locator confirmAlert = page.locator(".react-confirm-alert");
    private final Locator confirmRemoveButton = page.locator("div.confirmation-dialog--buttons--confirm").filter(new Locator.FilterOptions().setHasText("Remove"));
    private final Locator createTechnologyButton = page.locator(".project-bottom-technologies:has-text('technologies') .submit-button .btn.btn-primary:has-text('Create')");
    private final Locator firstOptionPeople = page.locator(".project-top").getByTestId("dropdown").getByTestId("option").first();
    private final Locator deleteProjectButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Remove"));

    private static final String NEW_PROJECT_URL = "/new-project";
    private static final String PROJECT_URL = "/projects";
    private static final String EDIT_PROJECT_URL = "/edit-project";


    public PlaygroundPage(Page page){ super(page); }

    public void createProjectExpectSuccess(String title){
        try {
            newProjectButton.click();
            Assert.assertTrue(isNewProjectPageVisible(), "New project page should be visible");
            fillValidNewProjectForm(title);


        }
        catch(Exception e){
            Assert.fail("Create new project failed: " + e.getMessage());
        }
    }

    public void createWholeProjectExpectSuccess(String title){
        try {
            newProjectButton.click();
            Assert.assertTrue(isNewProjectPageVisible(), "New project page should be visible");
            fillValidWholeNewProjectForm(title);
        }
        catch(Exception e){
            Assert.fail("Create new project failed: " + e.getMessage());
        }
    }

    public void fillValidNewProjectForm(String title){
        titleInput.click();
        titleInput.fill(title);
        submitButton.click();
    }

    public void fillValidWholeNewProjectForm(String title){
        Random random = new Random();
        titleInput.click();
        titleInput.fill(title);
        submitButton.click();
        pickPeopleDroplist.click();
        firstOptionPeople.click();
        //selectAllPeopleDroplist.click();
        submitButtonPeople.click();
        technologyInput.click();
        //unique technology
        technologyInput.fill("Tech " + random.nextLong(100, 10000));
        createTechnologyButton.click();
        //grab one that exists delete it and add again
        Locator tech = getRandomTechnology();
        if(tech != null){
            tech.locator(".settings-list-buttons-remove").click();
            Assert.assertTrue(isConfirmationDialogVisible(), "Confirmation dialog should be visible");
            confirmRemoveButton.click();
            technologyInput.fill(tech.textContent());
            createTechnologyButton.click();
        }
    }

    public boolean isNewProjectPageVisible(){
        page.waitForURL(url -> url.contains(NEW_PROJECT_URL));
        return page.url().contains(NEW_PROJECT_URL);
    }
    public boolean isPlaygroundPageVisible(){
        page.waitForURL(url -> url.contains(PROJECT_URL));
        return page.url().contains(PROJECT_URL);
    }
    public boolean isEditProjectPageVisible(){
        page.waitForURL(url -> url.contains(EDIT_PROJECT_URL));
        return page.url().contains(EDIT_PROJECT_URL);
    }

    private Locator getRandomTechnology(){
        if(!isEditProjectPageVisible()){
            safeNavigate(EDIT_PROJECT_URL);
        }
        Locator allTechnologies = page.locator(".settings-list");
        page.locator(".settings-list").first().waitFor();
        int count = allTechnologies.count();
        if(count == 0){
            return null;
        }
        Random random = new Random();
        int ran = random.nextInt(count);
        System.out.println(allTechnologies.nth(ran).textContent());
        return allTechnologies.nth(ran);
    }

    public Locator getProjectByTitle(String title){
        if(!isPlaygroundPageVisible()){
            safeNavigate(PROJECT_URL);
        }
        Locator projectCardTitle = page.locator(".preview-card-title-value:text-is('" + title + "')");
        //if tests fail it needs a longer wait
        page.waitForTimeout(3000);

        return projectCardTitle;
    }

    public Locator getProjectCardByTitle(String title){
        if(!isPlaygroundPageVisible()){
            safeNavigate(PROJECT_URL);
        }
        Locator projectCard = page.locator("a.preview-card:has(.preview-card-title-value:text-is('" + title + "'))");
        projectCard.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        return projectCard;
    }

    public boolean isProjectExist(String title){
        return getProjectByTitle(title).isVisible();
    }

    public Locator getRandomProject(){
        if(!isPlaygroundPageVisible()){
            safeNavigate(PROJECT_URL);
        }
        Locator allProjects = page.locator(".preview-card-title-value");
        page.locator(".preview-card-title-value").first().waitFor();

        int count = allProjects.count();
        if(count == 0){
            return null;
        }
        Random random = new Random();

        return allProjects.nth(random.nextInt(count));
    }

    public boolean isConfirmationDialogVisible(){
        confirmAlert.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return confirmAlert.isVisible();
    }

    public void deleteProjectExpectSuccess(String title){
        try {
            getProjectByTitle(title).click();
            deleteProjectButton.click();
            Assert.assertTrue(isConfirmationDialogVisible(), "Confirmation dialog should be visible");
            confirmRemoveButton.click();

        }
        catch(Exception e){
            Assert.fail("Delete test case failed: " + e.getMessage());
        }
    }

    public boolean isToastifyVisible(){
        toastContainer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return toastContainer.isVisible();

    }
}
