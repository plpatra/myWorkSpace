package com.iostestdemo.baserunner;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.iostestdemo.wrapper.GenericWrappers;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
			features = { "." }, 
			glue 	 = { "com.iostestdemo.glue" },
			tags 	 = { "~@Desktop", "@sum, @alert" }, 
			plugin 	 = { "com.cucumber.listener.ExtentCucumberFormatter:", "rerun:target/rerun.txt" }, 
			monochrome = true, 
			dryRun = false
			)

public class iPadTest {
	@BeforeClass
	public static void setUp() {
		try {
			GenericWrappers.setStartTime();
			GenericWrappers.setDriver("iPhoneSimulator");
			System.setProperty("cucumberReportPath",GenericWrappers.getReportPath());
			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			extentProperties.setReportPath(GenericWrappers.getReportPath());
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		//GenericWrappers.quitBrowser();
		GenericWrappers.webDriver = null;
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("OS", System.getProperty("os.name"));
		Reporter.setSystemInfo("Device", "Desktop");
		Reporter.setSystemInfo("Browser", "Chrome");
	}
}
