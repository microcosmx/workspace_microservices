import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceTrain {
    public static void testTrain(WebDriver driver) throws InterruptedException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('train_update_id').value='GaoTieOne'");
        js.executeScript("document.getElementById('train_update_economyClass').value='120'");
        js.executeScript("document.getElementById('train_update_confortClass').value='60'");

        driver.findElement(By.id("train_update_button")).click();
        Thread.sleep(1000);
//        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
//        if(statusSignIn ==null || statusSignIn.length() <= 0) {
//            System.out.println("Failed,Status of Sign In btn is NULL!");
//            driver.quit();
//        }else
//            System.out.println("Sign Up btn status:"+statusSignIn);
    }

    public static void testQueryTrain(WebDriver driver) throws InterruptedException{
        driver.findElement(By.id("station_query_button")).click();
        Thread.sleep(1000);
        //gain Travel list
        List<WebElement> stationList = driver.findElements(By.xpath("//table[@id='query_station_list_table']/tbody/tr"));

        if (stationList.size() > 0)
            System.out.printf("Success to Query Station and Station list size is %d.%n",stationList.size());
        else
            System.out.println("Failed to Query Station or Station list size is 0");
    }
    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.21/");

        //test train
        testTrain(driver);
        testQueryTrain(driver);
        //Close the browser
        driver.quit();
    }
}
