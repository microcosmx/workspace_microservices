import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

/**
 * Created by ZDH on 2017/7/19.
 */
public class TestFlowOne {
    //Test Flow Preserve Step 1: - Login
    public static void testLogin(WebDriver driver)throws InterruptedException{
        //locate Login email input
        WebElement elementLoginID = driver.findElement(By.id("flow_preserve_login_email"));
        elementLoginID.clear();
        elementLoginID.sendKeys("fdse_microservices@163.com");

        //locate Login pwd input
        WebElement elementLoginPwd = driver.findElement(By.id("flow_preserve_login_password"));
        elementLoginPwd.clear();
        elementLoginPwd.sendKeys("DefaultPassword");

        //locate Login Login submit
        driver.findElement(By.id("flow_preserve_login_button")).click();

        Thread.sleep(1000);
        //get login status
        String statusLogin = driver.findElement(By.id("flow_preserve_login_msg")).getText();
        if(statusLogin ==null || statusLogin.length() <= 0)
            System.out.println("False，status login is null!");
        else
            System.out.println("Success: "+statusLogin);
    }

    //test Flow Preserve Step 2: - Ticket Booking
    public static void testBooking(WebDriver driver) throws InterruptedException{
        //locate booking startingPlace input
        WebElement elementBookingStartingPlace = driver.findElement(By.id("travel_booking_startingPlace"));
        elementBookingStartingPlace.clear();
        elementBookingStartingPlace.sendKeys("Shang Hai");

        //locate booking terminalPlace input
        WebElement elementBookingTerminalPlace = driver.findElement(By.id("travel_booking_terminalPlace"));
        elementBookingTerminalPlace.clear();
        elementBookingTerminalPlace.sendKeys("Tai Yuan");

        //locate booking Date input
        //WebElement element_booking_date = driver.findElement(By.id("travel_booking_date"));
        //element_booking_date.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_booking_date').value='2017-08-13'");
        //element_booking_date.clear();
        //element_booking_date.("2017-08-13");

        //locate Train Type input
        WebElement elementBookingTraintype = driver.findElement(By.id("search_select_train_type"));
        Select selTraintype = new Select(elementBookingTraintype);
        selTraintype.selectByValue("1"); //ALL

        //locate Train search button
        WebElement elementBookingSearchBtn = driver.findElement(By.id("travel_booking_button"));
        elementBookingSearchBtn.click();

        Thread.sleep(1000);
        List<WebElement> ticketsList = driver.findElements(By.xpath("//table[@id='tickets_booking_list_table']/tbody/tr"));
        //Confirm ticket selection
        if (ticketsList.size() == 0) {
            elementBookingSearchBtn.click();
            ticketsList = driver.findElements(By.xpath("//table[@id='tickets_booking_list_table']/tbody/tr"));
        }
        if(ticketsList.size() > 0) {
            //Pick up a train at random and book tickets
            Random rand = new Random();
            int i = rand.nextInt(1000) % ticketsList.size(); //int范围类的随机数
            WebElement elementBookingSeat = ticketsList.get(i).findElement(By.xpath("td[10]/select"));
            Select selSeat = new Select(elementBookingSeat);
            selSeat.selectByValue("3"); //2st
            ticketsList.get(i).findElement(By.xpath("td[13]/button")).click();
        }
        else
            System.out.println("Tickets search failed!!!");

    }

    public static void testSelectContacts(WebDriver driver)throws InterruptedException{
        List<WebElement> contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        //Confirm ticket selection
        if (contactsList.size() == 0) {
            driver.findElement(By.id("refresh_booking_contacts_button")).click();
            Thread.sleep(1000);
            contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        }
        //assert contacts_list.size() > 1;
        if (contactsList.size() == 1){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementByClassName('booking_contacts_name').value='Contacts_Test'");
            js.executeScript("document.getElementByClassName('booking_contacts_documentType').value='ID Card'");
            js.executeScript("document.getElementByClassName('booking_contacts_documentNumber').value='DocumentNumber_Test  '");
            js.executeScript("document.getElementByClassName('booking_contacts_phoneNumber').value='ContactsPhoneNum_Test'");
            contactsList.get(0).findElement(By.xpath("td[7]/label/input")).click();
        }

        if (contactsList.size() > 1) {

            Random rand = new Random();
            int i = rand.nextInt(100) % (contactsList.size() - 1); //int范围类的随机数
            contactsList.get(i).findElement(By.xpath("td[7]/label/input")).click();
        }
        driver.findElement(By.id("ticket_select_contacts_confirm_btn")).click();
    }

    public static void main(String[] args) throws InterruptedException {
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit TTS
        driver.navigate().to("http://10.141.212.21/");

        // Find the text input element by its name
        driver.findElement(By.id("flow_one_page")).click();

        //test Login
        testLogin(driver);

        //test Booking
        testBooking(driver);

        //test Contacts selecting
        testSelectContacts(driver);

        //Close the browser
        driver.quit();
    }
}
