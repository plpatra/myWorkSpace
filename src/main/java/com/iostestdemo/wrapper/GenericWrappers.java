package com.iostestdemo.wrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.logging.Level;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class GenericWrappers {
	public static WebDriver webDriver;
	public static WebDriverWait wait;
	public static String screenShotPath;
	public static boolean isAppClosed;
	
	public static void setDriver(String driver) throws MalformedURLException {

		switch (driver) {
		case "Chrome":
			String osName = System.getProperty("os.name");
			String chromeDriverStr = "webdriver.chrome.driver";
			switch (osName) {
			case "Linux":
				System.setProperty(chromeDriverStr, properties("Chrome.linux"));
				break;
			case "Mac OS X":
				System.setProperty(chromeDriverStr, properties("Chrome.mac"));
				break;
			default:
				System.setProperty(chromeDriverStr,
						properties("Chrome.windows"));
				break;
			}
//			ChromeOptions options = new ChromeOptions();
//			DesiredCapabilities cap = DesiredCapabilities.chrome();
//			cap.setCapability("acceptInsecureCerts", true);
//			cap.setCapability(ChromeOptions.CAPABILITY, options);
//			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
//					UnexpectedAlertBehaviour.ACCEPT);
//			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
//			webDriver = new ChromeDriver(cap);
			webDriver = new ChromeDriver();
			//webDriver = new RemoteWebDriver(new URL("http://10.140.237.208:4444/wd/hub"),cap);
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", properties("Ie.windows"));
			webDriver = new InternetExplorerDriver();
			break;
		case "Safari":
			webDriver = new SafariDriver();
			break;
		case "iPad":
			startAppiumServer();
			break;
		case "iPhoneSimulator":
			startSimulator();
			break;

		default:
			break;
		}

	}
	public static void startAppiumServer() throws MalformedURLException {
		if ((webDriver == null) || (webDriver != null & !(isAppiumDriver()))) {
			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability("deviceName", properties("MobileDevice"));
				cap.setCapability("platformName", "iOS");
				cap.setCapability("platformVersion", properties("Version"));
				cap.setCapability("automationName", "XCUITest");

				if (properties("Isrealdevice").equalsIgnoreCase("Yes")) {
					// cap.setCapability(MobileCapabilityType.UDID,
					// properties("UDID"));
					cap.setCapability(MobileCapabilityType.UDID, "auto");
					cap.setCapability("bundleId", properties("Bundleid"));
				} else if (properties("IsmobileApp").equalsIgnoreCase("No")) {
					cap.setCapability(MobileCapabilityType.UDID, properties("UDID"));
					cap.setCapability(MobileCapabilityType.BROWSER_NAME,
							properties("Browser"));
					if (properties("Browser").equalsIgnoreCase("chrome"))
						cap.setCapability(MobileCapabilityType.APP,
								properties("Appth"));
				} else if (properties("IsmobileApp").equalsIgnoreCase("Yes")) {
					cap.setCapability(MobileCapabilityType.APP,
							properties("AppPath"));
					if (properties("Reset").equalsIgnoreCase("Yes"))
						cap.setCapability("fullReset", true);
					else
						cap.setCapability("noReset", true);
				}
				cap.setCapability("noReset", true);
				cap.setCapability("webkitResponseTimeout", 5000);
				cap.setCapability("newCommandTimeout", 70000);
				cap.setCapability("simpleIsVisibleCheck", true);
				cap.setCapability("autoAcceptAlerts", true);

				webDriver = new IOSDriver<WebElement>(new URL(properties("AppiumURL") + "/wd/hub"), cap);
				webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}

	public static void startSimulator() throws MalformedURLException {
		if ((webDriver == null) || (webDriver != null & !(isAppiumDriver()))) {
			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability("deviceName", GenericWrappers.properties("MobileDevice"));
				cap.setCapability("platformName", GenericWrappers.properties("Platform"));
				cap.setCapability("platformVersion", GenericWrappers.properties("Version"));
				cap.setCapability("automationName", "XCUITest");
				cap.setCapability(MobileCapabilityType.APP,	GenericWrappers.properties("AppPath"));
				cap.setCapability("noReset", true);
				//cap.setCapability("webkitResponseTimeout", 5000);
				//cap.setCapability("newCommandTimeout", 70000);
				cap.setCapability("simpleIsVisibleCheck", true);
				cap.setCapability("autoAcceptAlerts", true);
				webDriver = new IOSDriver<WebElement>(new URL(GenericWrappers.properties("AppiumURL") + "/wd/hub"), cap);
				//webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (webDriver != null && isAppiumDriver()) {
			if (isAppClosed)
				launchApp();
		}

	}

	public static String properties(String key) {
		Properties prob = null;
		File file = new File("./BuildDetails.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prob = new Properties();
		try {
			prob.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prob.getProperty(key);
	}

		public static void scrolltoViewElement(WebElement element) {
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoViewIfNeeded(true);", element);
		}

	public static WebElement getElementByTextWithScroll(
			List<WebElement> elements, String value) {
		WebElement returnElement = null;
		for (WebElement element : elements) {
			scrolltoViewElement(element);
			if (element.getText().contains(value)) {
				returnElement = element;
				break;
			}
		}
		return returnElement;
	}
	
	public static void switchWindow()
			{
			Set<String> Window=webDriver.getWindowHandles();
				 webDriver.switchTo().window((String) Window.toArray()[1]);
			}
	
	public static void jsClickOnElement(WebElement element) {
		try {
			((JavascriptExecutor) webDriver).executeScript(
					"arguments[0].click();", element);
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			jsClickOnElement(element);
		} catch (Exception e) {
			e.printStackTrace();
			element.click();
		}
	}

	public static void clickOnElement(WebElement element)
	{
		//waitForElementToBeClickable(element,1);
		element.click();
	}
	
	public static void doubleClickOnElement(WebElement element)
	{
		//waitForElementToBeClickable(element,3);
		Actions action = new Actions(webDriver);
		action.moveToElement(element).doubleClick().perform();
	}
	public static String jsGetTextoFElement(WebElement element) {
		return (String) ((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].innerHTML;", element);
	}

	@SuppressWarnings("unchecked")
	public void closeAppiumAndBrowser() {
		try {

			if (webDriver instanceof AppiumDriver<?>) {
				((AppiumDriver<WebElement>) webDriver).closeApp();
			} else
				webDriver.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public WebElement getWebelElement(String locators) {
		WebElement ele = null;
		String[] loc = locators.split("`");
		switch (loc[0]) {
		case "ByName":
			ele = webDriver.findElement(By.name(loc[1]));
			break;
		case "FByName":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByName(loc[1]);
			break;
		case "FByClassName":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByClassName(loc[1]);
			break;
		case "FByXpath":
			ele = ((AppiumDriver<WebElement>) webDriver)
					.findElementByXPath(loc[1]);
			break;

		default:
			break;
		}
		return ele;
	}

	public void enterUsingKeyBoard(Keyboard key, String value) {
		key.pressKey(value);

	}


//	public static void waitForVisibilityOfElement(WebElement element) {
//		wait = new WebDriverWait(webDriver, 100);
//		wait.until(ExpectedConditions.visibilityOf(element));
//	}

//	public static boolean waitForElementToBeClickable(WebElement element, int waitTimeInSec) {
//		WebElement visibleElement;
//		wait = new WebDriverWait(webDriver, waitTimeInSec);
//		try {
//			visibleElement = wait.until(ExpectedConditions
//					.elementToBeClickable(element));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return visibleElement != null ? true : false;
//	}

	/**
	 * This method will switch From Native to WebView
	 */
	@SuppressWarnings("unchecked")
	public static void switchFromNativeToWebView() {
		if (isAppiumDriver()) {
			if (!((AppiumDriver<WebElement>) webDriver).getContext().contains(
					"NAT")) {
				return;
			}
			Set<String> AvailableContexts = ((AppiumDriver<WebElement>) webDriver)
					.getContextHandles();
			for (String context : AvailableContexts) {
				if (context.contains("WEBVIEW"))
					((AppiumDriver<WebElement>) webDriver).context(context);
			}
		}
	}

	/**
	 * This method will switch the control to the Native App
	 */
	
	@SuppressWarnings("unchecked")
	public static void switchToNativeApp() {

		if (isAppiumDriver()) {
			if (((AppiumDriver<WebElement>) webDriver).getContext().contains(
					"NATIVE_APP")) {
				return;
			}
			Set<String> AvailableContexts = ((AppiumDriver<?>) webDriver)
					.getContextHandles();
			for (String context : AvailableContexts) {
				// log.debug("Available context" + context);
				if (context.contains("NATIVE_APP"))
					((AppiumDriver<WebElement>) webDriver).context(context);
			}
		}
	}

	/**
	 * This method will return the webelemnt with locator ById
	 * 
	 * @param id
	 * @return
	 */
	public WebElement getWebElementById(String id) {
		return webDriver.findElement(By.id(id));
	}

	/**
	 * This method will return the webelement with locator ByXpath
	 * 
	 * @param id
	 * @return
	 */
	public static WebElement getWebElementByXpath(String xpath) {
		return webDriver.findElement(By.xpath(xpath));
	}

	public void switchElementToClick(WebElement ele, String data) {
		if (data.contains("Yes")) {
			ele.click();
		}
	}

	/**
	 * Hides the keyboard in WebView
	 * 
	 * @author Kalyan, Raipati
	 * @since 30-Mar-2017 void
	 */
	public static void hideKeyboardOnWebView() {
		switchToNativeApp();
		try {
			WebElement keyboard = webDriver.findElement(By
					.name("Hide keyboard"));
			keyboard.click();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		switchFromNativeToWebView();
	}

	public static void waitBeforeExecute() {

		int seconds = 5;
		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < seconds * 1000);
	}

	public static void sleepInSeconds(int seconds) {

		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < seconds * 1000);
	}

	public static void sleepInMilliSeconds(long milliseconds) {

		long start, stop;
		start = System.currentTimeMillis();
		do {
			stop = System.currentTimeMillis();
		} while ((stop - start) < milliseconds);
	}

	public static void closeApp() {
		if (webDriver instanceof AppiumDriver)
			((AppiumDriver) GenericWrappers.webDriver).closeApp();
	}

	public static void launchApp() {
		if (webDriver instanceof AppiumDriver) {
			if (isAppClosed)
				((AppiumDriver) GenericWrappers.webDriver).launchApp();
		}
	}

	public static boolean isAppiumDriver() {
		return (webDriver instanceof AppiumDriver);
	}

	public static void quitBrowser() {
		webDriver.quit();
	}

	public static void quitApp() {
		((AppiumDriver) webDriver).quit();
	}

	private static void swipeInIpadSettingPanel() {

		int height = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getHeight();
		// log.debug(height);
		int width = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getWidth();
		// log.debug(height);
		((AppiumDriver) webDriver).swipe(width - 100, height, width - 100,
				height - 200, 500);
		sleepInMilliSeconds(100);
	}

	private static void swipeOffIpadSettingPanel() {
		int height = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getHeight();
		int width = ((AppiumDriver) webDriver)
				.findElementByClassName("UIAWindow").getSize().getWidth();
		((AppiumDriver) webDriver).tap(1, height / 2, width / 2, 1);
		sleepInMilliSeconds(100);
	}

	private static boolean isWifiEnabled() {
		int value = Integer.parseInt(((AppiumDriver<?>) webDriver)
				.findElementByAccessibilityId("Wi-Fi").getAttribute("value"));
		return value == 1 ? true : false;
	}

	public static void turnOnIpadWifi() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (!isWifiEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId("Wi-Fi")
					.click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}

	public static void turnOffIpadWifi() {
		switchToNativeApp();
		swipeInIpadSettingPanel();
		if (isWifiEnabled()) {
			((AppiumDriver<?>) webDriver).findElementByAccessibilityId("Wi-Fi")
					.click();
		}
		swipeOffIpadSettingPanel();
		switchFromNativeToWebView();
	}
	

	public static WebElement getChildByLocator(WebElement element, By locator) {
		return element.findElement(locator);
	}

	public static List<String> getTextOfAllElements(List<WebElement> elements) {
		List<String> textList = new ArrayList<>();
		for (WebElement element : elements) {
			textList.add(element.getText().trim());
		}
		return textList;
	}
	
	

	public static boolean isNotEmpty(String s) {
		return Objects.nonNull(s) && !s.isEmpty();
	}

	public static Dimension getWindowDimention() {
		return webDriver.manage().window().getSize();
	}

	public static Dimension getElementDimention(WebElement element) {
		return element.getSize();
	}

	public static String getCheckboxValueOnElement(WebElement element) {
		return element.isSelected() ? "Yes" : "No";
	}

	public static void setStartTime() {
		System.setProperty("current.date.time", new SimpleDateFormat(
				"dd-MM-yyyy-hh-mm-ssa").format(new Date()));
	}

	public static boolean isPresentAndDisplayed(final WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static boolean isEnabled(final WebElement element) {
		try {
			return element.isEnabled();
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static boolean arePresentAndDisplayed(final List<WebElement> elements) {
		boolean isDisplayed = false;
		try {
			for (WebElement element : elements)
			{
				isDisplayed = element.isDisplayed();
				if(isDisplayed==false)
				{
					break;
				}
			}
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
			
	public static void sendKeysOnMobile(String keysToSend) {
		((AppiumDriver<WebElement>) GenericWrappers.webDriver).getKeyboard()
				.sendKeys(keysToSend);
	}

	public static void drawOnCanvas(WebElement element) {
		TouchAction action = new TouchAction(
				(AppiumDriver<WebElement>) webDriver);
		action.longPress(element, 135, 15).waitAction(10).moveTo(100, 70)
				.release().perform();

	}

	public static void drawOnCanvas(String xpath) {
		MobileElement element = (MobileElement) GenericWrappers.webDriver
				.findElement(By.xpath(xpath));
		TouchAction t = new TouchAction(
				(AppiumDriver<MobileElement>) GenericWrappers.webDriver);
		t.press(element).moveTo(10, 10).release().perform();

	}

	public static void launchBrowser() {
		String env;
		if(properties("IsJenkins").equalsIgnoreCase("Yes"))
			env=System.getenv("Environment");
		else
			env=properties("Environment");
		switch (env) {
		case "SIT":
			webDriver.get(properties("SIT.URL"));
			break;
		case "UAT":	
			webDriver.get(properties("UAT.URL"));
			break;
		}
	}
	
	public static String getReportPath() {
		String reportDate = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSS-a")
				.format(new Date());
		screenShotPath = "Reports/report";
		File oldReport=new File(screenShotPath);
		File newReport=new File(screenShotPath+reportDate);
		oldReport.renameTo(newReport);
		String reportPath = screenShotPath + "/Report.html";
		return reportPath; 

	}

		public static void setTextValueOnElement(WebElement element, String textValue) {
			if (GenericWrappers.isNotEmpty(textValue)) {
			//	if (element.isEnabled())
				element.sendKeys(textValue);
			}
		}	
		public static void acceptAlert()
			{
			 Alert alert = webDriver.switchTo().alert();
		     alert.accept();
			}
		
		public static boolean isAlertPresent(){
		    boolean foundAlert = false;
		    WebDriverWait wait = new WebDriverWait(webDriver, 5);
		    try {
		      //  wait.until(ExpectedConditions.alertIsPresent());
		        foundAlert = true;
		    } catch (Exception eTO) {
		        foundAlert = false;
		    }
		    return foundAlert;
		}
}
