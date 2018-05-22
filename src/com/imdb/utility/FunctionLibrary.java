package com.imdb.utility;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {

	UtilityFunctions utl = new UtilityFunctions();
	private static Properties propertyFile = new Properties();
	
	/** This function is used to visit any imdb site
	 * @author  avleen_kaur
	 * @since  20-May-2018 
	 * @param 	WebDriver 
	 */
	public void visitSite(WebDriver driver)
	{
		String URL="https://www.imdb.com/";
		driver.get(URL);
		driver.manage().window().maximize();
		
	}
	/** This function is used to visit top rated movies
	 * @author  avleen_kaur
	 * @since  20-May-2018 
	 * @param 	WebDriver 
	 */
	public void visitTopRatedMovies(WebDriver driver)
	{
		driver.findElement(utl.getObjectLocator("TopRatedMovies", "0", "", "")).click();
	}
	/** This function is used to verify page title
	 * @author  avleen_kaur
	 * @since  20-May-2018 
	 * @param 	WebDriver 
	 */
	public void verifyTitle(WebDriver driver) throws CustomException
	{
		String expectedTitle = "Top Rated Indian Movies";
		String actualTitle  = driver.findElement(utl.getObjectLocator("MovieListTitle", "0", "", "")).getText();
		if(actualTitle.contains(expectedTitle))
		{
			Assert.assertTrue(actualTitle.contains(expectedTitle));
		}
		else{
			Reporter.log("EXPECTED Title : "+"'"+expectedTitle+"'"+" NOT FOUND ON THE SITE");
			throw new com.imdb.utility.CustomException("EXPECTED Title : "+"'"+expectedTitle+"'"+" NOT FOUND ON THE SITE");
		}
	}
	/** This function is used to sort movies by IMDB ranking
	 * @author  avleen_kaur
	 * @since  20-May-2018 
	 * @param 	WebDriver 
	 */
	public void sortByIMDBRating(WebDriver driver)
	{
		Select select = new Select(driver.findElement(utl.getObjectLocator("MovieRanking", "0", "", "")));
		select.selectByVisibleText("IMDb Rating");
	}
	/** This function is used to Count Number of movies displayed
	 * @author  avleen_kaur
	 * @since  20-May-2018 
	 * @param 	WebDriver 
	 */
	public boolean verifyMovieCount(WebDriver driver)
	{
		String movieCount = driver.findElement(utl.getObjectLocator("MovieCount", "0", "", "")).getText();
		if(movieCount.equalsIgnoreCase("250"))
		{
			return true;
		}
		else{
		return false;
		}
	}
	/** This function is used to clear recently viewed history
	 * @author  avleen_kaur
	 * @since  21-May-2018 
	 * @param 	WebDriver 
	 */
	public void clearHistory(WebDriver driver)
	{
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(utl.getObjectLocator("ClearHistory", "0", "", ""))).perform();
		driver.findElement(utl.getObjectLocator("ClearHistory", "0", "", "")).click();
	}
}
