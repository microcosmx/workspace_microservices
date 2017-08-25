package error.queue;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *错误内容：用来重现连续多次操作速度过快导致的错误问题
 *正确过程：登录、预定、退票
 *错误流程：登录、点完预定迅速点退票，预定流程还没跑完就开始退票导致退票出错
 *触发流程：由于操作起来太麻烦所以我把三个步骤整合在了一个服务里，
 *        想要触发操作只要访问这个链接即可
 *        http://10.141.212.24:12898/doErrorQueue
 *        http://10.141.212.22:12898/doErrorQueue/1/注册邮箱/密码
 *        访问时，有一定概率是正确流程，有一定概率错误
 *正确流程，最终日志中会出现【登录完成】，【预定完成】【退票完成】
 *错误流程会出现【登录完成】【Order Not Found】【预定完成】
 *测试：设定30个用户名、密码，输入到网址中，测试，收集日志
 *
 * Date：2017-8-25
 * update by zdh
 */

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