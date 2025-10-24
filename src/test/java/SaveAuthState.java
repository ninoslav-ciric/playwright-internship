import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class SaveAuthState {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();

            Page page = context.newPage();
            page.navigate("https://yourapp.com/login");

            // Fill in login form
            page.fill("#username", "yourUsername");
            page.fill("#password", "yourPassword");
            page.click("#login-button");

            // Wait for navigation or confirmation
            page.waitForURL("https://yourapp.com/dashboard");

            // Save login state
            context.storageState(
                    new BrowserContext.StorageStateOptions()
                            .setPath(Paths.get("authState.json"))
            );

            System.out.println("✅ Auth state saved to authState.json");
        }
    }
}
