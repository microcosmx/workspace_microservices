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
 *测试黄牛检测bug：根据黄牛检测，当达到检测上限时，订单confirm提交时，黄牛检测失败，订票信息发出，
 *              但系统最终还会检测一次，此时检测会出错。
 *              （故障暂仅对定高铁有效）
 *系统初始化状态：黄牛检测，一个小时内最多下单5次，
 *测试流程：连续购买随机1-6张高铁票，测试能否购票成功。共设计30个用户，进行测试。
 */
public class TestProcessSeqSta {
    private WebDriver driver;
    private String baseUrl;

    private String trainType;//0--all,1--GaoTie,2--others

    private int bookingNumAax;
    private double errorRate;

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
        baseUrl = "http://10.141.212.23/";

        trainType = "1"; //设定票类型为高铁
        bookingNumAax = 5;

        errorRate = 0.2;

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"fdse501@163.com","DefaultPassword"},
                {"fdse502@163.com","DefaultPassword"},
                {"fdse503@163.com","DefaultPassword"},
                {"fdse504@163.com","DefaultPassword"},
                {"fdse505@163.com","DefaultPassword"},
                {"fdse506@163.com","DefaultPassword"},
                {"fdse507@163.com","DefaultPassword"},
                {"fdse508@163.com","DefaultPassword"},
                {"fdse509@163.com","DefaultPassword"},
                {"fdse510@163.com","DefaultPassword"},

                {"fdse511@163.com","DefaultPassword"},
                {"fdse512@163.com","DefaultPassword"},
                {"fdse513@163.com","DefaultPassword"},
                {"fdse514@163.com","DefaultPassword"},
                {"fdse515@163.com","DefaultPassword"},
                {"fdse516@163.com","DefaultPassword"},
                {"fdse517@163.com","DefaultPassword"},
                {"fdse518@163.com","DefaultPassword"},
                {"fdse519@163.com","DefaultPassword"},
                {"fdse520@163.com","DefaultPassword"},

                {"fdse521@163.com","DefaultPassword"},
                {"fdse522@163.com","DefaultPassword"},
                {"fdse523@163.com","DefaultPassword"},
                {"fdse524@163.com","DefaultPassword"},
                {"fdse525@163.com","DefaultPassword"},
                {"fdse526@163.com","DefaultPassword"},
                {"fdse527@163.com","DefaultPassword"},
                {"fdse528@163.com","DefaultPassword"},
                {"fdse529@163.com","DefaultPassword"},
                {"fdse530@163.com","DefaultPassword"},

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
            //Assert.assertEquals(i < bookingNumAax,true);
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
        Random randDate = new Random();
        int randomDate = randDate.nextInt(25); //int范围类的随机数
        newDate.add(Calendar.DATE, randomDate+5);//随机定5-30天后的票
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
