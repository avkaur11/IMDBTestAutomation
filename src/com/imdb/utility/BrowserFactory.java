package com.imdb.utility;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class BrowserFactory {
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	public static WebDriver driver = null;
	/**
	 * This method is a Factory method for getting browsers.
	 * @author  avleen_kaur
	 * @since  20-May-2018
	 * @param  1.browserName - provide browser name which is required to be invoked for ex.chrome, firefox,ie,htmlunit etc
	 */
	public  static WebDriver getBrowser(String browserName) {
		WebDriver driver = null;
		switch (browserName) {
		case "Firefox":
			driver = drivers.get("Firefox");
			if (driver == null) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir")+"/Drivers/IEDriverServer.exe");
				driver = new FirefoxDriver();
				drivers.put("Firefox", driver);
			}
			break;
		case "IE":
			driver = drivers.get("IE");
			if (driver == null) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir")+"/Drivers/geckodriver.exe");
				driver = new InternetExplorerDriver();
				drivers.put("IE", driver);
			}
			break;
		case "Chrome":
			driver = drivers.get("Chrome");
			if (driver == null) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")+"/Drivers/chromedriver.exe");
				driver = new ChromeDriver();
				drivers.put("Chrome", driver);
			}
		/*case "HtmlUnit":
			driver = drivers.get("HtmlUnit");
			if(driver == null) {
				driver = new HtmlUnitDriver();
				drivers.put("HtmlUnit", driver);
			}
			break;*/
		}
		return driver;
	}

}
