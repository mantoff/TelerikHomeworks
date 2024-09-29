package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pages.InventoryPage;
import pages.LoginPage;

import java.lang.reflect.Executable;

public class ProductsTests extends BaseTest {

    public String backpackTitle;
    public String shirtTitle;

    @BeforeEach
    public void beforeTest(){
        loginPage.navigate();
        loginPage.fillLoginForm("standard_user", "secret_sauce");

        backpackTitle = "Sauce Labs Backpack";
        shirtTitle = "Sauce Labs Bolt T-Shirt";

        inventoryPage.addProductByTitle(backpackTitle);
        inventoryPage.addProductByTitle(shirtTitle);

        inventoryPage.clickShoppingCartLink();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart(){

        // Assert Items and Totals
        var items = driver.findElements(By.className("inventory_item_name"));

        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        Assertions.assertEquals(backpackTitle, items.get(0).getText(), "Item title not as expected");
        Assertions.assertEquals(shirtTitle, items.get(1).getText(), "Item title not as expected");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation(){

        // Assert Items and Totals
        driver.findElement(By.id("checkout")).click();

        // fill form
        fillShippingDetails("Fname", "lname", "zip");

        driver.findElement(By.id("continue")).click();

        var items = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        var total = driver.findElement(By.className("summary_total_label")).getText();
        double expectedPrice = 29.99 + 15.99 + 3.68;
        String expectedTotal = String.format("Total: $%.2f", expectedPrice);

        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertEquals(backpackTitle, items.get(0).getText(), "Item title not as expected");
        Assertions.assertEquals(shirtTitle, items.get(1).getText(), "Item title not as expected");
        Assertions.assertEquals(expectedTotal, total, "Items total price not as expected");
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(){
        // Add Backpack and T-shirt to shopping cart

        // Click on shopping Cart

        driver.findElement(By.id("checkout")).click();
        // Fill Contact Details
        fillShippingDetails("Fname", "lname", "zip");

        driver.findElement(By.id("continue")).click();

        // Complete Order
        driver.findElement(By.id("finish")).click();

        //WebElement shoppingCartBadge = driver.findElement(By.cssSelector("span[data-test='shopping-cart-badge']"));

        // Assert Items removed from Shopping Cart
        Assertions.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("span[data-test='shopping-cart-badge']")) );
    }
}