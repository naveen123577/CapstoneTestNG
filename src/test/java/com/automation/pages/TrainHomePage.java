package com.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TrainHomePage extends BasePage {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    @FindBy(xpath = "//span[text()='redRail: Train Ticket Booking']")
    WebElement trainHomeHeading;
    @FindBy(xpath = "//label[text()='From']")
    WebElement clkFromField;

    @FindBy(xpath = "//label[text()='To']")
    WebElement clkToField;

    @FindBy(xpath = "//input[@id='src']")
    WebElement fromCity;

    @FindBy(xpath = "//div[@class='solr_results_block'][1]")
    WebElement firstCity;

    @FindBy(xpath = "//input[@id='dst']")
    WebElement destinationCity;

    @FindBy(xpath = "//div[@class='solr_results_block'][1]")
    WebElement destinationFirstCity;

    @FindBy(css = ".home_date_wrap")
    WebElement clkDate;

    @FindBy(xpath = "(//div[@class='sc-gqjmRU fnURhG'])[3]")
    WebElement nextBtn;


    @FindBy(xpath = "//button[text()='search trains']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@class='srp_trainname_wrap']")
    List<WebElement> trainList;

    @FindBy(xpath = "//img[@alt='exchange icon']")
    WebElement swapButton;

    @FindBy(xpath = "//p[text()='Live Train Status']")
    WebElement statusTrain;

    @FindBy(name = "pnrNo")
    WebElement trainNumber;

    @FindBy(xpath = "//button[text()='Check Status']")
    WebElement chkStatusButton;

    @FindBy(xpath = "//div[text()='Train Starting Date']")
    WebElement trainStartText;

    @FindBy(xpath = "//span[@class='lts_header']")
    WebElement trainHeading;

    @FindBy(xpath = "//div[@class='lts_solr_wrap']")
    WebElement trainFirstOption;

    @FindBy(xpath = "//div[@class='error_page_text']")
    WebElement errMsg;


    public void clickTrainTicket() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement ticketButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='rail_tickets_vertical']")));
        ticketButton.click();

    }

    public void enterSourceCity(String from) throws InterruptedException {
        clkFromField.click();
        fromCity.sendKeys(from);
        Thread.sleep(1000);
        firstCity.click();
    }

    public void enterDestinationCity(String to) throws InterruptedException {
        clkToField.click();
        destinationCity.sendKeys(to);
        Thread.sleep(1000);
        destinationFirstCity.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void pickADate(String date) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", clkDate);
        selectDate(date, driver);
    }

    public void selectDate(String date, WebDriver driver) {
        String monthYear = date.substring(date.indexOf(" ") + 1);
        String day = date.substring(0, date.indexOf(" "));

        WebElement monthYearElement = driver.findElement(By.xpath("(//div[@class='sc-gqjmRU fnURhG'])[2]"));

        while (!monthYearElement.getText().substring(0, 8).equals(monthYear.substring(0, 8))) {
            nextBtn.click();
            monthYearElement = driver.findElement(By.xpath("(//div[@class='sc-gqjmRU fnURhG'])[2]"));
        }

        String xpath = "//span[contains(@class,'sc-htoDjs cvnjgw') and text() = %s]";
        WebElement dayElement = driver.findElement(By.xpath(String.format(xpath, day)));
        dayElement.click();
    }


    public boolean verifyResultsForTrain() {
        return trainList.size() >= 1;
    }

    public String getPopupMessage() throws InterruptedException {
        Thread.sleep(3000);
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }


    public boolean verifySwapButton() {
        String beforeSource = fromCity.getAttribute("value").trim();
        String beforeDestination = destinationCity.getAttribute("value").trim();
        swapButton.click();
        return fromCity.getAttribute("value").trim().equalsIgnoreCase(beforeDestination) && destinationCity.getAttribute("value").equalsIgnoreCase(beforeSource);
    }

    public void clickLiveTrainStatus() {
        statusTrain.click();
    }

    public void enterTrainNumber(String trainNo) throws InterruptedException {

        trainNumber.sendKeys(trainNo);
        Thread.sleep(3000);
        String xpath = String.format("//div[@class='lts_solr_wrap']//b[text()='%S']", trainNo);
        WebElement firstOption = driver.findElement(By.xpath(xpath));
        firstOption.click();

    }

    public void clickCheckStatus() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", chkStatusButton);

    }

    public boolean verifyStatusOfTrain() {
        return trainStartText.isDisplayed() && trainHeading.isDisplayed();
    }

    public String getErrorMessage() {

        return errMsg.getText();
    }

    public boolean verifyTrainHomePage() {
        return isPresent(trainHomeHeading);
    }
}
