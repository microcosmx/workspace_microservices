package error.normal;

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
 *Ts-Error-Normal 简单逻辑错误
 *Fail 与 Success的主要区别点：
 *contacts调用的接口不一样
 *Fail 与 Fail 与 Success的区别点：
 *security调用的接口不一样

 *执行过程：
 *第一次订票：联系人是空的，填写并选择联系人
 *第二次订票：联系人有一条了但是不管它，继续填写并选择联系人，注意证件号ID要写的一样，写的一样

 *理论上只有第一次订票应该成功，因为后边证件号重复但是人不重复不应该创建成功
 *主要收集这二次操作的日志
 *
 *测试：正常购票，第一次输入联系人信息，第二次也输入联系人信息，但有一定概率和第一次联系ID重复，
 *     测试能否下单成功
 */
public class TestErrorNormal {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    private String trainType;//0--all,1--GaoTie,2--others
//    private String orderID; //非高铁票订单号
 //   private String orderStatus; //非高铁票订单号
    //define username and password
//    private String username;
//    private String password;


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
    public static String getNewString(String str,double sameRate) {
        String newstr = getRandomString(str.length());
        if(Math.random()<sameRate)
            return str;
        else
            return newstr;
    }

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Program/chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.24/";
        //define username and password
        //username = "fdse_microservices@163.com";
        //password = "DefaultPassword";
        trainType = "2";//非高铁

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
    public void test(String userid,String password) throws Exception{
        driver.get(baseUrl + "/");
        userRegister(userid,password);
        userLogin(userid,password);

        double stringSameRate = 0.4;

        /**
         *第一次购买
         */
        String contactName_1 = getRandomString(5);
        String documentType_1 = "1";//ID Card
        String idNumber_1 = getRandomString(8);
        String phoneNumber_1 = getRandomString(11);

        booking(contactName_1,documentType_1,idNumber_1,phoneNumber_1);

        /**
         *第二次购买
         */
        String contactName_2 = getNewString(contactName_1,stringSameRate);
        String documentType_2 = "1";//ID Card
        String idNumber_2 = getNewString(idNumber_1,stringSameRate);
        String phoneNumber_2 = getNewString(phoneNumber_1,stringSameRate);

        booking(contactName_2,documentType_2,idNumber_2,phoneNumber_2);
    }
    /**
     *买票
     */
    public void booking(String name,String documentType,String documentNumber,String phoneNumber)throws Exception{
        searchTickets();
        selectContacts(name,documentType,documentNumber,phoneNumber);
        confirmTicket();

        Alert javascriptConfirm = null;
        String statusAlert;

        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .alertIsPresent());
            javascriptConfirm = driver.switchTo().alert();
            statusAlert = driver.switchTo().alert().getText();
            System.out.println("The Alert information of Confirming Ticket："+statusAlert);
            javascriptConfirm.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
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
    public void selectContacts(String name,String documentType,String documentNumber,String phoneNumber)throws Exception{
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

        contactsList.get(contact_i).findElement(By.xpath("td[2]/input")).sendKeys(name);

        WebElement elementContactstype = contactsList.get(contact_i).findElement(By.xpath("td[3]/select"));
        Select selTraintype = new Select(elementContactstype);
        selTraintype.selectByValue(documentType); //ID type

        contactsList.get(contact_i).findElement(By.xpath("td[4]/input")).sendKeys(documentNumber);
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
        Thread.sleep(1000);

    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}