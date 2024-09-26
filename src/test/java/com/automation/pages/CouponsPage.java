package com.automation.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Set;

public class CouponsPage extends BasePage {
    
    @FindBy(xpath = "//h1[@class='XCN' and text()='Offers']")
    WebElement offerHeading;

    @FindBy(css = ".OfferPro")
    List<WebElement>couponCodes;

    public boolean isOfferPageDisplayed() {
        Set<String> handles = driver.getWindowHandles();
        String current = driver.getWindowHandle();
        for(String handle : handles){
            if(!handle.equals(current)){
                driver.close();
                driver.switchTo().window(handle);
            }
        }
        return offerHeading.isDisplayed();
    }

    public boolean isAllCouponsHaveCode() {
        for(WebElement code : couponCodes){
            return code.getText().contains("Use code");
        }
        return false;
    }
}
