package com.automation.pages;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BusHomePage extends BasePage {

    @FindBy(xpath = "//h1[@class='sc-jAaTju fOJRFi' and text()=\"India's No. 1 Online Bus Ticket Booking Site\"]")
    WebElement heading;

    @FindBy(css = "#src")
    WebElement travelFromInput;

    @FindBy(xpath = "//ul[@class='sc-dnqmqq dZhbJF']/li[1]")
    WebElement sourceSelect;

    @FindBy(id = "dest")
    WebElement travelToInput;

    @FindBy(xpath = "//ul[@class='sc-dnqmqq dZhbJF']/li[1]")
    WebElement destinationSelect;

    @FindBy(css = "#onwardCal")
    WebElement dateSelect;

    @FindBy(xpath = "(//div[@class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']/following-sibling::div)[1]")
    WebElement monthYearDate;

    @FindBy(xpath = "//div[@class='DayNavigator__IconBlock-qj8jdz-2 iZpveD'][3]")
    WebElement nextBtn;

    @FindBy(css = "#search_button")
    WebElement searchBusBtn;

    @FindBy(className = "scrollTopButton")
    WebElement scrollTopButton;

    @FindBy(xpath = "//div[@class='sc-eHgmQL ehQshY']/i")
    WebElement swapButton;

    @FindBy(xpath = "(//text[@class='placeHolderMainText'])[1]")
    WebElement travelFromSwap;

    @FindBy(xpath = "(//text[@class='placeHolderMainText'])[2]")
    WebElement travelToSwap;

    @FindBy(xpath = "//h2[contains(@class,'OfferSection')]/parent::div/child::a")
    WebElement viewAllOfferBtn;
    
    @FindBy(xpath = "//span[@class='name_rb_secondary_item' and text()='Account']")
    WebElement profileDropDownBtn;

    @FindBy(xpath = "//span[@class='header_dropdown_item_name' and text()='Cancel Ticket']")
    WebElement cancelTicketOption;

    public void openWebsite() {
        driver.get(ConfigReader.getConfigValue("base.url"));
    }

    public boolean verifyUserIsOnHomePage() {
        return heading.isDisplayed();
    }

    public void enterSourceCity() {
        travelFromInput.sendKeys(ConfigReader.getConfigValue("Bus.travelFrom"));
        sourceSelect.click();
    }

    public void enterDestinationCity() {
        travelToInput.sendKeys(ConfigReader.getConfigValue("Bus.travelTo"));
        destinationSelect.click();
    }

    public void clickOnSearchButton() {
        searchBusBtn.click();
    }

    public void selectADate(String date) {
        dateSelect.click();
        selectDate(date, driver);
    }

    public void selectDate(String date, WebDriver driver){
        String monthYear = date.substring(date.indexOf(" ")+1);
        String day = date.substring(0,date.indexOf(" "));

        WebElement monthYearElement = driver.findElement(By.xpath("(//div[@class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']/following-sibling::div)[1]"));

        while(!monthYearElement.getText().substring(0,8).equals(monthYear)){
            // clicking the next button
            nextBtn.click();
            monthYearElement = driver.findElement(By.xpath("(//div[@class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']/following-sibling::div)[1]"));
        }

        String xpath = "//span[contains(@class,'DayTiles__CalendarDaysSpan') and text() = %s]";
        WebElement dayElement = driver.findElement(By.xpath(String.format(xpath,day)));
        dayElement.click();
    }

    public void scrollsToTheBottomOfThePage() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.className("globalPresenceHomeV2"));

        int yOffset = element.getLocation().getY();
        for (int i = 0; i < yOffset; i += 10) {
            js.executeScript("window.scrollBy(0, 10);");
            Thread.sleep(10);  // Adjust the sleep time to control speed
        }
    }

    public boolean isBackToScrollButtonPresent() {
        return isPresent(scrollTopButton);
    }

    public void clickOnBackToScrollButton() {
        scrollTopButton.click();
    }

    public boolean isPageScrollsToTopOfPage() throws InterruptedException {
        Thread.sleep(4000);
        return heading.isDisplayed();
    }

    public boolean isCitiesAreSwapped() {
        String beforeSwapSourceCity = travelFromSwap.getText();
        String beforeSwapDestinationCity = travelToSwap.getText();
        swapButton.click();
        String afterSwapSourceCity = travelFromSwap.getText();
        String afterSwapDestinationCity = travelToSwap.getText();
        return afterSwapSourceCity.equals(beforeSwapDestinationCity) && afterSwapDestinationCity.equals(beforeSwapSourceCity);
    }

    public void clickOnViewAllOffersBtn() {
        viewAllOfferBtn.click();
    }

    public void clickOnProfileDropDownBtn() {
        profileDropDownBtn.click();
    }

    public void clickOnCancelTicket() {
        cancelTicketOption.click();
    }
}
