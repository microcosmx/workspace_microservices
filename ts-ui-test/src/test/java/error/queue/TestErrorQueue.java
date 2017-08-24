package error.queue;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestErrorQueue {
    private WebDriver driver;
    private String baseUrl;
    private List<WebElement> myOrdersList;
    private String trainType;//0--all,1--GaoTie,2--others
    private String orderID; //非高铁票订单号
    private String orderStatus; //非高铁票订单号
    //define username and password
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
        //baseUrl = "http://10.141.212.24/";
        trainType = "2";//非高铁
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"fdse401@163.com","DefaultPassword"},
                {"fdse402@163.com","DefaultPassword"},
                {"fdse403@163.com","DefaultPassword"},
                {"fdse404@163.com","DefaultPassword"},
                {"fdse405@163.com","DefaultPassword"},
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
    public void testCancelTickets(String userid,String password) throws Exception{
        baseUrl = "http://10.141.212.22:12898/doErrorQueue/1/"+userid+"/"+password;
        driver.get(baseUrl);
        //String status = driver.findElement(By.xpath("//html/body/text()")).getText();
        //Assert.assertEquals(status.startsWith("[Do"),true);
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}