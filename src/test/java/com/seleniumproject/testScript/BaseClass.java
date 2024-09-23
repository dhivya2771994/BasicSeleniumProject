package com.seleniumproject.testScript;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.seleniumproject.util.ReadConfigFile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	ReadConfigFile readConfig = new ReadConfigFile();
	protected static WebDriver driver;
	public String app_url = readConfig.getAppUrl();

	public String username = readConfig.getUsername();
	public String password = readConfig.getPassword();

	public static Logger logger;
	public static ExtentReports extentreport;
	public static ExtentHtmlReporter extentHtmlReport;
	public static ExtentTest extentTest;
	public static String reportname;

	@BeforeSuite
	public void initialExtentReport() {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportname = "Test-Report-" + timestamp + ".html";
		extentHtmlReport = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + reportname);
		// extentHtmlReport.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		extentreport = new ExtentReports();
		extentreport.attachReporter(extentHtmlReport);
		extentreport.setSystemInfo("Host Name", "localhose");
		extentreport.setSystemInfo("Environment", "sit");
		extentreport.setSystemInfo("User", "Dhivya");
		extentHtmlReport.config().setDocumentTitle("Basic Selenium Testng Framework");
		extentHtmlReport.config().setReportName("Selenium TestRun Report");
		extentHtmlReport.config().setTheme(Theme.STANDARD);
	}

	@BeforeClass
	@Parameters({ "Browser" })
	public void browserSetup(ITestContext context, String browser) {
		logger = Logger.getLogger(getClass());
		PropertyConfigurator.configure("log4j.properties");
		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("Please provide specific Browser driver");
		}
	}

	@BeforeMethod
	public void getExtentReportTestName(ITestContext context) {
		extentTest = extentreport.createTest(context.getName());
	}

	@AfterMethod
	public void checkStatus(Method m, ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String imgpath = takescreenshot(result.getName());
			try {
				extentTest.addScreenCaptureFromPath(imgpath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// extentTest.createNode(result.getMethod().getMethodName());
			extentTest.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.pass(result.getName() + " is Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.skip(result.getName() + " is Skipped");
		} else {
		}
	}

	public static String takescreenshot(String testMethodname) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destfile = System.getProperty("user.dir") + "\\Screenshots\\" + testMethodname + ".png";
		try {
			// FileHandler.copy(srcFile, destFile);
			FileUtils.copyFile(srcFile, new File(destfile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destfile;
	}

	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}

	@AfterSuite
	public void flushExtentReport() {
		extentreport.flush();
	}

}
