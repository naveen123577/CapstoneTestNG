package com.automation.test;

import com.automation.pages.BusHomePage;
import com.automation.pages.BusSearchResultPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    BusHomePage busHomePage;
    BusSearchResultPage busSearchResultPage;

    @BeforeMethod
    public void setUp(){
        ConfigReader.initConfig();
        DriverManager.createDriver();
        busHomePage = new BusHomePage();
        busSearchResultPage = new BusSearchResultPage();
    }
    @AfterMethod
    public void clean(){
        DriverManager.getDriver().quit();
    }
}
