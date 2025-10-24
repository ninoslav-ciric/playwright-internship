package assertion;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import java.util.List;

public class ManageProjectsAsserts extends AssertsBase {

    public void projectCreated(Page page) {
        softAssert.assertTrue(page.url().startsWith("https://qa-sandbox.ni.htec.rs/edit-project/"));
        softAssert.assertAll();
    }

    public void addedPeople(Page page, List<String> names) {
        var text = page
                .locator("#picky__button__button")
                .locator("span")
                .first()
                .innerText();

        if (names.size() > 3) {
            softAssert.assertEquals(names.size() + "selected", text);
            return;
        }

        StringBuilder expected = new StringBuilder(names.getFirst());
        for (String name : names.subList(1, names.size())) {
            expected.append(", ").append(name);
        }
        softAssert.assertEquals(expected, text);
    }

    public void isPersonInTeam(Page page, String name, String team) {
        PlaywrightAssertions.assertThat(page.locator(".person-container-bottom--teams-people--person-name").first()).containsText(name);
        PlaywrightAssertions.assertThat(page
                .locator(".person-container-bottom--teams-people--value-technologies")
                .first()
                .locator("div")
                .first()).containsText(team);
    }

    public void projectNotExists(Page page, String title) {
        PlaywrightAssertions.assertThat(page.locator(".preview-card-title-value").getByText(title).first()).isAttached();
    }
}
