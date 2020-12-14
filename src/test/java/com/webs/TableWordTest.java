package com.webs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TableWordTest {
	
	@Test
	public void mytest() throws InterruptedException {
String path = System.getProperty("user.dir")+"/files/hhh.pdf";
		
		System.out.println(path);
		

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/upload");
		
		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id='file-upload']")).sendKeys(path);
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[@id='file-submit']")).click();
		
		Thread.sleep(2000);

		
		System.out.println(driver.findElement(By.xpath("//*[@id='content']/div/h3")).getText());
	}


}
