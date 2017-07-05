package example;

import java.io.File;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sample_error_process_cache  {
	
	public static int count = 60;
	
    public static void main(String[] args) {
    	
    	System.setProperty("webdriver.chrome.driver", "/Users/admin/work/workspace_spring/testing/selenium/selenium_webdriver/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
            	driver.get("http://localhost:16006/process6_sync_crossreq");
            	if(count-- < 0){
            		this.cancel();
            		timer.cancel();
            		driver.quit();
            	}
            }
        }, 1000, 1000);
		
    }
}