import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceRegister {
    private WebDriver driver;
    private String baseUrl;
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.21/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void testRegister() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('register_email').value='chaojifudan@outlook.com'");
        js.executeScript("document.getElementById('register_password').value='DefaultPassword'");

        driver.findElement(By.id("register_button")).click();
        String statusSignUp = driver.findElement(By.id("register_result_msg")).getText();
        if(statusSignUp ==null || statusSignUp.length() <= 0) {
            System.out.println("Failed,Status of Sign Up btn is NULL!");
            driver.quit();
        }else
            System.out.println("Sign Up btn status:"+statusSignUp);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
