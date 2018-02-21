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
        caps.setCapability("platformVersion", "8.1");
        caps.setCapability("platformName","Android");
        caps.setCapability("browserName", "Chrome");

        Date startDate = new Date();
        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), startDate));

        URL url = new URL("https://us1.appium.testobject.com/wd/hub");

        driver = new AndroidDriver(url, caps);
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
