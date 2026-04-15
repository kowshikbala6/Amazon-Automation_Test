package Loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestRef {

    private WebDriver driver;

    public void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            String screenshotPath = "screenshots/";
            Files.createDirectories(Paths.get(screenshotPath));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = now.format(formatter);
            String destinationPath = screenshotPath + screenshotName + "_" + timestamp + ".png";

            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            System.out.println("✅ Screenshot saved: " + destinationPath);
        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void setUp() {
        System.out.println("🚀 Initializing WebDriver...");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testAmazonHomepage() throws InterruptedException {
        System.out.println("📍 Test 1: Navigating to Amazon homepage...");
        driver.get("https://www.amazon.com");
        takeScreenshot("amazon_homepage");
        Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void testAmazonIndia() throws InterruptedException {
        System.out.println("📍 Test 2: Navigating to Amazon India...");
        driver.get("https://www.amazon.in/ref=nav_logo");
        takeScreenshot("amazon_india");
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void testSearchMobile() throws InterruptedException {
        System.out.println("🔍 Test 3: Searching for mobile...");
        driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("mobile");
        takeScreenshot("search_mobile");
        Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void testClickSearch() throws InterruptedException {
        System.out.println("🔎 Test 4: Clicking search button...");
        driver.findElement(By.id("nav-search-submit-button")).click();
        takeScreenshot("search_results");
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {
        System.out.println("🛑 Closing browser...");
        if (driver != null) {
            driver.quit();
        }
    }

}





