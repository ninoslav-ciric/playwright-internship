package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static utils.ConfigReader.getBaseUrl;

public class ProjectsPage extends LoggedInPage{
    private final String pageURL = getBaseUrl() + "projects";
    private final Locator addNew_btn = page.locator("a[href='/new-project']");
    private final Locator title_input = page.locator("input[name='title']");
    private final Locator title_submit_btn = page.locator("div.submit-button>button");
    private final Locator person_name_input = page.getByPlaceholder("Person");
    private final Locator person_submit_btn = page.locator("div.project-bottom-technologies>div.submit-button>button").last();
    private final Locator person_error_message = page.locator("#validation-msg");
    private final Locator portraitGrid = page.locator("div.portrait-grid");
    private final Locator people_selector = page.locator("button[data-testid='picky-input']").first();
    private final Locator people_submit_btn = page.locator("div.project-top-people>div.submit-button>button");

    public ProjectsPage(Page page) {
        super(page);
    }

    @Override
    public void goTo() {
        page.navigate(pageURL);
    }

    private void openCreateModal(){
        addNew_btn.click();
    }

    public void createProjectTitle(){
        openCreateModal();
        String projectsTitle = "Project " + ThreadLocalRandom.current().nextInt(1,1_000);
        title_input.fill(projectsTitle);
        title_submit_btn.click();
    }

    public List<String> getAllPersonsNames(){
        return page.locator("div.person-container-bottom--teams-people--person-name")
                .all()
                .stream()
                .map(Locator::textContent)
                .toList();
    }

    public String getExistingPersonName(){
        page.locator("div.person-container-bottom--teams-people--person-name").nth(0)
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(1000));
        List<String> person_names = getAllPersonsNames();
        int randIdx = ThreadLocalRandom.current().nextInt(0, person_names.size()-1);
        return person_names.get(randIdx);
    }

    public Locator getPersonErrorMessage(){ return person_error_message; }

    public void createNewExistingPerson(){
        String name = getExistingPersonName();
        person_name_input.fill(name);
        person_submit_btn.click();
    }

    public void createProject(){
        createProjectTitle();
        createNewExistingPerson();
    }

    public List<Locator> getAllProjects(){
        return portraitGrid.locator("a.preview-card").all();
    }

    public List<String> addPeopleOnProject(Locator project){
        project.click();
        people_selector.click();
        page
                .locator("div#picky-list")
                .locator("div#picky-option-selectall[data-selectall='true']")
                .nth(0)
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(1000));
        Locator selectAll = page
                .locator("div#picky-list")
                .locator("div#picky-option-selectall[data-selectall='true']")
                .first();
        List<String> selectedNames = page.locator("div[data-testid='dropdown']")
                .locator("div[data-testid='option']")
                .all()
                        .stream()
                                .map(Locator::textContent)
                                        .toList();
        selectAll.click();
        people_submit_btn.scrollIntoViewIfNeeded();
        people_submit_btn.click();
        page.goBack();
        return selectedNames;
    }

    public Locator findProjectByTitle(String title){
        List<Locator> projects = getAllProjects();
        for(Locator project : projects){
            String actual_title = project.locator("div.preview-card-title-value").textContent();
            if(Objects.equals(actual_title, title)){
                return project;
            }
        }
        return null;
    }

    public Locator getPreviewButton(Locator locator){
        return locator.locator("div.preview-card-body--items-single-preview-title");
    }

    public List<String> getPersonNamesInPreview(){
        page.locator("div.modal-body").locator("div.project-container-bottom--teams-people--person-name")
                .nth(0)
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return page.locator("div.modal-body").locator("div.project-container-bottom--teams-people--person-name")
                .all()
                .stream()
                .map(Locator::textContent)
                .toList();
    }
}
