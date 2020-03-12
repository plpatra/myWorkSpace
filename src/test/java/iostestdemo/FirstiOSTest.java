package iostestdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FirstiOSTest {

	public static WebDriver webDriver;
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		firstTest();
	}

	public static void firstTest() throws MalformedURLException{
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "iPhone 8");
		cap.setCapability("platformName", "iOS");
		cap.setCapability("platformVersion", "13.2");
		cap.setCapability("automationName", "XCUITest");
		cap.setCapability(MobileCapabilityType.APP,	"/Users/plpatra/Desktop/TestApp/ios-test-app/build/Release-iphonesimulator/TestApp.app");
		cap.setCapability("noReset", true);
		cap.setCapability("webkitResponseTimeout", 5000);
		cap.setCapability("newCommandTimeout", 70000);
		cap.setCapability("simpleIsVisibleCheck", true);
		cap.setCapability("autoAcceptAlerts", true);
		webDriver = new IOSDriver<WebElement>(new URL("http://localhost:4723" + "/wd/hub"), cap);
		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
}
