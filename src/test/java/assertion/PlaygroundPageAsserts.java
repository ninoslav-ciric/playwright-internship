package assertion;


import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.PlaygroundPage;

public class PlaygroundPageAsserts {
    private final SoftAssert softAssert;
    public PlaygroundPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void assertAddNewProject(PlaygroundPage playgroundPage, String title){
        softAssert.assertTrue(playgroundPage.isEditProjectPageVisible(), "Edit project page should be visible");
        softAssert.assertTrue(playgroundPage.toastContainer.isVisible(), "Toastify confirmation should be visible");
        softAssert.assertTrue(playgroundPage.toastContainer.textContent().trim().contains("Project created successfully"), "Toast message does not contain expected text");
        playgroundPage.backButton.click();
        Assert.assertTrue(playgroundPage.isPlaygroundPageVisible(), "Playground page should be visible");

        Assert.assertTrue(playgroundPage.isProjectExist(title), "Project should be visible in playground page");
        softAssert.assertAll();
    }

    public void assertAddWholeNewProject(PlaygroundPage playgroundPage, String title){
        softAssert.assertTrue(playgroundPage.isEditProjectPageVisible(), "Edit project page should be visible");
        playgroundPage.backButton.click();
        Assert.assertTrue(playgroundPage.isPlaygroundPageVisible(), "Playground page should be visible");
        //get projCard and body and check every value
        Locator projCard = playgroundPage.getProjectCardByTitle(title);
        Locator body = projCard.locator(".preview-card-body");
       //check values of body
        Locator allBodyValues = body.locator(".preview-card-body--items-single-value");
        body.locator(".preview-card-body--items-single-value").first().waitFor();
        softAssert.assertEquals(allBodyValues.nth(0).textContent(), "1");
        softAssert.assertEquals(allBodyValues.nth(1).textContent(), "1");
        //softAssert.assertEquals(allBodyValues.nth(2).textContent(), "0");
        softAssert.assertAll();
    }

    public void assertDeleteProject(PlaygroundPage playgroundPage, String title){
        Assert.assertTrue(playgroundPage.isPlaygroundPageVisible(), "Playground page should be visible");
        softAssert.assertTrue(playgroundPage.isToastifyVisible(), "Toastify alert should be visible");
        String toastText = playgroundPage.toastContainer.textContent();
        softAssert.assertTrue(toastText.trim().contains("Project removed successfully"), "Toast message does not contain expected text");
        softAssert.assertFalse(playgroundPage.isProjectExist(title), "Project with that title should not exist");
        softAssert.assertAll();
    }
}
