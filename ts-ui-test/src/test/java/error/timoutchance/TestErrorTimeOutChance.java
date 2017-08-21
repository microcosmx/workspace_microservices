package error.timoutchance;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *测试order service访问Timeout bug：限定订单微服务访问数量限制，当超过一定数量时，访问订单微服务会出现timeout问题。
 *系统初始化状态：order微服务一次访问数量限定为2个。（为方便模拟，设定为2，其他数量同理）
 *测试流程：正常下单两次，但两次confirm时，后台处理存在差异：
 *       correct情况：cifirm后，后台正常处理，能很快的完成order的访问，下单成功，order服务会很快释放。
 *                   此时进行第二次下单，会成功
 *       error情况：cifirm后，后台处理延迟，order微服务会长时间占用，才能下单成功。
 *                 在order被占用的情况下，此时进行第二次下单（需要访问order微服务），则不能完成下单，会抛Timeout，下单失败
 */
public class TestErrorTimeOutChance {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    //define username and password
    private String username = "fdse_microservices@163.com";
    private String password = "DefaultPassword";
    private String trainType;//0--all,1--GaoTie,2--others

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
        username = "fdse_microservices@163.com";
        password = "DefaultPassword";
        trainType = "1"; //设定票类型为高铁
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

    /**
     *购买高铁票，并付款
     */
    @Test (dependsOnMethods = {"testLogin"})
    public void testConfirmTicketCorrect() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicketCorrect();
        alertConfirmCorrect();
    }
    @Test (dependsOnMethods = {"testConfirmTicketCorrect"})
    public void testConfirmTicketError() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicketError();
        alertConfirmError();
    }

    public void searchTickets() throws Exception {
        /**
         *输入信息，查票，并选择坐席后，点击Booking按钮
         */
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
        js.executeScript("document.getElementById('travel_booking_date').value='2017-09-30'");

        //locate Train Type input
        WebElement elementBookingTraintype = driver.findElement(By.id("search_select_train_type"));
        Select selTraintype = new Select(elementBookingTraintype);
        selTraintype.selectByValue(trainType);

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
        if (ticketsList.size() > 0) {
            //Pick up a train at random and book tickets
            System.out.printf("Success to search tickets，the tickets list size is:%d%n", ticketsList.size());
            Random rand = new Random();
            int i = rand.nextInt(1000) % ticketsList.size(); //int范围类的随机数
            WebElement elementBookingSeat = ticketsList.get(i).findElement(By.xpath("td[10]/select"));
            Select selSeat = new Select(elementBookingSeat);
            selSeat.selectByValue("3"); //2st
            ticketsList.get(i).findElement(By.xpath("td[13]/button")).click();
            Thread.sleep(1000);
        } else
            System.out.println("Tickets search failed!!!");
        Assert.assertEquals(ticketsList.size() > 0, true);
    }
    public void selectContacts() throws Exception {
        /**
         *选择联系人
         */
        List<WebElement> contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        //Confirm ticket selection
        if (contactsList.size() == 0) {
            driver.findElement(By.id("refresh_booking_contacts_button")).click();
            Thread.sleep(1000);
            contactsList = driver.findElements(By.xpath("//table[@id='contacts_booking_list_table']/tbody/tr"));
        }
        if (contactsList.size() == 0)
            System.out.println("Show Contacts failed!");
        Assert.assertEquals(contactsList.size() > 0, true);

        if (contactsList.size() == 1) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementByClassName('booking_contacts_name').value='Contacts_Test'");
            js.executeScript("document.getElementByClassName('booking_contacts_documentType').value='ID Card'");
            js.executeScript("document.getElementByClassName('booking_contacts_documentNumber').value='DocumentNumber_Test'");
            js.executeScript("document.getElementByClassName('booking_contacts_phoneNumber').value='ContactsPhoneNum_Test'");
            contactsList.get(0).findElement(By.xpath("td[7]/label/input")).click();
        }

        if (contactsList.size() > 1) {

            Random rand = new Random();
            int i = rand.nextInt(100) % (contactsList.size() - 1); //int范围类的随机数
            contactsList.get(i).findElement(By.xpath("td[7]/label/input")).click();
        }
        driver.findElement(By.id("ticket_select_contacts_confirm_btn")).click();
        System.out.println("Ticket contacts selected btn is clicked");
        Thread.sleep(1000);
    }
    /**
     *正常情况
     */
    public void confirmTicketCorrect() throws Exception {
        /**
         *确认购票信息，并Confirm
         */
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

        driver.findElement(By.id("reproduct_ticket_confirm_error_btn")).click();
        Thread.sleep(1000);
        System.out.println("Confirm Ticket! The btn is confirm_correct");
    }
    public void alertConfirmCorrect() throws Exception{
        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 60).until(ExpectedConditions
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
    /**
     *故障情况
     */
    public void confirmTicketError() throws Exception {
        /**
         *确认购票信息，并Confirm
         */
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

        driver.findElement(By.id("reproduct_long_connection_btn")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("reproduct_ticket_confirm_error_btn")).click();
        Thread.sleep(1000);
        System.out.println("Confirm Ticket! The btn is confirm_error");
    }
    public void alertConfirmError() throws Exception{
        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 60).until(ExpectedConditions
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

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}