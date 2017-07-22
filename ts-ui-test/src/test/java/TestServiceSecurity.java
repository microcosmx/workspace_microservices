import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by ZDH on 2017/7/19.
 */
public class TestServiceSecurity {
    public static void testSecurity(WebDriver driver)throws InterruptedException{
        driver.findElement(By.id("refresh_security_config_button")).click();
        Thread.sleep(1000);
        List<WebElement> securityList = driver.findElements(By.xpath("//table[@id='security_config_list_table']/tbody/tr"));
        if(securityList.size() > 0) {
            System.out.printf("Success,Security Config List's size is %d.%n", securityList.size());
            testSecurityCheck(driver);
        }
        else
            System.out.println("False,Security Config List's size is 0 or Failed");

    }

    public static void testSecurityCheck(WebDriver driver) throws InterruptedException{
        driver.findElement(By.id("security_check_account_id")).clear();
        driver.findElement(By.id("security_check_account_id")).sendKeys("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        driver.findElement(By.id("security_check_button")).click();
        Thread.sleep(1000);
        String statusSecurityCheck = driver.findElement(By.id("security_check_message")).getText();
        if(statusSecurityCheck ==null || statusSecurityCheck.length() <= 0)
            System.out.println("False, status security check is null!");
        else
            System.out.println("Success:"+statusSecurityCheck);
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
        testSecurity(driver);

        //Close the browser
        driver.quit();
    }
}
