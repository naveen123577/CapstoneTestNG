package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class TrainBookingPage extends BasePage {


    @FindBy(xpath = "(//div[@class='searchtrainClasses']/div[1])[1]")
    WebElement clkSeat;

    @FindBy(xpath = "//div[text()='OKAY, GO AHEAD']")
    WebElement clickGoAhead;

    @FindBy(xpath = "//input[@placeholder='IRCTC username']")
    WebElement clickUsernameField;

    @FindBy(xpath = "//div[@class='check_irctc_un']")
    WebElement clickCheckUsername;

    @FindBy(className = "validation_error")
    WebElement errorMessage;

    public void clickASeat() throws InterruptedException {
        clkSeat.click();
        Thread.sleep(2000);
        if (isPresent(clickGoAhead)) {
            clickGoAhead.click();
        }
    }

    public String verifyUsername() {
        return errorMessage.getText();
    }

    public void enterUsername(String username) {

        Set<String> handles = driver.getWindowHandles();
        String current = driver.getWindowHandle();
        for (String handle : handles) {
            if (!handle.equals(current)) {
                driver.close();
                driver.switchTo().window(handle);
            }
        }
        javaScriptExecutorClick(clickUsernameField);
        clickUsernameField.sendKeys(username);
    }

    public void clickOnCheckUserName() {
        javaScriptExecutorClick(clickCheckUsername);
    }
}
