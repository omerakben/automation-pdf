package com.academy.techcenture;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DownloadPdf {

    WebDriver driver = null;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
    }


    @Test
    public void downloadPdf() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://automationpractice.com/index.php");
        driver.findElement(By.xpath("//a[@class='login']")).click();
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kevinlee1234@gmail.com");
        driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys("Kevin123");
        driver.findElement(By.xpath("//button[contains(.,'Sign in')]")).click();
        driver.findElement(By.xpath("//a[.='Order history and details']")).click();

        //locator to click the pdf download link
        driver.findElement(By.xpath("//a[@href='http://automationpractice.com/index.php?controller=pdf-invoice&id_order=450847']")).click();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();

    }
}
