package com.webs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TableWordTest {

	public static void main(String[] args) {
		
		//variables
		String wordToFindInItemTitles = "Table";
		int numberOfSearchResultPages = 0;
		int numberOfItemsInSearchResult = 0;
		int NumberOfTitlesWithTableWord = 0;
		int NumberOfTitlesWithoutTableWord = 0;
		
		// driver initialization and implicit wait
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.webstaurantstore.com/");
		
		// send "stainless work table" to search bar
		WebElement searchField = driver.findElement(By.id("searchval"));
		searchField.sendKeys("stainless work table");

		// click on search button
		WebElement searchButton = driver.findElement(By.xpath("//form[@id='searchForm']//button"));
		searchButton.click();

		//page numbers with search results
		List<WebElement> pageNumbers = driver.findElements(By.cssSelector("li.rc-pagination-item"));
		
		//items appearing in search result
		List<WebElement> searchResultItems;

		//loop through items in every page to check 'Table' word in title
		for (int i = 1; i <= pageNumbers.size(); i++) {
			searchResultItems = driver.findElements(By.xpath("//a[@data-testid='itemDescription']"));
			numberOfSearchResultPages++;

			for (WebElement item : searchResultItems) {
				if (item.getText().contains(wordToFindInItemTitles)) {
					NumberOfTitlesWithTableWord++;
					numberOfItemsInSearchResult++;
					continue;
				} else {
					NumberOfTitlesWithoutTableWord++;
					numberOfItemsInSearchResult++;
					System.out.println(NumberOfTitlesWithoutTableWord + ". " + "Item without 'Table' in title"
							+ " (Page: " + numberOfSearchResultPages + "). Item name: " + item.getText());
				}
			}

			try {
				jsClick(driver, pageNumbers.get(i));
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
			pageNumbers = driver.findElements(By.cssSelector("li.rc-pagination-item"));
		}
		
		// add last of the found items to the cart 
		List<WebElement> addToCart = driver.findElements(By.name("addToCartButton"));

		for (int i = 0; i < addToCart.size(); i++) {
			if (i == addToCart.size() - 1) {
				jsClick(driver, addToCart.get(i));
			}
		}

		// empty the cart and confirm it
		WebElement viewCart = driver.findElement(By.xpath("//div[@id='watnotif-wrapper']//a[text()='View Cart']"));
		viewCart.click();

		WebElement emptyCart = driver.findElement(By.xpath("//a[text()='Empty Cart']"));
		jsClick(driver, emptyCart);

		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement confirmEmptyCart = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Empty Cart']")));
		confirmEmptyCart.click();

		
		//print findings on the console
		System.out.println();

		System.out.println("Number of pages with search results: " + numberOfSearchResultPages);
		System.out.println("Number of items in search results: " + numberOfItemsInSearchResult);
		System.out.println("Number of titles with 'Table' word: " + NumberOfTitlesWithTableWord);
		System.out.println("Number of titles without 'Table' word: " + NumberOfTitlesWithoutTableWord);

		driver.quit();
	}

	
	// JavaScript click method
	public static void jsClick(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

}
