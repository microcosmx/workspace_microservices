import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestServiceBasicInfo {
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
    public void testBasicInfo() throws Exception{
        driver.get(baseUrl + "/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('basic_information_tripId').value='G1234'");
        js.executeScript("document.getElementById('basic_information_trainTypeId').value='GaoTieOne'");
        js.executeScript("document.getElementById('basic_information_startingStation').value='shanghai'");
        js.executeScript("document.getElementById('basic_information_stations').value='beijing'");
        js.executeScript("document.getElementById('basic_information_terminalStation').value='taiyuan'");

        String jsstartingTime = "document.getElementById('basic_information_startingTime').value='09:51'";
        js.executeScript(jsstartingTime);
        String jssendTime = "document.getElementById('basic_information_endTime').value='15:51'";
        js.executeScript(jssendTime);

        js.executeScript("document.getElementById('basic_information_startingPlace').value='Shang Hai'");
        js.executeScript("document.getElementById('basic_information_endPlace').value='Tai Yuan'");

        js.executeScript("document.getElementById('basic_information_departureTime').value='2017-08-20'");
        driver.findElement(By.id("basic_information_button")).click();
        Thread.sleep(1000);

        //gain BasicInfo list
        List<WebElement> basicInfoList = driver.findElements(By.xpath("//table[@id='query_basic_information_list_table']/tbody/tr"));
        if (basicInfoList.size() > 0)
            System.out.printf("Success to Query BasicInfo and BasicInfo list size is %d.%n",basicInfoList.size());
        else
            System.out.println("Failed to Query BasicInfo or BasicInfo list size is 0");
        Assert.assertEquals(basicInfoList.size() > 0,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
