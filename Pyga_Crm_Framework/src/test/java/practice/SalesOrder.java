package practice;

import java.awt.Desktop.Action;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

public class SalesOrder {


	@Test
	public void salesorderFromVendor() throws InterruptedException, Exception {
		/*utilities : property file*/
		String path = "./src/main/resources/Commondata.properties";
		FileInputStream fis= new FileInputStream(path);
		Properties p= new Properties();
		p.load(fis);
		
		
		WebDriver driver=null ;
		//String browser=p.getProperty("browser");
				String browser=System.getProperty("browser", p.getProperty("browser"));
				/* Open Browser*/
				if(browser.equalsIgnoreCase("chrome")) {
					driver= new ChromeDriver();
				}else if(browser.equalsIgnoreCase("edge")) {
					driver=new EdgeDriver();
				}else if (browser.equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
				}
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				//Login to the application
				driver.get(System.getProperty("url", p.getProperty("url")));
				driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(System.getProperty("username", p.getProperty("username")));
				driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(System.getProperty("password", p.getProperty("password")));
				driver.findElement(By.xpath("//input[@id='submitButton']")).click();
		//Click on Vendor and create a vendor
		WebElement moreele = driver.findElement(By.xpath("//td[@onmouseout=\"fnHide_Event('allMenu');\"]"));
		Actions action = new Actions(driver);
		action.moveToElement(moreele).perform();
		driver.findElement(By.xpath("//a[@name='Vendors']")).click();
		//click on create vendor
		driver.findElement(By.xpath("//img[@title='Create Vendor...']")).click();
		//Give Vendor name: Bupl

		String str=RandomString.make(2);
		String vendorname="BUPL"+str;
		driver.findElement(By.xpath("//input[@name='vendorname']")).sendKeys(vendorname);
		//give email: bupl@gmail.com
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("bupl@gmail.com");
		//address
		//Street: 342 Balasore
		driver.findElement(By.xpath("//textarea[@name='street']")).sendKeys("342 Balasore");
		//city: Balasore
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Balasore");
		//Postal code: 756040
		driver.findElement(By.xpath("//input[@id='postalcode']")).sendKeys("756040");
		//po box: Dhobasila
		driver.findElement(By.xpath("//input[@id='pobox']")).sendKeys("Dhobasila");
		// state: Odisha
		driver.findElement(By.xpath("//input[@id='state']")).sendKeys("Odisha");
		//country: India
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		//click on save button
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
		//Confirmation Vendor created
		String confirmvendor = driver.findElement(By.xpath("//span[@class='small']")).getText();
		if (confirmvendor.contains("Updated today")) {
			System.out.println("Created a Vendor");
		}
		else
			System.out.println("Failed to create a Vendor");

		//Click on Product and create a product by adding vendor
		//click on product tab
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Products']")).click();
		//click on create product
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		//Give Product name
		String productname="CI cover"+str;
		driver.findElement(By.xpath("//input[@name='productname']")).sendKeys(productname);
		//click on productactive check box
		WebElement checkbox = driver.findElement(By.xpath("//input[@name='discontinued']"));
		if (checkbox.isSelected()) {

		}
		else
			checkbox.click();
		//part number: 101
		driver.findElement(By.xpath("//input[@id='productcode']")).sendKeys("101");
		//give sales start date 2023-06-30

		//give sales start date 2023-08-21
		driver.findElement(By.xpath("//img[@id='jscal_trigger_sales_start_date']")).click();
		 String monthyear = driver.findElement(By.xpath("//td[@class='title']")).getText();
		String month = monthyear.split(",")[0].trim();
		String year = monthyear.split(",")[1].trim();
		while( !((month.equalsIgnoreCase("august")) && (year.equalsIgnoreCase("2023")))) {
			driver.findElement(By.xpath("//td[contains(text(),'›')]")).click();
			 monthyear = driver.findElement(By.xpath("//td[@class='title']")).getText();
			 month = monthyear.split(",")[0].trim();
			 year = monthyear.split(",")[1].trim();
		}
		
		//driver.findElement(By.xpath("//img[@id='jscal_trigger_sales_start_date']")).click();
		driver.findElement(By.xpath("//td[normalize-space()='21']")).click();
		//Select Manufacturer:		AltvetPet Inc.
		driver.findElement(By.xpath("//select[@name='manufacturer']")).click();
		driver.findElement(By.xpath("//select[@name='manufacturer']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
		//select product category : Hardware
		driver.findElement(By.xpath("//select[@name='productcategory']")).click();
		driver.findElement(By.xpath("//select[@name='productcategory']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
		//select vendor name
		// click on add vendor icon
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		//get window handles
		String pwh = driver.getWindowHandle();
		Set<String> allwh = driver.getWindowHandles();

		for (String string : allwh) {
			if(!(pwh.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(vendorname);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+vendorname+"']")).click();
			}
		}
		driver.switchTo().window(pwh);
		//give vendor part number:403
		driver.findElement(By.xpath("//input[@id='vendor_part_no']")).sendKeys("403");
		//give unit price: 10
		driver.findElement(By.xpath("//input[@id='unit_price']")).sendKeys("10");
		//give qty in stock:100
		driver.findElement(By.xpath("//input[@id='qtyinstock']")).sendKeys("100");
		//give qty on demand:20
		driver.findElement(By.xpath("//input[@id='qtyindemand']")).sendKeys("20");
		//click on savebutton
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
		//Confirmation Product created
		String confirmproduct = driver.findElement(By.xpath("//span[@class='small']")).getText();
		if (confirmproduct.contains("Updated today")) {
			System.out.println("Created a Product");
		}
		else
			System.out.println("Failed to create a Product");

		//Create a quote
		//Click on Qoute 
		WebElement more_ele = driver.findElement(By.xpath("//a[.='More']"));
		wait.until(ExpectedConditions.visibilityOf(more_ele));
		action.moveToElement(more_ele).perform();
		driver.findElement(By.xpath("//a[@name='Quotes']")).click();
		// click on create button
		driver.findElement(By.xpath("//img[@title='Create Quote...']")).click();
		//give subject
		String quotename = "ci cover qoute"+str;
		driver.findElement(By.xpath("//input[@name='subject']")).sendKeys(quotename);
		//give valid till date
		driver.findElement(By.xpath("//img[@id='jscal_trigger_validtill']")).click();
		driver.findElement(By.xpath("//td[normalize-space()='30']")).click();
		//select the organization: Shruthi services

		String orgname="Shruthi services";
		driver.findElement(By.xpath("//td[text()='Organization Name 			']/..//img")).click();
		String pwh1 = driver.getWindowHandle();
		Set<String> allwh1 = driver.getWindowHandles();

		for (String string : allwh1) {
			if(!(pwh1.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(orgname);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+orgname+"']")).click();
				driver.switchTo().alert().accept();
			}
		}
		driver.switchTo().window(pwh1);
		//click on add product
		driver.findElement(By.xpath("//img[@id='searchIcon1']")).click();
		String pwh2 = driver.getWindowHandle();
		Set<String> allwh2 = driver.getWindowHandles();

		for (String string : allwh2) {
			if(!(pwh2.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(productname);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+productname+"']")).click();
			}
		}
		driver.switchTo().window(pwh2);
		//Give qty: 10
		driver.findElement(By.xpath("//input[@id='qty1']")).sendKeys("10");
		//click on savebutton
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
		//Confirmation Quote created
		String confirmquote = driver.findElement(By.xpath("//span[@class='small']")).getText();
		if (confirmquote.contains("Updated today")) {
			System.out.println("Created The Quote");
		}
		else
			System.out.println("Failed to create a Quote");

		//Create a Sales order
		//Click on sales order
		WebElement more_ele1 = driver.findElement(By.xpath("//a[.='More']"));
		wait.until(ExpectedConditions.visibilityOf(more_ele1));
		action.moveToElement(more_ele1).perform();
		driver.findElement(By.xpath("//a[@name='Sales Order']")).click();
		//click on create button
		driver.findElement(By.xpath("//img[@title='Create Sales Order...']")).click();
		//give subject:
		String salesordersub = productname+"salesorder";
		driver.findElement(By.xpath("//input[@name='subject']")).sendKeys(salesordersub);
		//click on quote and select
		driver.findElement(By.xpath("//img[@onclick='selectQuote()']")).click();
		String pwh3 = driver.getWindowHandle();
		Set<String> allwh3 = driver.getWindowHandles();

		for (String string : allwh3) {
			if(!(pwh3.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(quotename);
				driver.findElement(By.xpath("//select[@name='search_field']")).click();
				driver.findElement(By.xpath("//select[@name='search_field']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+quotename+"']")).click();
			}
		}
		driver.switchTo().window(pwh3);

		//click on add organization and select
		driver.findElement(By.xpath("//td[text()='Organization Name 			']/..//img")).click();
		String pwh4 = driver.getWindowHandle();
		Set<String> allwh4 = driver.getWindowHandles();

		for (String string : allwh4) {
			if(!(pwh4.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(orgname);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+orgname+"']")).click();
				driver.switchTo().alert().accept();
			}
		}
		driver.switchTo().window(pwh4);
		//click on savebutton
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
		//Confirmation Vendor created
		String confirmso = driver.findElement(By.xpath("//span[@class='small']")).getText();
		if (confirmso.contains("Updated today")) {
			System.out.println("Created The Sales Order");
		}
		else
			System.out.println("Failed to create a Sales order");
		
		//logout
		WebElement icon=driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		action.moveToElement(icon).perform();
		driver.findElement(By.xpath("//a[normalize-space()='Sign Out']")).click();


		Thread.sleep(5000);
		driver.quit();
		System.out.println("Sales Order Completed");
	}
}
