package com.imdb.TestScripts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.imdb.utility.CustomException;
import com.imdb.utility.FunctionLibrary;
import com.imdb.utility.UtilityFunctions;


public class FetchMovieDetailTest {
	UtilityFunctions utl = new UtilityFunctions();
	FunctionLibrary  funLib = new FunctionLibrary();
	public String testCaseName = null;
	public WebDriver driver;
	String testBrowser;
	@BeforeSuite
	public void setupEnvironment() throws IOException
	{
       //Load Object Repository Property file
		utl.LoadPropertiesFile(System.getProperty("user.dir")+"\\src\\com\\imdb\\OR\\ObjectRepo.properties");
		//Load constants Property file
		utl.LoadPropertiesFile(System.getProperty("user.dir")+"\\src\\com\\imdb\\configuration\\Constants.properties");
		testBrowser = utl.getPropertiesValue("TEST_BROWSER");
		
	}
	@BeforeMethod
	public void testSetup(ITestContext context) throws FileNotFoundException, IOException
	{
		testCaseName = context.getCurrentXmlTest().getName();
		driver = com.imdb.utility.BrowserFactory.getBrowser(testBrowser);
	}
	@Test
	public void fetchMovieTitle() throws CustomException, ClassNotFoundException, SQLException, IOException
	{
		funLib.visitSite(driver);
		funLib.visitTopRatedMovies(driver);
		if(utl.isElementPresent(driver, utl.getObjectLocator("MovieListTitle", "0", "", ""))){
		funLib.verifyTitle(driver);
		funLib.verifyMovieCount(driver);
		funLib.sortByIMDBRating(driver);
		utl.CaptureScreenShot(driver,testCaseName);
		utl.createNewTable();
			if(utl.isElementPresent(driver, utl.getObjectLocator("MovieTitle", "0", "", ""))){
				utl.fetchMovieDetails("MovieTitle", "MovieRating", "MovieReleaseYear",driver);
				utl.fetchDatabaseResults();
				utl.dropTable();
				utl.CaptureScreenShot(driver,testCaseName);
				funLib.clearHistory(driver);
			}
		}
		else{
			throw new CustomException("Page Title not Valid");
		}
	}
	@AfterMethod
	public void testTeardown(ITestResult result) throws IOException, InterruptedException
	{
		if(ITestResult.FAILURE==result.getStatus())
		{
			Reporter.setCurrentTestResult(result);
			utl.CaptureScreenShot(driver,testCaseName);
		}
			
	}


}
