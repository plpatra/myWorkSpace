package com.iostestdemo.glue;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.iostestdemo.pages.TestAppPage;
import com.iostestdemo.wrapper.GenericWrappers;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.MobileElement;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.Command;

public class TestAppStepDef {
	static String isAppClosed = "No";
	private static boolean isFailed;
	TestAppPage tsPage = new TestAppPage();
	
	@Given("^The TestApp launched with simulator$")
	public void the_app_launched_with_simulator() throws Throwable {
		assertTrue("App not Launched", GenericWrappers.isPresentAndDisplayed(tsPage.getFirstInput()));
	}

	@Then("^I launch the TestApp and do sum of \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_launch_the_TestApp_and_do_sub_of_and(String arg1, String arg2) throws Throwable {
		//MobileElement el1 =(MobileElement)GenericWrappers.webDriver.findElement(By.xpath("//XCUIElementTypeTextField[@name=\"IntegerA\"]"));

		MobileElement el1 = (MobileElement) tsPage.getFirstInput();
		MobileElement el2 = (MobileElement) tsPage.getSecondInput();
		MobileElement el3 = (MobileElement) tsPage.getComputeSumButton();
		MobileElement answer = (MobileElement) tsPage.getResultText();

		GenericWrappers.setTextValueOnElement(el1, arg1);
		GenericWrappers.setTextValueOnElement(el2, arg2);
		GenericWrappers.clickOnElement(el3);
		assertTrue("Answer is not correct", answer.getText().equals(Integer.toString(Integer.parseInt(arg1)+Integer.parseInt(arg2))));
		
		
	}
	
	@Then("^I invoke the Alert$")
	public void i_invoke_the_Alert() throws Throwable {
		MobileElement alert = (MobileElement) tsPage.getShowAlert();
		GenericWrappers.clickOnElement(alert);
			GenericWrappers.acceptAlert();
	}
	
	@Then("^I Assert Validation status$")
	public static void i_Assert_Validation_status() {
		if(isFailed)
		{
			Assert.fail("Failed due to validation errors");
		}
	}	
	
	@Then("^I simulate different \"([^\"]*)\" and \"([^\"]*)\" throughput$")
	public void i_simulate_different_and_throughput(int downloadThroughput, int uploadThroughput) throws Throwable {
	   System.out.println("Network Throughput Test");
	   ChromeDriver driver = new ChromeDriver();
	   if (downloadThroughput > 0 && uploadThroughput > 0) {
          CommandExecutor executor = driver.getCommandExecutor();
           Map map = new HashMap();
           map.put("offline", false);
           map.put("latency", 5);
           map.put("download_throughput", downloadThroughput);
           map.put("upload_throughput", uploadThroughput);
           Response response = executor.execute(new Command(driver.getSessionId(), "setNetworkConditions", 
        		   ImmutableMap.of("network_conditions", ImmutableMap.copyOf(map))));
	   }
	   driver.get("http://google.com");
       driver.quit();
	}
	
	@Then("^I serch a text$")
	public void i_serch_a_text() throws Throwable {
		GenericWrappers.setTextValueOnElement(tsPage.getSearchInput(), "Selenium");
		GenericWrappers.clickOnElement(tsPage.getSearchSubmit());
	}
}