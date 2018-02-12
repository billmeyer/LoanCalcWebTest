package com.saucelabs.billmeyer;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public abstract class LoanCalcBaseTest
{
    protected static final String baseUrl = "http://billmeyer.io/loancalc/";
    protected StringBuffer verificationErrors = new StringBuffer();

    protected String userName = System.getenv("SAUCE_USERNAME");
    protected String accessKey = System.getenv("SAUCE_ACCESS_KEY");

    protected void testLoanCalc(WebDriver driver)
    {
        try
        {
            driver.get(baseUrl);

            // clear all the input fields...
            driver.findElement(By.id("loanAmount")).clear();
            driver.findElement(By.id("interest")).clear();
            driver.findElement(By.id("salesTax")).clear();
            driver.findElement(By.id("term")).clear();
            driver.findElement(By.id("downPayment")).clear();
            driver.findElement(By.id("tradeIn")).clear();
            driver.findElement(By.id("fees")).clear();

            // populate all the input fields with test data...
            driver.findElement(By.id("loanAmount")).sendKeys("15670.23");
            driver.findElement(By.id("interest")).sendKeys("6.75");
            driver.findElement(By.id("salesTax")).sendKeys("7.75");
            driver.findElement(By.id("term")).sendKeys("5");
            driver.findElement(By.id("downPayment")).sendKeys("750.00");
            driver.findElement(By.id("tradeIn")).sendKeys("3500.00");
            driver.findElement(By.id("fees")).sendKeys("350.00");

            driver.findElement(By.id("calculate")).click();

            String loanTotal = driver.findElement(By.id("loanTotal")).getAttribute("value");
            String monthlyPayment = driver.findElement(By.id("monthlyPayment")).getAttribute("value");
            String totalInterest = driver.findElement(By.id("totalInterest")).getAttribute("value");
            String totalCost = driver.findElement(By.id("totalCost")).getAttribute("value");

            Assert.assertEquals("$15,014.65", loanTotal);
            Assert.assertEquals("$250.24", monthlyPayment);
            Assert.assertEquals("$2,301.23", totalInterest);
            Assert.assertEquals("$19,264.65", totalCost);
        }
        catch (WebDriverException e)
        {
            e.printStackTrace();
        }
    }

    protected void log(LoanCalcBaseTest instance, String output)
    {
        System.out.printf("[%s][%s] %s\n", instance.getClass().getSimpleName(), Thread.currentThread().getName(), output);
    }
}
