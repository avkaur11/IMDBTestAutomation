package com.imdb.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class UtilityFunctions {
	private FileInputStream stream;
	private String RepositoryFile;
	private static Properties propertyFile = new Properties();
	DatabaseConnectivity db = new DatabaseConnectivity();
	private ResultSet resultSet = null;
	Connection c = null;
    Statement stmt = null;
	WebDriver driver;
	/** This function is used to Load Properties file.
	 * @author  avleen_kaur
	 * @since  20-May-2018
	 * @param  1.fileName - provide property file name which need to be loaded
	 */
	public void LoadPropertiesFile(String fileName) throws IOException
	{
			this.RepositoryFile = fileName;
			stream = new FileInputStream(RepositoryFile);
			propertyFile.load(stream);
			stream.close();
	}
	/** This function is used to get locator of any Object also with flexibility to modify locators value at run time if required.
	* @author  avleen_kaur
	* @since  20-May-2018
	* @param  1.locatorName - provide locator name which is given in the property file to identify objects.
	* @param  2.withParam  - Provide the counter 1 or 0 weather want to modify locator at run time or not.
	* @param  3.oldStr  - Provide the string which needs to be replaced at run time.
	* @param  4.newStr  - Provide the the new string value to replace the old string.  
	*/
	public By getObjectLocator(String locatorName,String withParam,String oldStr,String newStr)
	{
		String locatorProperty = propertyFile.getProperty(locatorName);
		String locatorType = locatorProperty.split(":",2)[0];
		String locatorValue = locatorProperty.split(":",2)[1];
		if ((withParam.equalsIgnoreCase("0")))
		{
			locatorValue = locatorValue;
			
		}else if((withParam.equalsIgnoreCase("1")))
		{
			String NewLocator = locatorValue.replace(oldStr, newStr);
			locatorValue = NewLocator;
		}else{
			System.out.println("Please provide correct arguments values to find Object Locator");
		}
		
			By locator = null;
			switch(locatorType)
			{
			case "Id":
				locator = By.id(locatorValue);
				break;
			case "Name":
				locator = By.name(locatorValue);
				break;
			case "CssSelector":
				locator = By.cssSelector(locatorValue);
				break;
			case "LinkText":
				locator = By.linkText(locatorValue);
				break;
			case "PartialLinkText":
				locator = By.partialLinkText(locatorValue);
				break;
			case "TagName":
				locator = By.tagName(locatorValue);
				break;
			case "Xpath":
				locator = (By.xpath(locatorValue.trim()));
				break;
			}
			
			return locator;
	}
	/** 
	 * This function is used to read values from properties file
	* @author  avleen_kaur
	* @since  20-May-2018
	* @param  1.propKey - provide the key name for which value needs to be fetched
	*/
	public String getPropertiesValue(String propKey)
	{
		String propValue =  propertyFile.getProperty(propKey);
		return propValue;
	}
	/** This function is used to get locator of any Object also with flexibility to modify locators value at run time if required.
	* @author  avleen_kaur
	* @since  20-May-2018
	* @param  1.locatorNameMovieTitle - provide locator name which is given in the property file to identify movie title.
	* @param  2.locatorNameMovieYear - provide locator name which is given in the property file to identify movie Year.
	* @param  3.locatorNameMovieRating - provide locator name which is given in the property file to identify movie Rating.
	* @param  4.WebDriver  - Provide the the Driver detail which is given in property file.
	* @param Performing Insert opertaion into the database for every iteration.
	*/
	public void fetchMovieDetails(String locatorMovieTitle,String locatorMovieYear, String locatorMovieRating, WebDriver driver) throws ClassNotFoundException, SQLException
	{
		String locatorPropertyMovieTitle = propertyFile.getProperty(locatorMovieTitle);
		String locatorPropertyMovieYear = propertyFile.getProperty(locatorMovieYear);
		String locatorPropertyMovieRating = propertyFile.getProperty(locatorMovieRating);
		String locatorType = locatorPropertyMovieTitle.split(":",2)[0];
		String locatorValueMovieTitle = locatorPropertyMovieTitle.split(":",2)[1];
		String locatorValueMovieYear = locatorPropertyMovieYear.split(":",2)[1];
		String locatorValueMovieRating = locatorPropertyMovieRating.split(":",2)[1];
		String locatorValMovieTitle = null;
		String locatorValMovieYear = null;
		String locatorValMovieRating = null;
		Connection c = db.getConnection();
		System.out.println("Records start getting inserted into the database");
		for(int i=0;i<250;i++)
		{
			i=i+1;
			//driver.findElement(getObjectLocator("MovieTitle","0", "", "")).getText();
			String newVal = Integer.toString(i);
			//System.out.println(newVal);
			String NewLocatorMovieTitle = locatorValueMovieTitle.replace("1", newVal);
			String NewLocatorMovieYear = locatorValueMovieYear.replace("1", newVal);
			String NewLocatorMovieRating = locatorValueMovieRating.replace("1", newVal);
			locatorValMovieTitle = NewLocatorMovieTitle;
			locatorValMovieYear = NewLocatorMovieYear;
			locatorValMovieRating = NewLocatorMovieRating;
			String fetchTitleMovieTitle = locatorValMovieTitle.trim();
			String fetchTitleMovieYear = locatorValMovieYear.trim();
			String fetchTitleMovieRating = locatorValMovieRating.trim();
			//System.out.println(fetchTitle);
			String MovieTitle = driver.findElement(By.xpath(fetchTitleMovieTitle)).getText();
			String MovieYear = driver.findElement(By.xpath(fetchTitleMovieYear)).getText();
			String MovieRating = driver.findElement(By.xpath(fetchTitleMovieRating)).getText();
			 int MovieSeq = i;
			 String MovieReleaseYear = MovieYear.substring(1, 5);
			try
			{
			stmt = c.createStatement();
			 String sql = "INSERT INTO MovieDetails (MovieSequence,MovieName,MovieYear,MovieRating) " + "VALUES ("+ MovieSeq + ",'" +MovieTitle + "','"+ MovieReleaseYear +"',' " + MovieRating +"' );";
			// System.out.println(MovieSeq + " "+MovieTitle + " "+ MovieReleaseYear + " "+MovieRating);
	        stmt.executeUpdate(sql);
	        stmt.close();
	        //c.commit();
	        
			}
			catch(Exception e)
			{
				 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		         System.exit(0);
			}
			// System.out.println("Table updated successfully");
			i=i-1;
			
			
		}
		c.close();
		
	}
	/** 
	 * This function is used to read values from properties file
	* @author  avleen_kaur
	* @since  20-May-2018
	* @param  Creating a new table in the database with name MovieDetails.
	*/
	public void createNewTable() throws ClassNotFoundException, SQLException
	{
		Connection c = db.getConnection();
		try
		{
		stmt = c.createStatement();
		String sql = "CREATE TABLE MovieDetails " +
			"(MovieSequence int NOT NULL," +
            "MovieName  String," +
            "MovieYear  String, " + 
            "MovieRating  String)"; 
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
	    
		}
		catch(Exception e)
		{
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		// System.out.println("Table created successfully");
	}
	/** 
	 * This function is used to read values from properties file
	* @author  avleen_kaur
	* @since  20-May-2018
	* @param  Fetching the results from the database with table name MovieDetails.
	*/
	public void fetchDatabaseResults() throws ClassNotFoundException, SQLException
	{
		Connection c = db.getConnection();
		try
		{
		stmt = c.createStatement();
	    String sql = " SELECT * FROM MovieDetails ORDER BY MovieSequence; "; 
	    ResultSet res=stmt.executeQuery(sql);
	    while(res.next())
        {
	    		int MovieId = res.getInt("MovieSequence");
                String  MovieName = res.getString("MovieName");
                String  MovieYear= res.getString("MovieYear");
                String  MovieRating = res.getString("MovieRating");
                System.out.println("MovieName sequence is : " + MovieId);
                System.out.println("MovieName is: " + MovieName);
                System.out.println("MovieYear is: " + MovieYear);
                System.out.println("MovieRating is: " + MovieRating);
                System.out.println("------------------------------");
        }
        stmt.close();
        c.close();
		}
		catch(Exception e)
		{
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		 //System.out.println("fetched details successfully");
	}
	/** 
	 * This function is used to save screen shots at specified folders
	* @author  avkaur
	* @since  20-May-2018
	* @param  1.propKey - provide webDriver instance
	 * @throws IOException 
	*/
	public void CaptureScreenShot(WebDriver driver,String testMethodName) throws IOException {
		LoadPropertiesFile(System.getProperty("user.dir")+"/src/com/imdb/configuration/Constants.properties");
		String baseFolderPath = getPropertiesValue("PATH_SCREENSHOT_BASE");
		String fileSeperator = "\\";
		File destfolder = new File(baseFolderPath+fileSeperator+testMethodName);
		if (!destfolder.exists())
		{
			destfolder.mkdir();
		}
		try{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Thread.sleep(4000);
		//String srcStr = scrFile.toString();
		//String [] srcImgName = srcStr.split(StringEscapeUtils.escapeJava("\\"));
		File screenShotFile = new File(destfolder+fileSeperator+testMethodName+System.currentTimeMillis()+".png");
		FileUtils.copyFile(scrFile,screenShotFile);
		}catch(Exception e)
		{
			System.out.println("Exception while taking screenshot "+e.getMessage());
		}

	  }
	/** 
	 * This function is used to Verify Element is present on the page or not 
	* @author  avkaur
	* @since  20-May-2018 
	*/
	public boolean isElementPresent(WebDriver driver,By locator)
	{
		try{
			WebDriverWait myDynamicElement = new WebDriverWait(driver, 5);
			myDynamicElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
			}
			catch(Exception e){
			return false;
			}
	}
	/** 
	 * This function is used to drop the existing table
	* @author  avkaur
	* @since  21-May-2018 
	*/
	public void dropTable() throws ClassNotFoundException, SQLException
	{
		Connection c = db.getConnection();
		try
		{
		stmt = c.createStatement();
	    String sql = " DROP TABLE MovieDetails; ";
	    stmt.executeUpdate(sql);
        stmt.close();
        c.close();
		}
		catch(Exception e)
		{
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		System.out.println("table dropped successfully");
	}

}
