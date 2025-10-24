package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static utils.ConfigReader.getBaseUrl;

public class TestCasesPage extends LoggedInPage{
    private final String pageURL = getBaseUrl() + "testcases";
    private final Locator portraitGrid = page.locator("div.portrait-grid");
    private final Locator deleteBtn = page.locator("button.btn.btn-danger");
    private final Locator createNewBtn = page.locator("a.btn.btn-primary[href='/new-testcase']");
    private final Locator title_input = page.getByPlaceholder("Title");
    private final Locator description_input = page.getByPlaceholder("Description");
    private final Locator expectedResult_input = page.getByPlaceholder("Expected Result");
    private final Locator addNewStep_btn = page.locator("div.full-width-btn");
    private final Locator submit_btn = page.locator("div.submit-button").locator("button.btn.btn-primary.float-right");

    public TestCasesPage(Page page) {
        super(page);
    }

    @Override
    public void goTo() {
        page.navigate(pageURL);
    }

    public List<Locator> getAllTestCases(){
        return portraitGrid.locator("a.preview-card").all();
    }

    public Locator verifyIndividualPage(Page page) throws Exception {
        String currentURL = page.url();
        String editURL = getBaseUrl() + "edit-testcase/";
        if(currentURL.contains(editURL)){
            if(deleteBtn == null) throw new Exception("Delete button could not be located");

            return deleteBtn;
        }
        return null;
    }

    public String getCaseIDFromURL(){
        String currentURL = page.url();
        String editURL = getBaseUrl() + "edit-testcase/";
        if(currentURL.contains(editURL)) {
            return currentURL.split(editURL)[1];
        }
        return null;
    }

    public Locator getRemoveTestCaseButtonPopup(){return page.locator("div.confirmation-dialog--buttons--confirm");}

    private Locator getCreateNewBtn(){ return createNewBtn; }


    public void fillCreationForm(String title, String description, String expectedResult, List<Number> list_steps){
        title_input.fill(title);
        description_input.fill(description);
        expectedResult_input.fill(expectedResult);
        for(int i = 0;i < list_steps.size(); i++){
            Locator testSteps_loc = page.locator("input[name='step-" + i + "']");
            testSteps_loc.fill(list_steps.get(i).toString());
            if(i != list_steps.size() - 1){
                addNewStep_btn.click();
            }
        }
    }

    public void submitForm(){
        submit_btn.click();
    }

    public void createNewTestCase(String title,String description, String expectedResult){
        Locator newBtn = getCreateNewBtn();
        newBtn.click();
        ArrayList<Number> steps = new ArrayList<>();
        for(int i = 0;i<10;i++){
            steps.add(ThreadLocalRandom.current().nextInt(1,1_000_000));
        }
        fillCreationForm(title, description, expectedResult, steps);
        submitForm();
    }

    public String deleteCase() throws Exception {
        String caseID = getCaseIDFromURL();

        Locator deleteBtn = verifyIndividualPage(page);
        deleteBtn.click();

        Locator confirmDeleteBtn = getRemoveTestCaseButtonPopup();
        confirmDeleteBtn.click();

        return caseID;
    }

    public Locator getCreationError(){ return page.locator("#validation-msg");}

    public Locator getSuccessToast(){
        return page.locator("div.Toastify__toast.Toastify__toast--default.SUCCESS_TOAST").first();
    }

    public Locator findTestCaseByTitle(String title){
        List<Locator> allTestCases = getAllTestCases();
        allTestCases.getFirst().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(1000));
        for(Locator testCase : allTestCases){
            String found_title = testCase.locator("div.preview-card-title-value").textContent();
            if(Objects.equals(found_title, title)) return testCase;
        }
        return null;
    }
}
