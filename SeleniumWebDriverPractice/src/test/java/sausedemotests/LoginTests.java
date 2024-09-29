package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @BeforeEach
    public void beforeTest() {
        loginPage.navigate();
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user","problem_user", "performance_glitch_user", "error_user", "visual_user"})
    public void userAuthenticated_when_validCredentialsProvided(String username){

        loginPage.fillLoginForm(username, "secret_sauce");
        inventoryPage.waitForPageTitle();

        // Add Assert
        inventoryPage.assertNavigated();
    }

    @Test
    public void userNotAuthenticated_when_lockedOut() {
        loginPage.fillLoginForm("locked_out_user", "secret_sauce");
        String lockedMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assertions.assertTrue(lockedMsg.contains("locked"));
    }

    @Test
    public void userNotAuthenticated_when_invalidUser() {
        loginPage.fillLoginForm("invalid", "secret_sauce");
        String lockedMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assertions.assertTrue(lockedMsg.contains("Username and password do not match"));
    }

    @Test
    public void userNotAuthenticated_when_invalidPassword() {
        loginPage.fillLoginForm("standard_user", "invalid");
        String lockedMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assertions.assertTrue(lockedMsg.contains("Username and password do not match"));
    }

}
