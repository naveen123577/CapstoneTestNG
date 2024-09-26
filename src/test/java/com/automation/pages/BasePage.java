package com.automation.pages;

import com.automation.utils.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class BasePage {
    WebDriver driver;
    JavascriptExecutor js;
    public BasePage(){
        this.driver = DriverManager.getDriver();
        this.js = (JavascriptExecutor)driver;
        PageFactory.initElements(driver,this);
    }
    public boolean isPresent(WebElement element) {
        try {
            setImplicitWait(5);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            setImplicitWait(30);
        }
    }
    public void setImplicitWait(long sec) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
    }
    public void javaScriptExecutorClick(WebElement element){
        js.executeScript("arguments[0].click()",element);
    }
}
