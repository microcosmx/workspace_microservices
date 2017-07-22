import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceLogin {
    public static void testSignIn(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('login_email').value='chaojifudan@outlook.com'");
        js.executeScript("document.getElementById('login_password').value='DefaultPassword'");

        driver.findElement(By.id("login_button")).click();
        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
        if(statusSignIn ==null || statusSignIn.length() <= 0) {
            System.out.println("Failed,Status of Sign In btn is NULL!");
            driver.quit();
        }else
            System.out.println("Sign Up btn status:"+statusSignIn);
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
        testSignIn(driver);

        //Close the browser
        driver.quit();
    }
}
