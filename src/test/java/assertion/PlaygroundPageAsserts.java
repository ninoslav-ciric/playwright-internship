package assertion;

import org.testng.asserts.SoftAssert;
import pages.PlaygroundNewProjectPage;

import java.util.List;

public class PlaygroundPageAsserts {

    private final SoftAssert softAssert;

    public PlaygroundPageAsserts() {
        this.softAssert = new SoftAssert();
    }

    public void invalidateNewProject(PlaygroundNewProjectPage playgroundNewProjectPage) {

        softAssert.assertFalse(playgroundNewProjectPage.isErrorVisible(), "NewCase creating failed");
        softAssert.assertAll();
    }

    public void deleteProject(String titleToDelete, List<String> titleLista) {
        softAssert.assertFalse(titleLista.contains(titleToDelete), "Deleted project should no longer exist");
        softAssert.assertAll();
    }

}
