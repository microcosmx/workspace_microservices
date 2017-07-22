import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by ZDH on 2017/7/21.
 */
public class TestServiceContacts {
    public static void testContacts(WebDriver driver)throws InterruptedException{
        driver.findElement(By.id("refresh_contacts_button")).click();
        Thread.sleep(1000);
        List<WebElement> contactsList = driver.findElements(By.xpath("//table[@id='contacts_list_table']/tbody/tr"));
        //List<WebElement> contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        if(contactsList.size() > 0) {
            System.out.printf("Success,Contacts List's size is %d.%n", contactsList.size());
        }
        else
            System.out.println("False,Contacts List's size is 0 or Failed");

    }
    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.24/");

        //test contacts
        testContacts(driver);

        //Close the browser
        driver.quit();
    }
}
