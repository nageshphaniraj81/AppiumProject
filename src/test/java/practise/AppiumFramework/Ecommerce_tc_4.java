package practise.AppiumFramework;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.CheckoutPage;
import pageObjects.FormPage;
import pageObjects.ItemSelectionPage;

public class Ecommerce_tc_4 extends base {

	@BeforeTest
	public void killAllNodes() throws IOException, InterruptedException {
		// taskkill /F /IM node.exe
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}

	@Test
	public void totalValidation() throws IOException, InterruptedException {

		// 15 -General-Store.apk4.0
		service = startServer();

		// Initiate Driver
		AndroidDriver<AndroidElement> driver = capabilities("GeneralStoreApp");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Creating page objects
		FormPage formPage = new FormPage(driver);
		CheckoutPage checkOutPage = new CheckoutPage(driver);
		ItemSelectionPage itemSelectionPage = new ItemSelectionPage(driver);

		// Creating utilities object
		Utilities u = new Utilities(driver);

		// Enter values in the home page
		formPage.getNameField().sendKeys("hello");
		driver.hideKeyboard();
		formPage.femaleOption.click();
		formPage.getcountrySelection().click();

		// Select items in the product list page
		u.scrollToText("Argentina");
		itemSelectionPage.country.click();
		itemSelectionPage.letsShop.click();
		itemSelectionPage.addToCart.get(0).click();
		itemSelectionPage.addToCart.get(0).click();
		itemSelectionPage.cart.click();

		// sync
		Thread.sleep(4000);

		// Price validation module
		validatePrice(checkOutPage);
		service.stop();

	}

	private void validatePrice(CheckoutPage checkOutPage) {
		int count = checkOutPage.productList.size();
		double sum = 0;

		for (int i = 0; i < count; i++) {
			String amount1 = checkOutPage.productList.get(i).getText();
			double amount = getAmount(amount1);
			sum = sum + amount;// 280.97+116.97.
		}

		System.out.println(sum + "sum of products");
		String total = checkOutPage.totalAmount.getText();
		total = total.substring(1);
		double totalValue = Double.parseDouble(total);
		System.out.println(totalValue + "Total value of products");
		Assert.assertEquals(sum, totalValue);
	}

	public static double getAmount(String value) {
		value = value.substring(1);
		double amount2value = Double.parseDouble(value);
		return amount2value;

	}

}
