package example;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class sample_local_normal  {
    public static void main(String[] args) {
    	
    	System.setProperty("webdriver.chrome.driver", "/Users/admin/work/workspace_spring/testing/selenium/selenium_webdriver/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		List<String> handles = Arrays.asList("http://localhost:16006/handle6_1", "http://localhost:16003/handle3_1");
		handles.stream().forEach(action -> {
	    	driver.get(action + "?cal=-1");
			driver.get(action + "?cal=-10");
			driver.get(action + "?cal=-200");
			driver.get(action + "?cal=0.1");
			driver.get(action + "?cal=0.5");
			driver.get(action + "?cal=1");
			driver.get(action + "?cal=2");
			driver.get(action + "?cal=3");
			driver.get(action + "?cal=4");
			driver.get(action + "?cal=5");
			driver.get(action + "?cal=6");
			driver.get(action + "?cal=7");
			driver.get(action + "?cal=8");
			driver.get(action + "?cal=9");
			driver.get(action + "?cal=9.9");
			driver.get(action + "?cal=10");
			driver.get(action + "?cal=25");
			driver.get(action + "?cal=30");
			driver.get(action + "?cal=50");
			driver.get(action + "?cal=60");
			driver.get(action + "?cal=80");
			driver.get(action + "?cal=81");
			driver.get(action + "?cal=90");
			driver.get(action + "?cal=91");
			driver.get(action + "?cal=95");
			driver.get(action + "?cal=96");
			driver.get(action + "?cal=97");
			driver.get(action + "?cal=98");
			driver.get(action + "?cal=100");
			driver.get(action + "?cal=150");
			driver.get(action + "?cal=120");
			driver.get(action + "?cal=1000");
			
			for (int i=0; i<30; i++ ){
				driver.get(action + "?cal=" + Math.random()*100);
			}
			
//			List<Integer> list = Stream.iterate(1, item -> item+3).limit(100).collect(Collectors.toList());
//			list.stream().forEach(idx -> {
//				driver.get(action + "?cal=" + idx);
//			});
	    });
		
		
		
        driver.quit();
    }
}