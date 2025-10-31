package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {
    public DashboardPage(Page page) { super(page); }

    public Locator getMenuItemByName(String name){
        Locator menuItems = page.locator(".menu-items--item-value");
        return menuItems.filter(new Locator.FilterOptions().setHasText(name));
    }
}
