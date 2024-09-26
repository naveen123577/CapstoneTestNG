package com.automation.listeners;

import com.automation.utils.AllureReportManager;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.ITestResult;

public class AllureReportListener extends AllureTestNg {
    @Override
        public void onTestFailure(ITestResult result) {
            AllureReportManager.attachScreenshot();
            super.onTestFailure(result);
        }
}
