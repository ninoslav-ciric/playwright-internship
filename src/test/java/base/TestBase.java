package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.testng.annotations.*;
import pages.BasePage;
import utils.Allure;

import java.nio.file.Paths;

import static utils.Allure.logStep;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected BasePage currentPage;

    @BeforeSuite(alwaysRun = true)
    public static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

    }

    @AfterSuite(alwaysRun = true)
    public static void tearDownClass() {
        browser.close();
        playwright.close();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        page = browser.newPage();
        logStep("Opening new page");
        page.context().tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(org.testng.ITestResult result) {
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            String tracePath = "target/playwright-report/trace-" + System.currentTimeMillis() + ".zip";
            String screenShotPath = "screenshots/" + System.currentTimeMillis() + ".png";
            page.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenShotPath)));
        } else {
            page.context().tracing().stop();
        }
        page.close();
    }
}
