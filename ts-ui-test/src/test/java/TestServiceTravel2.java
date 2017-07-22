import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Random;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceTravel2 {
    public static void testTravel2(WebDriver driver) throws InterruptedException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel2_update_tripId').value='Z1234'");
        js.executeScript("document.getElementById('travel2_update_trainTypeId').value='ZhiDa'");
        js.executeScript("document.getElementById('travel2_update_startingStationId').value='shanghai'");
        js.executeScript("document.getElementById('travel2_update_stationsId').value='beijing'");
        js.executeScript("document.getElementById('travel2_update_terminalStationId').value='taiyuan'");
        js.executeScript("document.getElementById('travel2_update_startingTime').value='11:17'");
        js.executeScript("document.getElementById('travel2_update_endTime').value='15:19'");

        driver.findElement(By.id("travel2_update_button")).click();
        Thread.sleep(1000);
//        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
//        if(statusSignIn ==null || statusSignIn.length() <= 0) {
//            System.out.println("Failed,Status of Sign In btn is NULL!");
//            driver.quit();
//        }else
//            System.out.println("Sign Up btn status:"+statusSignIn);
    }
    public static void testQueryTravel2(WebDriver driver) throws InterruptedException{
        driver.findElement(By.id("travel2_queryAll_button")).click();
        Thread.sleep(1000);
        //gain Travel list
        List<WebElement> travel2List = driver.findElements(By.xpath("//table[@id='query_travel2_list_table']/tbody/tr"));

        if (travel2List.size() > 0)
            System.out.printf("Success to Query Travel2 and Travel2 list size is %d.%n",travel2List.size());
        else
            System.out.println("Failed to Query Travel2 or Travel2 list size is 0");
    }

    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.21/");

        //test Travel
        testTravel2(driver);
        testQueryTravel2(driver);
        //Close the browser
        driver.quit();
    }
}
