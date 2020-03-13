package com.iostestdemo.glue;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.iostestdemo.pages.TestAppPage;
import com.iostestdemo.wrapper.GenericWrappers;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.MobileElement;

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
}
