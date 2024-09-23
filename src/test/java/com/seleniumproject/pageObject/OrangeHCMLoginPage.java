package com.seleniumproject.pageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrangeHCMLoginPage{
	WebDriver driver;
    public OrangeHCMLoginPage(WebDriver driver){
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

	@FindBy(name="username")
	WebElement userName;
	
	@FindBy(name="password")
	WebElement passWord;

	@FindBy(xpath="//*[@type=\"submit\"]")
	WebElement loginBtn;
	
	@FindBy(xpath="//span[@class=\"oxd-userdropdown-tab\"]")
	WebElement logoutddn;
	
	@FindBy(linkText ="Logout")
	WebElement logoutbtn;
	
	public void setUsername(String uname) {
		userName.sendKeys(uname);
	}
	
	public void setPassword(String pwd) {
		passWord.sendKeys(pwd);
	}
	
	public void clickLoginButton() {
		loginBtn.click();
	}
	
	public void clickLogOutButton() {
		logoutddn.click();
		logoutbtn.click();
	}
}
