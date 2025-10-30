package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DashboardPage extends BasePage {

    private final Locator navTitle = page.locator(".navigate-left-title:has-text('Dashboard')");
    private final Locator logoutLink  = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Logout"));
    private final Locator avatar      = page.locator(".nav-links--link-img img");

    private final Locator sideMenu = page.locator(".menu .menu-items");

    private final Locator menuDashboard  =
            sideMenu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Dashboard"));
    private final Locator menuProfile    =
            sideMenu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Profile"));
    private final Locator menuTestCases  =
            sideMenu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Test Cases"));
    private final Locator menuPlayground =
            sideMenu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Playground"));
    private final Locator menuReports    =
            sideMenu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Reports"));

    private final Locator cardGrid = page.locator(".main .card-grid");

    private final Locator cardProfile =
            cardGrid.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("HTEC1 Interview"));
    private final Locator cardTestCases  = cardGrid.locator("a.card[href='/testcases']");
    private final Locator cardPlayground =
            cardGrid.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Playground"));
    private final Locator cardReports =
            cardGrid.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Reports"));

    public Locator getCardProfile() {
        return cardProfile;
    }

    public Locator getCardTestCases() {
        return cardTestCases;
    }

    public Locator getCardPlayground() {
        return cardPlayground;
    }

    public Locator getCardReports() {
        return cardReports;
    }

    public DashboardPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        safeNavigate(baseUrl + "dashboard");
        navTitle.waitFor();
    }

    public boolean isLoaded() {
        return navTitle.isVisible() && logoutLink.isVisible();
    }

    // --- Menu navigation ---
    public void goToDashboard()  { menuDashboard.click(); }
    public void goToProfile()    { menuProfile.click(); }
    public void goToTestCases()  { menuTestCases.click(); }
    public void goToPlayground() { menuPlayground.click(); }
    public void goToReports()    { menuReports.click(); }



    public boolean isAvatarVisible() { return avatar.isVisible(); }

    public String url() { return page.url(); }
}
