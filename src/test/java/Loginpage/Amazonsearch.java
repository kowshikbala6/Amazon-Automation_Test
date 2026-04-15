package Loginpage;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class Amazonsearch {
    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.out.println("🚀 Initializing WebDriver...");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void searchproducts() throws InterruptedException {
        System.out.println("📍 Test 1: Navigating to Amazon homepage...");
        driver.get("https://www.amazon.com");
        driver.manage().window().maximize();
        takeScreenshot("amazon_homepage");
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void search() throws InterruptedException {
        System.out.println("🔍 Test 2: Searching for products...");
        driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("mobile");
        takeScreenshot("search_input");
        driver.findElement(By.id("nav-search-submit-button")).click();
        sleep(3000);
        takeScreenshot("search_results");
        System.out.println("✅ Search completed successfully!");
    }

    // Screenshot function - saves to Result folder with timestamp
    public void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            String screenshotPath = "Result/";
            Files.createDirectories(Paths.get(screenshotPath));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = now.format(formatter);
            String destinationPath = screenshotPath + screenshotName + "_" + timestamp + ".png";

            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            System.out.println("✅ Screenshot saved: " + destinationPath);
        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        System.out.println("🛑 Closing browser...");
        if (driver != null) {
            driver.quit();
        }
    }

}





