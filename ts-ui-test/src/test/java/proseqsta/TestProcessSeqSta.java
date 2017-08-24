package proseqsta;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *测试黄牛检测bug：根据黄牛检测，当达到检测上限时，由于订单confirm提交，但还未返回结果前，
 *              此时如果连续下单并提交confirm，就能下单成功，导致黄牛检测失败
 *系统初始化状态：黄牛检测，一个小时内最多下单5次，仅对高铁有效。默认账户下，已成功4次下单。
 *测试流程：连续购买2张高铁票，即第一次提交confirm后，不等返回结果，再次下单。查询订单数量，测试能否成功订到第6张票或者更多
 */
public class TestProcessSeqSta {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;

    private String trainType;//0--all,1--GaoTie,2--others
    private String orderID; //高铁票订单号
    //private String orderID; //高铁票订单号
    private int bookingNumAax;
    private int bookingTimes;
    private double errorRate;

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

        trainType = "1"; //设定票类型为高铁
        bookingNumAax = 5;

        errorRate = 0.2;

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"fdse101@163.com","DefaultPassword"},
                {"fdse102@163.com","DefaultPassword"},
                {"fdse103@163.com","DefaultPassword"},
                {"fdse104@163.com","DefaultPassword"},
                {"fdse105@163.com","DefaultPassword"},
                {"fdse106@163.com","DefaultPassword"},
                {"fdse107@163.com","DefaultPassword"},
                {"fdse108@163.com","DefaultPassword"},
                {"fdse109@163.com","DefaultPassword"},
                {"fdse110@163.com","DefaultPassword"},

                {"fdse111@163.com","DefaultPassword"},
                {"fdse112@163.com","DefaultPassword"},
                {"fdse113@163.com","DefaultPassword"},
                {"fdse114@163.com","DefaultPassword"},
                {"fdse115@163.com","DefaultPassword"},
                {"fdse116@163.com","DefaultPassword"},
                {"fdse117@163.com","DefaultPassword"},
                {"fdse118@163.com","DefaultPassword"},
                {"fdse119@163.com","DefaultPassword"},
                {"fdse120@163.com","DefaultPassword"},

                {"fdse121@163.com","DefaultPassword"},
                {"fdse122@163.com","DefaultPassword"},
                {"fdse123@163.com","DefaultPassword"},
                {"fdse124@163.com","DefaultPassword"},
                {"fdse125@163.com","DefaultPassword"},
                {"fdse126@163.com","DefaultPassword"},
                {"fdse127@163.com","DefaultPassword"},
                {"fdse128@163.com","DefaultPassword"},
                {"fdse129@163.com","DefaultPassword"},
                {"fdse130@163.com","DefaultPassword"},

        };
    }
    @Test (dataProvider="user")
    public void testBooking(String userid,String password) throws Exception{
        driver.get(baseUrl + "/");
        userRegister(userid,password);
        userLogin(userid,password);
        double bookTimes;
        if(Math.random()< errorRate)
            bookTimes = bookingNumAax+1;
        else {
            Random rand = new Random();
            bookTimes = 1 + rand.nextInt(10) % 3;
        }

        for(int i = 0;i < bookTimes; i++){
            searchTickets();
            selectContacts();
            confirmTicket();
            Assert.assertEquals(i < bookingNumAax,true);
        }


    }

    /**
     *注册新用户
     */
    public void userRegister(String userid,String password)throws Exception{
        driver.findElement(By.id("microservice_page")).click();
        driver.findElement(By.id("register_email")).clear();
        driver.findElement(By.id("register_email")).sendKeys(userid);
        driver.findElement(By.id("register_password")).clear();
        driver.findElement(By.id("register_password")).sendKeys(password);

        driver.findElement(By.id("register_button")).click();
        Thread.sleep(1000);

        String statusSignUp = driver.findElement(By.id("register_result_msg")).getText();
        if ("".equals(statusSignUp))
            System.out.println("Failed,Status of Sign Up btn is NULL!");
        else
            System.out.println("Sign Up btn status:"+statusSignUp);
        Assert.assertEquals(statusSignUp.startsWith("Success"),true);
    }
    /**
     *系统先登录
     */
    public void userLogin(String userid,String password)throws Exception{
        //call function login
        login(driver,userid,password);
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
        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DATE, 20);//定20天以后的
        bookDate=sdf.format(newDate.getTime());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_booking_date').value='"+bookDate+"'");

        //locate Train Type input
        WebElement elementBookingTraintype = driver.findElement(By.id("search_select_train_type"));
        Select selTraintype = new Select(elementBookingTraintype);
        selTraintype.selectByValue(trainType); //高铁票

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
            contactsList.get(0).findElement(By.xpath("td[2]/input")).sendKeys("Contacts_Test");

            WebElement elementContactstype = contactsList.get(0).findElement(By.xpath("td[3]/select"));
            Select selTraintype = new Select(elementContactstype);
            selTraintype.selectByValue("1"); //ID type:ID Card

            contactsList.get(0).findElement(By.xpath("td[4]/input")).sendKeys("DocumentNumber_Test");
            contactsList.get(0).findElement(By.xpath("td[5]/input")).sendKeys("ContactsPhoneNum_Test");
            contactsList.get(0).findElement(By.xpath("td[6]/label/input")).click();
        }

        if (contactsList.size() > 1) {

            Random rand = new Random();
            int i = rand.nextInt(100) % (contactsList.size() - 1); //int范围类的随机数
            contactsList.get(i).findElement(By.xpath("td[6]/label/input")).click();
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
        //Thread.sleep(1000);
        System.out.println("Confirm Ticket!");

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 40).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            javascriptConfirm.accept();
            Assert.assertEquals(statusAlert.startsWith("Success"),true);
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
    }

    public void testAlertConfirm() throws Exception{
        Alert javascriptConfirm = null;
        String statusAlert;
        int i=0;
        while (i++ < bookingTimes){
            try {
                new WebDriverWait(driver, 60).until(ExpectedConditions
                        .alertIsPresent());
                javascriptConfirm = driver.switchTo().alert();
                statusAlert = driver.switchTo().alert().getText();
                System.out.println("The Alert information of Confirming Ticket："+statusAlert);
                javascriptConfirm.accept();
                Assert.assertEquals(statusAlert.startsWith("Success"),true);
            } catch (NoAlertPresentException NofindAlert) {
                NofindAlert.printStackTrace();
            }
        }

    }
    public void testViewOrders() throws Exception{
        driver.findElement(By.id("flow_two_page")).click();
        driver.findElement(By.id("refresh_my_order_list_button")).click();
        Thread.sleep(1000);
        //gain my oeders
        myOrdersList = driver.findElements(By.xpath("//div[@id='my_orders_result']/div"));
        if (myOrdersList.size() > 0) {
            System.out.printf("Success to show my orders list，the list size is:%d%n",myOrdersList.size());
        }
        else if(myOrdersList.size() > bookingNumAax)
            System.out.printf("success to test the max order numbers，the list size is:%d%n",myOrdersList.size());
        else
            System.out.println("Failed to show my orders list，the list size is 0 or No orders in this user!");
        Assert.assertEquals(myOrdersList.size() > bookingNumAax,true);
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
