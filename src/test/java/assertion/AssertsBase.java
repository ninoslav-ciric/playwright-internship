package assertion;

import org.testng.asserts.SoftAssert;

public abstract class AssertsBase {

    protected final SoftAssert softAssert;
    public AssertsBase() {
        this.softAssert = new SoftAssert();
    }
}
