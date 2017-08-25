package error.extnormal;

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
 * 错误说明：
 *在对订单进行支付，但站内余额不足的时候，会跳转到一个第三方的非java写的服务
 *正常流程：站内余额不足，调用rest-external-service，这个服务返回true或者false，表示付款通过第三方的服务正常完成
 *错误流程：站内余额不足，调用rest-external-service，这个服务在规定规定时间内不返回，导致调用超时
 *触发流程：第三方服务有延迟，有一定的机率超时或者不超时，多试几次就行
 *正常流程：控制台输出[Inside Payment][Turn to Outside Payment] 外部服务调用正常，页面返回支付成功
 *错误流程：[Inside Payment][Turn to Outside Payment] 外部服务调用超时，页面返回支付失败
 *
 * 测试：设定账户余额为0，购票时，由于余额不足，触发调用第三方支付，有一定概率触发故障
 *      30个用户，30次测试
 *
 *Date：2017-8-25
 *Update by zdh
 */

public class TestErrorExtNormal {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    private String trainType;//0--all,1--GaoTie,2--others
    private String orderID; //非高铁票订单号
    //private String orderStatus; //非高铁票订单号
    //define username and password
    public static void login(WebDriver driver,String username,String password){
        driver.findElement(By.id("flow_one_page")).click();
        driver.findElement(By.id("flow_preserve_login_email")).clear();
        driver.findElement(By.id("flow_preserve_login_email")).sendKeys(username);
        driver.findElement(By.id("flow_preserve_login_password")).clear();
        driver.findElement(By.id("flow_preserve_login_password")).sendKeys(password);
        driver.findElement(By.id("flow_preserve_login_button")).click();
    }
    //获取指定位数的随机字符串(包含数字,0<length)
    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }
    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        trainType = "2";//非高铁
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"fdse301@163.com","DefaultPassword"},
                {"fdse302@163.com","DefaultPassword"},
                {"fdse303@163.com","DefaultPassword"},
                {"fdse304@163.com","DefaultPassword"},
                {"fdse305@163.com","DefaultPassword"},
                {"fdse306@163.com","DefaultPassword"},
                {"fdse307@163.com","DefaultPassword"},
                {"fdse308@163.com","DefaultPassword"},
                {"fdse309@163.com","DefaultPassword"},
                {"fdse310@163.com","DefaultPassword"},

                {"fdse311@163.com","DefaultPassword"},
                {"fdse312@163.com","DefaultPassword"},
                {"fdse313@163.com","DefaultPassword"},
                {"fdse314@163.com","DefaultPassword"},
                {"fdse315@163.com","DefaultPassword"},
                {"fdse316@163.com","DefaultPassword"},
                {"fdse317@163.com","DefaultPassword"},
                {"fdse318@163.com","DefaultPassword"},
                {"fdse319@163.com","DefaultPassword"},
                {"fdse320@163.com","DefaultPassword"},

                {"fdse321@163.com","DefaultPassword"},
                {"fdse322@163.com","DefaultPassword"},
                {"fdse323@163.com","DefaultPassword"},
                {"fdse324@163.com","DefaultPassword"},
                {"fdse325@163.com","DefaultPassword"},
                {"fdse326@163.com","DefaultPassword"},
                {"fdse327@163.com","DefaultPassword"},
                {"fdse328@163.com","DefaultPassword"},
                {"fdse329@163.com","DefaultPassword"},
                {"fdse330@163.com","DefaultPassword"},

        };
    }

    @Test (dataProvider="user")
    public void testExternalNormal(String userid,String password) throws Exception{
        driver.get(baseUrl + "/");
        userRegister(userid,password);
        userLogin(userid,password);
        searchTickets();
        selectContacts();
        confirmTicket();
        payTicket();
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
        String bookDate = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        Random randDate = new Random();
        int randomDate = randDate.nextInt(26); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
        bookDate=sdf.format(newDate.getTime());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('travel_booking_date').value='"+bookDate+"'");

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

        String contactName = getRandomString(5);
        String documentType = "1";//ID Card
        String idNumber = getRandomString(8);
        String phoneNumber = getRandomString(11);
        contactsList.get(contact_i).findElement(By.xpath("td[2]/input")).sendKeys(contactName);

        WebElement elementContactstype = contactsList.get(contact_i).findElement(By.xpath("td[3]/select"));
        Select selTraintype = new Select(elementContactstype);
        selTraintype.selectByValue(documentType); //ID type

        contactsList.get(contact_i).findElement(By.xpath("td[4]/input")).sendKeys(idNumber);
        contactsList.get(contact_i).findElement(By.xpath("td[5]/input")).sendKeys(phoneNumber);
        contactsList.get(contact_i).findElement(By.xpath("td[6]/label/input")).click();

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
        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            javascriptConfirm.accept();
            Thread.sleep(1000);
            Assert.assertEquals(statusAlert.startsWith("Success"),true);
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
        System.out.println("pay  ticket!");

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 60).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            javascriptConfirm.accept();
            Thread.sleep(1000);
            Assert.assertEquals(statusAlert.startsWith("Success"),true);
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }

    }


    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}