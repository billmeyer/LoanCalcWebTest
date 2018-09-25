package com.saucelabs.billmeyer;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class LoanCalcIOSRealTest extends LoanCalcBaseTest
{
    protected IOSDriver driver;

    @BeforeMethod
    public void setUp() throws Exception
    {
    }

    @AfterMethod
    public void tearDown() throws Exception
    {
        if (driver != null) driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }

    @Test
    public void testIOSonRealDevice() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.safari();
        caps.setCapability("testobject_api_key", toAccessKey);

        // Any search strings used on https://saucelabs.com/devices to filter devices can be used to specify the 'deviceName'.  The first
        // available device matching the filter will be allocated for the test.
//        caps.setCapability("deviceName", "iPhone X");

        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");

        Date startDate = new Date();
//        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), startDate));
        caps.setCapability("name", String.format("%s - %s", this.getClass().getSimpleName(), caps.getBrowserName()));

        URL url = new URL("https://us1.appium.testobject.com/wd/hub");

        driver = new IOSDriver(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String sessionId = driver.getSessionId().toString();
        log(this, "Started %s, session ID=%s.\n", startDate, sessionId);

        boolean result = testLoanCalc(driver);

        Date stopDate = new Date();
        log(this, "Completed %s, %d seconds.\n", stopDate, (stopDate.getTime() - startDate.getTime())/ 1000L);

        reportTestObjectResult(sessionId, result);

        driver.quit();
    }
}
