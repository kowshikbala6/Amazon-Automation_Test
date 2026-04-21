package Loginpage;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class Amazonsearch {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.out.println("🚀 Initializing WebDriver using WebDriverManager...");
        try {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            // When running in CI (or headless), enable headless and disable sandbox
            // Comment out headless for local debugging
            // options.addArguments("--headless=new");
            // Allow remote origins for newer Chrome/ChromeDriver versions
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            this.driver = new ChromeDriver(options);
            this.driver.manage().window().maximize();
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (Exception e) {
            System.err.println("❌ Failed to start ChromeDriver session: " + e.getMessage());
            // Print stacktrace to help diagnose 'session not created' errors
            e.printStackTrace();
            throw e;
        }
    }

    @Test(priority = 1)
    public void searchproducts() throws InterruptedException {
        System.out.println("📍 Test 1: Navigating to Amazon homepage...");
        driver.get("https://www.amazon.com");
        takeScreenshot("amazon_homepage");
        Thread.sleep(3000);
    }

    @Test(priority = 2)
    public void search() throws InterruptedException {
        System.out.println("🔍 Test 2: Searching for products...");
        // Wait for the search input to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("mobile");
        takeScreenshot("search_input");
        driver.findElement(By.id("nav-search-submit-button")).click();
        // Wait for results container - adjust the locator if Amazon changes layout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.s-main-slot")));
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
