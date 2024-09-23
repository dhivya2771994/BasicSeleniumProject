package com.seleniumproject.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.seleniumproject.testScript.BaseClass;

public class ExtentReportListenser extends TestListenerAdapter {
	 public  ExtentHtmlReporter extenthtmlreporter;
	  public  ExtentReports extent;
	  public  ExtentTest test;
	public void onTestStart(ITestResult result) {
		String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportname = "Test-Report-"+timestamp+".html";
		extenthtmlreporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/"+reportname);
	    extenthtmlreporter.loadXMLConfig(System.getProperty("user.dir")+"/extent.config.xml");
		extent=new ExtentReports();
		extent.attachReporter(extenthtmlreporter);
		extent.setSystemInfo("Host Name", "localhose");
		extent.setSystemInfo("Environment", "sit");
		extent.setSystemInfo("User", "Dhivya");
		extenthtmlreporter.config().setDocumentTitle("Basic Selenium Testng Framework");
		extenthtmlreporter.config().setReportName("Selenium TestRun Report");
		extenthtmlreporter.config().setTheme(Theme.STANDARD);
		
	  }
	  public void onTestSuccess(ITestResult result) {
	    if (result.getStatus() == ITestResult.SUCCESS) {
	   // test=extent.createTest(result.getName());
	      test.log(Status.PASS, "Pass Test case is: " + result.getName());
	    }
	  }

	  public void onTestFailure(ITestResult result) {
	    if (result.getStatus() == ITestResult.FAILURE) {
	    	long date=result.getMethod().getDate();
			String imagepath=BaseClass.takescreenshot(result.getMethod().getMethodName()+date);
	      test.log(Status.FAIL,
	          MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	      test.log(Status.FAIL,
	          MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
	      

try {
	test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath(imagepath).build());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	    }
	  }

	  public void onTestSkipped(ITestResult result) {
	    if (result.getStatus() == ITestResult.SKIP) {
	      test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
	    }
	  }

	  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    test.log(Status.INFO, "Test case failed but within success percentage: " + result.getName());
	  }

	  public void onStart(ITestContext context) {
	    //test.log(Status.INFO, "Test Execution Started");
	  }

	  public void onFinish(ITestContext context) {
	    try {
	      Map<String, Object> testResult = new HashMap<String,Object>();
	      testResult.put("TotalTestCaseCount", context.getAllTestMethods().length);
	      testResult.put("PassedTestCaseCount", context.getPassedTests().size());
	      testResult.put("FailedTestCaseCount", context.getFailedTests().size());
	      testResult.put("SkippedTestCaseCount", context.getSkippedTests().size());

	      ObjectMapper mapper = new ObjectMapper();
	      mapper.enable(SerializationFeature.INDENT_OUTPUT);
	      String filePath = "test-output/ExtentReport/TestExecutionReport.json";
	      mapper.writeValue(new File(filePath), testResult);
	    } catch (IOException e) {
	      throw new RuntimeException("Error occurred while writing to TestExecutionReport.json file: ",
	          e);
	    }
	  }
}
