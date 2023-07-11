package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

public class Invoice_234 {

	@Test
	public void createInvoiceFromVendor() throws InterruptedException, Exception {
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
		//Click on Product and create a product by adding vendor
		//click on product tab
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[.='Products']")).click();
		//click on create product
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		//Give Product name
		String str=RandomString.make(2);
		String productname="CI Pipe"+str;
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


		//create a campaign
		WebElement more1 = driver.findElement(By.xpath("//a[.='More']"));
		Actions action= new Actions(driver);
		action.moveToElement(more1).perform();
		driver.findElement(By.name("Campaigns")).click();
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();

		//campaign name
		String Campaign_name = "camp"+str;
		driver.findElement(By.name("campaignname")).sendKeys(Campaign_name);

		//campaign type
		WebElement type = driver.findElement(By.name("campaigntype"));
		type.click();
		Select s2=new Select(type);
		s2.selectByValue("Advertisement");

		//target audience
		driver.findElement(By.id("targetaudience")).sendKeys("Kids");

		//sponsor
		driver.findElement(By.id("sponsor")).sendKeys("Nestle");

		//num sent
		driver.findElement(By.id("numsent")).sendKeys("75");

		//add product
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		String pwh1 = driver.getWindowHandle();
		Set<String> allwh1 = driver.getWindowHandles();
		for(String wh1:allwh1)
		{
			driver.switchTo().window(wh1);
		}

		driver.findElement(By.id("search_txt")).sendKeys(productname);
		WebElement in1 = driver.findElement(By.xpath("//select[@name='search_field']"));
		in1.click();
		Select s3=new Select(in1);
		s3.selectByValue("productname");
		driver.findElement(By.xpath("//input[@name='search']")).click();
		driver.findElement(By.xpath("//a[.='"+productname+"']")).click();

		//back to parent window
		driver.switchTo().window(pwh1);
		driver.findElement(By.id("targetsize")).sendKeys("500");

		//budget cost
		WebElement bcost = driver.findElement(By.name("budgetcost"));
		bcost.clear();
		bcost.sendKeys("50000");

		//expected response
		WebElement res = driver.findElement(By.name("expectedresponse"));
		Select s4=new Select(res);
		s4.selectByValue("Good");

		//expected sales count
		driver.findElement(By.id("expectedsalescount")).sendKeys("5000");

		//Expected Response Count
		driver.findElement(By.id("expectedresponsecount")).sendKeys("4000");

		//actual cost
		WebElement acost = driver.findElement(By.name("actualcost"));
		acost.clear();
		acost.sendKeys("47900");

		//expected revenue
		WebElement rev = driver.findElement(By.name("expectedrevenue"));
		rev.clear();
		rev.sendKeys("100000");

		//description
		driver.findElement(By.name("description")).sendKeys("Testing description");

		//save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//verify campaign is created
		WebElement campCheck = driver.findElement(By.xpath("//span[contains(text(),'Updated today')]"));
		if(campCheck.isDisplayed()) {
			System.out.println("Campaign is created");
		}
		else {
			System.out.println("Campaign is not created");
		}

		//Create Organization
		//click on organization tab
		driver.findElement(By.linkText("Organizations")).click();
		//click on create
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		//give organization name
		String org_name = "Mohapatra"+str;
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(org_name);
		//website"
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys("www.mgrp.com");
		//Annual Revenue
		driver.findElement(By.xpath("//input[@name='annual_revenue']")).sendKeys("100000000");
		//biiling address
		driver.findElement(By.xpath("//textarea[@name='bill_street']")).sendKeys("Balasore");
		//po box
		driver.findElement(By.xpath("//textarea[@name='bill_street']")).sendKeys("dhobasila");
		//billing city
		driver.findElement(By.xpath("//input[@id='bill_city']")).sendKeys("Balasore");
		//billing state
		driver.findElement(By.xpath("//input[@id='bill_state']")).sendKeys("Odisha");
		//billing pin
		driver.findElement(By.xpath("//input[@id='bill_code']")).sendKeys("756040");
		//billing country
		driver.findElement(By.xpath("//input[@id='bill_country']")).sendKeys("India");
		//click on copy biiling address
		driver.findElement(By.xpath("//input[@onclick='return copyAddressRight(EditView)']"));
		//click on savebutton
		driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
		//verify organization is created
		WebElement org = driver.findElement(By.xpath("//span[contains(text(),'Updated today')]"));
		if(org.isDisplayed()) {
			System.out.println("Organization is created");
		}
		else {
			System.out.println("Organization is not created");
		}

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
		String pwh2 = driver.getWindowHandle();
		Set<String> allwh2 = driver.getWindowHandles();

		for (String string : allwh2) {
			if(!(pwh1.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(org_name);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+org_name+"']")).click();
				driver.switchTo().alert().accept();
			}
		}
		driver.switchTo().window(pwh2);
		//click on add product
		driver.findElement(By.xpath("//img[@id='searchIcon1']")).click();
		String pwh3 = driver.getWindowHandle();
		Set<String> allwh3 = driver.getWindowHandles();

		for (String string : allwh3) {
			if(!(pwh2.contains(string))) {
				driver.switchTo().window(string);
				driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(productname);
				driver.findElement(By.xpath("//input[@name='search']")).click();
				driver.findElement(By.xpath("//a[.='"+productname+"']")).click();
			}
		}
		driver.switchTo().window(pwh3);
		//Give qty: 10
		driver.findElement(By.xpath("//input[@id='qty1']")).sendKeys("10");
		//copy billing address
		driver.findElement(By.xpath("//input[@onclick='return copyAddressRight(EditView)']")).click();
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
				String pwh5 = driver.getWindowHandle();
				Set<String> allwh5 = driver.getWindowHandles();

				for (String string : allwh5) {
					if(!(pwh3.contains(string))) {
						driver.switchTo().window(string);
						driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(quotename);
						driver.findElement(By.xpath("//select[@name='search_field']")).click();
						driver.findElement(By.xpath("//select[@name='search_field']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
						driver.findElement(By.xpath("//input[@name='search']")).click();
						driver.findElement(By.xpath("//a[.='"+quotename+"']")).click();
					}
				}
				driver.switchTo().window(pwh5);

				//click on add organization and select
				driver.findElement(By.xpath("//td[text()='Organization Name 			']/..//img")).click();
				String pwh4 = driver.getWindowHandle();
				Set<String> allwh4 = driver.getWindowHandles();

				for (String string : allwh4) {
					if(!(pwh4.contains(string))) {
						driver.switchTo().window(string);
						driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(org_name);
						driver.findElement(By.xpath("//input[@name='search']")).click();
						driver.findElement(By.xpath("//a[.='"+org_name+"']")).click();
						driver.switchTo().alert().accept();
					}
				}
				driver.switchTo().window(pwh4);
				//copy billing address
				driver.findElement(By.xpath("//input[@onclick='return copyAddressRight(EditView)']")).click();
				//click on savebutton
				driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
				//Confirmation Sales order created
				String confirmso = driver.findElement(By.xpath("//span[@class='small']")).getText();
				if (confirmso.contains("Updated today")) {
					System.out.println("Created The Sales Order");
				}
				else
					System.out.println("Failed to create a Sales order");
				
				//Create Invoice
				WebElement more_ele2 = driver.findElement(By.xpath("//a[.='More']"));
				wait.until(ExpectedConditions.visibilityOf(more_ele2));
				action.moveToElement(more_ele2).perform();
				driver.findElement(By.xpath("//a[@name='Invoice']")).click();
				//click on create button
				driver.findElement(By.xpath("//img[@title='Create Invoice...']")).click();
				//give subject of invoice
				String inv_sub = "Invoice for "+productname;
				driver.findElement(By.xpath("//input[@name='subject']")).sendKeys(inv_sub);
				//select sales order
				driver.findElement(By.xpath("//img[@onclick='selectSalesOrder();']")).click();
				String pwh7 = driver.getWindowHandle();
				Set<String> allwh7 = driver.getWindowHandles();
				for (String string : allwh7) {
					if(!(pwh7.contains(string))) {
						driver.switchTo().window(string);
						driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(salesordersub);
						driver.findElement(By.xpath("//select[@name='search_field']")).click();
						driver.findElement(By.xpath("//select[@name='search_field']")).sendKeys(Keys.ARROW_DOWN+""+Keys.ENTER);
						driver.findElement(By.xpath("//input[@name='search']")).click();
						driver.findElement(By.xpath("//a[.='"+salesordersub+"']")).click();
						
					}
				}
				driver.switchTo().window(pwh7);
				
				//Select Organization name
				driver.findElement(By.xpath("//td[text()='Organization Name 			']/..//img")).click();
				String pwh6 = driver.getWindowHandle();
				Set<String> allwh6 = driver.getWindowHandles();

				for (String string : allwh6) {
					if(!(pwh4.contains(string))) {
						driver.switchTo().window(string);
						driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(org_name);
						driver.findElement(By.xpath("//input[@name='search']")).click();
						driver.findElement(By.xpath("//a[.='"+org_name+"']")).click();
						driver.switchTo().alert().accept();
					}
				}
				driver.switchTo().window(pwh6);
				//copy billing address
				driver.findElement(By.xpath("//input[@onclick='return copyAddressRight(EditView)']")).click();
				//click on savebutton
				driver.findElement(By.xpath("//input[@value='  Save  ']")).click();
				//Confirmation Invoice created
				String confirminv = driver.findElement(By.xpath("//span[@class='small']")).getText();
				if (confirminv.contains("Updated today")) {
					System.out.println("Created The Invoice");
				}
				else
					System.out.println("Failed to create invoice");
				
				//logout
				WebElement icon=driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
				action.moveToElement(icon).perform();
				driver.findElement(By.xpath("//a[normalize-space()='Sign Out']")).click();

				Thread.sleep(5000);
				driver.quit();
				System.out.println("Invoice Completed");
	}
}
