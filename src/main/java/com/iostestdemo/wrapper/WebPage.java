package com.iostestdemo.wrapper;

public class WebPage extends Page {
	/**
	 * A constructor to initialize PageFactory for all child web pages
	 */
	public WebPage() {
		GenericWrappers.switchFromNativeToWebView();
	}
}
