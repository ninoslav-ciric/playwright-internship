package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import pages.TestCasesPage;

import utils.AllureUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import static utils.ConfigReader.getBaseUrl;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected LoginPage loginPage;
    protected TestCasesPage testCasesPage;
    protected DashboardPage dashboardPage;

    @BeforeSuite(alwaysRun = true)
    public static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        AllureUtil.beforeSuiteLog("Starting test suite");
    }

    @AfterSuite(alwaysRun = true)
    public static void tearDownClass() {
        browser.close();
        playwright.close();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        page = browser.newPage();
        page.context().tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
        loginPage = new LoginPage(page);
        loginPage.navigateToLogin(getBaseUrl());
        testCasesPage = new TestCasesPage(page);
        dashboardPage = new DashboardPage(page);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(org.testng.ITestResult result) {
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            String tracePath = "target/playwright-report/trace-" + System.currentTimeMillis() + ".zip";
            String screenShotPath = "screenshots/" + System.currentTimeMillis() + ".png";
            page.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
            byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenShotPath)));
            InputStream inputStream = new ByteArrayInputStream(bytes);
            Allure.addAttachment("Fail: " , inputStream);
        } else {
            page.context().tracing().stop();
        }
        page.close();
    }
}
