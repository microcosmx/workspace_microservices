import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceCellect {
    public static void testTicketCollect(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('single_collect_order_id').value='5ad7750b-a68b-49c0-a8c0-32776b067703'");
        driver.findElement(By.id("single_collect_button")).click();
        String statusTicketCollect = driver.findElement(By.id("single_collect_order_result")).getText();
        if(statusTicketCollect ==null || statusTicketCollect.length() <= 0)
            System.out.println("False,status security check is null!");
        else
            System.out.println("Ticket Collect status:"+statusTicketCollect);
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
        testTicketCollect(driver);

        //Close the browser
        driver.quit();
    }
}
