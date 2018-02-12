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

public class LoanCalcIOSSimulatorTest extends LoanCalcBaseTest
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
    public void testIOS() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("testobject_api_key", "2FD1A02F77F44BDE917269A9C7D1877B");
        caps.setCapability("username", userName);
        caps.setCapability("accesskey", accessKey);

        caps.setCapability("appiumVersion", "1.7.2");
        caps.setCapability("deviceName", "iPhone X Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion", "11.2");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "Safari");

        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), new Date()));

        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        driver = new IOSDriver(url, caps);
        String sessionId = driver.getSessionId().toString();
//        SauceREST sauceRest = new SauceREST(userName, accessKey);

        System.out.printf("Test:       %s\n", caps.getCapability("name"));
        System.out.printf("Session ID: %s\n", sessionId);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        testLoanCalc(driver);

        driver.quit();
    }
}
