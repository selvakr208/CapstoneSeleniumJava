package com.testcases;

import java.lang.reflect.Method;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.WebApplicationBase;
import com.utilities.ReadExcelFile;

public class LoginPageTest extends WebApplicationBase{
	WebElement element; 
	
	
	@Test 
	@Parameters ({"validUsername", "validPassword"})
	public void verifyValidLogin(String validUserName, String validPassword, Method method) {
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("username_ID")));
		element.click();
		element.clear();
		element.sendKeys(validUserName);
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("password_ID")));
		element.click();
		element.clear();
		element.sendKeys(validPassword);
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("loginButton_ID")));
		element.click();
		
		
		
		System.out.println("The HomePage URL is "+driver.getCurrentUrl());
		
		if(driver.getCurrentUrl().equals(loginPageProperties.getProperty("ExpectedHomePageURl"))) {
			takeScreenShot(driver,method.getName());
			
			Assert.assertEquals(loginPageProperties.getProperty("ExpectedHomePageURl"), driver.getCurrentUrl());
		}
		else {
			takeScreenShot(driver,method.getName()+"_condition fail");
			Assert.assertEquals(loginPageProperties.getProperty("ExpectedHomePageURl"), driver.getCurrentUrl());
		}

		
		
	}
	
	
	@Test 
	@Parameters ({"inValidUsername", "inValidPassword"})
	public void verifyInValidLogin(String inValidUserName, String inValidPassword, Method method)  {
		
		
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("username_ID")));
		element.click();
		element.clear();
		//element.sendKeys("standard_Invaliduser");
		element.sendKeys(inValidUserName);
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("password_ID")));
		element.click();
		element.clear();
		//element.sendKeys("secret_Invalidpwd");
		element.sendKeys(inValidPassword);
		
		element = driver.findElement(By.id(loginPageProperties.getProperty("loginButton_ID")));
		element.click();
		
		System.out.println("The LoginPage URL is "+driver.getCurrentUrl());
		
		if(driver.getCurrentUrl().toString().equals(loginPageProperties.getProperty("ExpectedHomePageURl"))) {
			takeScreenShot(driver,method.getName());
			
			Assert.assertEquals(loginPageProperties.getProperty("ExpectedLoginPageURL"), driver.getCurrentUrl());
		}
		else {
			takeScreenShot(driver,method.getName()+"_condition fail");
			Assert.assertNotEquals(loginPageProperties.getProperty("ExpectedHomePageURl"), driver.getCurrentUrl());
		}
		
	}
	
	
	@Test(dataProviderClass = ReadExcelFile.class, dataProvider = "testdata")
	public void verifyLogin(String validUserName, String validPassword, String expectedTestStatus, Method method) {

		element = driver.findElement(By.id(loginPageProperties.getProperty("username_ID")));
		element.click();
		element.clear();
		element.sendKeys(validUserName);

		element = driver.findElement(By.id(loginPageProperties.getProperty("password_ID")));
		element.click();
		element.clear();
		element.sendKeys(validPassword);

		element = driver.findElement(By.id(loginPageProperties.getProperty("loginButton_ID")));
		element.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			element = wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath(loginPageProperties.getProperty("shoppingCart_Link")))));

			boolean isElementDisplayed = element.isDisplayed();
			if (isElementDisplayed) {
				takeScreenShot(driver, method.getName() + "_Passed");
				Assert.assertEquals(true, isElementDisplayed);

			} else {

				takeScreenShot(driver, method.getName() + "_Failed");

				Assert.assertEquals(true, isElementDisplayed);
			}

		} catch (Exception e) {
			if(expectedTestStatus.equalsIgnoreCase("fail")) {
				takeScreenShot(driver, method.getName() + "_Negative case passed");
				Assert.assertTrue(expectedTestStatus.equalsIgnoreCase("fail"));
				
			}else {
				takeScreenShot(driver, method.getName() + "_Failed due to Exception");
				Assert.assertTrue(expectedTestStatus.equalsIgnoreCase("fail"));
			}
		}

	}

}
