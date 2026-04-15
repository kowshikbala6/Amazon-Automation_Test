package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);

    }

    public void login(String username) {
        driver=new ChromeDriver();
            driver.get("https://www.amazon.com");
            driver.findElement(By.xpath("//*[@id='ap_email_login']")).sendKeys("9500601556");
        driver.findElement(By.xpath("//*[@id='continue']")).click();
        }
}
