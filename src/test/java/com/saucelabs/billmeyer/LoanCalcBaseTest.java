package com.saucelabs.billmeyer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

public abstract class LoanCalcBaseTest
{
    protected static final String baseUrl = "http://billmeyer.io/loancalc/";
    protected StringBuffer verificationErrors = new StringBuffer();

    protected String userName = System.getenv("SAUCE_USERNAME");
    protected String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    protected String toAccessKey = System.getenv("TESTOBJECT_API_KEY");

    protected boolean testLoanCalc(RemoteWebDriver driver)
    {
        try
        {
            driver.get(baseUrl);

            ///////////////////////////////////////////////////////////////////
            // clear all the input fields...
            driver.findElement(By.id("loanAmount")).clear();
            driver.findElement(By.id("interest")).clear();
            driver.findElement(By.id("salesTax")).clear();
            driver.findElement(By.id("term")).clear();
            driver.findElement(By.id("downPayment")).clear();
            driver.findElement(By.id("tradeIn")).clear();
            driver.findElement(By.id("fees")).clear();

            ///////////////////////////////////////////////////////////////////
            // populate all the input fields with test data...
            driver.findElement(By.id("loanAmount")).sendKeys("15670.23");
            driver.findElement(By.id("interest")).sendKeys("6.75");
            driver.findElement(By.id("salesTax")).sendKeys("7.75");
            driver.findElement(By.id("term")).sendKeys("5");
            driver.findElement(By.id("downPayment")).sendKeys("750.00");
            driver.findElement(By.id("tradeIn")).sendKeys("3500.00");
            driver.findElement(By.id("fees")).sendKeys("350.00");

            ///////////////////////////////////////////////////////////////////
            // Calculate the loan...
            driver.findElement(By.id("calculate")).click();

            ///////////////////////////////////////////////////////////////////
            // Read the computed terms of the loan...
            String loanTotal = driver.findElement(By.id("loanTotal")).getAttribute("value");
            String monthlyPayment = driver.findElement(By.id("monthlyPayment")).getAttribute("value");
            String totalInterest = driver.findElement(By.id("totalInterest")).getAttribute("value");
            String totalCost = driver.findElement(By.id("totalCost")).getAttribute("value");

            ///////////////////////////////////////////////////////////////////
            // Verify assumptions...
            Assert.assertEquals("$15,014.65", loanTotal);
            Assert.assertEquals("$250.24", monthlyPayment);
            Assert.assertEquals("$2,301.23", totalInterest);
            Assert.assertEquals("$19,264.65", totalCost);

            return true;
        }
        catch (WebDriverException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void reportSauceLabsResult(WebDriver driver, boolean status)
    {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + status);
    }

    /**
     * Uses the Appium V2 RESTful API to report test result status to the Sauce Labs dashboard.
     *
     * @param sessionId The session ID we want to set the status for
     * @param status    TRUE if the test was successful, FALSE otherwise
     * @see https://api.testobject.com/#!/Appium_Watcher_API/updateTest
     */
    public void reportTestObjectResult(String sessionId, boolean status)
    {
        // The Appium REST Api expects JSON payloads...
        MediaType[] mediaType = new MediaType[]{MediaType.APPLICATION_JSON_TYPE};

        // Construct the new REST client...
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target("https://app.testobject.com/api/rest/v2/appium");

        // Construct the REST body payload...
        Entity entity = Entity.json(Collections.singletonMap("passed", status));

        // Build a PUT request to /v2/appium/session/{:sessionId}/test
        Invocation.Builder request = resource.path("session").path(sessionId).path("test").request(mediaType);

        // Execute the PUT request...
        request.put(entity);
    }

    public void log(LoanCalcBaseTest instance, String format, Object... args)
    {
        String mergedFormat = "[%s][%s] " + format;
        Object[] mergedArgs = new Object[args.length + 2];
        mergedArgs[0] = Thread.currentThread().getName();
        mergedArgs[1] = instance.getClass().getSimpleName();
        System.arraycopy(args, 0, mergedArgs, 2, args.length);

        System.out.printf(mergedFormat, mergedArgs);
    }

    protected void log(LoanCalcBaseTest instance, String output)
    {
        System.out.printf("[%s][%s] %s\n", Thread.currentThread().getName(), instance.getClass().getSimpleName(), output);
    }
}
