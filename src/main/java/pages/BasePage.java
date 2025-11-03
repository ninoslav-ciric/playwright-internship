package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.Timeouts;

import java.nio.file.Paths;

public abstract class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;

    }

    protected void click(String selector) {
        page.click(selector);
    }

    protected void fill(String selector, String text) {
        page.fill(selector, text);
    }

    protected void safeLocatorFill(Locator locator, String text)
    {
        try
        {
            locator.waitFor();
            locator.fill(text);
        }
        catch(PlaywrightException e)
        {
            takeScreenshot("locator_fill_failure_" + System.currentTimeMillis());
            throw new RuntimeException("Failed to fill using locator: " + locator, e);
        }
    }

    protected void safeClick(String selector) {
        try {
            page.waitForSelector(selector);
            page.click(selector);
        } catch (PlaywrightException e) {
            takeScreenshot("click_failure_" + System.currentTimeMillis());
            throw new RuntimeException("Failed to click: " + selector, e);
        }
    }

    protected void safeLocatorClick(Locator locator)
    {
        try
        {
            locator.waitFor();
            locator.click();
        }
        catch (PlaywrightException e)
        {
            takeScreenshot("click_failure_" + System.currentTimeMillis());
            throw new RuntimeException("Failed to click locator: " + locator, e);
        }
    }

    protected void safeNavigate(String url) {
        try {
            page.navigate(url);
            page.waitForURL(actualUrl -> actualUrl.contains("sandbox"));
        } catch (PlaywrightException e) {
            takeScreenshot("click_failure_" + System.currentTimeMillis());
            throw new RuntimeException("Failed to navigate to: " + url, e);
        }
    }

    protected void takeScreenshot(String name) {
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get("screenshots/" + name + ".png")));
    }

    protected void waitForVisible(Locator locator, int timeout)
    {
        try
        {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
        }
        catch (PlaywrightException e)
        {
            takeScreenshot("wait_failure_" + System.currentTimeMillis());
            throw new RuntimeException("Element still not visible : " + locator, e);
        }
    }

    public String getUrl() { return page.url(); }

    public abstract void navigateTo(String url);
}
