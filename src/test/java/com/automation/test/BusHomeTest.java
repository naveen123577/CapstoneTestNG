package com.automation.test;

import com.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BusHomeTest extends BaseTest{

    @Test
    public void searchForBussesBetweenTwoCities(){
        busHomePage.openWebsite();
        Assert.assertTrue(busHomePage.verifyUserIsOnHomePage());
        busHomePage.enterSourceCity();
        busHomePage.enterDestinationCity();
        busHomePage.selectADate(ConfigReader.getConfigValue("Bus.travelDate"));
        busHomePage.clickOnSearchButton();
        Assert.assertTrue(busSearchResultPage.isSearchResultFound());
    }
}
