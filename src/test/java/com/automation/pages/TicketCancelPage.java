package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class TicketCancelPage extends BasePage {

    @FindBy(css = ".cancel-ticket-heading")
    WebElement ticketCancelHeading;

    @FindBy(xpath = "//input[@class='input-box input-text' and @placeholder='Enter Ticket No']")
    WebElement ticketNumInput;

    @FindBy(xpath = "//input[@class='input-box input-text' and @placeholder='Enter Mobileno']")
    WebElement mobileNumInput;

    @FindBy(className = "message")
    WebElement errorMessage;

    @FindBy(xpath = "//div[@class='button-comp-lbl bg-color-d84e55' and text()='Select Passengers']")
    WebElement selectPassengerBtn;
    
    public boolean isTicketCancelPageDisplayed() {
        Set<String> handles = driver.getWindowHandles();
        String current = driver.getWindowHandle();
        for(String handle : handles){
            if(!handle.equals(current)){
                driver.close();
                driver.switchTo().window(handle);
            }
        }
        return isPresent(ticketCancelHeading);
    }

    public void enterInvalidTicketDetails(String ticketNum, String mobNum) {
        ticketNumInput.sendKeys(ticketNum);
        mobileNumInput.sendKeys(mobNum);
    }

    public void clickOnSelectPassengerBtn() {
        selectPassengerBtn.click();
    }

    public String isErrorMsgReceived() {
        return errorMessage.getText();
    }
}
