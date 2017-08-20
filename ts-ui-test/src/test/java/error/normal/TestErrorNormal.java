package error.normal;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *Ts-Error-Normal 简单逻辑错误
 *Fail 与 Success的主要区别点：
 *contacts调用的接口不一样
 *Fail 与 Fail 与 Success的区别点：
 *security调用的接口不一样

 *执行过程：
 *第一次订票：联系人是空的，填写并选择联系人
 *第二次订票：联系人有一条了但是不管它，继续填写并选择联系人，注意证件号要写的一样，写的一样
 *第三次订票：联系人有两条了但是不管它，继续填写并选择联系人，注意证件号要写的一样，写的一样

 *理论上只有第一次订票应该成功，因为后边两次证件号重复但是人不重复不应该创建成功
 *主要就是要收集这三次操作的日志
 *
 */
public class TestErrorNormal {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    private String trainType;//0--all,1--GaoTie,2--others
//    private String orderID; //非高铁票订单号
 //   private String orderStatus; //非高铁票订单号
    //define username and password
    private String username;
    private String password;
    public static void login(WebDriver driver,String username,String password){
        driver.findElement(By.id("flow_one_page")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(password);
        driver.findElement(By.id("flow_preserve_login_button")).click();
    }
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        //define username and password
        username = "fdse_microservices@163.com";
        password = "DefaultPassword";
        trainType = "2";//非高铁
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     *系统先登录
     */
    @Test
    public void testLogin()throws Exception{
        driver.get(baseUrl + "/");

        //call function login
        login(driver,username,password);
        Thread.sleep(1000);

        //get login status
        String statusLogin = driver.findElement(By.id("flow_preserve_login_msg")).getText();
        if("".equals(statusLogin))
            System.out.println("Failed to Login! Status is Null!");
        else if(statusLogin.startsWith("Success"))
            System.out.println("Success to Login! Status:"+statusLogin);
        else
            System.out.println("Failed to Login! Status:"+statusLogin);
        Assert.assertEquals(statusLogin.startsWith("Success"),true);
    }


    @Test (dependsOnMethods = {"testLogin"})
    public void testFirstBooking() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicket();

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            Assert.assertEquals(statusAlert.startsWith("Success"),true);
            javascriptConfirm.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
    }
    @Test (dependsOnMethods = {"testFirstBooking"})
    public void testSecondBooking() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicket();

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            Assert.assertEquals(statusAlert.startsWith("Success"),false);
            javascriptConfirm.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
    }
    @Test (dependsOnMethods = {"testSecondBooking"})
    public void testThirdBooking() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicket();

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            Assert.assertEquals(statusAlert.startsWith("Success"),false);
            javascriptConfirm.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
    }

    /**
     *购买非高铁票,确认订单
     */
    public void searchTickets() throws Exception{
        driver.findElement(By.id("flow_one_page")).click();
        //locate booking startingPlace input
        WebElement elementBookingStartingPlace = driver.findElement(By.id("travel_booking_startingPlace"));
        elementBookingStartingPlace.clear();
        elementBookingStartingPlace.sendKeys("Shang Hai");

        //locate booking terminalPlace input
        WebElement elementBookingTerminalPlace = driver.findElement(By.id("travel_booking_terminalPlace"));
        elementBookingTerminalPlace.clear();
        elementBookingTerminalPlace.sendKeys("Tai Yuan");

        //locate booking Date input
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_booking_date').value='2017-08-31'");

        //locate Train Type input
        WebElement elementBookingTraintype = driver.findElement(By.id("search_select_train_type"));
        Select selTraintype = new Select(elementBookingTraintype);
        selTraintype.selectByValue(trainType); //非高铁票

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
            System.out.printf("Success to search tickets，the tickets list size is:%d%n",ticketsList.size());
            Random rand = new Random();
            int i = rand.nextInt(1000) % ticketsList.size(); //int范围类的随机数
            WebElement elementBookingSeat = ticketsList.get(i).findElement(By.xpath("td[10]/select"));
            Select selSeat = new Select(elementBookingSeat);
            selSeat.selectByValue("3"); //2st
            ticketsList.get(i).findElement(By.xpath("td[13]/button")).click();
            Thread.sleep(1000);
        }
        else
            System.out.println("Tickets search failed!!!");
        Assert.assertEquals(ticketsList.size() > 0,true);
    }
    public void selectContacts()throws Exception{
        List<WebElement> contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        //Confirm ticket selection
        if (contactsList.size() == 0) {
            driver.findElement(By.id("refresh_booking_contacts_button")).click();
            Thread.sleep(1000);
            contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        }
        if(contactsList.size() == 0)
            System.out.println("Show Contacts failed!");
        Assert.assertEquals(contactsList.size() > 0,true);

        int contact_i=contactsList.size()-1;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementByClassName('booking_contacts_name').value='Contacts_Test'");
        js.executeScript("document.getElementByClassName('booking_contacts_documentType').value='ID Card'");
        js.executeScript("document.getElementByClassName('booking_contacts_documentNumber').value='DocumentNumber_Test'");
        js.executeScript("document.getElementByClassName('booking_contacts_phoneNumber').value='ContactsPhoneNum_Test'");
        contactsList.get(contact_i).findElement(By.xpath("td[7]/label/input")).click();

        driver.findElement(By.id("ticket_select_contacts_confirm_btn")).click();
        System.out.println("Ticket contacts selected btn is clicked");
        Thread.sleep(1000);
    }
    public void confirmTicket()throws Exception{
        String itemFrom = driver.findElement(By.id("ticket_confirm_from")).getText();
        String itemTo = driver.findElement(By.id("ticket_confirm_to")).getText();
        String itemTripId = driver.findElement(By.id("ticket_confirm_tripId")).getText();
        String itemPrice = driver.findElement(By.id("ticket_confirm_price")).getText();
        String itemDate = driver.findElement(By.id("ticket_confirm_travel_date")).getText();
        String itemName = driver.findElement(By.id("ticket_confirm_contactsName")).getText();
        String itemSeatType = driver.findElement(By.id("ticket_confirm_seatType_String")).getText();
        String itemDocumentType = driver.findElement(By.id("ticket_confirm_documentType")).getText();
        String itemDocumentNum = driver.findElement(By.id("ticket_confirm_documentNumber")).getText();
        boolean bFrom = !"".equals(itemFrom);
        boolean bTo = !"".equals(itemTo);
        boolean bTripId = !"".equals(itemTripId);
        boolean bPrice = !"".equals(itemPrice);
        boolean bDate = !"".equals(itemDate);
        boolean bName = !"".equals(itemName);
        boolean bSeatType = !"".equals(itemSeatType);
        boolean bDocumentType = !"".equals(itemDocumentType);
        boolean bDocumentNum = !"".equals(itemDocumentNum);
        boolean bStatusConfirm = bFrom && bTo && bTripId && bPrice && bDate && bName && bSeatType && bDocumentType && bDocumentNum;
        if(bStatusConfirm == false){
            driver.findElement(By.id("ticket_confirm_cancel_btn")).click();
            System.out.println("Confirming Ticket Canceled!");
        }
        Assert.assertEquals(bStatusConfirm,true);

        driver.findElement(By.id("ticket_confirm_confirm_btn")).click();
        System.out.println("Confirm Ticket!");
        Thread.sleep(1000);

    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}