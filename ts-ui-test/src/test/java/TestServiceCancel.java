import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceCancel {
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
    public void testCheckRefund() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('single_cancel_order_id').value='5ad7750b-a68b-49c0-a8c0-32776b067703'");

        driver.findElement(By.id("single_cancel_refund_button")).click();
        Thread.sleep(500);
        String statusCancelRefundBtn = driver.findElement(By.id("single_cancel_refund_result")).getText();
        System.out.println("Cancel Refund Btn status:"+statusCancelRefundBtn);
        Assert.assertEquals(!"".equals(statusCancelRefundBtn), true);
    }
    @Test (dependsOnMethods = {"testCheckRefund"})
    public void testTicketCancel() throws Exception {
        driver.findElement(By.id("single_cancel_button")).click();
        Thread.sleep(1000);
        String statusCancelOrderResult = driver.findElement(By.id("single_cancel_order_result")).getText();
        System.out.println("Do Cancel Btn status:"+statusCancelOrderResult);
        Assert.assertEquals(!"".equals(statusCancelOrderResult), true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
