package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;

public class NewProjectPage extends BasePage{

    public NewProjectPage(Page page) {
        super(page);
    }

    private final String NAVIGATELEFT_SELECTOR = ".navigate-left-btn.btn";

    private final Locator titleProjectInput = page.getByPlaceholder("Title");
    private final Locator submitButton = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Submit"));
    private final Locator validationMsg = page.locator("#validation-msg");

    public void navigateToNewProjectPage(String url) {
        try {
            safeNavigate(url);
        } catch (TimeoutError e) {
            throw new RuntimeException("Failed to navigate to login page: " + e.getMessage());
        }
    }

    public void expectSuccessfullyEnteringTitleAndClickSubmit(String nazivProjekta){
        titleProjectInput.fill(nazivProjekta);
        submitButton.click();
        //return new EditPage(page);
    }
    public String getCurrentUrl() {
        return page.url();
    }
    public String getValidationMsg() {
        return validationMsg.textContent();
    }

}
