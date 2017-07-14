import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by ZDH on 2017/7/13.
 */
public class Test_TTS_ui {
    //Test Flow Preserve Step 1: - Login
    public static void test_login(WebDriver driver){
        //locate Login email input
        WebElement element_login_id = driver.findElement(By.id("flow_preserve_login_email"));
        element_login_id.clear();
        element_login_id.sendKeys("fdse_microservices@163.com");

        //locate Login pwd input
        WebElement element_login_pwd = driver.findElement(By.id("flow_preserve_login_password"));
        element_login_pwd.clear();
        element_login_pwd.sendKeys("DefaultPassword");

        //locate Login Login submit
        driver.findElement(By.id("flow_preserve_login_button")).click();

        //Thread.sleep(100);
        //get login status
        String Login_status = driver.findElement(By.id("flow_preserve_login_status")).getText();
        System.out.println(Login_status);
    }

    //test Flow Preserve Step 2: - Ticket Booking
    public static void test_booking(WebDriver driver) {
        //locate booking startingPlace input
        WebElement element_booking_startingPlace = driver.findElement(By.id("travel_booking_startingPlace"));
        element_booking_startingPlace.clear();
        element_booking_startingPlace.sendKeys("Shang Hai");

        //locate booking terminalPlace input
        WebElement element_booking_terminalPlace = driver.findElement(By.id("travel_booking_terminalPlace"));
        element_booking_terminalPlace.clear();
        element_booking_terminalPlace.sendKeys("Tai Yuan");

        //locate booking Date input
        WebElement element_booking_date = driver.findElement(By.id("travel_booking_date"));
        //element_booking_date.click();
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("document.getElementById('date03').readOnly=false;");
        element_booking_date.clear();
        element_booking_date.sendKeys("2017-08-13");

        //locate Train Type input
        WebElement element_booking_traintype = driver.findElement(By.id("search_select_train_type"));
        element_booking_traintype.clear();
        Select sel = new Select(element_booking_traintype);
        sel.selectByValue("1"); //ALL

        //locate Train search button
        WebElement element_booking_button = driver.findElement(By.id("travel_booking_button"));
        element_booking_button.click();

    }

    public static void main(String[] args) throws InterruptedException{
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        //System.setProperty("webdriver.ie.driver", "D:/Program/IEDriverServer_x64_3.4.0/IEDriverServer.exe");
        //WebDriver driver = new InternetExplorerDriver();

        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //System.setProperty("webdriver.ie.driver", "D:/Program/IEDriverServer_x64_3.4.0/IEDriverServer.exe");
        //WebDriver driver = new InternetExplorerDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.24/");
        //driver.get("http://10.141.212.24/");
        // Find the text input element by its name
        driver.findElement(By.linkText("流程一")).click();

        //Test login
        test_login(driver);

        //Test booking
        test_booking(driver);

        //Close the browser
        driver.quit();
    }
}
