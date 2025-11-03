package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.Timeouts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardPage extends BasePage
{
    private final Locator cards = page.locator(".main .card-grid");
    private final Locator menu  = page.locator(".menu .menu-items");

    private final Locator profileCard   = cards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("HTEC1 Interview"));
    private final Locator testCasesCard = cards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Test Cases"));
    private final Locator projectsCard  = cards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Playground"));
    private final Locator reportsCard   = cards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Reports"));

    private final Locator profileMenu      = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Profile"));
    private final Locator testCasesMenu    = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Test Cases"));
    private final Locator projectsMenu     = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Playground"));
    private final Locator reportsMenu      = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Reports"));
    private final Locator examMenu         = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Exam"));
    private final Locator introductionMenu = menu.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Introduction"));


    public DashboardPage(Page page)
    {
        super(page);
    }

    @Override
    public void navigateTo(String url)
    {
        safeNavigate(url);
        waitForVisible(cards, Timeouts.DEFAULT_TIMEOUT);
    }

    public void openCard(String cardName)
    {
        Locator card = getCardByName(cardName);
        safeLocatorClick(card);
    }

    public boolean isCardVisible(String cardName)
    {
        Locator card = getCardByName(cardName);
        return card.isVisible();
    }


    public Locator getProfileCard()  { return profileCard; }
    public Locator getTestCasesCard(){ return testCasesCard; }
    public Locator getProjectsCard() { return projectsCard; }
    public Locator getReportsCard()  { return reportsCard; }

    public Locator getCardByName(String name)
    {
        return cards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(name));
    }

    public Map<String, String> getAllCardsInMap()
    {
        Map<String, String> cardMap = new LinkedHashMap<>();
        List<Locator> cardsLocators = getAllCards();

        for (Locator card : cardsLocators)
        {
            String name = card.innerText().trim();
            String locator = card.getAttribute("href");
            cardMap.put(name, locator);
        }

        return cardMap;
    }

    private List<Locator> getAllCards()
    {
        return cards.locator("a.card").all();
    }


    public Locator getProfileMenu()     { return profileMenu; }
    public Locator getTestCasesMenu()   { return testCasesMenu; }
    public Locator getProjectsMenu()    { return projectsMenu; }
    public Locator getReportsMenu()     { return reportsMenu; }
    public Locator getExamMenu()        { return examMenu; }
    public Locator getIntroductionMenu(){ return introductionMenu; }
}
