package com.iostestdemo.glue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import gherkin.formatter.model.Step;
import java.net.MalformedURLException;

import org.junit.Assert;

import com.iostestdemo.wrapper.GenericWrappers;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class StartupSetpDef {
	static boolean isFailed;

	@Before()
	public static void beforeScenario(Scenario scenario) throws MalformedURLException {
		if (GenericWrappers.webDriver == null) {
			System.exit(0);
		} else {
			if (!GenericWrappers.isAppiumDriver()) {
				GenericWrappers.launchBrowser();
			} else {
				if (GenericWrappers.isAppClosed) {
					GenericWrappers.launchApp();
					//GenericWrappers.switchToNativeApp();
					GenericWrappers.isAppClosed = false;
				}
			}

		}
	}

	@After
	public void afterScenario(Scenario scenario) {
		try {
			String result = "FAIL";
			if (GenericWrappers.isAppiumDriver()) {
				GenericWrappers.closeApp();
			}
			if(scenario.isFailed()) 
				result= "FAIL";
			else
				result = "PASS";
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		finally {
			GenericWrappers.isAppClosed = true;
		}

	}
}
