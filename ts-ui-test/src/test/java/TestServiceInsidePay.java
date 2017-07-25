import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestServiceInsidePay {
    private WebDriver driver;
    private String baseUrl;
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void testInsidePay() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("inside_payment_orderId")).clear();
        driver.findElement(By.id("inside_payment_orderId")).sendKeys("5ad7750b-a68b-49c0-a8c0-32776b067703");
        driver.findElement(By.id("inside_payment_tripId")).clear();
        driver.findElement(By.id("inside_payment_tripId")).sendKeys("G1234");
        driver.findElement(By.id("inside_payment_pay_button")).click();
        Thread.sleep(1000);

        String statusInsidePay = driver.findElement(By.id("inside_payment_result")).getText();
        if (!"".equals(statusInsidePay))
            System.out.println("Success: "+statusInsidePay);
        else
            System.out.println("False, status of inside payment result is null!");
        Assert.assertEquals(statusInsidePay.equals(""),false);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
