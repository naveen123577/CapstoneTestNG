package com.automation.pages;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BusSearchResultPage extends BasePage {

    @FindBy(xpath = "//div[@class='travels lh-24 f-bold d-color']")
    List<WebElement>buses;

    @FindBy(css = ".f-bold.busFound")
    WebElement busNum;

    @FindBy(xpath = "//div[@class='msg' and text()='Oops! No buses found.']")
    WebElement noBusesMsg;
    
    @FindBy(xpath = "//input[@id='bt_SLEEPER']/parent::li/child::label[@class='custom-checkbox']")
    WebElement sleeperCheckBox;
    
    @FindBy(xpath = "//input[@id='bt_AC']/parent::li/child::label[@class='custom-checkbox']")
    WebElement acCheckBox;

    @FindBy(xpath = "//input[@id='bt_NONAC']/parent::li/child::label[@class='custom-checkbox']")
    WebElement nonAcCheckBox;

    @FindBy(xpath = "//div[@class='bus-type f-12 m-top-16 l-color evBus']")
    List<WebElement> busTypeList;

    @FindBy(xpath = "//button[@class='btn' and text()='CLEAR ALL FILTERS']")
    WebElement clearAllFilterBtn;

    @FindBy(xpath = "//span[@class='txt-val ' and text()='Boarding & Dropping Points']")
    WebElement boardingAndDroppingBtn;

    @FindBy(className = "bpdp_list_title")
    WebElement boardingAndDroppingDetails;

    @FindBy(xpath = "(//div[@class='button view-seats fr'])[1]")
    WebElement viewSeatBtn;

    @FindBy(xpath = "//canvas[@data-type='lower']")
    WebElement canvasElement;

    @FindBy(className = "bpdp-point")
    WebElement boardingPointHeadingInViewSeat;

    @FindBy(xpath = "//li[@class='db oh']")
    List<WebElement>boardingDroppingDetailsList;

    @FindBy(xpath = "//span[@class='bpdp-point' and text() = 'DROPPING POINT']")
    WebElement droppingPointOptionSelectInViewSeat;

    @FindBy(xpath = "//div[@class='hideSeats fr']")
    WebElement hideSeatsBtn;

    @FindBy(xpath = "(//span[@class='f-19 f-bold'])[1]")
    WebElement firstBusPriceInListingPage;

    @FindBy(xpath = "(//span[@class='fr fare-summary-value'])[2]/span[2]")
    WebElement firstBusPriceInBoardingDroppingPage;

    public boolean isSearchResultFound() {
        String busNumber = busNum.getText();
        ConfigReader.setConfigValue("busNumber", busNumber);
        return !buses.isEmpty() || noBusesMsg.isDisplayed();
    }

    public void clickOnSleeperCheckBox() {
        sleeperCheckBox.click();
    }

    public boolean isSleeperBusResultsDisplayed() {
        for (WebElement bus: busTypeList){
            return bus.getText().toLowerCase().contains("sleeper");
        }
        return false;
    }

    public void clickOnAcCheckBox() {
        acCheckBox.click();
    }

    public boolean isAcBusResultsDisplayed() {
        for (WebElement bus: busTypeList){
            return bus.getText().toUpperCase().contains("A/C");
        }
        return false;
    }

    public void clickOnNonAcCheckBox() {
        nonAcCheckBox.click();
    }

    public boolean isNonAcBusResultsDisplayed() {
        for (WebElement bus: busTypeList){
            return bus.getText().toUpperCase().contains("NON A/C");
        }
        return false;
    }

    public void clickOnClearAllFilterBtn() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while(true) {
            WebElement element = driver.findElement(By.id("view-more-btn"));
            int yOffset = element.getLocation().getY();
            for (int i = 0; i < yOffset; i += 10) {
                js.executeScript("window.scrollBy(0, 10);");
                Thread.sleep(15);  // Adjust the sleep time to control speed
            }
            if(isPresent(element)){
                break;
            }
        }

        js.executeScript("arguments[0].click()",clearAllFilterBtn);

    }

    public boolean isFullListOfBusesAreDisplayed() throws InterruptedException {
        Thread.sleep(4000);
        String busNumber = busNum.getText();
        return busNumber.equals(ConfigReader.getConfigValue("busNumber"));
    }

    public boolean isClearAllFilterBtnDisplayed() {
        return isPresent(clearAllFilterBtn);
    }

    public void clickOnBoardingAndDroppingPointsButton() {
        boardingAndDroppingBtn.click();
    }

    public boolean isBoardingAndDroppingPointsDisplayed() {
        return isPresent(boardingAndDroppingDetails);
    }

    public void clickOnEmptySeat(){
        var canvas_dimension = canvasElement.getSize();
        var canvas_center_x = canvas_dimension.getWidth()+10;
        var canvas_center_y = canvas_dimension.getHeight()-10;

        int x = 10, y = 10;

        while (x<canvas_center_x && y< canvas_center_y && !isPresent(boardingPointHeadingInViewSeat)){
            clickOnSingleEmptySeat(canvasElement,x,y);
            x=x+10;
            y=y+10;
        }
    }

    public void clickOnViewSeat() {
        viewSeatBtn.click();
    }

    public void clickOnSingleEmptySeat(WebElement canvas, int x, int y){
        new Actions(driver)
                .moveToElement(canvas,x,y)
                .click().pause(1000).build().perform();
    }

    public boolean isBoardingAndDroppingDetailsListPresent() {
        boolean boardingDetails = !boardingDroppingDetailsList.isEmpty();
        droppingPointOptionSelectInViewSeat.click();
        boardingDroppingDetailsList = driver.findElements(By.xpath("//li[@class='db oh']"));
        return boardingDetails && !boardingDroppingDetailsList.isEmpty();
    }

    public boolean isSeatsAreVisible() {
        return isPresent(canvasElement);
    }

    public void clickOnHideSeatsButton() {
        hideSeatsBtn.click();
    }

    public void storeFirstBusPriceOfListingPage() {
        String firstBusPrice = firstBusPriceInListingPage.getText();
        ConfigReader.setConfigValue("firstBusPriceListingPage",firstBusPrice);
    }

    public boolean isPriceOfListingPageAndBoardingDroppingPageSame() {
        String firstBusPriceBoardingDropping = firstBusPriceInBoardingDroppingPage.getText();
        double priceDouble = Double.parseDouble(firstBusPriceBoardingDropping);
        int firstBusPriceBoardingPageIntValue = (int) priceDouble;
        int firstBusPriceListingPageIntValue = Integer.parseInt(ConfigReader.getConfigValue("firstBusPriceListingPage"));
        return firstBusPriceBoardingPageIntValue >= firstBusPriceListingPageIntValue;
    }
}
