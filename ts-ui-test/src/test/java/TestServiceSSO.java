import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/19.
 */
public class TestServiceSSO {

    public static void testSSOAccount(WebDriver driver){
        driver.findElement(By.id("refresh_account_button")).click();

        WebElement elementLoginAccountList = driver.findElement(By.id("refresh_login_account_button"));
        elementLoginAccountList.click();
    }

    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.24/");

        //test sso
        testSSOAccount(driver);

        //Close the browser
        driver.quit();
    }
}

