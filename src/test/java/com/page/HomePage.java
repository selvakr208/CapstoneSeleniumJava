

  package com.page;
  
  import org.openqa.selenium.WebDriver; import org.openqa.selenium.WebElement;
  import org.openqa.selenium.support.FindBy; import
  org.openqa.selenium.support.How; import
  org.openqa.selenium.support.PageFactory;
  
  public class HomePage {
  
  // WebDriver driver;
  
  
  public HomePage(WebDriver driver) {
  
  PageFactory.initElements(driver, this); }
  
  @FindBy(how=How.XPATH,using = "//title[text()='Swag Labs']") private WebElement
  homePageTitle;
  
  public boolean verifyHomePagetitle() {
	String actualTitle=  homePageTitle.getText();
	boolean status = false;
	if(actualTitle.equals("Swag Labs")) {
		status = true;
	}
	return status;
  }
  }
 