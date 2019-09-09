package com.giveindia.page;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.giveindia.base.Connection;

public class WikiHomePage extends Connection {

	WebDriver driver;

	By externalLinks = By.xpath("//span[@id='External_links']//parent::h2//following-sibling::ul/li/a");
	By elementsLinks = By.xpath("//*[@aria-describedby='periodic-table-legend']//a");

	public WikiHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public String getHomePageTitle() {
		return driver.getTitle();
	}

	public List<String> clickOnExternalLinks() {
		List<String> titles = new ArrayList<String>();

		List<WebElement> listOfLinks = driver.findElements(externalLinks);

		for (WebElement element : listOfLinks) {
			String url = element.getAttribute("href");
			verifyLinkActive(url);
			titles.add(driver.getTitle());
			driver.navigate().back();
		}

		return titles;
	}

	public static void verifyLinkActive(String linkUrl) {
		try {
			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

			httpURLConnect.setConnectTimeout(3000);

			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
			}
		} catch (Exception e) {

		}
	}

	public OxygenWikiPage clickOnPeriodicTableElementTitles(String element) {
		System.out.println("Scrioll into the view and then click on the element");
		
		WebElement webElement = driver.findElement(By.xpath("//*[@aria-labelledby='Diatomic_chemical_elements']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);

		List<WebElement> listOfLinks = driver.findElements(externalLinks);

		for (WebElement ele : listOfLinks) {
			if (ele.getAttribute("title").equalsIgnoreCase(element)) {
				ele.click();
				break;
			}
		}

		return new OxygenWikiPage(driver);
	}

}
