package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DashboardPage extends BasePage{
    public DashboardPage(Page page){ super(page);}

    private final Locator cards = page.locator(".main .card-grid");
    private final Locator interviewCard = cards.getByRole(AriaRole.LINK , new Locator.GetByRoleOptions().setName("HTEC1 Interview")).first();
    private final Locator playgroundCard = cards.getByRole(AriaRole.LINK , new Locator.GetByRoleOptions().setName("Playground")).first();
    private final Locator reportsCard = cards.getByRole(AriaRole.LINK , new Locator.GetByRoleOptions().setName("Reports")).first();
    private final Locator testcasesCard = cards.getByRole(AriaRole.LINK , new Locator.GetByRoleOptions().setName("Test Cases")).first();

    public boolean isInterviewCardVisible()
    {
        interviewCard.waitFor();
        return interviewCard.isVisible();
    }
    public boolean isPlaygroundCardVisible()
    {
        playgroundCard.waitFor();
        return playgroundCard.isVisible();
    }
    public boolean isReportsCardVisible()
    {
        reportsCard.waitFor();
        return reportsCard.isVisible();
    }
    public boolean isTestCasesCardVisible()
    {
        testcasesCard.waitFor();
        return testcasesCard.isVisible();
    }


}
