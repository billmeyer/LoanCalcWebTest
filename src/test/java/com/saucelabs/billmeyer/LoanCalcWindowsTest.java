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

public class LoanCalcWindowsTest extends LoanCalcBaseTest
{
    @BeforeMethod
    public void setUp() throws Exception
    {
    }

    @AfterMethod
    public void tearDown() throws Exception
    {
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }

    @Test
    public void testChrome() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "67");
        caps.setCapability("recordVideo", "true");
        caps.setCapability("recordScreenshots", "true");
        caps.setCapability("screenResolution", "1920x1080");
        caps.setCapability("extendedDebugging", true);

        caps.setCapability("username", userName);
        caps.setCapability("accesskey", accessKey);

        Date startDate = new Date();
//        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), startDate));
        caps.setCapability("name", String.format("%s - %s", this.getClass().getSimpleName(), caps.getBrowserName()));

        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        RemoteWebDriver driver = new RemoteWebDriver(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String sessionId = driver.getSessionId().toString();
        log(this, "Started %s, session ID=%s.\n", startDate, sessionId);

        boolean result = testLoanCalc(driver);

        Date stopDate = new Date();
        log(this, "Completed %s, %d seconds.\n", stopDate, (stopDate.getTime() - startDate.getTime())/ 1000L);

        reportSauceLabsResult(driver, result);

        driver.quit();
    }

    @Test
    public void testEdge() throws Exception
    {
        DesiredCapabilities caps = DesiredCapabilities.edge();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "16.16299");
        caps.setCapability("recordVideo", "true");
        caps.setCapability("recordScreenshots", "true");
        caps.setCapability("screenResolution", "1280x800");

        caps.setCapability("username", userName);
        caps.setCapability("accesskey", accessKey);

        Date startDate = new Date();
        caps.setCapability("name", String.format("%s - %s [%s]", this.getClass().getSimpleName(), caps.getBrowserName(), startDate));

        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        RemoteWebDriver driver = new RemoteWebDriver(url, caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String sessionId = driver.getSessionId().toString();
        log(this, "Started %s, session ID=%s.\n", startDate, sessionId);

        boolean result = testLoanCalc(driver);

        Date stopDate = new Date();
        log(this, "Completed %s, %d seconds.\n", stopDate, (stopDate.getTime() - startDate.getTime())/ 1000L);

        reportSauceLabsResult(driver, result);

        driver.quit();
    }
}
