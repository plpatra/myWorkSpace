package com.iostestdemo.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.iostestdemo.wrapper.WebPage;


public class TestAppPage extends WebPage {

		@FindBy(xpath = "//XCUIElementTypeTextField[@name=\"IntegerA\"]")
		WebElement firstInput;
		
		@FindBy(xpath = "//XCUIElementTypeTextField[@name=\"IntegerB\"]")
		WebElement secondInput;
		
		@FindBy(xpath = "//XCUIElementTypeButton[@name=\"ComputeSumButton\"]")
		WebElement computeSumButton;
		
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Answer\"]")
		WebElement resultText;
		
		@FindBy(xpath = "//XCUIElementTypeButton[@name=\"show alert\"]")
		WebElement showAlert;

		@FindBy(xpath = "//input[@title='Search']")
		WebElement searchInput;
		
		@FindBy(xpath = "//div[contains(@class,'FPdoLc')]//input[contains(@class,'gNO89b' )]")
		WebElement searchSubmit;
	
		public WebElement getFirstInput() {
			return firstInput;
		}
		public WebElement getSecondInput() {
			return secondInput;
		}
		
		public WebElement getComputeSumButton() {
			return computeSumButton;
		}
		public WebElement getResultText() {
			return resultText;
		}
		public WebElement getShowAlert() {
			return showAlert;
		}
		public WebElement getSearchInput() {
			return searchInput;
		}
		
		public WebElement getSearchSubmit() {
			return searchSubmit;
		}
	}