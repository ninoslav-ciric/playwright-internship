package base;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PlaygroundPage;
import pages.TestCasePage;

import java.nio.file.Paths;

import static utils.ConfigReader.*;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected LoginPage loginPage;
    protected TestCasePage testCasePage;
    protected DashboardPage dashboardPage;
    protected PlaygroundPage playgroundPage;

    @BeforeSuite
    public static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterSuite
    public static void tearDownClass() {
        browser.close();
        playwright.close();
    }

    @BeforeMethod
    public void setup() {
        page = browser.newPage();
        page.context().tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
        loginPage = new LoginPage(page);
        loginPage.navigateToLogin(getBaseUrl());
        testCasePage = new TestCasePage(page);
        dashboardPage = new DashboardPage(page);
        playgroundPage = new PlaygroundPage(page);


    }

    @AfterMethod
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

    public void login(){
        loginPage.loginExpectSuccess(getValidEmail(), getValidPassword());
        Assert.assertTrue(loginPage.isDashboardVisible(), "Dashboard should be visible");
    }

    public void navigateToTestCasePage(){
        dashboardPage.getMenuItemByName("Test Cases").click();
        Assert.assertTrue(testCasePage.isTestcasePageVisible(), "Test case page should be visible");
    }

    public void navigateToPlaygroundPage(){
        dashboardPage.getMenuItemByName("Playground").click();
        Assert.assertTrue(playgroundPage.isPlaygroundPageVisible(), "Playground page should be visible");
    }

 }
