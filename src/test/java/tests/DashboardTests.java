package tests;

import assertion.DashboardPageAsserts;
import base.TestBase;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;

import java.util.List;

public class DashboardTests extends TestBase {
    @Test(
            groups = {"UI"},
            description = "Verify every link in card grid works, on dashboard page"
    )
    public void verifyAllLinksInCardGrid(){
        currentPage = new DashboardPage(page);
        DashboardPage dashboardPage = (DashboardPage) currentPage;
        dashboardPage.goTo();

        List<Locator> cards = dashboardPage.getCardsFromGrid();

        if(cards.isEmpty()){
            Assert.fail("Could not find cards elements");
        }

        for(Locator card : cards){
            DashboardPageAsserts dashboardPageAsserts = new DashboardPageAsserts(page);
            String expectedURL = card.getAttribute("href");
            card.click();
            String actualURL = page.url();
            dashboardPageAsserts.validateURL(actualURL,expectedURL);
            page.goBack();
        }
    }
}
