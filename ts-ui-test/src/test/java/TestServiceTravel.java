import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceTravel {
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
    public void testTravel() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_update_tripId').value='G1234'");
        js.executeScript("document.getElementById('travel_update_trainTypeId').value='GaoTieOne'");
        js.executeScript("document.getElementById('travel_update_startingStationId').value='shanghai'");
        js.executeScript("document.getElementById('travel_update_stationsId').value='beijing'");
        js.executeScript("document.getElementById('travel_update_terminalStationId').value='taiyuan'");
        js.executeScript("document.getElementById('travel_update_startingTime').value='11:17'");
        js.executeScript("document.getElementById('travel_update_endTime').value='15:29'");

        driver.findElement(By.id("travel_update_button")).click();
        Thread.sleep(1000);
//        String statusSignIn = driver.findElement(By.id("login_result_msg")).getText();
//        if(statusSignIn ==null || statusSignIn.length() <= 0) {
//            System.out.println("Failed,Status of Sign In btn is NULL!");
//            driver.quit();
//        }else
//            System.out.println("Sign Up btn status:"+statusSignIn);
    }
    @Test (dependsOnMethods = {"testTravel"})
    public void testQueryTravel() throws Exception{
        driver.findElement(By.id("travel_queryAll_button")).click();
        Thread.sleep(1000);
        //gain Travel list
        List<WebElement> travelList = driver.findElements(By.xpath("//table[@id='query_travel_list_table']/tbody/tr"));

        if (travelList.size() > 0)
            System.out.printf("Success to Query Travel and Travel list size is %d.%n",travelList.size());
        else
            System.out.println("Failed to Query Travel or Travel list size is 0");
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
