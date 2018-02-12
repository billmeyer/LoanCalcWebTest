package com.saucelabs.billmeyer;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class LoanCalcEdgeOnWindowsTest extends LoanCalcBaseTest
{
    protected RemoteWebDriver driver;

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
    public void testEdgeOnWindows() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.edge();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "14.14393");
        caps.setCapability("recordVideo", "true");
        caps.setCapability("recordScreenshots", "true");
        caps.setCapability("screenResolution", "1280x800");

        caps.setCapability("username", userName);
        caps.setCapability("accesskey", accessKey);

        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), new Date()));

        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        driver = new RemoteWebDriver(url, caps);
        String sessionId = driver.getSessionId().toString();

        System.out.printf("Test:       %s\n", caps.getCapability("name"));
        System.out.printf("Session ID: %s\n", sessionId);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        testLoanCalc(driver);

        driver.quit();
    }
}
