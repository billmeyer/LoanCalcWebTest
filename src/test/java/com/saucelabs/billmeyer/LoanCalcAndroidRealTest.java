package com.saucelabs.billmeyer;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class LoanCalcAndroidRealTest extends LoanCalcBaseTest
{
    protected AndroidDriver driver;

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
    public void testAndroidonRealDevice() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("testobject_api_key", "2FD1A02F77F44BDE917269A9C7D1877B");
        caps.setCapability("appiumVersion", "1.7.2");
        caps.setCapability("deviceName","Google Pixel 2 XL");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion", "8.0");
        caps.setCapability("platformName","Android");
        caps.setCapability("browserName", "Chrome");

        caps.setCapability("name", String.format("%s - %s [%s]",
                this.getClass().getSimpleName(), caps.getBrowserName(), new Date()));

        URL url = new URL("https://us1.appium.testobject.com/wd/hub");

        driver = new AndroidDriver(url, caps);
        String sessionId = driver.getSessionId().toString();
//        SauceREST sauceRest = new SauceREST(userName, accessKey);

        System.out.printf("Test:       %s\n", caps.getCapability("name"));
        System.out.printf("Session ID: %s\n", sessionId);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        boolean result = testLoanCalc(driver);
        reportTestObjectResult(sessionId, result);

        driver.quit();
    }
}
