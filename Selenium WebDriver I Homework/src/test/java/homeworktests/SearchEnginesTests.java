package homeworktests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class SearchEnginesTests {

    private static final String SEARCH_INPUT = "Telerik Academy Alpha";
    private static final String EXPECTED_TITLE = "IT Career Start in 6 Months - Telerik Academy Alpha";

    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void maximizeWindow() throws InterruptedException {
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeWindow() {
        driver.close();
    }

    @Test
    public void validateBingResult() {
        driver.get("https://www.bing.com/");
        driver.findElement(By.id("sb_form_q")).sendKeys(SEARCH_INPUT, Keys.ENTER);
        String actual = driver.findElement(By.cssSelector("h2[class*='topTitle']")).getText();
        Assert.assertEquals(actual, EXPECTED_TITLE);
    }

    @Test
    public void validateGoogleResult() {
        driver.get("https://www.google.com/");
        driver.findElement(By.id("L2AGLb")).click();
        driver.findElement(By.name("q")).sendKeys(SEARCH_INPUT, Keys.ENTER);
        String actual = driver.findElement(By.cssSelector("h3[class*='LC20lb']")).getText();
        Assert.assertEquals(actual, EXPECTED_TITLE);
    }
}
