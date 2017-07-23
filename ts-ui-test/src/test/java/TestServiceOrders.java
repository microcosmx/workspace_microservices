import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ZDH on 2017/7/20.
 */


public class TestServiceOrders {
    public static void testOrders(WebDriver driver){
        WebElement elementRefreshOrdersBtn = driver.findElement(By.id("refresh_order_button"));
        WebElement elementOrdertypeGTCJ = driver.findElement(By.xpath("//*[@id='microservices']/div[4]/div[1]/h3/input[1]"));
        WebElement elementOrdertypePT = driver.findElement(By.xpath("//*[@id='microservices']/div[4]/div[1]/h3/input[2]"));
        elementOrdertypeGTCJ.click();
        elementOrdertypePT.click();
        if(elementOrdertypeGTCJ.isEnabled() || elementOrdertypePT.isEnabled()){
            elementRefreshOrdersBtn.click();
            System.out.println("Show Orders according database!");
        }
        else {
            elementRefreshOrdersBtn.click();
            Alert javascriptConfirm = driver.switchTo().alert();
            javascriptConfirm.accept();
            elementOrdertypeGTCJ.click();
            elementOrdertypePT.click();
            elementRefreshOrdersBtn.click();
        }
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
        testOrders(driver);

        //Close the browser
        driver.quit();
    }
}
