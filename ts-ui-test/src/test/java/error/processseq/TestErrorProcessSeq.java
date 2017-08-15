package error.processseq;

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
 *测试退票bug：退非高铁票时，由于退票操作时，退款进程延迟，导致已经退款，但订单状态显示为other
 *测试流程：先定一张非高铁票，并付款。然后cancel该订单，退款成功后，订单状态却显示为other
 *        再定一张非高铁票，并付款。然后cancel该订单，退款成功后，订单状态却显示为cancel
 *        （故障发生概率设定为奇数次退款失败，偶数次退款成功）
 */
public class TestErrorProcessSeq {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    private String trainType;//0--all,1--GaoTie,2--others
    private String orderID; //非高铁票订单号
    private String orderStatus; //非高铁票订单号
    //define username and password
    String username;
    String password;
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
    public void testCancelTicketsError() throws Exception{
        searchTickets();
        selectContacts();
        confirmTicket();
        payTicket();

        viewOrders();
        cancelOrder();
        //get order status
        getOrderStatus();
        System.out.println("The status of order "+orderID+" is "+orderStatus);
        Assert.assertEquals(orderStatus.startsWith("other"),true);
    }
    @Test (dependsOnMethods = {"testCancelTicketsError"})
    public void testCancelTicketsCorrect() throws Exception{
        Thread.sleep(60000);

        searchTickets();
        selectContacts();
        confirmTicket();
        payTicket();

        viewOrders();
        cancelOrder();

        getOrderStatus();

        System.out.println("The status of order "+orderID+" is "+orderStatus);
        Assert.assertEquals(orderStatus.startsWith("Cancel"),true);
    }

    /**
     *购买非高铁票，并付款
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

        if (contactsList.size() == 1){
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
        Thread.sleep(1000);
        System.out.println("Confirm Ticket!");

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
    public void payTicket ()throws Exception {
        orderID = driver.findElement(By.id("preserve_pay_orderId")).getAttribute("value");
        String itemPrice = driver.findElement(By.id("preserve_pay_price")).getAttribute("value");
        String itemTripId = driver.findElement(By.id("preserve_pay_tripId")).getAttribute("value");
        boolean bOrderId = !"".equals(orderID);
        boolean bPrice = !"".equals(itemPrice);
        boolean bTripId = !"".equals(itemTripId);
        boolean bStatusPay = bOrderId && bPrice && bTripId;
        if(bStatusPay == false)
            System.out.println("Confirming Ticket failed!");
        Assert.assertEquals(bStatusPay,true);

        driver.findElement(By.id("preserve_pay_button")).click();
        Thread.sleep(1000);
        String itemCollectOrderId = driver.findElement(By.id("preserve_collect_order_id")).getAttribute("value");
        Assert.assertEquals(!"".equals(itemCollectOrderId),true);
        System.out.println("Success to pay and book ticket!");
    }

    /**
     *查询订单，测试cancel
     */
    public void viewOrders() throws Exception{
        driver.findElement(By.id("flow_two_page")).click();
        driver.findElement(By.id("refresh_my_order_list_button")).click();
        Thread.sleep(1000);
        //gain my oeders
        myOrdersList = driver.findElements(By.xpath("//div[@id='my_orders_result']/div"));
        if (myOrdersList.size() > 0) {
            System.out.printf("Success to show my orders list，the list size is:%d%n",myOrdersList.size());
        }
        else
            System.out.println("Failed to show my orders list，the list size is 0 or No orders in this user!");
        Assert.assertEquals(myOrdersList.size() > 0,true);
    }
    public void cancelOrder() throws Exception{
        System.out.printf("The orders list size is:%d%n",myOrdersList.size());
        int i;
        //Find the first not paid order .
        for(i = 0;i < myOrdersList.size();i++) {
            //while(!(statusOrder.startsWith("Not")) && i < myOrdersList.size()) {
            //statusOrder = myOrdersList.get(i).findElement(By.xpath("/div[2]/div/div/form/div[7]/div/label[2]")).getText();
            String myOrderID = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[1]/div/label")).getText();
            //Search the orders paid but not collected
            if(myOrderID.equals(orderID))
                break;
        }
        if(i == myOrdersList.size() || i > myOrdersList.size())
            System.out.println("Failed,can't find the order!");
        Assert.assertEquals(i < myOrdersList.size(),true);

        orderStatus = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[7]/div/label[2]")).getText();
        System.out.println("The status of order "+orderID+" is "+orderStatus);
        Assert.assertEquals(!"".equals(orderStatus),true);

        myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[12]/div/button[2]")).click();
        Thread.sleep(1000);

        //String statusCancle = driver.findElement(By.id());
        //System.out.println("The Alert information of Payment："+statusAlert);
        driver.findElement(By.id("ticket_cancel_panel_confirm")).click();
        Thread.sleep(10000);
    }

    /**
     *查询订单，查看取消订单状态
     */
    public void getOrderStatus() throws Exception{
        driver.findElement(By.id("flow_two_page")).click();
        driver.findElement(By.id("refresh_my_order_list_button")).click();
        Thread.sleep(1000);
        //gain my oeders
        myOrdersList = driver.findElements(By.xpath("//div[@id='my_orders_result']/div"));
        if (myOrdersList.size() > 0) {
            System.out.printf("Success to show my orders list，the list size is:%d%n",myOrdersList.size());
        }
        else
            System.out.println("Failed to show my orders list，the list size is 0 or No orders in this user!");
        Assert.assertEquals(myOrdersList.size() > 0,true);

        int i;
        //Find the first not paid order .
        for(i = 0;i < myOrdersList.size();i++) {
            //while(!(statusOrder.startsWith("Not")) && i < myOrdersList.size()) {
            //statusOrder = myOrdersList.get(i).findElement(By.xpath("/div[2]/div/div/form/div[7]/div/label[2]")).getText();
            String myOrderID = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[1]/div/label")).getText();
            //Search the orders paid but not collected
            if(myOrderID.equals(orderID))
                break;
        }
        if(i == myOrdersList.size() || i > myOrdersList.size())
            System.out.println("Failed,can't find the order!");
        Assert.assertEquals(i < myOrdersList.size(),true);

        orderStatus = myOrdersList.get(i).findElement(By.xpath("div[2]//form[@role='form']/div[7]/div/label[2]")).getText();

        System.out.println("The status of order "+orderID+" is "+orderStatus);
        Assert.assertEquals(!"".equals(orderStatus),true);
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
