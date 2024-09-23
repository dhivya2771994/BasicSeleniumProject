package com.seleniumproject.testScript;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.seleniumproject.pageObject.OrangeHCMLoginPage;
import com.seleniumproject.util.XlsxUtil;

public class OrangeHCMLoginTest extends BaseClass {
	OrangeHCMLoginPage hcmloginpage; 
	
	@Test(dataProvider = "loginData")
	public void loginTest(String username,String password) {
		hcmloginpage= new OrangeHCMLoginPage(driver);
		driver.get(app_url);
		extentTest.info("I launch Orange HRM");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		hcmloginpage.setUsername(username);
		extentTest.info("I enter Username");
		hcmloginpage.setPassword(password);
		extentTest.info("I enter Password");
		hcmloginpage.clickLoginButton();
		extentTest.info("I click the login button");
		Assert.assertTrue(driver.getTitle().contains("OrangeHRM"),"Title is not wrong");
		extentTest.info("I verify the page title");
		hcmloginpage.clickLogOutButton();
		extentTest.info("I logout the application");
		
	}
	
	@Test
	public void loginTestConfig() {
		hcmloginpage= new OrangeHCMLoginPage(driver);
		driver.get(app_url);
		extentTest.info("I launch Orange HRM");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		hcmloginpage.setUsername(username);
		extentTest.info("I enter Username");
		hcmloginpage.setPassword(password);
		extentTest.info("I enter Password");
		hcmloginpage.clickLoginButton();
		extentTest.info("I click the login button");
		Assert.assertTrue(driver.getTitle().contains("OrangeHRM"),"Title is not wrong");
		extentTest.info("I verify the page title");
		hcmloginpage.clickLogOutButton();
		extentTest.info("I logout the application");
		
	}
	
	@DataProvider(name="loginData")
	public Object[][] getTestData() {
		String path = System.getProperty("user.dir")+"\\TestData\\";
		String sheetname = "TestData";
		Object[][] objdata = null;
		try {
			XlsxUtil util= new XlsxUtil(path+"/TestData.xlsx", sheetname);
			objdata = util.testData(path, sheetname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objdata;	
	}
}
