
package com.base;

import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.utilities.ReadPropertiesFile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebApplicationBase {

	public static WebDriver driver;
	public static Properties configProperties = new Properties();
	public static Properties locatorProperties = new Properties();
	public static Properties loginPageProperties = new Properties();
	
	public static FileReader configFileReader;
	//public static FileReader locatorsReader;
	public static FileReader loginPageLocatorsReader;
	public static String testURL;
	public static String browser;
	
	ReadPropertiesFile readPropertiesFile = new ReadPropertiesFile();
	

	@BeforeSuite
	public void getBrowserURL() throws IOException {
		
		System.out.println("Before Suite is invoked....");

	
			
			System.out.println(" The Path is"+System.getProperty("user.dir"));
			configFileReader = new FileReader(System.getProperty("user.dir")+"/configfiles/config.properties");	
			loginPageLocatorsReader = new FileReader(System.getProperty("user.dir")+"/configfiles/LoginPage.properties");
			
			
			configProperties.load(configFileReader);
			loginPageProperties.load(loginPageLocatorsReader);
			
			
			browser = configProperties.getProperty("browser");
			testURL = configProperties.getProperty("testurl");
			
			
			
			System.out.println("The browser is "+browser);
			System.out.println("The test URL is "+testURL);
			

		
		
		
	}
	

	@BeforeMethod
	public void setUp() throws IOException {
		
		System.out.println("Before Method is invoked....");

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(testURL);
			System.out.println("Browser launched the URL successfully...!");

		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(testURL);
		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));		
			driver.get(testURL);
		}
	}
	
	public WebDriver getdriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver=driver;
	}



	@AfterMethod
	public void tearDown() {
		
		System.out.println("After Suite is invoked....");
		
		
		driver.close();
		driver.quit();

	}
	
	
	public static void takeScreenShot(WebDriver webdriver, String screenShotName) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String timestamp = dateFormat.format(new Date());
            String fileName = screenShotName + "_" + timestamp + ".png";
			
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir") + "/screenshot/" + fileName);
		FileUtils.copyFile(SrcFile, DestFile);
		}
		catch(Exception e) {
			System.out.println("Error while taking screenshot. Make sure the screenshot folder is clean..!" + e);
		}
	}
	

}
