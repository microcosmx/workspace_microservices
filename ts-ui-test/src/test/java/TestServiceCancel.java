import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceCancel {
    public static void testTicketCancel(WebDriver driver) throws InterruptedException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('single_cancel_order_id').value='5ad7750b-a68b-49c0-a8c0-32776b067703'");

        driver.findElement(By.id("single_cancel_refund_button")).click();
        Thread.sleep(500);
        String statusCancelRefundBtn = driver.findElement(By.id("single_cancel_refund_status")).getText();
        if(statusCancelRefundBtn ==null || statusCancelRefundBtn.length() <= 0) {
            System.out.println("Failed,Status of Cancel Refund btn is NULL!");
            driver.quit();
        }else
            System.out.println("Cancel Refund Btn status:"+statusCancelRefundBtn);

        driver.findElement(By.id("single_cancel_button")).click();
        Thread.sleep(500);
        String statusCancelOrderResult = driver.findElement(By.id("single_cancel_order_result")).getText();
        if(statusCancelOrderResult ==null || statusCancelOrderResult.length() <= 0) {
            System.out.println("Failed,Status of Do Cancel btn is NULL!");
            driver.quit();
        }else
            System.out.println("Do Cancel Btn status:"+statusCancelOrderResult);
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
        testTicketCancel(driver);

        //Close the browser
        driver.quit();
    }
}
