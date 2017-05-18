package example;

import java.io.File;
import java.net.URL;

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

public class sample0  {
    public static void main(String[] args) {
    	
    	System.setProperty("webdriver.chrome.driver", "/Users/admin/work/workspace_spring/testing/selenium/selenium_webdriver/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.navigate().to("http://localhost:16006/hello6?cal=5");
		driver.navigate().to("http://localhost:16006/hello6?cal=6");
		driver.navigate().to("http://localhost:16006/hello6?cal=10");
		driver.navigate().to("http://localhost:16006/hello6?cal=25");
		driver.navigate().to("http://localhost:16006/hello6?cal=30");
		driver.navigate().to("http://localhost:16006/hello6?cal=50");
		driver.navigate().to("http://localhost:16006/hello6?cal=60");
		driver.navigate().to("http://localhost:16006/hello6?cal=80");
		driver.navigate().to("http://localhost:16006/hello6?cal=81");
		driver.navigate().to("http://localhost:16006/hello6?cal=90");
		driver.navigate().to("http://localhost:16006/hello6?cal=91");
		driver.navigate().to("http://localhost:16006/hello6?cal=95");
		driver.navigate().to("http://localhost:16006/hello6?cal=96");
		driver.navigate().to("http://localhost:16006/hello6?cal=97");
		driver.navigate().to("http://localhost:16006/hello6?cal=98");
		driver.navigate().to("http://localhost:16006/hello6?cal=100");
		
		driver.get("http://172.20.0.1:16006/hello6?cal=150");
		driver.get("http://172.20.0.1:16006/hello6?cal=120");
		driver.get("http://172.20.0.1:16006/hello6?cal=1000");
		
        driver.quit();
    }
}