package com.imdb.utility;

import org.openqa.selenium.WebDriver;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;


public class Listener implements ITestListener {
	UtilityFunctions utlL = new UtilityFunctions();
	FunctionLibrary funLibL= new FunctionLibrary();
	WebDriver driver;
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test in a suite finished: "+ context.getName()+ "	and time is: "+getCurrentTime());
		
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test in a suite started: "+ context.getName()+ "	and time is: "+getCurrentTime());
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log("Reporting test log ");
		Reporter.log("STATUS : FAIL");
		System.out.println("Test method failed: "+ result.getName()+ "	and time is: "+getCurrentTime());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test method skipped: "+ result.getName()+ "	and time is: "+getCurrentTime()); 
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test method started: "+ result.getName()+ "	and time is: "+getCurrentTime());    
	 }
		
	

	
	public void onTestSuccess(ITestResult result) {
		Reporter.log("STATUS : PASS");
		System.out.println("Test method success: "+ result.getName()+ " and time is: "+getCurrentTime());
		
	}
	
	public String getCurrentTime(){
	    DateFormat dateFormat = 
	        new SimpleDateFormat("HH:mm:ss:SSS");
	    Date dt = new Date();
	    return dateFormat.format(dt);    
	  }
}
