package com.nextory.test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import static io.appium.java_client.AppiumBy.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.net.MalformedURLException;
import java.net.URL;


public class NextoryLoginTest {
    private AndroidDriver driver;


    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        //Device setup & Automation configuration
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Oppo A57"); // or use "69acdef3" as a fallback
        caps.setCapability(MobileCapabilityType.UDID, "69acdef3");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12");

        caps.setCapability("appPackage", "com.gtl.nextory");
        caps.setCapability("appActivity", "fr.youboox.app.MainActivity");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        caps.setCapability("noReset", true);
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("skipDeviceInitialization", true);
        caps.setCapability("skipServerInstallation", false);
        caps.setCapability("ignoreHiddenApiPolicyError", true);

        //Initialize driver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

       /* driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));*/
    }



        @Test
        public void testLoginAndSearchBook() throws InterruptedException {
            // Test Flow 1: Tap “Log in” button
            driver.findElement(By.xpath("//android.widget.TextView[@text='Log in']")).click();
            System.out.println("✔ Tap on Log in ");

            // Test Flow 2: Enter email
            driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"LoginScreen.EmailInputField\"]\n")).sendKeys("testVisitorInterview@nextory.com");
            System.out.println("✔ Email entered");

            // Test Flow 3: Enter password
            driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"LoginScreen.PasswordInputField\"]\n")).sendKeys("password");
            System.out.println("✔ Password entered");

            // Test Flow 4: Tap “Log in” again
            driver.findElement(By.xpath("//android.view.View[@resource-id=\"LoginScreen.LoginButton\"]/android.widget.Button\n")).click();
            System.out.println("✔ Login button tapped ");

            // Test Flow 5: Wait & check for “Home” tab
            WebElement homeTab = driver.findElement(By.xpath("//android.view.View[@resource-id=\"TabBar.home\"]"));
            Assert.assertTrue(homeTab.isDisplayed());
            System.out.println("✔ Home tab visible ");


            // Test Flow 6: Tap on “Search” tab
            driver.findElement(By.xpath("//android.view.View[@resource-id=\"TabBar.search\"]")).click();
            System.out.println("✔ Search tab tapped ");

            // Test Flow 7: Tap on search input field
            driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"Search.button\"]\n")).click();
            System.out.println("✔ Search input field tapped ");

            //Test Flow 8: Type "Harry Potter"
            driver.findElement(androidUIAutomator("new UiSelector().resourceId(\"SearchInputField\")")).sendKeys("harry potter");
            System.out.println("✔ 'Harry Potter' entered into search ");


            // Test Flow 9: Wait a bit
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("com.nextory.android:id/book_title")));


            // Test Flow 10: Check if first result contains “Harry Potter”
            WebElement firstResult = driver.findElement(By.xpath("//android.widget.TextView[@text='harry potter och de vises sten']"));
            String resultText = firstResult.getText().toLowerCase();
            Assert.assertTrue("First search result does not contain 'harry potter'", resultText.contains("harry potter"));
            System.out.println("✅ First search result text: " + resultText);

    }



    @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }












