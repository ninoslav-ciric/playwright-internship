package assertion;

import com.microsoft.playwright.Page;
import org.testng.asserts.SoftAssert;

public class AddTestCaseAsserts extends AssertsBase {

    public void testCaseNotCreated(Page page) {
        softAssert.assertEquals(page.url(), "https://qa-sandbox.ni.htec.rs/new-testcase");
        softAssert.assertAll();
    }

}
