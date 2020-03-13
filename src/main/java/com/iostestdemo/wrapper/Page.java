/**
 */
package com.iostestdemo.wrapper;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.support.PageFactory;

public class Page {
	/**
	 * A constructor to initialize PageFactory for all child pages
	 */
	public Page() {
		if (GenericWrappers.isAppiumDriver()) {
			PageFactory.initElements(new AppiumFieldDecorator(
					GenericWrappers.webDriver), this);

		} else {
			PageFactory.initElements(GenericWrappers.webDriver, this);
		}
	}

}
