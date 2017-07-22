import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/19.
 */
public class TestServiceExecute {
    public static void testExecute(WebDriver driver)throws InterruptedException{
        driver.findElement(By.id("execute_order_id")).clear();
        driver.findElement(By.id("execute_order_id")).sendKeys("5ad7750b-a68b-49c0-a8c0-32776b067703");
        driver.findElement(By.id("execute_order_button")).click();
        Thread.sleep(1000);
        String statusExecute = driver.findElement(By.id("execute_order_message")).getText();
        if(statusExecute ==null || statusExecute.length() <= 0)
            System.out.println("False: status execute is null!");
        else
            System.out.println("Success: "+statusExecute);
    }

    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.24/");

        //test execute
        testExecute(driver);

        //Close the browser
        driver.quit();
    }
}
